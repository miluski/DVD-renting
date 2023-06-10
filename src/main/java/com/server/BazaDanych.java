package com.server;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Klasa przyjmująca dane od serwera i zezwalająca na połączenie się z bazą danych
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class BazaDanych implements Callable<String> {
    /**
     * Lista danych odbierana od serwera
     */
    public final List<String> dataList = new LinkedList<>();
    /**
     * Instancja klasy EkranSerwer
     */
    public EkranSerwer ekranSerwer;
    /**
     * Atrybut pozwalający na wykonywanie zapytań do bazy danych
     */
    public Statement state;
    /**
     * Atrybut pozwalający na podłączenie się do bazy danych
     */
    public Connection connect;
    /**
     * Lista zwracanych pobranych danych
     */
    public List<String> panelData = new LinkedList<>();
    /**
     * Atrybut informujący o tym, jaka operacja ma zostać wykonana
     */
    private String operation;
    /**
     * Atrybut będący adresem IP używanym do połączenia się serwera z bazą danych
     */
    private String IP;

    /**
     * Podstawowy konstruktor pozwalający na tworzenie nowych instancji klasy
     */
    public BazaDanych() {
    }

    /**
     * Konstruktor umożliwiający ustawienie operacji, którą baza danych ma wykonać
     *
     * @param ekranSerwer Instancja klasy EkranSerwer
     * @param operation   Operacja do wykonania
     * @param IP          Adres IP ustawiany do połączenia z bazą danych
     */
    public BazaDanych(EkranSerwer ekranSerwer, String operation, String IP) {
        this.operation = operation;
        this.ekranSerwer = ekranSerwer;
        this.IP = IP;
        setUpDataBase();
    }

    /**
     * Konstruktor umożliwiający ustawienie referencji do Instancji klasy EkranSerwer
     *
     * @param ekranSerwer Przekazywana instancja klasy EkranSerwer
     */
    public BazaDanych(EkranSerwer ekranSerwer) {
        this.dataList.addAll(ekranSerwer.panelData);
        this.IP = ekranSerwer.IP;
        this.operation = this.dataList.get(0);
        this.ekranSerwer = ekranSerwer;
    }

    /**
     * Metoda zwracająca listę pobranych danych
     *
     * @return Zwraca listę pobranych danych
     */
    public List<String> getPanelData() {
        return this.panelData;
    }

    /**
     * Metoda obsługująca postępowanie w razie wystąpienia wyjątku
     *
     * @param ex Otrzymany wyjątek
     */
    public void catchServe(@NotNull Exception ex) {
        ekranSerwer.setMessage(ex.getMessage());
        new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "BazaDanych", "error");
    }

    /**
     * Jest to metoda umożliwająca zatwierdzenie wykonanych zmian w bazie danych
     */
    public void commitQuery() {
        try {
            String commit_query = "COMMIT";
            state.executeUpdate(commit_query);
        } catch (SQLException ex) {
            ekranSerwer.setMessage(ex.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + ex.getMessage(), "BazaDanych", "error");
        }
    }

    /**
     * Jest to metoda pozwalająca na zainicjowanie parametrow polaczenia z baza danych
     *
     * @return Zwraca informację o tym, czy podłączenie się udało, czy nie
     */
    public String setUpDataBase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection("jdbc:oracle:thin:@" + IP + ":1521:xe", "system", "admin");
            ekranSerwer.setMessage("Połączono z bazą danych");
            new Logs("[ " + new java.util.Date() + " ] " + "Connected to the database", "BazaDanych", "info");
            state = connect.createStatement();
            switch (operation) {
                case "GetClientID", "GetDVDID", "LogoutAll", "DeleteReservation", "CheckReservations", "DownloadNickname", "AdminDetentionNotifications", "AdminReservationNotifications", "UserNotifications", "Register", "Login", "Logout", "DataPass", "DataReceive", "UpdateCount", "ReviewDVDCollection", "DVDAvalaible", "ReservateDVD", "ReviewMyRents", "ReviewMyReturns", "AddDVD", "EditDVD", "DeleteDVD", "DVDWareHouseAvalaible", "AddSameDVDs", "ReviewClients", "DeleteClient", "EditClient", "RentDVD", "ReturnDVD", "ReviewReservations", "NewBill", "ReviewBills" ->
                        wymianaDanych(operation);
                case "Management" -> zarzadzajBaza(dataList.get(1));
                case "Recovery" -> recoveryData();
            }
            connect.close();
            return "Success";
        } catch (Exception except) {
            ekranSerwer.setMessage(except.getMessage());
            new Logs("[ " + new java.util.Date() + " ] " + except.getMessage(), "BazaDanych", "fatal");
            return except.getMessage();
        }
    }

    /**
     * Jest to metoda pozwalająca na przerwanie działania w trakcie zbyt długiego oczekiwania
     *
     * @return Zwraca rezultat metody setUpDataBase
     */
    @Override
    public String call() {
        return setUpDataBase();
    }

    /**
     * Jest to metoda odpowiadająca za wybór odpowiedniej metody do wymiany danych z bazą
     *
     * @param operation Jest to parametr przechowujący typ operacji wykonywanej na bazie danych
     */
    public void wymianaDanych(@NotNull String operation) {
        try {
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
                case "AdminDetentionNotifications" -> adminDetentionNotifications();
                case "AdminReservationNotifications" -> adminReservationNotifications();
                case "UserNotifications" -> userNotifications();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Metoda pobierająca z bazy dane o rachunkach wskazanego użytkownika
     */
    private void reviewBills() {
        try {
            panelData.clear();
            String query = "SELECT bills.bill_id, " + "users_data.user_name, " + "bills.nip, " + "bills.regon, " + "bills.pesel," + "bills.kwota, " + "bills.data " + "FROM bills " + "JOIN users_data ON users_data.user_id = bills.user_id";
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                panelData.add(Integer.toString(result.getInt("bill_id")));
                panelData.add(result.getString("user_name"));
                panelData.add(result.getString("nip"));
                panelData.add(result.getString("regon"));
                panelData.add(result.getString("pesel"));
                panelData.add(result.getString("kwota"));
                panelData.add(result.getString("data"));
            }
            result.close();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na zapis danych tworzonego rachunku w bazie
     */
    private void newBill() {
        try {
            String query = "INSERT INTO bills (bill_id,user_id,NIP,REGON,PESEL,Kwota,Data) " + "VALUES(bill_id_seq.nextval,?,?,?,?,?,SYSDATE)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            for (int i = 1; i < ekranSerwer.panelData.size(); i++) {
                if (i < 2) preparedStatement.setInt(i, Integer.parseInt(ekranSerwer.panelData.get(i)));
                else preparedStatement.setString(i, ekranSerwer.panelData.get(i));
            }
            preparedStatement.executeUpdate();
            ekranSerwer.setMessage("Pomyślnie wystawiono rachunek!");
            panelData.add("Pomyślnie wystawiono rachunek!");
            new Logs("[ " + new java.util.Date() + " ] " + "Added new Bill", "BazaDanych", "info");
            commitQuery();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pobierająca z bazy dane dotyczące rezerwacji wskazanego użytkownika
     */
    private void reviewReservations() {
        try {
            panelData.clear();
            String query = "SELECT reservations.reservation_id, " + "users.user_nickname, " + "dvds_data.film_name " + "FROM reservations " + "JOIN users ON users.user_id = reservations.user_id " + "JOIN dvds_data ON dvds_data.dvd_id = reservations.dvd_id";
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                panelData.add(result.getString("user_nickname"));
                panelData.add(result.getString("film_name"));
            }
            result.close();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pobierająca z bazy dane dotyczące zwrotów wskazanego użytkownika
     */
    private void reviewMyReturns() {
        try {
            panelData.clear();
            String query = "SELECT returns.return_id, " + "dvds_data.film_name, " + "dvds_data.film_direction, " + "dvds_data.film_type, " + "dvds_data.country, " + "dvds_data.production_year, " + "dvds_data.film_language, " + "dvds_data.video_length, " + "returns.rent_date, " + "returns.return_date " + "FROM returns " + "JOIN dvds_data ON dvds_data.dvd_id = returns.dvd_id " + "JOIN users ON users.user_id = returns.user_id " + "WHERE dvds_data.dvd_id = returns.dvd_id " + "AND returns.user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(1)));
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
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
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pobierająca z bazy dane dotyczące wypożyczeń wskazanego użytkownika
     */
    private void reviewMyRents() {
        try {
            panelData.clear();
            String query = "SELECT rents.rent_id, " + "dvds_data.film_name, " + "dvds_data.film_direction, " + "dvds_data.film_type, " + "dvds_data.country, " + "dvds_data.production_year, " + "dvds_data.film_language, " + "dvds_data.video_length, " + "rents.rent_date " + "FROM rents " + "JOIN dvds_data ON dvds_data.dvd_id = rents.dvd_id " + "JOIN users ON users.user_id = rents.user_id " + "WHERE dvds_data.dvd_id = rents.dvd_id " + "AND rents.user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(1)));
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
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
        } catch (Exception ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda usuwająca wypożyczenie płyty DVD przy jej zwrocie
     *
     * @throws SQLException Wyjątek powstały w wyniku napotkania wyjątku z języka SQL
     */
    private void deleteDVDRent() throws SQLException {
        String query2 = "DELETE FROM rents WHERE rent_id = ?";
        PreparedStatement preparedStatement1 = connect.prepareStatement(query2);
        preparedStatement1.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(3)));
        preparedStatement1.executeUpdate();
        preparedStatement1.close();
    }

    /**
     * Metoda pozwalająca na zapis do bazy danych dodawanego zwrotu płyty DVD
     */
    private void returnDVD() {
        try {
            String query = "INSERT INTO returns(return_id,user_id,dvd_id,rent_date,return_date) " + "VALUES(return_id_seq.nextval,?,?,?,SYSDATE)";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date bufferDate = dateFormat.parse(ekranSerwer.panelData.get(4));
            java.sql.Date rentDate = new java.sql.Date(bufferDate.getTime());
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(1)));
            preparedStatement.setInt(2, Integer.parseInt(ekranSerwer.panelData.get(2)));
            preparedStatement.setDate(3, rentDate);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            deleteDVDRent();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "User " + ekranSerwer.panelData.get(1) + " Returned DVD " + ekranSerwer.panelData.get(2), "BazaDanych", "info");
            ekranSerwer.setMessage("Pomyślnie zwrócono DVD!");
            panelData.add("Pomyślnie zwrócono DVD!");
        } catch (SQLException | ParseException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na zapis do bazy danych dodawanego wypożyczenia płyty DVD
     */
    private void rentDVD() {
        try {
            String query = "INSERT INTO rents(rent_id,user_id,dvd_id,rent_date) " + "VALUES(rent_id_seq.nextval,?,?,SYSDATE)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            for (int i = 1; i < ekranSerwer.panelData.size(); i++) {
                preparedStatement.setString(i, ekranSerwer.panelData.get(i));
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            ekranSerwer.setMessage("Pomyślnie wypożyczono płytę DVD!");
            panelData.add("Pomyślnie wypożyczono płytę DVD!");
            new Logs("[ " + new java.util.Date() + " ] " + "User " + ekranSerwer.panelData.get(1) + " Rented DVD " + ekranSerwer.panelData.get(2), "BazaDanych", "info");
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }
    /**
     * Metoda, której zadaniem jest zapis nowych danych w tabeli users
     *
     * @throws SQLException Wyjątek powstały w wyniku napotkania wyjątku z języka SQL
     */
    private void saveNewUserData() throws SQLException{
        String query2 = "UPDATE users " + "SET user_nickname = ?, " + "user_password = ? " + "WHERE user_id = ?";
        PreparedStatement preparedStatement2 = connect.prepareStatement(query2);
        preparedStatement2.setString(1, ekranSerwer.panelData.get(5));
        preparedStatement2.setString(2, ekranSerwer.panelData.get(6));
        preparedStatement2.setInt(3, Integer.parseInt(ekranSerwer.panelData.get(7)));
        preparedStatement2.executeUpdate();
        preparedStatement2.close();
    }
    /**
     * Metoda pozwalająca na zapis do bazy nowych danych wybranego użytkownika do edycji
     */
    private void editClient() {
        try {
            String query = "UPDATE users_data " + "SET user_name = ?, " + "user_surname = ?, " + "user_email = ?, " + "user_phone_number = ? " + "WHERE user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.panelData.get(1));
            preparedStatement.setString(2, ekranSerwer.panelData.get(2));
            preparedStatement.setString(3, ekranSerwer.panelData.get(3));
            preparedStatement.setInt(4, Integer.parseInt(ekranSerwer.panelData.get(4)));
            preparedStatement.setInt(5, Integer.parseInt(ekranSerwer.panelData.get(7)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            saveNewUserData();
            ekranSerwer.setMessage("Pomyślnie edytowano wybranego klienta!");
            panelData.add("Pomyślnie edytowano wybranego klienta!");
            new Logs("[ " + new java.util.Date() + " ] " + "Edited client " + ekranSerwer.panelData.get(7), "BazaDanych", "info");
            commitQuery();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na usunięcie z bazy danych wskazanego użytkownika
     */
    private void deleteClient() {
        try {
            String query = "DELETE FROM users_data " + "WHERE user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(1)));
            String query2 = "DELETE FROM users " + "WHERE user_id = ?";
            PreparedStatement preparedStatement2 = connect.prepareStatement(query2);
            preparedStatement2.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(1)));
            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Deleted client " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
            ekranSerwer.setMessage("Pomyślnie usunięto zaznaczonego klienta!");
            panelData.add("Pomyślnie usunięto zaznaczonego klienta!");
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na pobranie danych do przeglądu użytkowników wypożyczalnii
     */
    private void reviewClients() {
        try {
            panelData.clear();
            String query = "SELECT * FROM users_data";
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                panelData.add(Integer.toString(result.getInt("user_id")));
                panelData.add(result.getString("user_name"));
                panelData.add(result.getString("user_surname"));
                panelData.add(result.getString("user_email"));
                panelData.add(result.getString("user_phone_number"));
            }
            result.close();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na zapis do bazy danych nowej liczby egzemplarzy wskazanej płyty DVD
     */
    private void addSameDVDs() {
        try {
            String query = "UPDATE dvds_data " + "SET number_of_copies = ? " + "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(1)));
            preparedStatement.setInt(2, Integer.parseInt(ekranSerwer.panelData.get(2)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Changed count of DVD " + ekranSerwer.panelData.get(2), "BazaDanych", "info");
            ekranSerwer.setMessage("Pomyślnie zmieniono liczbę DVD!");
            panelData.add("Pomyślnie zmieniono liczbę DVD!");
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na pobranie danych do przeglądu kolekcji DVD
     */
    private void reviewDVDCollection(String option) {
        try {
            panelData.clear();
            String query = "SELECT * FROM dvds_data";
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                if (option.equals("DVDWareHouseAvalaible")) {
                    panelData.add(result.getString("film_name"));
                    panelData.add(result.getString("number_of_copies"));
                } else {
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
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na usunięcie z bazy danych wskazanej płyty DVD
     */
    private void deleteDVD() {
        try {
            String query = "DELETE FROM dvds_data " + "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(1)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            ekranSerwer.setMessage("Pomyślnie usunięto DVD!");
            panelData.add("Pomyślnie usunięto wybrane DVD!");
            new Logs("[ " + new java.util.Date() + " ] " + "Deleted DVD " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na usunięcie z bazy danych wskazanej rezerwacji
     */
    private void deleteReservation() {
        try {
            String query = "DELETE FROM reservations " + "WHERE dvd_id = ? " + "AND user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(2)));
            preparedStatement.setInt(2, (Integer.parseInt(ekranSerwer.panelData.get(1))));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            new Logs("[ " + new java.util.Date() + " ] " + "Deleted reservation for DVD: " + ekranSerwer.panelData.get(2) + " reservation by user: " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
        } catch (SQLException ex) {
            catchServe(ex);
        }
        commitQuery();
    }

    /**
     * Metoda pozwalająca na zapis do bazy nowych danych wybranej płyty DVD do edycji
     */
    private void editDVD() {
        try {
            String query = "UPDATE dvds_data " + "SET film_name = ?, " + "film_direction = ?, " + "film_type = ?, " + "country = ?, " + "production_year = ?, " + "film_language = ?, " + "video_length = ?, " + "number_of_copies = ?, " + "day_payment = ? " + "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.panelData.get(1));
            preparedStatement.setString(2, ekranSerwer.panelData.get(2));
            preparedStatement.setString(3, ekranSerwer.panelData.get(3));
            preparedStatement.setString(4, ekranSerwer.panelData.get(4));
            preparedStatement.setString(5, ekranSerwer.panelData.get(5));
            preparedStatement.setString(6, ekranSerwer.panelData.get(6));
            preparedStatement.setString(7, ekranSerwer.panelData.get(7));
            preparedStatement.setString(8, ekranSerwer.panelData.get(8));
            preparedStatement.setString(9, ekranSerwer.panelData.get(9));
            preparedStatement.setInt(10, Integer.parseInt(ekranSerwer.panelData.get(10)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Edited DVD " + ekranSerwer.panelData.get(10), "BazaDanych", "info");
            ekranSerwer.setMessage("Pomyślnie edytowano wybrane DVD!");
            panelData.add("Pomyślnie edytowano wybrane DVD!");
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na zapis do bazy danych dodawanej płyty DVD
     */
    private void addDVD() {
        try {
            String query = "INSERT INTO dvds_data (dvd_id, film_name, film_direction, film_type, country, production_year, film_language, video_length, number_of_copies, day_payment) " + "VALUES(dvd_id_seq.nextval,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            for (int i = 1; i < ekranSerwer.panelData.size(); i++) {
                if (i == 8) {
                    preparedStatement.setInt(i, Integer.parseInt(ekranSerwer.panelData.get(i)));
                } else {
                    preparedStatement.setString(i, ekranSerwer.panelData.get(i));
                }
            }
            preparedStatement.executeQuery();
            preparedStatement.close();
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Added new DVD " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
            ekranSerwer.setMessage("Pomyślnie dodano nową płytę DVD!\n");
            panelData.add("Pomyślnie dodano nową płytę DVD!\n");
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na pobranie ID użytkownika do wykonania nastepnego zapytania
     */
    private void getClientID() {
        String query = "SELECT user_id " + "FROM users " + "WHERE user_nickname = ?";
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.panelData.get(1));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                panelData.add(resultSet.getString("user_id"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na pobranie ID płyty DVD do wykonania nastepnego zapytania
     */
    private void getDVDID() {
        String query = "SELECT dvd_id " + "FROM dvds_data " + "WHERE film_name = ?";
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.panelData.get(1));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                panelData.add(resultSet.getString("dvd_id"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na zapis do bazy danych dotyczących statusu zalogowania się pojedynczego użytkownika lub wszystkich użytkowników
     */
    private void logout(@NotNull String howManyUsers) {
        try {
            if (howManyUsers.equals("oneUser")) {
                new Logs("[ " + new java.util.Date() + " ] " + "Logout from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
                String query = "UPDATE users " + "SET user_logged = 'no' " + "WHERE user_nickname = ?";
                PreparedStatement preparedstate = connect.prepareStatement(query);
                preparedstate.setString(1, ekranSerwer.panelData.get(1));
                preparedstate.executeQuery();
                preparedstate.close();
            } else {
                String query = "UPDATE users " + "SET user_logged = 'no'";
                state.executeUpdate(query);
            }
            commitQuery();
            panelData.add("Pomyślnie wylogowano!");
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda sprawdzająca, czy dany użytkownik istnieje w bazie danych
     *
     * @return Zwraca listę informacji o użytkowniku, jeśli istnieje
     * @throws SQLException Wyjątek powstały w wyniku napotkania wyjątku z języka SQL
     */
    private List<String> checkUserExists() throws SQLException {
        String query = "SELECT * " + "FROM users " + "WHERE user_nickname = ? " + "AND user_password = ?";
        PreparedStatement preparedstate = connect.prepareStatement(query);
        preparedstate.setString(1, ekranSerwer.panelData.get(1));
        preparedstate.setString(2, ekranSerwer.panelData.get(2));
        ResultSet result = preparedstate.executeQuery();
        if (result.next()) {
            List<String> returningData = new LinkedList<>();
            returningData.add(result.getString("user_rank"));
            returningData.add(result.getString("user_logged"));
            return returningData;
        }
        preparedstate.close();
        return null;
    }

    /**
     * Metoda obsługująca zdarzenie pomyślnej próby zalogowania się na konto
     *
     * @throws SQLException Wyjątek powstały w wyniku napotkania wyjątku z języka SQL
     */
    private void successfullLogin() throws SQLException {
        ekranSerwer.setMessage("Pomyslnie zalogowano! ");
        new Logs("[ " + new java.util.Date() + " ] " + "Login from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
        String query_logged = "UPDATE users " + "SET user_logged = 'yes' " + "WHERE user_nickname = ? " + "AND user_password = ?";
        PreparedStatement preparedstate2 = connect.prepareStatement(query_logged);
        preparedstate2.setString(1, ekranSerwer.panelData.get(1));
        preparedstate2.setString(2, ekranSerwer.panelData.get(2));
        preparedstate2.executeQuery();
        preparedstate2.close();
    }

    /**
     * Metoda obsługująca zdarzenie niepomyślnej próby zalogowania się na konto
     *
     * @param rank Ranga użytkownika
     * @throws SQLException Wyjątek powstały w wyniku napotkania wyjątku z języka SQL
     */
    private void unsuccessfullLogin(String rank) throws SQLException {
        new Logs("[ " + new java.util.Date() + " ] " + "Unsuccessfull login try from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + ekranSerwer.panelData.get(1), "BazaDanych", "info");
        panelData.add("Nie udana próba logowania!");
        panelData.add(rank);
    }

    /**
     * Metoda pozwalająca na sprawdzenie poprawności wprowadzonych danych przy próbie zalogowania się na konto
     */
    private void login() {
        try {
            panelData.clear();
            java.util.List<String> informations = new LinkedList<>();
            if (checkUserExists() == null) unsuccessfullLogin("Nieznana");
            else informations.addAll(Objects.requireNonNull(checkUserExists()));
            if (informations.get(1).equals("no")) {
                successfullLogin();
                if (informations.get(0).equals("admin")) {
                    ekranSerwer.setMessage("Uzytkownik to administrator");
                } else if (informations.get(0).equals("user")) {
                    ekranSerwer.setMessage("To zwykly uzytkownik");
                } else {
                    informations.set(0, "Nieznana");
                }
                panelData.add("Pomyślnie zalogowano!");
                panelData.add(informations.get(0));
            } else {
                unsuccessfullLogin(informations.get(0));
            }
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda zwracająca informację o tym, czy podany użytkownik istnieje w bazie danych
     *
     * @return Zwraca true, jeśli użytkownik istnieje w bazie
     */
    private boolean findUser() throws SQLException {
        String query0 = "SELECT * " + "FROM users " + "WHERE user_nickname = ?";
        PreparedStatement preparedstate0 = connect.prepareStatement(query0);
        preparedstate0.setString(1, dataList.get(3));
        ResultSet result = preparedstate0.executeQuery();
        return result.next();
    }

    /**
     * Metoda wysyłająca dane użytkownika do tabeli users
     *
     * @throws SQLException Wyjątek powstały w wyniku napotkania wyjątku z języka SQL
     */
    private void sendUsersTableData() throws SQLException {
        String query = "INSERT INTO users (user_nickname, user_password, user_coverage_key, user_id) " + "VALUES (?, ?, ?, user_id_seq.nextval)";
        PreparedStatement preparedstate = connect.prepareStatement(query);
        preparedstate.setString(1, dataList.get(3));
        preparedstate.setString(2, dataList.get(6));
        preparedstate.setString(3, dataList.get(7));
        preparedstate.executeUpdate();
        preparedstate.close();
    }

    /**
     * Metoda wysyłająca dane użytkownika do tabeli users_data
     *
     * @throws SQLException Wyjątek powstały w wyniku napotkania wyjątku z języka SQL
     */
    private void sendUsersDataTableData() throws SQLException {
        String query2 = "INSERT INTO users_data (user_name, user_surname, user_email, user_phone_number, user_data_id, user_id) " + "VALUES (?, ?, ?, ?, user_data_id_seq.nextval, user_id_seq.currval)";
        PreparedStatement preparedstate2 = connect.prepareStatement(query2);
        preparedstate2.setString(1, dataList.get(1));
        preparedstate2.setString(2, dataList.get(2));
        preparedstate2.setString(3, dataList.get(4));
        preparedstate2.setString(4, dataList.get(5));
        preparedstate2.executeUpdate();
        preparedstate2.close();
    }

    /**
     * Metoda pozwalająca na zapis danych pobranych przy rejestracji nowego użytkownika do bazy
     */
    private void register() {
        try {
            if (findUser()) {
                new Logs("[ " + new java.util.Date() + " ] " + "Unsuccessfull register from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + dataList.get(3), "BazaDanych", "info");
                panelData.add("Podany uzytkownik istnieje juz w bazie danych!");
            } else {
                sendUsersTableData();
                sendUsersDataTableData();
                new Logs("[ " + new java.util.Date() + " ] " + "Register from: " + ekranSerwer.client.getInetAddress().getHostAddress() + " Nickname: " + dataList.get(3), "BazaDanych", "info");
                panelData.add("Pomyślnie zarejestrowano!");
                commitQuery();
            }
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda pozwalająca na zapis danych dotyczących rezerwacji płyty DVD
     */
    private void reservateDVD() {
        try {
            String query = "INSERT INTO reservations(reservation_id,dvd_id,user_id) " + "VALUES(rent_id_seq.nextval,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            for (int i = 1; i < ekranSerwer.panelData.size(); i++) {
                preparedStatement.setInt(i, Integer.parseInt(ekranSerwer.panelData.get(i)));
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            new Logs("[ " + new java.util.Date() + " ] " + "New DVD reservation", "BazaDanych", "info");
            ekranSerwer.setMessage("Pomyślnie zarezerwowano płytę DVD!");
            panelData.add("Pomyślnie zarezerwowano płytę DVD!");
        } catch (SQLException ex) {
            catchServe(ex);
        }
        commitQuery();
    }

    /**
     * Metoda pozwalająca na zaktualizowanie stanu wybranej płyty DVD
     */
    private void updateCount() {
        try {
            String query = "UPDATE dvds_data " + "SET number_of_copies = ? " + "WHERE dvd_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(3)));
            preparedStatement.setInt(2, Integer.parseInt(ekranSerwer.panelData.get(2)));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            new Logs("[ " + new java.util.Date() + " ] " + "Changed count of DVD " + ekranSerwer.panelData.get(2), "BazaDanych", "info");
        } catch (SQLException ex) {
            catchServe(ex);
        }
        commitQuery();
    }

    /**
     * Metoda pozwalająca na pobranie danych dotyczących rezerwacji z bazy
     */
    private void checkReservations() {
        try {
            String query = "SELECT * " + "FROM reservations " + "JOIN dvds_data ON dvds_data.dvd_id = reservations.dvd_id " + "JOIN users ON users.user_id = reservations.user_id " + "WHERE dvds_data.dvd_id = reservations.dvd_id " + "AND users.user_id = reservations.user_id " + "AND users.user_nickname = ? " + "AND dvds_data.film_name = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, ekranSerwer.panelData.get(1));
            preparedStatement.setString(2, ekranSerwer.panelData.get(2));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                panelData.clear();
                panelData.add("true");
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
    private void downloadNickname() {
        try {
            String query = "SELECT users.user_nickname " + "FROM users " + "WHERE users.user_id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(ekranSerwer.panelData.get(1)));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                panelData.clear();
                panelData.add(resultSet.getString("user_nickname"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda umożliwiająca pobór niezbędnych danych do wyświetlania powiadomień dotyczących rezerwacji płyt DVD dla administratora
     */
    private void adminReservationNotifications() {
        try {
            panelData.clear();
            ResultSet resultSet;
            String reservationQuery = "SELECT dvds_data.film_name, " + "users_data.user_email " + "FROM users_data " + "JOIN reservations ON reservations.user_id = users_data.user_id " + "JOIN dvds_data ON reservations.dvd_id = dvds_data.dvd_id " + "WHERE reservations.user_id = users_data.user_id " + "AND reservations.dvd_id = dvds_data.dvd_id";
            resultSet = state.executeQuery(reservationQuery);
            while (resultSet.next()) {
                panelData.add(resultSet.getString(1));
                panelData.add(resultSet.getString(2));
            }
            resultSet.close();
            if (panelData.isEmpty()) panelData.add("Brak powiadomień");
        } catch (SQLException ex) {
            panelData.add("Brak powiadomień");
            catchServe(ex);
        }
    }

    /**
     * Metoda umożliwiająca pobór niezbędnych danych do wyświetlania powiadomień dotyczących przetrzymywania płyt DVD dla administratora
     */
    private void adminDetentionNotifications() {
        try {
            panelData.clear();
            String detentionDVDQuery = "SELECT users.user_id, " + "rents.dvd_id, " + "rents.rent_date " + "FROM rents " + "JOIN dvds_data ON dvds_data.dvd_id = rents.dvd_id " + "JOIN users ON users.user_id = rents.user_id " + "WHERE EXTRACT (MONTH FROM SYSDATE) - EXTRACT(MONTH FROM rent_date)>2 " + "AND dvds_data.dvd_id = rents.dvd_id " + "AND users.user_id = rents.user_id";
            ResultSet resultSet2 = state.executeQuery(detentionDVDQuery);
            while (resultSet2.next()) {
                panelData.add(Integer.toString(resultSet2.getInt(1)));
                panelData.add(Integer.toString(resultSet2.getInt(2)));
                panelData.add(String.valueOf(resultSet2.getDate(3)));
            }
            if (panelData.isEmpty()) panelData.add("Brak powiadomień");
            resultSet2.close();
        } catch (SQLException ex) {
            panelData.add("Brak powiadomień");
            catchServe(ex);
        }
    }

    /**
     * Metoda umożliwiająca pobór niezbędnych danych do wyświetlania powiadomień dla użytkownika
     */
    private void userNotifications() {
        try {
            String detentionDVDQuery = "SELECT rents.dvd_id, dvds_data.film_name, rents.rent_date " + "FROM rents " + "JOIN dvds_data ON dvds_data.dvd_id = rents.dvd_id " + "JOIN users ON users.user_id = rents.user_id " + "WHERE EXTRACT (MONTH FROM SYSDATE) - EXTRACT(MONTH FROM rent_date)>2 " + "AND dvds_data.dvd_id = rents.dvd_id " + "AND users.user_nickname = ? " + "AND users.user_id = rents.user_id";
            PreparedStatement preparedStatement = connect.prepareStatement(detentionDVDQuery);
            preparedStatement.setString(1, ekranSerwer.panelData.get(1));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                panelData.add(Integer.toString(resultSet.getInt(1)));
                panelData.add(resultSet.getString(2));
                panelData.add(resultSet.getString(3));
            }
            if (panelData.isEmpty()) panelData.add("Brak powiadomień");
            resultSet.close();
        } catch (SQLException ex) {
            panelData.add("Brak powiadomień");
            catchServe(ex);
        }
    }

    /**
     * Metoda wyszukująca poprawność wprowadzonych danych przez użytkownika w bazie
     *
     * @return Zwraca informację o tym, czy podane dane zostały znalezione
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private boolean findRecoveredUser() throws SQLException {
        String query = "SELECT * " + "FROM users " + "WHERE user_nickname = ? " + "AND user_coverage_key = ?";
        PreparedStatement preparedState = connect.prepareStatement(query);
        preparedState.setString(1, dataList.get(1));
        preparedState.setString(2, dataList.get(2));
        ResultSet resultSet = preparedState.executeQuery();
        return resultSet.next();
    }

    /**
     * Metoda generująca klucz do odzyskiwania konta
     *
     * @return Zwraca wygenerowany klucz
     */
    private int generateKey() {
        return (new Random().nextInt(999999 - 100000) + 100000);
    }

    /**
     * Metoda wysyłająca nowe dane użytkownika uzyskane przy odzyskiwaniu konta do bazy
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private String sendNewUserData() throws SQLException {
        String key = Integer.toString(generateKey());
        String update_query = "UPDATE users " + "SET user_coverage_key = ?, " + "user_password = ? " + "WHERE user_nickname = ? " + "AND user_coverage_key = ?";
        PreparedStatement preparedStatement = connect.prepareStatement(update_query);
        preparedStatement.setString(1, key);
        preparedStatement.setString(2, dataList.get(3));
        preparedStatement.setString(3, dataList.get(1));
        preparedStatement.setString(4, dataList.get(2));
        preparedStatement.executeQuery();
        preparedStatement.close();
        return key;
    }

    /**
     * Metoda umożliwiająca na zapis zmienionego hasła do bazy i wygenerowanie nowego klucza do odzyskiwania hasła
     */
    public void recoveryData() {
        try {
            if (findRecoveredUser()) {
                String key = sendNewUserData();
                commitQuery();
                new Logs("[ " + new java.util.Date() + " ] " + "Recovered password", "BazaDanych", "info");
                panelData.add("Pomyślnie zmieniono hasło!\n\nTwój nowy klucz zapasowy to: " + key + "\n Zapisz go w bezpiecznym miejscu!");
            } else {
                panelData.add("Wystąpił błąd przy próbie zmiany hasła!");
            }
        } catch (Exception ex) {
            catchServe(ex);
        }
    }

    /**
     * Metoda tworząca tabelę zawierającą dane logowania użytkowników
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createTableUsers() throws SQLException {
        String users = "CREATE TABLE users(" + "user_id NUMBER(6) PRIMARY KEY, " + "user_nickname VARCHAR2(30), " + "user_password VARCHAR2(200)," + " user_coverage_key VARCHAR2(6) NOT NULL, " + "user_logged VARCHAR2(3) DEFAULT 'no', " + "user_rank VARCHAR2(30) DEFAULT 'user')";
        state.executeUpdate(users);
        ekranSerwer.setMessage("Pomyślnie utworzono tabele dla użytkowników!\n");
        panelData.add("Pomyślnie utworzono tabele dla użytkowników!\n");
    }

    /**
     * Metoda tworząca tabelę zawierającą dane ogólne użytkowników
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createTableUsersData() throws SQLException {
        String usersData = "CREATE TABLE users_data(" + "user_data_id NUMBER(6) PRIMARY KEY, " + "user_name VARCHAR2(30), " + "user_surname VARCHAR2(30), " + "user_email VARCHAR2(30), " + "user_phone_number NUMBER(12), " + "user_id NUMBER(6) REFERENCES users(user_id))";
        state.executeUpdate(usersData);
        ekranSerwer.setMessage("Pomyślnie utworzono tabele dla danych użytkowników!\n");
        panelData.add("Pomyślnie utworzono tabele dla danych użytkowników!\n");
    }

    /**
     * Metoda tworząca tabelę zawierającą dane płyt DVD
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createTableDvdsData() throws SQLException {
        String dvdsData = "CREATE TABLE dvds_data(" + "dvd_id NUMBER(6) PRIMARY KEY, " + "film_name VARCHAR2(50), " + "film_direction VARCHAR2(50), " + "film_type VARCHAR2(50), " + "country VARCHAR2(30), " + "production_year VARCHAR2(10), " + "film_language VARCHAR2(30), " + "video_length VARCHAR2(15), " + "number_of_copies NUMBER(6) DEFAULT '0'," + "day_payment VARCHAR2(30) DEFAULT '0 zl')";
        state.executeUpdate(dvdsData);
        ekranSerwer.setMessage("Pomyślnie utworzono tabele dla danych płyt DVD!\n");
        panelData.add("Pomyślnie utworzono tabele dla danych płyt DVD!\n");
    }

    /**
     * Metoda tworząca tabelę zawierającą dane wypożyczeń
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createTableRents() throws SQLException {
        String rents = "CREATE TABLE rents(" + "rent_id NUMBER(6) PRIMARY KEY, " + "dvd_id NUMBER(6) CONSTRAINT rents_dvd_id_fk REFERENCES dvds_data(dvd_id), " + "user_id NUMBER(6) CONSTRAINT rents_user_id_fk REFERENCES users(user_id), " + "rent_date DATE)";
        state.executeUpdate(rents);
        ekranSerwer.setMessage("Pomyślnie utworzono tabele dla wypożyczeń płyt DVD!\n");
        panelData.add("Pomyślnie utworzono tabele dla wypożyczeń płyt DVD!\n");
    }

    /**
     * Metoda tworząca tabelę zawierającą dane zwrotów
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createTableReturns() throws SQLException {
        String returns = "CREATE TABLE returns(" + "return_id NUMBER(6) PRIMARY KEY, " + "dvd_id NUMBER(6) CONSTRAINT returns_dvd_id_fk REFERENCES dvds_data(dvd_id), " + "user_id NUMBER(6) CONSTRAINT returns_user_id_fk REFERENCES users(user_id), " + "rent_date DATE, " + "return_date DATE)";
        state.executeUpdate(returns);
        ekranSerwer.setMessage("Pomyślnie utworzono tabele dla zwrotów płyt DVD!\n");
        panelData.add("Pomyślnie utworzono tabele dla zwrotów płyt DVD!\n");
    }

    /**
     * Metoda tworząca tabelę zawierającą dane rachunków
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createTableBills() throws SQLException {
        String bills = "CREATE TABLE bills(" + "bill_id NUMBER(6) PRIMARY KEY, " + "user_id NUMBER(6) CONSTRAINT bills_user_id_fk REFERENCES users(user_id), " + "NIP NUMBER(10), " + "REGON NUMBER(9), " + "PESEL NUMBER(11), " + "Kwota NUMBER, " + "Data DATE)";
        state.executeUpdate(bills);
        ekranSerwer.setMessage("Pomyślnie utworzono tabele dla rachunków klientów!\n");
        panelData.add("Pomyślnie utworzono tabele dla rachunków klientów!\n");
    }

    /**
     * Metoda tworząca tabelę zawierającą dane rezerwacji
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createTableReservations() throws SQLException {
        String reservations = "CREATE TABLE reservations(" + "reservation_id NUMBER(6) PRIMARY KEY, " + "user_id NUMBER(6) CONSTRAINT reservations_user_id_fk REFERENCES users(user_id), " + "dvd_id NUMBER(6) CONSTRAINT reservations_dvd_id_fk REFERENCES dvds_data(dvd_id))";
        state.executeUpdate(reservations);
        ekranSerwer.setMessage("Pomyślnie utworzono tabele dla rezerwacji klientów!\n");
        panelData.add("Pomyślnie utworzono tabele dla rezerwacji klientów!\n");
    }

    /**
     * Metoda tworząca sekwencje id użytkowników
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createUsersSequence() throws SQLException {
        String sequence1 = "CREATE SEQUENCE user_id_seq START WITH 1";
        state.executeUpdate(sequence1);
        ekranSerwer.setMessage("Pomyślnie utworzono sekwencje ID użytkowników!\n");
        panelData.add("Pomyślnie utworzono sekwencje ID użytkowników!\n");
    }

    /**
     * Metoda tworząca sekwencje id danych użytkowników
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createUsersDataSequence() throws SQLException {
        String sequence2 = "CREATE SEQUENCE user_data_id_seq START WITH 1";
        state.executeUpdate(sequence2);
        ekranSerwer.setMessage("Pomyślnie utworzono sekwencje ID danych użytkowników!\n");
        panelData.add("Pomyślnie utworzono sekwencje ID danych użytkowników!\n");
    }

    /**
     * Metoda tworząca sekwencje id płyt DVD
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createDvdsSequence() throws SQLException {
        String sequence3 = "CREATE SEQUENCE dvd_id_seq START WITH 1";
        state.executeUpdate(sequence3);
        ekranSerwer.setMessage("Pomyślnie utworzono sekwencje ID płyt DVD!\n");
        panelData.add("Pomyślnie utworzono sekwencje ID płyt DVD!\n");
    }

    /**
     * Metoda tworząca sekwencje id wypożyczeń
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createRentsSequence() throws SQLException {
        String sequence4 = "CREATE SEQUENCE rent_id_seq START WITH 1";
        state.executeUpdate(sequence4);
        ekranSerwer.setMessage("Pomyślnie utworzono sekwencje ID wypożyczeń użytkowników!\n");
        panelData.add("Pomyślnie utworzono sekwencje ID wypożyczeń użytkowników!\n");
    }

    /**
     * Metoda tworząca sekwencje id zwrotów
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createReturnsSequence() throws SQLException {
        String sequence5 = "CREATE SEQUENCE return_id_seq START WITH 1";
        state.executeUpdate(sequence5);
        ekranSerwer.setMessage("Pomyślnie utworzono sekwencje ID zwrotów użytkowników!\n");
        panelData.add("Pomyślnie utworzono sekwencje ID zwrotów użytkowników!\n");
    }

    /**
     * Metoda tworząca sekwencje id rachunków
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createBillsSequence() throws SQLException {
        String sequence6 = "CREATE SEQUENCE bill_id_seq START WITH 1";
        state.executeUpdate(sequence6);
        ekranSerwer.setMessage("Pomyślnie utworzono sekwencje ID rachunków użytkowników!\n");
        panelData.add("Pomyślnie utworzono sekwencje ID rachunków użytkowników!\n");
    }

    /**
     * Metoda tworząca sekwencje id rezerwacji
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void createReservationsSequence() throws SQLException {
        String sequence7 = "CREATE SEQUENCE reservation_id_seq START WITH 1";
        state.executeUpdate(sequence7);
        ekranSerwer.setMessage("Pomyślnie utworzono sekwencje ID rezerwacji użytkowników!\n");
        panelData.add("Pomyślnie utworzono sekwencje ID rezerwacji użytkowników!\n");
    }

    /**
     * Metoda usuwająca tabelę zawierającą dane logowania użytkowników
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void dropTableUsers() throws SQLException {
        String dropTable1 = "DROP TABLE users CASCADE CONSTRAINTS";
        state.executeUpdate(dropTable1);
        ekranSerwer.setMessage("Pomyślnie usunieto tabele z użytkownikami!\n");
        panelData.add("Pomyślnie usunieto tabele z użytkownikami!\n");
    }

    /**
     * Metoda usuwająca tabelę zawierającą dane ogólne użytkowników
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void dropTableUsersData() throws SQLException {
        String dropTable2 = "DROP TABLE users_data CASCADE CONSTRAINTS";
        state.executeUpdate(dropTable2);
        ekranSerwer.setMessage("Pomyślnie usunieto tabele z danymi użytkowników!\n");
        panelData.add("Pomyślnie usunieto tabele z danymi użytkowników!\n");
    }

    /**
     * Metoda usuwająca tabelę zawierającą dane płyt DVD
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void dropTableDvdsData() throws SQLException {
        String dropTable3 = "DROP TABLE dvds_data CASCADE CONSTRAINTS";
        state.executeUpdate(dropTable3);
        ekranSerwer.setMessage("Pomyślnie usunieto tabele z danymi płyt DVD!\n");
        panelData.add("Pomyślnie usunieto tabele z danymi płyt DVD!\n");
    }

    /**
     * Metoda usuwająca tabelę zawierającą dane wypożyczeń
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void dropTableRents() throws SQLException {
        String dropTable4 = "DROP TABLE rents CASCADE CONSTRAINTS";
        state.executeUpdate(dropTable4);
        ekranSerwer.setMessage("Pomyślnie usunieto tabele z wypożyczeniami płyt DVD!\n");
        panelData.add("Pomyślnie usunieto tabele z wypożyczeniami płyt DVD!\n");
    }

    /**
     * Metoda usuwająca tabelę zawierającą dane zwrotów
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void dropTableReturns() throws SQLException {
        String dropTable5 = "DROP TABLE returns CASCADE CONSTRAINTS";
        state.executeUpdate(dropTable5);
        ekranSerwer.setMessage("Pomyślnie usunieto tabele ze zwrotami płyt DVD!\n");
        panelData.add("Pomyślnie usunieto tabele ze zwrotami płyt DVD!\n");
    }

    /**
     * Metoda usuwająca tabelę zawierającą dane rachunków
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void dropTableBills() throws SQLException {
        String dropTable6 = "DROP TABLE bills CASCADE CONSTRAINTS";
        state.executeUpdate(dropTable6);
        ekranSerwer.setMessage("Pomyślnie usunieto tabele z rachunkami klientów!\n");
        panelData.add("Pomyślnie usunieto tabele z rachunkami klientów!\n");
    }

    /**
     * Metoda usuwająca tabelę zawierającą dane rezerwacji
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void dropTableReservations() throws SQLException {
        String dropTable7 = "DROP TABLE reservations CASCADE CONSTRAINTS";
        state.executeUpdate(dropTable7);
        ekranSerwer.setMessage("Pomyślnie usunieto tabele z rezerwacjami klientów!\n");
        panelData.add("Pomyślnie usunieto tabele z rezerwacjami klientów!\n");
    }

    /**
     * Metoda usuwająca wszystkie dane zawarte w bazie danych
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void deleteFromAllTables() throws SQLException {
        String deleteFromTable1 = "DELETE FROM users WHERE user_rank != 'admin'";
        String deleteFromTable2 = "DELETE FROM users_data";
        String deleteFromTable3 = "DELETE FROM dvds_data";
        String deleteFromTable4 = "DELETE FROM rents";
        String deleteFromTable5 = "DELETE FROM returns";
        String deleteFromTable6 = "DELETE FROM bills";
        String deleteFromTable7 = "DELETE FROM reservations";
        state.executeUpdate(deleteFromTable7);
        state.executeUpdate(deleteFromTable6);
        state.executeUpdate(deleteFromTable5);
        state.executeUpdate(deleteFromTable4);
        state.executeUpdate(deleteFromTable3);
        state.executeUpdate(deleteFromTable2);
        state.executeUpdate(deleteFromTable1);
        ekranSerwer.setMessage("Pomyślnie usunięto wszystkie dane ze wszystkich tabel!\n");
        panelData.add("Pomyślnie usunięto wszystkie dane ze wszystkich tabel!\n");
    }

    /**
     * Metoda usuwająca sekwencję id użytkowników
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void deleteUsersSequence() throws SQLException {
        String dropSequence1 = "DROP SEQUENCE user_id_seq";
        state.executeUpdate(dropSequence1);
        ekranSerwer.setMessage("Pomyślnie usunięto sekwencje ID użytkowników!\n");
        panelData.add("Pomyślnie usunięto sekwencje ID użytkowników!\n");
    }

    /**
     * Metoda usuwająca sekwencję id danych użytkowników
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void deleteUsersDataSequence() throws SQLException {
        String dropSequence2 = "DROP SEQUENCE user_data_id_seq";
        state.executeUpdate(dropSequence2);
        ekranSerwer.setMessage("Pomyślnie usunięto sekwencje ID danych użytkowników!\n");
        panelData.add("Pomyślnie usunięto sekwencje ID danych użytkowników!\n");
    }

    /**
     * Metoda usuwająca sekwencję id płyt DVD
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void deleteDvdsSequence() throws SQLException {
        String dropSequence3 = "DROP SEQUENCE dvd_id_seq";
        state.executeUpdate(dropSequence3);
        ekranSerwer.setMessage("Pomyślnie usunięto sekwencje ID płyt DVD!\n");
        panelData.add("Pomyślnie usunięto sekwencje ID płyt DVD!\n");
    }

    /**
     * Metoda usuwająca sekwencję id wypożyczeń
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void deleteRentsSequence() throws SQLException {
        String dropSequence4 = "DROP SEQUENCE rent_id_seq";
        state.executeUpdate(dropSequence4);
        ekranSerwer.setMessage("Pomyślnie usunięto sekwencje ID wypożyczeń użytkowników!\n");
        panelData.add("Pomyślnie usunięto sekwencje ID wypożyczeń użytkowników!\n");
    }

    /**
     * Metoda usuwająca sekwencję id zwrotów
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void deleteReturnsSequence() throws SQLException {
        String dropSequence5 = "DROP SEQUENCE return_id_seq";
        state.executeUpdate(dropSequence5);
        ekranSerwer.setMessage("Pomyślnie usunięto sekwencje ID zwrotów użytkowników!\n");
        panelData.add("Pomyślnie usunięto sekwencje ID zwrotów użytkowników!\n");
    }

    /**
     * Metoda usuwająca sekwencję id rachunków
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void deleteBillsSequence() throws SQLException {
        String dropSequence6 = "DROP SEQUENCE bill_id_seq";
        state.executeUpdate(dropSequence6);
        ekranSerwer.setMessage("Pomyślnie usunięto sekwencje ID rachunków użytkowników!\n");
        panelData.add("Pomyślnie usunięto sekwencje ID rachunków użytkowników!\n");
    }

    /**
     * Metoda usuwająca sekwencję id rezerwacji
     *
     * @throws SQLException Wyjątek rzucany przy powstaniu wyjątku języka SQL
     */
    private void deleteReservationsSequence() throws SQLException {
        String dropSequence7 = "DROP SEQUENCE reservation_id_seq";
        state.executeUpdate(dropSequence7);
        ekranSerwer.setMessage("Pomyślnie usunięto sekwencje ID rezerwacji użytkowników!\n");
        panelData.add("Pomyślnie usunięto sekwencje ID rezerwacji użytkowników!\n");
    }

    /**
     * Metoda umożliwiająca wykonywanie zapytań do bazy
     *
     * @param option Jest to parametr pomagający określić, jaka operacja na bazie ma zostać wykonana
     */
    private void zarzadzajBaza(@NotNull String option) {
        try {
            switch (option) {
                case "CREATE TABLE users" -> createTableUsers();
                case "CREATE TABLE users_data" -> createTableUsersData();
                case "CREATE TABLE dvds_data" -> createTableDvdsData();
                case "CREATE TABLE rents" -> createTableRents();
                case "CREATE TABLE returns" -> createTableReturns();
                case "CREATE TABLE bills" -> createTableBills();
                case "CREATE TABLE reservations" -> createTableReservations();
                case "DROP TABLE users" -> dropTableUsers();
                case "DROP TABLE users_data" -> dropTableUsersData();
                case "DROP TABLE dvds_data" -> dropTableDvdsData();
                case "DROP TABLE rents" -> dropTableRents();
                case "DROP TABLE returns" -> dropTableReturns();
                case "DROP TABLE bills" -> dropTableBills();
                case "DROP TABLE reservations" -> dropTableReservations();
                case "CREATE SEQUENCE user_id" -> createUsersSequence();
                case "CREATE SEQUENCE user_data_id" -> createUsersDataSequence();
                case "CREATE SEQUENCE dvd_id" -> createDvdsSequence();
                case "CREATE SEQUENCE rent_id" -> createRentsSequence();
                case "CREATE SEQUENCE return_id" -> createReturnsSequence();
                case "CREATE SEQUENCE bill_id" -> createBillsSequence();
                case "CREATE SEQUENCE reservation_id" -> createReservationsSequence();
                case "DROP SEQUENCE user_id" -> deleteUsersSequence();
                case "DROP SEQUENCE user_data_id" -> deleteUsersDataSequence();
                case "DROP SEQUENCE dvd_id" -> deleteDvdsSequence();
                case "DROP SEQUENCE rent_id" -> deleteRentsSequence();
                case "DROP SEQUENCE return_id" -> deleteReturnsSequence();
                case "DROP SEQUENCE bill_id" -> deleteBillsSequence();
                case "DROP SEQUENCE reservation_id" -> deleteReservationsSequence();
                case "DeleteFromTables" -> deleteFromAllTables();
                default -> ekranSerwer.setMessage("Inna opcja w bazie danych");
            }
            commitQuery();
            new Logs("[ " + new java.util.Date() + " ] " + "Changed something in database construction", "BazaDanych", "info");
        } catch (SQLException ex) {
            ekranSerwer.setMessage(ex.getMessage());
            panelData.add(ex.getMessage());
        }
    }
}