package com.server;
import com.client.*;
import org.jetbrains.annotations.NotNull;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
/**
 * Klasa przyjmująca dane od serwera i zezwalająca na połączenie się z bazą danych
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class BazaDanych implements Callable<String> {
    /**
     * Podstawowy konstruktor pozwalający na tworzenie nowych instancji klasy
     */
    public BazaDanych(){}
    /**
     * Konstruktor umożliwiający ustawienie operacji, którą baza danych ma wykonać
     * @param ekranSerwer Instancja klasy EkranSerwer
     * @param operation Operacja do wykonania
     * @param IP Adres IP ustawiany do połączenia z bazą danych
     */
    public BazaDanych(EkranSerwer ekranSerwer, String operation, String IP){
        this.operation = operation;
        this.ekranSerwer = ekranSerwer;
        this.IP = IP;
        setUpDataBase();
    }
    /**
     * Konstruktor umożliwiający ustawienie referencji do Instancji klasy EkranSerwer
     * @param ekranSerwer Przekazywana instancja klasy EkranSerwer
     */
    public BazaDanych(EkranSerwer ekranSerwer){
        this.dataList.addAll(ekranSerwer.panelData);
        this.IP = ekranSerwer.IP;
        this.operation = ekranSerwer.operation;
        this.ekranSerwer = ekranSerwer;
    }
    /**
     * Metoda zwracająca listę pobranych danych
     * @return Zwraca listę pobranych danych
     */
    public List<String> getPanelData(){
        return this.panelData;
    }
    /**
     * Metoda zwracająca listę pobranych danych
     * @return Zwraca listę pobranych danych
     */
    public List<String> getPanelData2(){
        return this.panelData2;
    }
    /**
     * Lista danych odbierana od serwera
     */
    public final List<String> dataList = new LinkedList<>();
    /**
     * Instancja klasy EkranSerwer
     */
    public EkranSerwer ekranSerwer;
    /**
     * Atrybut informujący o tym, jaka operacja ma zostać wykonana
     */
    private String operation;
    /**
     * Atrybut będący adresem IP używanym do połączenia się serwera z bazą danych
     */
    private String IP;
    /**
     * Atrybut pozwalający na wykonywanie zapytań do bazy danych
     */
    public Statement state;
    /**
     * Atrybut pozwalający na podłączenie się do bazy danych
     */
    public Connection connect;
    /**
     * Atrybut przechowujący nickname użytkownika
     */
    protected String nick;
    /**
     * Atrybut przechowujący hasło użytkownika
     */
    protected String pass;
    /**
     * Atrybut przechowujący pierwsze imię użytkownika
     */
    protected String name;
    /**
     * Atrybut przechowujący wygenerowany nowy klucz do odzyskiwania konta
     */
    protected static String key;
    /**
     * Lista zwracanych pobranych danych
     */
    public List<String> panelData = new LinkedList<>();
    /**
     * Lista zwracanych pobranych danych
     */
    public List<String> panelData2 = new LinkedList<>();
    /**
     * Metoda obsługująca postępowanie w razie wystąpienia wyjątku
     * @param ex Otrzymany wyjątek
     */
    public void catchServe(@NotNull Exception ex){
        ekranSerwer.setMessage(ex.getMessage());
        new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "BazaDanych", "error");
    }
    /**
     * Jest to metoda umożliwająca zatwierdzenie wykonanych zmian w bazie danych
     */
    public void commitQuery(){
        try{
            String commit_query = "COMMIT";
            state.executeUpdate(commit_query);
        }
        catch(SQLException ex){
            ekranSerwer.setMessage(ex.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "BazaDanych","error");
        }
    }
    /**
     * Jest to metoda pozwalająca na zainicjowanie parametrow polaczenia z baza danych
     * @return Zwraca informację o tym, czy podłączenie się udało, czy nie
     */
    public String setUpDataBase(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection("jdbc:oracle:thin:@"+IP+":1521:xe", "system", "admin");
            ekranSerwer.setMessage("Połączono z bazą danych");
            state = connect.createStatement();
            switch (operation) {
                case "GetClientID", "GetDVDID", "LogoutAll", "DeleteReservation", "CheckReservations", "DownloadNickname", "AdminNotifications", "UserNotifications" ->
                        wymianaDanych(operation);
                case "Register", "Login", "Logout", "DataPass", "DataReceive", "UpdateCount" ->
                        wymianaDanych(ekranSerwer.getPassOperation());
                case "Management" ->
                        zarzadzajBaza(ekranSerwer.getManagementOperation());
                case "Recovery" ->
                        odzyskiwanieDanych();
            }
            connect.close();
            return "Success";
        }
        catch (Exception except) {
            ekranSerwer.setMessage(except.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + except.getMessage(), "BazaDanych", "fatal");
            return except.getMessage();
        }
    }
    /**
     * Jest to metoda pozwalająca na przerwanie działania w trakcie zbyt długiego oczekiwania
     * @return Zwraca rezultat metody setUpDataBase
     */
    @Override
    public String call() {
        return setUpDataBase();
    }
    /**
     * Jest to metoda odpowiadająca za wybór odpowiedniej metody do wymiany danych z bazą
     * @param operation Jest to parametr przechowujący typ operacji wykonywanej na bazie danych
     */
    public void wymianaDanych(@NotNull String operation) {
        try {
            this.nick = ekranSerwer.nick;
            this.pass = ekranSerwer.pass;
            switch (operation) {
                case "ReviewDVDCollection", "DVDAvalaible", "DVDWareHouseAvalaible" -> reviewDVDCollection(operation);
                case "ReviewMyRents", "ReviewRents" -> reviewMyRents();
                case "ReviewMyReturns", "ReviewReturns" -> reviewMyReturns();
                case "Register" -> register();
                case "Login" -> login();
                case "Logout" -> logout("oneUser");
                case "LogoutAll" -> logout("allUsers");
                case "AddDVD" -> addDVD();
                case "EditDVD" -> editDVD();
                case "DeleteDVD" -> deleteDVD();
                case "AddSameDVDs" -> addSameDVDs();
                case "ReviewClients" -> reviewClients();
                case "DeleteClient" -> deleteClient();
                case "EditClient" -> editClient();
                case "RentDVD" -> rentDVD();
                case "ReturnDVD" -> returnDVD();
                case "ReviewReservations" -> reviewReservations();
                case "ReservateDVD" -> reservateDVD();
                case "NewBill" -> newBill();
                case "ReviewBills" -> reviewBills();
                case "GetClientID" -> getClientID();
                case "GetDVDID" -> getDVDID();
                case "UpdateCount" -> updateCount();
                case "DeleteReservation" -> deleteReservation();
                case "CheckReservations" -> checkReservations();
                case "DownloadNickname" -> downloadNickname();
                case "AdminNotifications" -> adminNotifications();
                case "UserNotifications" -> userNotifications();
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    /**
     * Metoda pobierająca z bazy dane o rachunkach wskazanego użytkownika
     */
    private void reviewBills(){
        try{
            panelData.clear();
            String query = "SELECT bills.bill_id, " +
                    "users_data.user_name, " +
                    "bills.nip, " +
                    "bills.regon, " +
                    "bills.pesel," +
                    "bills.kwota, " +
                    "bills.data " +
                    "FROM bills " +
                    "JOIN users_data ON users_data.user_id = bills.user_id";
            ResultSet result = state.executeQuery(query);
            while(result.next()){
                panelData.add(Integer.toString(result.getInt("bill_id")));
                panelData.add(result.getString("user_name"));
                panelData.add(result.getString("nip"));
                panelData.add(result.getString("regon"));
                panelData.add(result.getString("pesel"));
                panelData.add(result.getString("kwota"));
                panelData.add(result.getString("data"));
            }
            result.close();
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis danych tworzonego rachunku w bazie
     */
    private void newBill(){
        try {
            String query = "INSERT INTO bills (bill_id,user_id,NIP,REGON,PESEL,Kwota,Data) " +
                    "VALUES(bill_id_seq.nextval,?,?,?,?,?,SYSDATE)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            int i=1;
            for(String buffer: ekranSerwer.panelData){
                if(i<2) preparedStatement.setInt(i,Integer.parseInt(buffer));
                else preparedStatement.setString(i,buffer);
                i++;
            }
            preparedStatement.executeUpdate();
            ekranSerwer.message = "Pomyślnie wystawiono rachunek!";
            new Logs("[ " + new java.util.Date() + " ] " + "Added new Bill" , "BazaDanych", "info");
            commitQuery();
        }
        catch (SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pobierająca z bazy dane dotyczące rezerwacji wskazanego użytkownika
     */
    private void reviewReservations(){
        try{
            panelData.clear();
            String query = "SELECT reservations.reservation_id, " +
                    "users.user_nickname, " +
                    "dvds_data.film_name " +
                    "FROM reservations " +
                    "JOIN users ON users.user_id = reservations.user_id " +
                    "JOIN dvds_data ON dvds_data.dvd_id = reservations.dvd_id";
            ResultSet result = state.executeQuery(query);
            while(result.next()){
                panelData.add(result.getString("user_nickname"));
                panelData.add(result.getString("film_name"));
            }
            result.close();
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pobierająca z bazy dane dotyczące zwrotów wskazanego użytkownika
     */
    private void reviewMyReturns(){
        try{
            panelData.clear();
            String query = "SELECT returns.return_id, " +
                    "dvds_data.film_name, " +
                    "dvds_data.film_direction, " +
                    "dvds_data.film_type, " +
                    "dvds_data.country, " +
                    "dvds_data.production_year, " +
                    "dvds_data.film_language, " +
                    "dvds_data.video_length, " +
                    "returns.rent_date, " +
                    "returns.return_date " +
                    "FROM returns " +
                    "JOIN dvds_data ON dvds_data.dvd_id = returns.dvd_id " +
                    "JOIN users ON users.user_id = returns.user_id " +
                    "WHERE dvds_data.dvd_id = returns.dvd_id " +
                    "AND returns.user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(ekranSerwer.userID));
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                panelData.add(Integer.toString((result.getInt("return_id"))));
                panelData.add(result.getString("film_name"));
                panelData.add(result.getString("film_direction"));
                panelData.add(result.getString("film_type"));
                panelData.add(result.getString("country"));
                panelData.add(result.getString("production_year"));
                panelData.add(result.getString("film_language"));
                panelData.add(result.getString("video_length"));
                panelData.add((result.getDate("rent_date")).toString());
                panelData.add((result.getDate("return_date")).toString());
            }
            result.close();
            preparedStatement.close();
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pobierająca z bazy dane dotyczące wypożyczeń wskazanego użytkownika
     */
    private void reviewMyRents(){
        try{
            panelData.clear();
            String query = "SELECT rents.rent_id, " +
                    "dvds_data.film_name, " +
                    "dvds_data.film_direction, " +
                    "dvds_data.film_type, " +
                    "dvds_data.country, " +
                    "dvds_data.production_year, " +
                    "dvds_data.film_language, " +
                    "dvds_data.video_length, " +
                    "rents.rent_date " +
                    "FROM rents " +
                    "JOIN dvds_data ON dvds_data.dvd_id = rents.dvd_id " +
                    "JOIN users ON users.user_id = rents.user_id " +
                    "WHERE dvds_data.dvd_id = rents.dvd_id " +
                    "AND rents.user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(ekranSerwer.userID));
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                panelData.add(Integer.toString((result.getInt("rent_id"))));
                panelData.add(result.getString("film_name"));
                panelData.add(result.getString("film_direction"));
                panelData.add(result.getString("film_type"));
                panelData.add(result.getString("country"));
                panelData.add(result.getString("production_year"));
                panelData.add(result.getString("film_language"));
                panelData.add(result.getString("video_length"));
                panelData.add((result.getDate("rent_date")).toString());
            }
            result.close();
            preparedStatement.close();
        }
        catch (Exception ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy danych dodawanego zwrotu płyty DVD
     */
    private void returnDVD(){
        try {
            String query = "INSERT INTO returns(return_id,user_id,dvd_id,rent_date,return_date) " +
                    "VALUES(return_id_seq.nextval,?,?,?,SYSDATE)";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date bufferDate = dateFormat.parse(ekranSerwer.panelData.get(3));
            java.sql.Date rentDate = new java.sql.Date(bufferDate.getTime());
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(ekranSerwer.panelData.get(0)));
            preparedStatement.setInt(2,Integer.parseInt(ekranSerwer.panelData.get(1)));
            preparedStatement.setDate(3,rentDate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            String query2 = "DELETE FROM rents WHERE rent_id = ?";
            PreparedStatement preparedStatement1 = connect.prepareStatement(query2);
            preparedStatement1.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(2)));
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "User " + ekranSerwer.panelData.get(0) + " Returned DVD " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
            ekranSerwer.message = "Pomyślnie zwrócono DVD!";
        }
        catch (SQLException | ParseException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy danych dodawanego wypożyczenia płyty DVD
     */
    private void rentDVD(){
        try {
            String query = "INSERT INTO rents(rent_id,user_id,dvd_id,rent_date) " +
                    "VALUES(rent_id_seq.nextval,?,?,SYSDATE)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            int i=1;
            for(String buffer: ekranSerwer.panelData){
                preparedStatement.setString(i,buffer);
                i++;
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            ekranSerwer.message = "Pomyślnie wypożyczono płytę DVD!";
            new Logs("[ " + new java.util.Date() + " ] " + "User " + ekranSerwer.panelData.get(0) + " Rented DVD " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
        }
        catch (SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy nowych danych wybranego użytkownika do edycji
     */
    private void editClient(){
        try{
            String query = "UPDATE users_data " +
                    "SET user_name = ?, " +
                    "user_surname = ?, " +
                    "user_email = ?, " +
                    "user_phone_number = ? " +
                    "WHERE user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.panelData.get(0));
            preparedStatement.setString(2, ekranSerwer.panelData.get(1));
            preparedStatement.setString(3, ekranSerwer.panelData.get(2));
            preparedStatement.setInt(4,Integer.parseInt(ekranSerwer.panelData.get(3)));
            preparedStatement.setInt(5,Integer.parseInt(ekranSerwer.panelData.get(6)));
            preparedStatement.executeUpdate();
            String query2 = "UPDATE users " +
                    "SET user_nickname = ?, " +
                    "user_password = ? " +
                    "WHERE user_id = ?";
            PreparedStatement preparedStatement2 = connect.prepareStatement(query2);
            preparedStatement2.setString(1, ekranSerwer.panelData.get(4));
            preparedStatement2.setString(2, ekranSerwer.panelData.get(5));
            preparedStatement2.setInt(3,Integer.parseInt(ekranSerwer.panelData.get(6)));
            preparedStatement2.executeUpdate();
            preparedStatement2.close();
            preparedStatement.close();
            ekranSerwer.message = "Pomyślnie edytowano wybranego klienta!";
            new Logs("[ " + new java.util.Date() + " ] " + "Edited client " + ekranSerwer.panelData.get(6), "BazaDanych", "info");
            commitQuery();
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na usunięcie z bazy danych wskazanego użytkownika
     */
    private void deleteClient(){
        try{
            String query = "DELETE FROM users_data " +
                    "WHERE user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(ekranSerwer.panelData.get(0)));
            String query2 = "DELETE FROM users " +
                    "WHERE user_id = ?";
            PreparedStatement preparedStatement2 = connect.prepareStatement(query2);
            preparedStatement2.setInt(1,Integer.parseInt(ekranSerwer.panelData.get(0)));
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Deleted client " + ekranSerwer.panelData.get(0), "BazaDanych", "info");
            ekranSerwer.message = "Pomyślnie usunięto zaznaczonego klienta!";
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na pobranie danych do przeglądu użytkowników wypożyczalnii
     */
    private void reviewClients(){
        try{
            panelData.clear();
            String query = "SELECT * FROM users_data";
            ResultSet result = state.executeQuery(query);
            while(result.next()){
                panelData.add(Integer.toString(result.getInt("user_id")));
                panelData.add(result.getString("user_name"));
                panelData.add(result.getString("user_surname"));
                panelData.add(result.getString("user_email"));
                panelData.add(result.getString("user_phone_number"));
            }
            result.close();
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy danych nowej liczby egzemplarzy wskazanej płyty DVD
     */
    private void addSameDVDs(){
        try{
            String query = "UPDATE dvds_data " +
                    "SET number_of_copies = ? " +
                    "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(ekranSerwer.panelData.get(0)));
            preparedStatement.setInt(2,Integer.parseInt(ekranSerwer.panelData.get(1)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Changed count of DVD " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
            ekranSerwer.message = "Pomyślnie zmieniono liczbę DVD!";
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na pobranie danych do przeglądu kolekcji DVD
     */
    private void reviewDVDCollection(String option){
        try{
            panelData.clear();
            String query = "SELECT * FROM dvds_data";
            ResultSet result = state.executeQuery(query);
            while(result.next()){
                if(option.equals("DVDWareHouseAvalaible")){
                    panelData.add(result.getString("film_name"));
                    panelData.add(result.getString("number_of_copies"));
                }
                else {
                    panelData.add(result.getString(1));
                    panelData.add(result.getString(2));
                    panelData.add(result.getString(3));
                    panelData.add(result.getString(4));
                    panelData.add(result.getString(5));
                    panelData.add(result.getString(6));
                    panelData.add(result.getString(7));
                    panelData.add(result.getString(8));
                    panelData.add(result.getString(9));
                    panelData.add(result.getString(10));
                }
            }
            result.close();
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na usunięcie z bazy danych wskazanej płyty DVD
     */
    private void deleteDVD(){
        try{
            String query = "DELETE FROM dvds_data " +
                    "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(ekranSerwer.panelData.get(0)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            ekranSerwer.message = "Pomyślnie usunięto wybrane DVD!";
            ekranSerwer.panelData.clear();
            new Logs("[ " + new java.util.Date() + " ] " + "Deleted DVD " +  ekranSerwer.panelData.get(0), "BazaDanych", "info");
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na usunięcie z bazy danych wskazanej rezerwacji
     */
    private void deleteReservation(){
        try{
            String query = "DELETE FROM reservations " +
                    "WHERE dvd_id = ? " +
                    "AND user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, ekranSerwer.dvdID);
            preparedStatement.setInt(2,(Integer.parseInt(ekranSerwer.userID)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            new Logs("[ " + new java.util.Date() + " ] " + "Deleted reservation for DVD: " +  ekranSerwer.dvdID + " reservation by user: " + ekranSerwer.userID, "BazaDanych", "info");
        }
        catch(SQLException ex){
            catchServe(ex);
        }
        commitQuery();
    }
    /**
     * Metoda pozwalająca na zapis do bazy nowych danych wybranej płyty DVD do edycji
     */
    private void editDVD(){
        try{
            String query = "UPDATE dvds_data " +
                    "SET film_name = ?, " +
                    "film_direction = ?, " +
                    "film_type = ?, " +
                    "country = ?, " +
                    "production_year = ?, " +
                    "film_language = ?, " +
                    "video_length = ?, " +
                    "number_of_copies = ?, " +
                    "day_payment = ? " +
                    "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.panelData.get(0));
            preparedStatement.setString(2, ekranSerwer.panelData.get(1));
            preparedStatement.setString(3, ekranSerwer.panelData.get(2));
            preparedStatement.setString(4, ekranSerwer.panelData.get(3));
            preparedStatement.setString(5, ekranSerwer.panelData.get(4));
            preparedStatement.setString(6, ekranSerwer.panelData.get(5));
            preparedStatement.setString(7, ekranSerwer.panelData.get(6));
            preparedStatement.setString(8, ekranSerwer.panelData.get(7));
            preparedStatement.setString(9, ekranSerwer.panelData.get(8));
            preparedStatement.setInt(10,Integer.parseInt(ekranSerwer.panelData.get(9)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Edited DVD " + ekranSerwer.panelData.get(9), "BazaDanych", "info");
            ekranSerwer.message = "Pomyślnie edytowano wybrane DVD!";
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy danych dodawanej płyty DVD
     */
    private void addDVD(){
        try {
            String query = "INSERT INTO dvds_data (dvd_id, film_name, film_direction, film_type, country, production_year, film_language, video_length, number_of_copies, day_payment) " +
                    "VALUES(dvd_id_seq.nextval,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            int i = 1;
            for(String data:ekranSerwer.panelData){
                if(i==8) {
                    preparedStatement.setInt(i,Integer.parseInt(data));
                }
                else {
                    preparedStatement.setString(i, data);
                }
                i++;
            }
            preparedStatement.executeQuery();
            preparedStatement.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Added new DVD " + ekranSerwer.panelData.get(0) , "BazaDanych", "info");
            ekranSerwer.panelData.clear();
            ekranSerwer.message = "Pomyślnie dodano nową płytę DVD!\n";
        }
        catch (SQLException ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na pobranie ID użytkownika do wykonania nastepnego zapytania
     */
    private void getClientID(){
        String query = "SELECT user_id " +
                "FROM users " +
                "WHERE user_nickname = ?";
        try{
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.selectedNick);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                panelData.add(resultSet.getString("user_id"));
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na pobranie ID płyty DVD do wykonania nastepnego zapytania
     */
    private void getDVDID(){
        String query = "SELECT dvd_id " +
                "FROM dvds_data " +
                "WHERE film_name = ?";
        try{
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.filmName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                panelData.add(resultSet.getString("dvd_id"));
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy danych dotyczących statusu zalogowania się pojedynczego użytkownika lub wszystkich użytkowników
     */
    private void logout(@NotNull String howManyUsers){
        try {
            if(howManyUsers.equals("oneUser")) {
                new Logs("[ " + new java.util.Date() + " ] " + "Logout from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + ekranSerwer.nick, "BazaDanych", "info");
                String query = "UPDATE users " +
                        "SET user_logged = 'no' " +
                        "WHERE user_nickname = ?";
                PreparedStatement preparedstate = connect.prepareStatement(query);
                preparedstate.setString(1, ekranSerwer.nick);
                preparedstate.executeQuery();
                preparedstate.close();
            }
            else{
                String query = "UPDATE users " +
                        "SET user_logged = 'no'";
                state.executeUpdate(query);
            }
            commitQuery();
            panelData.add("Pomyślnie wylogowano!");
        }
        catch (SQLException ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na sprawdzenie poprawności wprowadzonych danych przy próbie zalogowania się na konto
     */
    private void login(){
        try {
            String rank;
            String query = "SELECT * " +
                    "FROM users " +
                    "WHERE user_nickname = ? " +
                    "AND user_password = ?";
            PreparedStatement preparedstate = connect.prepareStatement(query);
            preparedstate.setString(1, this.nick);
            preparedstate.setString(2, this.pass);
            ResultSet result = preparedstate.executeQuery();
            if (result.next()) {
                rank = result.getString("user_rank");
                String is_logged = result.getString("user_logged");
                if(is_logged.equals("no")) {
                    ekranSerwer.setMessage("Pomyslnie zalogowano! ");
                    new Logs("[ " + new java.util.Date() + " ] " + "Login from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + nick, "BazaDanych", "info");
                    String query_logged = "UPDATE users " +
                            "SET user_logged = 'yes' " +
                            "WHERE user_nickname = ? " +
                            "AND user_password = ?";
                    PreparedStatement preparedstate2 = connect.prepareStatement(query_logged);
                    preparedstate2.setString(1, this.nick);
                    preparedstate2.setString(2, this.pass);
                    preparedstate2.executeQuery();
                    preparedstate2.close();
                    if (rank.equals("admin")) {
                        ekranSerwer.setMessage("Uzytkownik to administrator");
                    } else if (rank.equals("user")) {
                        ekranSerwer.setMessage("To zwykly uzytkownik");
                    }
                    else{
                        rank = "Nieznana";
                    }
                    panelData.add("Pomyślnie zalogowano!");
                    panelData.add(rank);
                }
                else if(is_logged.equals("yes")){
                    new Logs("[ " + new java.util.Date() + " ] " + "Unsuccessfull login try from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + nick, "BazaDanych", "info");
                    panelData.add("Na podane konto ktoś już jest zalogowany!");
                    panelData.add(rank);
                }
            }
            else{
                new Logs("[ " + new java.util.Date() + " ] " + "Unsuccessfull login try from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + nick, "BazaDanych", "info");
                rank = "not_logged";
                ekranSerwer.setMessage("Nie udana proba logowania!");
                panelData.add("Wprowadzono bledne dane logowania!");
                panelData.add(rank);
            }
            preparedstate.close();
        }
        catch (SQLException ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis danych pobranych przy rejestracji nowego użytkownika do bazy
     */
    private void register(){
        try {
            String query0 = "SELECT * " +
                    "FROM users " +
                    "WHERE user_nickname = ?";
            PreparedStatement preparedstate0 = connect.prepareStatement(query0);
            preparedstate0.setString(1, dataList.get(2));
            ResultSet result = preparedstate0.executeQuery();
            if (result.next()) {
                new Logs("[ " + new java.util.Date() + " ] " + "Unsuccessfull register from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + dataList.get(0), "BazaDanych", "info");
                panelData.add("Podany uzytkownik istnieje juz w bazie danych!");
            }
            else {
                new Logs("[ " + new java.util.Date() + " ] " + "Register from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + dataList.get(0), "BazaDanych", "info");
                panelData.add("Pomyślnie zarejestrowano!");
                String query = "INSERT INTO users (user_nickname, user_password, user_coverage_key, user_id) " +
                        "VALUES (?, ?, ?, user_id_seq.nextval)";
                String query2 = "INSERT INTO users_data (user_name, user_surname, user_email, user_phone_number, user_data_id, user_id) " +
                        "VALUES (?, ?, ?, ?, user_data_id_seq.nextval, user_id_seq.currval)";
                PreparedStatement preparedstate = connect.prepareStatement(query);
                preparedstate.setString(1, dataList.get(2));
                preparedstate.setString(2, dataList.get(5));
                preparedstate.setString(3, dataList.get(6));
                preparedstate.executeUpdate();
                preparedstate.close();
                PreparedStatement preparedstate2 = connect.prepareStatement(query2);
                preparedstate2.setString(1, dataList.get(0));
                preparedstate2.setString(2, dataList.get(1));
                preparedstate2.setString(3, dataList.get(3));
                preparedstate2.setString(4, dataList.get(4));
                preparedstate2.executeUpdate();
                preparedstate2.close();
                commitQuery();
            }
            preparedstate0.close();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis danych dotyczących rezerwacji płyty DVD
     */
    private void reservateDVD(){
        try {
            String query = "INSERT INTO reservations(reservation_id,dvd_id,user_id) " +
                    "VALUES(rent_id_seq.nextval,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            int i=1;
            for(String buffer: ekranSerwer.panelData){
                preparedStatement.setInt(i,Integer.parseInt(buffer));
                i++;
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            new Logs("[ " + new java.util.Date() + " ] " + "New DVD reservation", "BazaDanych", "info");
            ekranSerwer.message = "Pomyślnie zarezerwowano płytę DVD!";
        }
        catch (SQLException ex){
            catchServe(ex);
        }
        commitQuery();
    }
    /**
     * Metoda pozwalająca na zaktualizowanie stanu wybranej płyty DVD
     */
    private void updateCount(){
        try{
            String query = "UPDATE dvds_data " +
                    "SET number_of_copies = ? " +
                    "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(ekranSerwer.updatingItem));
            preparedStatement.setInt(2, ekranSerwer.updatingID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            new Logs("[ " + new java.util.Date() + " ] " + "Changed count of DVD " + ekranSerwer.updatingID, "BazaDanych", "info");
        }
        catch (SQLException ex) {
            catchServe(ex);
        }
        commitQuery();
    }
    /**
     * Metoda pozwalająca na pobranie danych dotyczących rezerwacji z bazy
     */
    private void checkReservations(){
        try{
            String query = "SELECT * " +
                    "FROM reservations " +
                    "JOIN dvds_data ON dvds_data.dvd_id = reservations.dvd_id " +
                    "JOIN users ON users.user_id = reservations.user_id " +
                    "WHERE dvds_data.dvd_id = reservations.dvd_id " +
                    "AND users.user_id = reservations.user_id " +
                    "AND users.user_nickname = ? " +
                    "AND dvds_data.film_name = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.nick);
            preparedStatement.setString(2, ekranSerwer.filmName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                ekranSerwer.existReservation = true;
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na pobranie nickname użytkownika o wskazanym ID
     */
    private void downloadNickname(){
        try{
            String query = "SELECT users.user_nickname " +
                    "FROM users " +
                    "WHERE users.user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(ekranSerwer.userID));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                ekranSerwer.nickname = resultSet.getString("user_nickname");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda umożliwiająca pobór niezbędnych danych do wyświetlania powiadomień dla administratora
     */
    private void adminNotifications(){
        try{
            panelData.clear();
            panelData2.clear();
            ResultSet resultSet;
            String reservationQuery = "SELECT dvds_data.film_name, " +
                    "users_data.user_email " +
                    "FROM users_data " +
                    "JOIN reservations ON reservations.user_id = users_data.user_id " +
                    "JOIN dvds_data ON reservations.dvd_id = dvds_data.dvd_id " +
                    "WHERE reservations.user_id = users_data.user_id " +
                    "AND reservations.dvd_id = dvds_data.dvd_id";
            resultSet = state.executeQuery(reservationQuery);
            while(resultSet.next()){
                panelData.add(resultSet.getString(1));
                panelData.add(resultSet.getString(2));
            }
            resultSet.close();
            String detentionDVDQuery = "SELECT users.user_id, " +
                    "rents.dvd_id, " +
                    "rents.rent_date " +
                    "FROM rents " +
                    "JOIN dvds_data ON dvds_data.dvd_id = rents.dvd_id " +
                    "JOIN users ON users.user_id = rents.user_id " +
                    "WHERE EXTRACT (MONTH FROM SYSDATE) - EXTRACT(MONTH FROM rent_date)>2 " +
                    "AND dvds_data.dvd_id = rents.dvd_id " +
                    "AND users.user_id = rents.user_id";
            ResultSet resultSet2 = state.executeQuery(detentionDVDQuery);
            while(resultSet2.next()) {
                panelData2.add(Integer.toString(resultSet2.getInt(1)));
                panelData2.add(Integer.toString(resultSet2.getInt(2)));
                panelData2.add(String.valueOf(resultSet2.getDate(3)));
            }
            if(panelData.isEmpty()&&panelData2.isEmpty()) panelData.add("Brak powiadomień");
            resultSet2.close();
        }
        catch (SQLException ex) {
            panelData.add("Brak powiadomień");
            catchServe(ex);
        }
    }
    /**
     * Metoda umożliwiająca pobór niezbędnych danych do wyświetlania powiadomień dla użytkownika
     */
    private void userNotifications(){
        try{
            String detentionDVDQuery = "SELECT rents.dvd_id, dvds_data.film_name, rents.rent_date " +
                    "FROM rents " +
                    "JOIN dvds_data ON dvds_data.dvd_id = rents.dvd_id " +
                    "JOIN users ON users.user_id = rents.user_id " +
                    "WHERE EXTRACT (MONTH FROM SYSDATE) - EXTRACT(MONTH FROM rent_date)>2 " +
                    "AND dvds_data.dvd_id = rents.dvd_id " +
                    "AND users.user_nickname = ? " +
                    "AND users.user_id = rents.user_id";
            PreparedStatement preparedStatement = connect.prepareStatement(detentionDVDQuery);
            preparedStatement.setString(1,ekranSerwer.nick);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                panelData.add(Integer.toString(resultSet.getInt(1)));
                panelData.add(resultSet.getString(2));
                panelData.add(resultSet.getString(3));
            }
            if(panelData.isEmpty()) panelData.add("Brak powiadomień");
            resultSet.close();
        }
        catch (SQLException ex) {
            panelData.add("Brak powiadomień");
            catchServe(ex);
        }
    }
    /**
     * Metoda umożliwiająca na zapis zmienionego hasła do bazy i wygenerowanie nowego klucza do odzyskiwania hasła
     */
    public void odzyskiwanieDanych() {
        try {
            String query = "SELECT * " +
                    "FROM users " +
                    "WHERE user_nickname = ? " +
                    "AND user_coverage_key = ?";
            PreparedStatement preparedState = connect.prepareStatement(query);
            preparedState.setString(1,dataList.get(0));
            preparedState.setString(2,dataList.get(1));
            ResultSet resultSet = preparedState.executeQuery();
            if(resultSet.next()){
                key = Integer.toString(new EkranUtworzKonto().generujKlucz());
                String update_query = "UPDATE users " +
                        "SET user_coverage_key = ?, " +
                        "user_password = ? " +
                        "WHERE user_nickname = ? " +
                        "AND user_coverage_key = ?";
                PreparedStatement preparedStatement = connect.prepareStatement(update_query);
                preparedStatement.setString(1,key);
                preparedStatement.setString(2,dataList.get(2));
                preparedStatement.setString(3,dataList.get(0));
                preparedStatement.setString(4,dataList.get(1));
                preparedStatement.executeQuery();
                preparedStatement.close();
                commitQuery();
                new Logs("[ " + new java.util.Date() + " ] " + "Recovered password" , "BazaDanych", "info");
                panelData.add("Pomyślnie zmieniono hasło!\n\nTwój nowy klucz zapasowy to: " + key + "\n Zapisz go w bezpiecznym miejscu!");
            }
            else{
                panelData.add("Wystąpił błąd przy próbie zmiany hasła!");
            }
            preparedState.close();
        }
        catch (Exception ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda umożliwiająca wykonywanie zapytań do bazy
     * @param option Jest to parametr pomagający określić, jaka operacja na bazie ma zostać wykonana
     */
    private void zarzadzajBaza(@NotNull String option) {
        String users = "CREATE TABLE users(" +
                "user_id NUMBER(6) PRIMARY KEY, " +
                "user_nickname VARCHAR2(30), " +
                "user_password VARCHAR2(200)," +
                " user_coverage_key VARCHAR2(6) NOT NULL, " +
                "user_logged VARCHAR2(3) DEFAULT 'no', " +
                "user_rank VARCHAR2(30) DEFAULT 'user')";
        String users_data = "CREATE TABLE users_data(" +
                "user_data_id NUMBER(6) PRIMARY KEY, " +
                "user_name VARCHAR2(30), " +
                "user_surname VARCHAR2(30), " +
                "user_email VARCHAR2(30), " +
                "user_phone_number NUMBER(12), " +
                "user_id NUMBER(6) REFERENCES users(user_id))";
        String dvds_data = "CREATE TABLE dvds_data(" +
                "dvd_id NUMBER(6) PRIMARY KEY, " +
                "film_name VARCHAR2(50), " +
                "film_direction VARCHAR2(50), " +
                "film_type VARCHAR2(50), " +
                "country VARCHAR2(30), " +
                "production_year VARCHAR2(10), " +
                "film_language VARCHAR2(30), " +
                "video_length VARCHAR2(15), " +
                "number_of_copies NUMBER(6) DEFAULT '0'," +
                "day_payment VARCHAR2(30) DEFAULT '0 zl')";
        String rents = "CREATE TABLE rents(" +
                "rent_id NUMBER(6) PRIMARY KEY, " +
                "dvd_id NUMBER(6) CONSTRAINT rents_dvd_id_fk REFERENCES dvds_data(dvd_id), " +
                "user_id NUMBER(6) CONSTRAINT rents_user_id_fk REFERENCES users(user_id), " +
                "rent_date DATE)";
        String returns = "CREATE TABLE returns(" +
                "return_id NUMBER(6) PRIMARY KEY, " +
                "dvd_id NUMBER(6) CONSTRAINT returns_dvd_id_fk REFERENCES dvds_data(dvd_id), " +
                "user_id NUMBER(6) CONSTRAINT returns_user_id_fk REFERENCES users(user_id), " +
                "rent_date DATE, " +
                "return_date DATE)";
        String bills = "CREATE TABLE bills(" +
                "bill_id NUMBER(6) PRIMARY KEY, " +
                "user_id NUMBER(6) CONSTRAINT bills_user_id_fk REFERENCES users(user_id), " +
                "NIP NUMBER(10), " +
                "REGON NUMBER(9), " +
                "PESEL NUMBER(11), " +
                "Kwota NUMBER, " +
                "Data DATE)";
        String reservations = "CREATE TABLE reservations(" +
                "reservation_id NUMBER(6) PRIMARY KEY, " +
                "user_id NUMBER(6) CONSTRAINT reservations_user_id_fk REFERENCES users(user_id), " +
                "dvd_id NUMBER(6) CONSTRAINT reservations_dvd_id_fk REFERENCES dvds_data(dvd_id))";
        String sequence1 = "CREATE SEQUENCE user_id_seq START WITH 1";
        String sequence2 = "CREATE SEQUENCE user_data_id_seq START WITH 1";
        String sequence3 = "CREATE SEQUENCE dvd_id_seq START WITH 1";
        String sequence4 = "CREATE SEQUENCE rent_id_seq START WITH 1";
        String sequence5 = "CREATE SEQUENCE return_id_seq START WITH 1";
        String sequence6 = "CREATE SEQUENCE bill_id_seq START WITH 1";
        String sequence7 = "CREATE SEQUENCE reservation_id_seq START WITH 1";
        String drop_table_1 = "DROP TABLE users CASCADE CONSTRAINTS";
        String drop_table_2 = "DROP TABLE users_data CASCADE CONSTRAINTS";
        String drop_table_3 = "DROP TABLE dvds_data CASCADE CONSTRAINTS";
        String drop_table_4 = "DROP TABLE rents CASCADE CONSTRAINTS";
        String drop_table_5 = "DROP TABLE returns CASCADE CONSTRAINTS";
        String drop_table_6 = "DROP TABLE bills CASCADE CONSTRAINTS";
        String drop_table_7 = "DROP TABLE reservations CASCADE CONSTRAINTS";
        String delete_from_table_1 = "DELETE FROM users WHERE user_rank != 'admin'";
        String delete_from_table_2 = "DELETE FROM users_data";
        String delete_from_table_3 = "DELETE FROM dvds_data";
        String delete_from_table_4 = "DELETE FROM rents";
        String delete_from_table_5 = "DELETE FROM returns";
        String delete_from_table_6 = "DELETE FROM bills";
        String delete_from_table_7 = "DELETE FROM reservations";
        String drop_sequence_1 = "DROP SEQUENCE user_id_seq";
        String drop_sequence_2 = "DROP SEQUENCE user_data_id_seq";
        String drop_sequence_3 = "DROP SEQUENCE dvd_id_seq";
        String drop_sequence_4 = "DROP SEQUENCE rent_id_seq";
        String drop_sequence_5 = "DROP SEQUENCE return_id_seq";
        String drop_sequence_6 = "DROP SEQUENCE bill_id_seq";
        String drop_sequence_7 = "DROP SEQUENCE reservation_id_seq";
        try {
            switch (option) {
                case "CREATE TABLE users" -> {
                    state.executeUpdate(users);
                    ekranSerwer.message = "Pomyślnie utworzono tabele dla użytkowników!\n";
                }
                case "CREATE TABLE users_data" -> {
                    state.executeUpdate(users_data);
                    ekranSerwer.message = "Pomyślnie utworzono tabele dla danych użytkowników!\n";
                }
                case "CREATE TABLE dvds_data" -> {
                    state.executeUpdate(dvds_data);
                    ekranSerwer.message = "Pomyślnie utworzono tabele dla danych płyt DVD!\n";
                }
                case "CREATE TABLE rents" -> {
                    state.executeUpdate(rents);
                    ekranSerwer.message = "Pomyślnie utworzono tabele dla wypożyczeń płyt DVD!\n";
                }
                case "CREATE TABLE returns" -> {
                    state.executeUpdate(returns);
                    ekranSerwer.message = "Pomyślnie utworzono tabele dla zwrotów płyt DVD!\n";
                }
                case "CREATE TABLE bills" -> {
                    state.executeUpdate(bills);
                    ekranSerwer.message = "Pomyślnie utworzono tabele dla rachunków klientów!\n";
                }
                case "CREATE TABLE reservations" -> {
                    state.executeUpdate(reservations);
                    ekranSerwer.message = "Pomyślnie utworzono tabele dla rezerwacji klientów!\n";
                }
                case "DROP TABLE users" -> {
                    state.executeUpdate(drop_table_1);
                    ekranSerwer.message = "Pomyślnie usunieto tabele z użytkownikami!\n";
                }
                case "DROP TABLE users_data" -> {
                    state.executeUpdate(drop_table_2);
                    ekranSerwer.message = "Pomyślnie usunieto tabele z danymi użytkowników!\n";
                }
                case "DROP TABLE dvds_data" -> {
                    state.executeUpdate(drop_table_3);
                    ekranSerwer.message = "Pomyślnie usunieto tabele z danymi płyt DVD!\n";
                }
                case "DROP TABLE rents" -> {
                    state.executeUpdate(drop_table_4);
                    ekranSerwer.message = "Pomyślnie usunieto tabele z wypożyczeniami płyt DVD!\n";
                }
                case "DROP TABLE returns" -> {
                    state.executeUpdate(drop_table_5);
                    ekranSerwer.message = "Pomyślnie usunieto tabele ze zwrotami płyt DVD!\n";
                }
                case "DROP TABLE bills" -> {
                    state.executeUpdate(drop_table_6);
                    ekranSerwer.message = "Pomyślnie usunieto tabele z rachunkami klientów!\n";
                }
                case "DROP TABLE reservations" -> {
                    state.executeUpdate(drop_table_7);
                    ekranSerwer.message = "Pomyślnie usunieto tabele z rezerwacjami klientów!\n";
                }
                case "CREATE SEQUENCE user_id" -> {
                    state.executeUpdate(sequence1);
                    ekranSerwer.message = "Pomyślnie utworzono sekwencje ID użytkowników!\n";
                }
                case "CREATE SEQUENCE user_data_id" -> {
                    state.executeUpdate(sequence2);
                    ekranSerwer.message = "Pomyślnie utworzono sekwencje ID danych użytkowników!\n";
                }
                case "CREATE SEQUENCE dvd_id" -> {
                    state.executeUpdate(sequence3);
                    ekranSerwer.message = "Pomyślnie utworzono sekwencje ID płyt DVD!\n";
                }
                case "CREATE SEQUENCE rent_id" -> {
                    state.executeUpdate(sequence4);
                    ekranSerwer.message = "Pomyślnie utworzono sekwencje ID wypożyczeń użytkowników!\n";
                }
                case "CREATE SEQUENCE return_id" -> {
                    state.executeUpdate(sequence5);
                    ekranSerwer.message = "Pomyślnie utworzono sekwencje ID zwrotów użytkowników!\n";
                }
                case "CREATE SEQUENCE bill_id" -> {
                    state.executeUpdate(sequence6);
                    ekranSerwer.message = "Pomyślnie utworzono sekwencje ID rachunków użytkowników!\n";
                }
                case "CREATE SEQUENCE reservation_id" -> {
                    state.executeUpdate(sequence7);
                    ekranSerwer.message = "Pomyślnie utworzono sekwencje ID rezerwacji użytkowników!\n";
                }
                case "DROP SEQUENCE user_id" -> {
                    state.executeUpdate(drop_sequence_1);
                    ekranSerwer.message = "Pomyślnie usunięto sekwencje ID użytkowników!\n";
                }
                case "DROP SEQUENCE user_data_id" -> {
                    state.executeUpdate(drop_sequence_2);
                    ekranSerwer.message = "Pomyślnie usunięto sekwencje ID danych użytkowników!\n";
                }
                case "DROP SEQUENCE dvd_id" -> {
                    state.executeUpdate(drop_sequence_3);
                    ekranSerwer.message = "Pomyślnie usunięto sekwencje ID płyt DVD!\n";
                }
                case "DROP SEQUENCE rent_id" -> {
                    state.executeUpdate(drop_sequence_4);
                    ekranSerwer.message = "Pomyślnie usunięto sekwencje ID wypożyczeń użytkowników!\n";
                }
                case "DROP SEQUENCE return_id" -> {
                    state.executeUpdate(drop_sequence_5);
                    ekranSerwer.message = "Pomyślnie usunięto sekwencje ID zwrotów użytkowników!\n";
                }
                case "DROP SEQUENCE bill_id" -> {
                    state.executeUpdate(drop_sequence_6);
                    ekranSerwer.message = "Pomyślnie usunięto sekwencje ID rachunków użytkowników!\n";
                }
                case "DROP SEQUENCE reservation_id" -> {
                    state.executeUpdate(drop_sequence_7);
                    ekranSerwer.message = "Pomyślnie usunięto sekwencje ID rezerwacji użytkowników!\n";
                }
                case "DeleteFromTables" -> {
                    state.executeUpdate(delete_from_table_7);
                    state.executeUpdate(delete_from_table_6);
                    state.executeUpdate(delete_from_table_5);
                    state.executeUpdate(delete_from_table_4);
                    state.executeUpdate(delete_from_table_3);
                    state.executeUpdate(delete_from_table_2);
                    state.executeUpdate(delete_from_table_1);
                    ekranSerwer.message = "Pomyślnie usunięto wszystkie dane ze wszystkich tabel!\n";
                }
                default -> ekranSerwer.setMessage("Inna opcja w bazie danych");
            }
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Changed something in database construction" , "BazaDanych", "info");
        }
        catch (SQLException ex) {
            ekranSerwer.message = ex.getMessage();
        }
    }
}