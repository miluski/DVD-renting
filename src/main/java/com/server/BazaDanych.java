package com.server;
import com.client.*;
import org.jetbrains.annotations.NotNull;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public BazaDanych(){
    }
    /**
     * Atrybut pozwalający na wykonywanie zapytań do bazy danych
     */
    protected static Statement state;
    /**
     * Atrybut pozwalający na podłączenie się do bazy danych
     */
    protected static Connection connect;
    /**
     * Atrybut przechowujący nickname użytkownika
     */
    protected static String nick;
    /**
     * Atrybut przechowujący hasło użytkownika
     */
    protected static String pass;
    /**
     * Atrybut przechowujący pierwsze imię użytkownika
     */
    protected static String name;
    /**
     * Atrybut przechowujący nazwisko użytkownika
     */
    protected static String surname;
    /**
     * Atrybut przechowujący adres email użytkownika
     */
    protected static String email;
    /**
     * Atrybut przechowujący numer telefonu użytkownika
     */
    protected static String phone_number;
    /**
     * Atrybut przechowujący wprowadzany klucz przy odzyskiwaniu konta
     */
    protected static String coverage_key;
    /**
     * Atrybut przechowujący wygenerowany nowy klucz do odzyskiwania konta
     */
    protected static String key;
    /**
     * Metoda obsługująca postępowanie w razie wystąpienia wyjątku
     * @param ex Otrzymany wyjątek
     */
    public static void catchServe(@NotNull Exception ex){
        EkranSerwer.setMessage(ex.getMessage());
        EkranSerwer.message = ex.getMessage();
        EkranGlownyAdmin.message = ex.getMessage();
        new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "BazaDanych", "error");
    }
    /**
     * Jest to metoda umożliwająca zatwierdzenie wykonanych zmian w bazie danych
     */
    public static void commitQuery(){
        try{
            String commit_query = "COMMIT";
            state.executeUpdate(commit_query);
        }
        catch(SQLException ex){
            EkranSerwer.setMessage(ex.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "BazaDanych","error");
        }
    }
    /**
     * Jest to metoda pozwalająca na zainicjowanie parametrow polaczenia z baza danych
     */
    @Override
    public String call() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection("jdbc:oracle:thin:@"+EkranSerwer.IP+":1521:xe", "system", "admin");
            EkranSerwer.setMessage("Połączono z bazą danych");
            state = connect.createStatement();
            switch (EkranSerwer.operation) {
                case "GetClientID", "GetDVDID", "DeleteReservation", "CheckReservations", "DownloadNickname", "AdminNotifications", "UserNotifications" ->
                        new WymianaDanych(EkranSerwer.operation);
                case "Register", "Login", "Logout", "LogoutAll", "DataPass", "DataReceive", "UpdateCount" ->
                        new WymianaDanych(EkranSerwer.passOperation);
                case "Management" ->
                        new ZarzadzajBaza(EkranSerwer.managementOperation);
                case "Recovery" ->
                        new OdzyskiwanieDanych();
            }
            connect.close();
            return "Success";
        }
        catch (Exception except) {
            EkranSerwer.setMessage(except.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + except.getMessage(), "BazaDanych", "fatal");
            return except.getMessage();
        }
    }
}
/**
 * Klasa dziedzicząca atrybuty i metody z klasy BazaDanych.
 * Jej zadaniem jest wykonanie zapytania do bazy danych i pobranie od niej danych
 */
final class WymianaDanych extends BazaDanych {
    /**
     * Jest to konstruktor odpowiadający za wybór odpowiedniej metody do wymiany danych z bazą
     * @param operation Jest to parametr przechowujący typ operacji wykonywanej na bazie danych
     */
    WymianaDanych(@NotNull String operation) {
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
    /**
     * Metoda pobierająca z bazy dane o rachunkach wskazanego użytkownika
     */
    private static void reviewBills(){
        try{
            EkranSerwer.panelData.clear();
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
                EkranSerwer.panelData.add(Integer.toString(result.getInt("bill_id")));
                EkranSerwer.panelData.add(result.getString("user_name"));
                EkranSerwer.panelData.add(result.getString("nip"));
                EkranSerwer.panelData.add(result.getString("regon"));
                EkranSerwer.panelData.add(result.getString("pesel"));
                EkranSerwer.panelData.add(result.getString("kwota"));
                EkranSerwer.panelData.add(result.getString("data"));
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
    private static void newBill(){
        try {
            String query = "INSERT INTO bills (bill_id,user_id,NIP,REGON,PESEL,Kwota,Data) " +
                    "VALUES(bill_id_seq.nextval,?,?,?,?,?,SYSDATE)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            int i=1;
            for(String buffer: EkranSerwer.panelData){
                if(i<2) preparedStatement.setInt(i,Integer.parseInt(buffer));
                else preparedStatement.setString(i,buffer);
                i++;
            }
            preparedStatement.executeUpdate();
            EkranSerwer.message = "Pomyślnie wystawiono rachunek!";
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
    private static void reviewReservations(){
        try{
            EkranSerwer.panelData.clear();
            String query = "SELECT reservations.reservation_id, " +
                    "users.user_nickname, " +
                    "dvds_data.film_name " +
                    "FROM reservations " +
                    "JOIN users ON users.user_id = reservations.user_id " +
                    "JOIN dvds_data ON dvds_data.dvd_id = reservations.dvd_id";
            ResultSet result = state.executeQuery(query);
            while(result.next()){
                EkranSerwer.panelData.add(result.getString("user_nickname"));
                EkranSerwer.panelData.add(result.getString("film_name"));
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
    private static void reviewMyReturns(){
        try{
            EkranSerwer.panelData.clear();
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
            preparedStatement.setInt(1,Integer.parseInt(EkranSerwer.userID));
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                EkranSerwer.panelData.add(Integer.toString((result.getInt("return_id"))));
                EkranSerwer.panelData.add(result.getString("film_name"));
                EkranSerwer.panelData.add(result.getString("film_direction"));
                EkranSerwer.panelData.add(result.getString("film_type"));
                EkranSerwer.panelData.add(result.getString("country"));
                EkranSerwer.panelData.add(result.getString("production_year"));
                EkranSerwer.panelData.add(result.getString("film_language"));
                EkranSerwer.panelData.add(result.getString("video_length"));
                EkranSerwer.panelData.add((result.getDate("rent_date")).toString());
                EkranSerwer.panelData.add((result.getDate("return_date")).toString());
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
    private static void reviewMyRents(){
        try{
            EkranSerwer.panelData.clear();
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
            preparedStatement.setInt(1,Integer.parseInt(EkranSerwer.userID));
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                EkranSerwer.panelData.add(Integer.toString((result.getInt("rent_id"))));
                EkranSerwer.panelData.add(result.getString("film_name"));
                EkranSerwer.panelData.add(result.getString("film_direction"));
                EkranSerwer.panelData.add(result.getString("film_type"));
                EkranSerwer.panelData.add(result.getString("country"));
                EkranSerwer.panelData.add(result.getString("production_year"));
                EkranSerwer.panelData.add(result.getString("film_language"));
                EkranSerwer.panelData.add(result.getString("video_length"));
                EkranSerwer.panelData.add((result.getDate("rent_date")).toString());
            }
            result.close();
            preparedStatement.close();
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy danych dodawanego zwrotu płyty DVD
     */
    private static void returnDVD(){
        try {
            String query = "INSERT INTO returns(return_id,user_id,dvd_id,rent_date,return_date) " +
                    "VALUES(return_id_seq.nextval,?,?,?,SYSDATE)";
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
            Date bufferDate = dateFormat.parse(EkranSerwer.panelData.get(3));
            java.sql.Date rentDate = new java.sql.Date(bufferDate.getTime());
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(EkranSerwer.panelData.get(0)));
            preparedStatement.setInt(2,Integer.parseInt(EkranSerwer.panelData.get(1)));
            preparedStatement.setDate(3,rentDate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            String query2 = "DELETE FROM rents WHERE rent_id = ?";
            PreparedStatement preparedStatement1 = connect.prepareStatement(query2);
            preparedStatement1.setInt(1, Integer.parseInt(EkranSerwer.panelData.get(2)));
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "User " + EkranSerwer.panelData.get(0) + " Returned DVD " + EkranSerwer.panelData.get(1), "BazaDanych", "info");
            EkranSerwer.message = "Pomyślnie zwrócono DVD!";
        }
        catch (SQLException | ParseException ex){
             catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy danych dodawanego wypożyczenia płyty DVD
     */
    private static void rentDVD(){
        try {
            String query = "INSERT INTO rents(rent_id,user_id,dvd_id,rent_date) " +
                    "VALUES(rent_id_seq.nextval,?,?,SYSDATE)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            int i=1;
            for(String buffer: EkranSerwer.panelData){
                preparedStatement.setString(i,buffer);
                i++;
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            EkranSerwer.message = "Pomyślnie wypożyczono płytę DVD!";
            new Logs("[ " + new java.util.Date() + " ] " + "User " + EkranSerwer.panelData.get(0) + " Rented DVD " + EkranSerwer.panelData.get(1), "BazaDanych", "info");
        }
        catch (SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy nowych danych wybranego użytkownika do edycji
     */
    private static void editClient(){
        try{
            System.out.println(Integer.parseInt(EkranSerwer.panelData.get(6)));
            String query = "UPDATE users_data " +
                    "SET user_name = ?, " +
                    "user_surname = ?, " +
                    "user_email = ?, " +
                    "user_phone_number = ? " +
                    "WHERE user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, EkranSerwer.panelData.get(0));
            preparedStatement.setString(2, EkranSerwer.panelData.get(1));
            preparedStatement.setString(3, EkranSerwer.panelData.get(2));
            preparedStatement.setInt(4,Integer.parseInt(EkranSerwer.panelData.get(3)));
            preparedStatement.setInt(5,Integer.parseInt(EkranSerwer.panelData.get(6)));
            preparedStatement.executeUpdate();
            String query2 = "UPDATE users " +
                    "SET user_nickname = ?, " +
                    "user_password = ? " +
                    "WHERE user_id = ?";
            PreparedStatement preparedStatement2 = connect.prepareStatement(query2);
            preparedStatement2.setString(1, EkranSerwer.panelData.get(4));
            preparedStatement2.setString(2, EkranSerwer.panelData.get(5));
            preparedStatement2.setInt(3,Integer.parseInt(EkranSerwer.panelData.get(6)));
            preparedStatement2.executeUpdate();
            preparedStatement2.close();
            preparedStatement.close();
            EkranSerwer.message = "Pomyślnie edytowano wybranego klienta!";
            new Logs("[ " + new java.util.Date() + " ] " + "Edited client " + EkranSerwer.panelData.get(6), "BazaDanych", "info");
            commitQuery();
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na usunięcie z bazy danych wskazanego użytkownika
     */
    private static void deleteClient(){
        try{
            String query = "DELETE FROM users_data " +
                    "WHERE user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(EkranSerwer.panelData.get(0)));
            String query2 = "DELETE FROM users " +
                    "WHERE user_id = ?";
            PreparedStatement preparedStatement2 = connect.prepareStatement(query2);
            preparedStatement2.setInt(1,Integer.parseInt(EkranSerwer.panelData.get(0)));
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Deleted client " + EkranSerwer.panelData.get(0), "BazaDanych", "info");
            EkranSerwer.message = "Pomyślnie usunięto zaznaczonego klienta!";
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na pobranie danych do przeglądu użytkowników wypożyczalnii
     */
    private static void reviewClients(){
        try{
            EkranSerwer.panelData.clear();
            String query = "SELECT * FROM users_data";
            ResultSet result = state.executeQuery(query);
            while(result.next()){
                EkranSerwer.panelData.add(Integer.toString(result.getInt("user_id")));
                EkranSerwer.panelData.add(result.getString("user_name"));
                EkranSerwer.panelData.add(result.getString("user_surname"));
                EkranSerwer.panelData.add(result.getString("user_email"));
                EkranSerwer.panelData.add(result.getString("user_phone_number"));
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
    private static void addSameDVDs(){
        try{
            String query = "UPDATE dvds_data " +
                    "SET number_of_copies = ? " +
                    "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(EkranSerwer.panelData.get(0)));
            preparedStatement.setInt(2,Integer.parseInt(EkranSerwer.panelData.get(1)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Changed count of DVD " + EkranSerwer.panelData.get(1), "BazaDanych", "info");
            EkranSerwer.message = "Pomyślnie zmieniono liczbę DVD!";
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na pobranie danych do przeglądu kolekcji DVD
     */
    private static void reviewDVDCollection(String option){
        try{
            EkranSerwer.panelData.clear();
            String query = "SELECT * FROM dvds_data";
            ResultSet result = state.executeQuery(query);
            while(result.next()){
                if(option.equals("DVDWareHouseAvalaible")){
                    EkranSerwer.panelData.add(result.getString("film_name"));
                    EkranSerwer.panelData.add(result.getString("number_of_copies"));
                }
                else {
                    EkranSerwer.panelData.add(result.getString(1));
                    EkranSerwer.panelData.add(result.getString(2));
                    EkranSerwer.panelData.add(result.getString(3));
                    EkranSerwer.panelData.add(result.getString(4));
                    EkranSerwer.panelData.add(result.getString(5));
                    EkranSerwer.panelData.add(result.getString(6));
                    EkranSerwer.panelData.add(result.getString(7));
                    EkranSerwer.panelData.add(result.getString(8));
                    EkranSerwer.panelData.add(result.getString(9));
                    EkranSerwer.panelData.add(result.getString(10));
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
    private static void deleteDVD(){
        try{
            String query = "DELETE FROM dvds_data " +
                    "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(EkranSerwer.panelData.get(0)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            EkranSerwer.message = "Pomyślnie usunięto wybrane DVD!";
            new Logs("[ " + new java.util.Date() + " ] " + "Deleted DVD " +  EkranSerwer.panelData.get(0), "BazaDanych", "info");
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na usunięcie z bazy danych wskazanej rezerwacji
     */
    private static void deleteReservation(){
        try{
            String query = "DELETE FROM reservations " +
                    "WHERE dvd_id = ? " +
                    "AND user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            System.out.println(EkranSerwer.dvdID + "\n" + EkranSerwer.userID);
            preparedStatement.setInt(1, EkranSerwer.dvdID);
            preparedStatement.setInt(2,(Integer.parseInt(EkranSerwer.userID)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            new Logs("[ " + new java.util.Date() + " ] " + "Deleted reservation for DVD: " +  EkranSerwer.dvdID + " reservation by user: " + EkranSerwer.userID, "BazaDanych", "info");
        }
        catch(SQLException ex){
            catchServe(ex);
        }
        commitQuery();
    }
    /**
     * Metoda pozwalająca na zapis do bazy nowych danych wybranej płyty DVD do edycji
     */
    private static void editDVD(){
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
            preparedStatement.setString(1, EkranSerwer.panelData.get(0));
            preparedStatement.setString(2, EkranSerwer.panelData.get(1));
            preparedStatement.setString(3, EkranSerwer.panelData.get(2));
            preparedStatement.setString(4, EkranSerwer.panelData.get(3));
            preparedStatement.setString(5, EkranSerwer.panelData.get(4));
            preparedStatement.setString(6, EkranSerwer.panelData.get(5));
            preparedStatement.setString(7, EkranSerwer.panelData.get(6));
            preparedStatement.setString(8, EkranSerwer.panelData.get(7));
            preparedStatement.setString(9, EkranSerwer.panelData.get(8));
            preparedStatement.setInt(10,Integer.parseInt(EkranSerwer.panelData.get(8)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Edited DVD " + EkranSerwer.panelData.get(8), "BazaDanych", "info");
            EkranSerwer.message = "Pomyślnie edytowano wybrane DVD!";
        }
        catch(SQLException ex){
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na zapis do bazy danych dodawanej płyty DVD
     */
    private static void addDVD(){
        try {
            String query = "INSERT INTO dvds_data (dvd_id, film_name, film_direction, film_type, country, production_year, film_language, video_length, number_of_copies, day_payment) " +
                    "VALUES(dvd_id_seq.nextval,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            int i = 1;
            for(String data:EkranSerwer.panelData){
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
            new Logs("[ " + new java.util.Date() + " ] " + "Added new DVD " + EkranSerwer.panelData.get(0) , "BazaDanych", "info");
            EkranSerwer.panelData.clear();
            EkranSerwer.message = "Pomyślnie dodano nową płytę DVD!\n";
        }
        catch (SQLException ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na pobranie ID użytkownika do wykonania nastepnego zapytania
     */
    private static void getClientID(){
        String query = "SELECT user_id " +
                "FROM users " +
                "WHERE user_nickname = ?";
        try{
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            switch(EkranSerwer.whichClient) {
                case "loggedIn" -> preparedStatement.setString(1, EkranSerwer.nick);
                case "selectedUser" -> preparedStatement.setString(1, EkranSerwer.selectedNick);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                EkranSerwer.userID = resultSet.getString("user_id");
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
    private static void getDVDID(){
        String query = "SELECT dvd_id " +
                "FROM dvds_data " +
                "WHERE film_name = ?";
        try{
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, EkranSerwer.filmName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                EkranSerwer.dvdID = resultSet.getInt("dvd_id");
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
    private static void logout(@NotNull String howManyUsers){
        try {
            if(howManyUsers.equals("oneUser")) {
                new Logs("[ " + new java.util.Date() + " ] " + "Logout from: " + EkranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + EkranSerwer.nick, "BazaDanych", "info");
                String query = "UPDATE users " +
                        "SET user_logged = 'no' " +
                        "WHERE user_nickname = ?";
                PreparedStatement preparedstate = connect.prepareStatement(query);
                preparedstate.setString(1, EkranSerwer.nick);
                preparedstate.executeQuery();
                preparedstate.close();
            }
            else{
                String query = "UPDATE users " +
                        "SET user_logged = 'no'";
                state.executeUpdate(query);
            }
            commitQuery();
            EkranGlownyAdmin.message = "Pomyślnie wylogowano! \n";
        }
        catch (SQLException ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda pozwalająca na sprawdzenie poprawności wprowadzonych danych przy próbie zalogowania się na konto
     */
    private static void login(){
        try {
            EkranLogowania.wiadomosc="";
            String query = "SELECT * " +
                    "FROM users " +
                    "WHERE user_nickname = ? " +
                    "AND user_password = ?";
            PreparedStatement preparedstate = connect.prepareStatement(query);
            preparedstate.setString(1, EkranSerwer.nick);
            preparedstate.setString(2, EkranSerwer.pass);
            ResultSet result = preparedstate.executeQuery();
            if (result.next()) {
                String rank = result.getString("user_rank");
                String is_logged = result.getString("user_logged");
                if(is_logged.equals("no")) {
                    EkranSerwer.setMessage("Pomyslnie zalogowano! ");
                    EkranSerwer.rank = rank;
                    new Logs("[ " + new java.util.Date() + " ] " + "Login from: " + EkranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + EkranSerwer.nick, "BazaDanych", "info");
                    String query_logged = "UPDATE users " +
                            "SET user_logged = 'yes' " +
                            "WHERE user_nickname = ? " +
                            "AND user_password = ?";
                    PreparedStatement preparedstate2 = connect.prepareStatement(query_logged);
                    preparedstate2.setString(1, EkranSerwer.nick);
                    preparedstate2.setString(2, EkranSerwer.pass);
                    preparedstate2.executeQuery();
                    preparedstate2.close();
                    if (rank.equals("admin")) {
                        EkranSerwer.setMessage("Uzytkownik to administrator");
                    } else if (rank.equals("user")) {
                        EkranSerwer.setMessage("To zwykly uzytkownik");
                    }
                    else{
                        EkranSerwer.rank = "Nieznana";
                    }
                    EkranLogowania.wiadomosc = "Pomyślnie zalogowano!";
                }
                else if(is_logged.equals("yes")){
                    new Logs("[ " + new java.util.Date() + " ] " + "Unsuccessfull login try from: " + EkranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + EkranSerwer.nick, "BazaDanych", "info");
                    EkranLogowania.wiadomosc = "Na podane konto ktoś już jest zalogowany!";
                    EkranSerwer.rank = rank;
                }
            }
            else{
                new Logs("[ " + new java.util.Date() + " ] " + "Unsuccessfull login try from: " + EkranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + EkranSerwer.nick, "BazaDanych", "info");
                EkranSerwer.rank = "not_logged";
                EkranSerwer.setMessage("Nie udana proba logowania!");
                EkranLogowania.wiadomosc = "Wprowadzono bledne dane logowania!";
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
    private static void register(){
        try {
            String query0 = "SELECT * " +
                    "FROM users " +
                    "WHERE user_nickname = ?";
            PreparedStatement preparedstate0 = connect.prepareStatement(query0);
            preparedstate0.setString(1, nick);
            ResultSet result = preparedstate0.executeQuery();
            if (result.next()) {
                new Logs("[ " + new java.util.Date() + " ] " + "Unsuccessfull register from: " + EkranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + nick, "BazaDanych", "info");
                EkranUtworzKonto.blad = "Podany uzytkownik istnieje juz w bazie danych!";
            }
            else {
                new Logs("[ " + new java.util.Date() + " ] " + "Register from: " + EkranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + nick, "BazaDanych", "info");
                EkranUtworzKonto.blad = "Sukces";
                String query = "INSERT INTO users (user_nickname, user_password, user_coverage_key, user_id) " +
                                "VALUES (?, ?, ?, user_id_seq.nextval)";
                String query2 = "INSERT INTO users_data (user_name, user_surname, user_email, user_phone_number, user_data_id, user_id) " +
                                "VALUES (?, ?, ?, ?, user_data_id_seq.nextval, user_id_seq.currval)";
                PreparedStatement preparedstate = connect.prepareStatement(query);
                preparedstate.setString(1, nick);
                preparedstate.setString(2, pass);
                preparedstate.setString(3, coverage_key);
                preparedstate.executeUpdate();
                preparedstate.close();
                PreparedStatement preparedstate2 = connect.prepareStatement(query2);
                preparedstate2.setString(1, name);
                preparedstate2.setString(2, surname);
                preparedstate2.setString(3, email);
                preparedstate2.setString(4, phone_number);
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
    private static void reservateDVD(){
        try {
            String query = "INSERT INTO reservations(reservation_id,dvd_id,user_id) " +
                            "VALUES(rent_id_seq.nextval,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            int i=1;
            for(String buffer: EkranSerwer.panelData){
                preparedStatement.setInt(i,Integer.parseInt(buffer));
                i++;
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            new Logs("[ " + new java.util.Date() + " ] " + "New DVD reservation", "BazaDanych", "info");
            EkranSerwer.message = "Pomyślnie zarezerwowano płytę DVD!";
        }
        catch (SQLException ex){
            catchServe(ex);
        }
        commitQuery();
    }
    /**
     * Metoda pozwalająca na zaktualizowanie stanu wybranej płyty DVD
     */
    private static void updateCount(){
        try{
            String query = "UPDATE dvds_data " +
                    "SET number_of_copies = ? " +
                    "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(EkranSerwer.updatingItem));
            preparedStatement.setInt(2, EkranSerwer.updatingID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            new Logs("[ " + new java.util.Date() + " ] " + "Changed count of DVD " + EkranSerwer.updatingID, "BazaDanych", "info");
        }
        catch (SQLException ex) {
            catchServe(ex);
        }
        commitQuery();
    }
    /**
     * Metoda pozwalająca na pobranie danych dotyczących rezerwacji z bazy
     */
    private static void checkReservations(){
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
            preparedStatement.setString(1, EkranSerwer.nick);
            preparedStatement.setString(2, EkranSerwer.filmName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                EkranSerwer.existReservation = true;
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
    private static void downloadNickname(){
        try{
            String query = "SELECT users.user_nickname " +
                    "FROM users " +
                    "WHERE users.user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(EkranSerwer.userID));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Klient.nickname = resultSet.getString("user_nickname");
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
    private static void adminNotifications(){
        try{
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
                Powiadomienia.adminReservationNotifications.add(resultSet.getString(1));
                Powiadomienia.adminReservationNotifications.add(resultSet.getString(2));
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
                Powiadomienia.adminDetentionDVDsNotifications.add(Integer.toString(resultSet2.getInt(1)));
                Powiadomienia.adminDetentionDVDsNotifications.add(Integer.toString(resultSet2.getInt(2)));
                Powiadomienia.adminDetentionDVDsNotifications.add(resultSet2.getString(3));
            }
            resultSet2.close();
        }
        catch (SQLException ex) {
            Powiadomienia.userDetentionDVDsNotifications.add("Brak powiadomień");
            catchServe(ex);
        }
    }
    /**
     * Metoda umożliwiająca pobór niezbędnych danych do wyświetlania powiadomień dla użytkownika
     */
    private static void userNotifications(){
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
            preparedStatement.setString(1,EkranSerwer.nick);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Powiadomienia.userDetentionDVDsNotifications.add(Integer.toString(resultSet.getInt(1)));
                Powiadomienia.userDetentionDVDsNotifications.add(resultSet.getString(2));
                Powiadomienia.userDetentionDVDsNotifications.add(resultSet.getString(3));
            }
            resultSet.close();
        }
        catch (SQLException ex) {
            Powiadomienia.userDetentionDVDsNotifications.add("Brak powiadomień");
            catchServe(ex);
        }
    }
}
/**
 * Klasa dziedzicząca atrybuty i metody z klasy BazaDanych.
 * Umożliwia awaryjną zmianę hasła przez użytkownika
 */
final class OdzyskiwanieDanych extends BazaDanych {
    /**
     * Konstruktor umożliwiający na zapis zmienionego hasła do bazy i wygenerowanie nowego klucza do odzyskiwania hasła
     */
    OdzyskiwanieDanych() {
        try {
            String query = "SELECT * " +
                    "FROM users " +
                    "WHERE user_nickname = ? " +
                    "AND user_coverage_key = ?";
            PreparedStatement preparedState = connect.prepareStatement(query);
            preparedState.setString(1,nick);
            preparedState.setString(2,coverage_key);
            ResultSet resultSet = preparedState.executeQuery();
            if(resultSet.next()){
                key = Integer.toString(EkranUtworzKonto.generujKlucz());
                String update_query = "UPDATE users " +
                        "SET user_coverage_key = ?, " +
                        "user_password = ? " +
                        "WHERE user_nickname = ? " +
                        "AND user_coverage_key = ?";
                PreparedStatement preparedStatement = connect.prepareStatement(update_query);
                preparedStatement.setString(1,key);
                preparedStatement.setString(2,pass);
                preparedStatement.setString(3,nick);
                preparedStatement.setString(4,coverage_key);
                preparedStatement.executeQuery();
                preparedStatement.close();
                commitQuery();
                new Logs("[ " + new java.util.Date() + " ] " + "Recovered password" , "BazaDanych", "info");
                EkranPrzywrocHaslo.message = "Pomyślnie zmieniono hasło!\n\nTwój nowy klucz zapasowy to: " + key + "\n Zapisz go w bezpiecznym miejscu!";
            }
            else{
                EkranPrzywrocHaslo.message = "Wystąpił błąd przy próbie zmiany hasła!";
            }
            preparedState.close();
        }
        catch (SQLException ex) {
            catchServe(ex);
        }
    }
}
/**
 * Klasa dziedzicząca atrybuty i metody z klasy BazaDanych.
 * Umożliwia zarządzanie konstrukcją bazy danych
 */
final class ZarzadzajBaza extends BazaDanych {
    /**
     * Konstruktor umożliwiający wykonywanie zapytań do bazy
     * @param option Jest to parametr pomagający określić, jaka operacja na bazie ma zostać wykonana
     */
    ZarzadzajBaza(@NotNull String option) {
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
                    EkranGlownyAdmin.message = "Pomyślnie utworzono tabele dla użytkowników!\n";
                }
                case "CREATE TABLE users_data" -> {
                    state.executeUpdate(users_data);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono tabele dla danych użytkowników!\n";
                }
                case "CREATE TABLE dvds_data" -> {
                    state.executeUpdate(dvds_data);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono tabele dla danych płyt DVD!\n";
                }
                case "CREATE TABLE rents" -> {
                    state.executeUpdate(rents);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono tabele dla wypożyczeń płyt DVD!\n";
                }
                case "CREATE TABLE returns" -> {
                    state.executeUpdate(returns);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono tabele dla zwrotów płyt DVD!\n";
                }
                case "CREATE TABLE bills" -> {
                    state.executeUpdate(bills);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono tabele dla rachunków klientów!\n";
                }
                case "CREATE TABLE reservations" -> {
                    state.executeUpdate(reservations);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono tabele dla rezerwacji klientów!\n";
                }
                case "DROP TABLE users" -> {
                    state.executeUpdate(drop_table_1);
                    EkranGlownyAdmin.message = "Pomyślnie usunieto tabele z użytkownikami!\n";
                }
                case "DROP TABLE users_data" -> {
                    state.executeUpdate(drop_table_2);
                    EkranGlownyAdmin.message = "Pomyślnie usunieto tabele z danymi użytkowników!\n";
                }
                case "DROP TABLE dvds_data" -> {
                    state.executeUpdate(drop_table_3);
                    EkranGlownyAdmin.message = "Pomyślnie usunieto tabele z danymi płyt DVD!\n";
                }
                case "DROP TABLE rents" -> {
                    state.executeUpdate(drop_table_4);
                    EkranGlownyAdmin.message = "Pomyślnie usunieto tabele z wypożyczeniami płyt DVD!\n";
                }
                case "DROP TABLE returns" -> {
                    state.executeUpdate(drop_table_5);
                    EkranGlownyAdmin.message = "Pomyślnie usunieto tabele ze zwrotami płyt DVD!\n";
                }
                case "DROP TABLE bills" -> {
                    state.executeUpdate(drop_table_6);
                    EkranGlownyAdmin.message = "Pomyślnie usunieto tabele z rachunkami klientów!\n";
                }
                case "DROP TABLE reservations" -> {
                    state.executeUpdate(drop_table_7);
                    EkranGlownyAdmin.message = "Pomyślnie usunieto tabele z rezerwacjami klientów!\n";
                }
                case "CREATE SEQUENCE user_id" -> {
                    state.executeUpdate(sequence1);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono sekwencje ID użytkowników!\n";
                }
                case "CREATE SEQUENCE user_data_id" -> {
                    state.executeUpdate(sequence2);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono sekwencje ID danych użytkowników!\n";
                }
                case "CREATE SEQUENCE dvd_id" -> {
                    state.executeUpdate(sequence3);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono sekwencje ID płyt DVD!\n";
                }
                case "CREATE SEQUENCE rent_id" -> {
                    state.executeUpdate(sequence4);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono sekwencje ID wypożyczeń użytkowników!\n";
                }
                case "CREATE SEQUENCE return_id" -> {
                    state.executeUpdate(sequence5);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono sekwencje ID zwrotów użytkowników!\n";
                }
                case "CREATE SEQUENCE bill_id" -> {
                    state.executeUpdate(sequence6);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono sekwencje ID rachunków użytkowników!\n";
                }
                case "CREATE SEQUENCE reservation_id" -> {
                    state.executeUpdate(sequence7);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono sekwencje ID rezerwacji użytkowników!\n";
                }
                case "DROP SEQUENCE user_id" -> {
                    state.executeUpdate(drop_sequence_1);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto sekwencje ID użytkowników!\n";
                }
                case "DROP SEQUENCE user_data_id" -> {
                    state.executeUpdate(drop_sequence_2);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto sekwencje ID danych użytkowników!\n";
                }
                case "DROP SEQUENCE dvd_id" -> {
                    state.executeUpdate(drop_sequence_3);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto sekwencje ID płyt DVD!\n";
                }
                case "DROP SEQUENCE rent_id" -> {
                    state.executeUpdate(drop_sequence_4);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto sekwencje ID wypożyczeń użytkowników!\n";
                }
                case "DROP SEQUENCE return_id" -> {
                    state.executeUpdate(drop_sequence_5);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto sekwencje ID zwrotów użytkowników!\n";
                }
                case "DROP SEQUENCE bill_id" -> {
                    state.executeUpdate(drop_sequence_6);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto sekwencje ID rachunków użytkowników!\n";
                }
                case "DROP SEQUENCE reservation_id" -> {
                    state.executeUpdate(drop_sequence_7);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto sekwencje ID rezerwacji użytkowników!\n";
                }
                case "DeleteFromTables" -> {
                    state.executeUpdate(delete_from_table_7);
                    state.executeUpdate(delete_from_table_6);
                    state.executeUpdate(delete_from_table_5);
                    state.executeUpdate(delete_from_table_4);
                    state.executeUpdate(delete_from_table_3);
                    state.executeUpdate(delete_from_table_2);
                    state.executeUpdate(delete_from_table_1);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto wszystkie dane ze wszystkich tabel!\n";
                }
                default -> EkranSerwer.setMessage("Inna opcja w bazie danych");
            }
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Changed something in database construction" , "BazaDanych", "info");
        }
        catch (SQLException ex) {
             catchServe(ex);
        }
    }
}