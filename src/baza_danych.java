/** @file baza_danych.java
 * @brief plik pozwalajacy na wymiane danych z baza danych
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 0.0.1
 */
import java.sql.*;

/** Klasa obslugujaca przesyl danych z serwera do bazy danych oraz odczyt ich i przesyl do serwera **/
public class baza_danych {
    static Statement state;
    static Connection connect;
    static String nick, pass, name, surname, email, phone_number, coverage_key;
    static String key;
    /** Metoda polacz_z_baza pozwala na zainicjowanie parametrow polaczenia z baza danych **/
    public static void polacz_z_baza() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin");
            state = connect.createStatement();
            switch(serwer.operation){
                case "Register" -> new wymiana_danych(serwer.pass_operation);
                case "Login" -> new wymiana_danych(serwer.pass_operation);
                case "Logout" -> new wymiana_danych(serwer.pass_operation);
                case "Management" -> new zarzadzaj_baza(serwer.management_operation);
                case "Recovery" -> new odzyskiwanie_danych();
                case "DataPass" -> new wymiana_danych(serwer.pass_operation);
            }
            connect.close();
        }
        catch (Exception except) {
            System.out.println("Kod bledu: " + except);
        }
    }
}
class wymiana_danych extends baza_danych {
    wymiana_danych(String data_type) {
        switch(data_type){
            case "Register" -> Register();
            case "Login" -> Login();
            case "Logout" -> Logout();
            case "AddDVD" -> AddDVD();
            case "EditDVD" -> EditDVD(); // TODO Oczekuje na frontend dialogowego z edycja DVD
            case "DeleteDVD" -> DeleteDVD(); // TODO Oczekuje na frontend dialogowego z usuwania DVD
            case "ReviewDVDCollection" -> ReviewDVDCollection(); // TODO Oczekuje na frontend dialogowego z przegladania kolekcji DVD
            case "DVDAvalaible" -> DVDAvalaible(); // TODO Oczekuje na frontend dialogowego z dostepnosci magazynowej DVD
            case "AddSameDVDs" -> AddSameDVDs(); // TODO Oczekuje na frontend dialogowego z dodawania egzemplarzy DVD do magazynu
            case "ReviewClients" -> ReviewClients(); // TODO Oczekuje na frontend dialogowego z przegladania listy klientow
            case "DeleteClient" -> DeleteClient(); // TODO Oczekuje na frontend dialogowego z usuwania klienta
            case "AddClient" -> AddClient(); // TODO Oczekuje na frontend dialogowego z dodawania klienta
            case "RentDVD" -> RentDVD(); // TODO Oczekuje na frontend dialogowego z wypozyczania dostepnych plyt DVD
            case "ReturnDVD" -> ReturnDVD(); // TODO Oczekuje na frontend dialogowego ze zwracania plyt DVD
            case "ReviewRents" -> ReviewRents(); // TODO Oczekuje na frontend dialogowego z przeglądania wszystkich wypożyczeń DVD
            case "ReviewReturns" -> ReviewReturns(); // TODO Oczekuje na frontend dialogowego z przeglądania wszystkich zwrotów DVD
            case "ReviewReservations" -> ReviewReservations(); // TODO Oczekuje na frontend dialogowego z przegladania rezerwacji plyt DVD
            case "NewBill" -> NewBill(); // TODO Oczekuje na frontend dialogowego z wystawiania rachunku
            case "ReviewBills" -> ReviewBills(); // TODO Oczekuje na frontend dialogowego z przegladania rachunkow
        }
    }
    private static void ReviewBills(){

    }
    private static void NewBill(){

    }
    private static void ReviewReservations(){

    }
    private static void ReviewReturns(){

    }
    private static void ReviewRents(){

    }
    private static void ReturnDVD(){

    }
    private static void RentDVD(){

    }
    private static void AddClient(){

    }
    private static void DeleteClient(){

    }
    private static void ReviewClients(){

    }
    private static void AddSameDVDs(){

    }
    private static void DVDAvalaible(){

    }
    private static void ReviewDVDCollection(){

    }
    private static void DeleteDVD(){

    }
    private static void EditDVD(){

    }
    private static void AddDVD(){
        try {
            String query = "INSERT INTO dvds_data (dvd_id, film_name, film_direction, film_type, country, production_year, film_language, video_length, number_of_copies, day_payment) VALUES(dvd_id_seq.nextval,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            int i = 1;
            for(String data:serwer.panel_data){
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
            DialogDodajDVD.message = "Pomyślnie dodano nową płytę DVD!\n";
        }
        catch (SQLException except) {
            System.out.println("Kod bledu: " + except);
            DialogDodajDVD.message = except.getMessage();
        }
    }
    private static void Logout(){
        try {
            String query = "UPDATE users SET user_logged = 'no' WHERE user_nickname = ?";
            PreparedStatement preparedstate = connect.prepareStatement(query);
            preparedstate.setString(1, serwer.nick);
            preparedstate.executeQuery();
            preparedstate.close();
            EkranGlownyAdmin.message = "Pomyślnie wylogowano! \n";
        }
        catch (SQLException except) {
            System.out.println("Kod bledu: " + except);
            EkranGlownyAdmin.message = except.getMessage();
        }
    }
    private static void Login(){
        try {
            String query = "SELECT * FROM users WHERE user_nickname = ? AND user_password = ?";
            PreparedStatement preparedstate = connect.prepareStatement(query);
            preparedstate.setString(1, serwer.nick);
            preparedstate.setString(2, serwer.pass);
            ResultSet result = preparedstate.executeQuery();
            if (result.next()) {
                String rank = result.getString("user_rank");
                String is_logged = result.getString("user_logged");
                if(is_logged.equals("no")) {
                    System.out.print("Pomyslnie zalogowano! ");
                    // TODO: po frontendzie głownej aplikacji dla użytkownika zoptymalizowac kod dotyczący ustawiania zalogowania na dane konto
                    if (rank.equals("admin")) {
                        System.out.print("Uzytkownik to administrator \n");
                        serwer.rank = rank;
                        String query_logged = "UPDATE users SET user_logged = 'yes' WHERE user_nickname = ? AND user_password = ?";
                        PreparedStatement preparedstate2 = connect.prepareStatement(query_logged);
                        preparedstate2.setString(1,serwer.nick);
                        preparedstate2.setString(2,serwer.pass);
                        preparedstate2.executeQuery();
                        preparedstate2.close();
                    } else if (rank.equals("user")) {
                        System.out.print("To zwykly uzytkownik \n");
                        serwer.rank = rank;
                        String query_logged = "UPDATE users SET user_logged = 'no' WHERE user_nickname = ? AND user_password = ?";
                        PreparedStatement preparedstate2 = connect.prepareStatement(query_logged);
                        preparedstate2.setString(1,serwer.nick);
                        preparedstate2.setString(2,serwer.pass);
                        preparedstate2.executeQuery();
                        preparedstate2.close();
                    }
                    else{
                        System.out.print(rank+"\n");
                        serwer.rank = "Nieznana";
                    }
                    EkranLogowania.wiadomosc = "Pomyślnie zalogowano!";
                }
                else if(is_logged.equals("yes")){
                    EkranLogowania.wiadomosc = "Na podane konto ktoś już jest zalogowany!";
                }
            }
            else{
                serwer.rank = "not_logged";
                System.out.print("Nie udana proba logowania!\n");
                EkranLogowania.wiadomosc = "Wprowadzono bledne dane logowania!";
            }
            preparedstate.close();
        }
        catch (SQLException except) {
            System.out.println("Kod bledu: " + except);
            EkranLogowania.wiadomosc = except.getMessage();
        }
    }
    private static void Register(){
        try {
            String query0 = "SELECT * FROM users WHERE user_nickname = ?";
            PreparedStatement preparedstate0 = connect.prepareStatement(query0);
            preparedstate0.setString(1, nick);
            ResultSet result = preparedstate0.executeQuery();
            if (result.next()) {
                EkranUtworzKonto.blad = "Podany uzytkownik istnieje juz w bazie danych!";
            }
            else {
                EkranUtworzKonto.blad = " ";
                String query = "INSERT INTO users (user_nickname, user_password, user_coverage_key, user_id) VALUES (?, ?, ?, user_id_seq.nextval)";
                String query2 = "INSERT INTO users_data (user_name, user_surname, user_email, user_phone_number, user_data_id, user_id) VALUES (?, ?, ?, ?, user_data_id_seq.nextval, user_id_seq.currval)";
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
                String commit_query = "COMMIT";
                state.executeUpdate(commit_query);
            }
            preparedstate0.close();
        } catch (SQLException except) {
            System.out.println("Kod bledu: " + except);
            EkranUtworzKonto.blad = except.getMessage();
        }
    }
}
class odzyskiwanie_danych extends baza_danych {
    odzyskiwanie_danych() {
        try {
            String query = "SELECT * FROM users WHERE user_nickname = ? AND user_coverage_key = ?";
            PreparedStatement preparedState = connect.prepareStatement(query);
            preparedState.setString(1,nick);
            preparedState.setString(2,coverage_key);
            ResultSet resultSet = preparedState.executeQuery();
            if(resultSet.next()){
                key = Integer.toString(EkranUtworzKonto.generacja_klucza());
                String update_query = "UPDATE users SET user_coverage_key = ?, user_password = ? WHERE user_nickname = ? AND user_coverage_key = ?";
                PreparedStatement preparedStatement = connect.prepareStatement(update_query);
                preparedStatement.setString(1,key);
                preparedStatement.setString(2,pass);
                preparedStatement.setString(3,nick);
                preparedStatement.setString(4,coverage_key);
                preparedStatement.executeQuery();
                preparedStatement.close();
                EkranPrzywrocHaslo.message = "Pomyślnie zmieniono hasło!\n\nTwój nowy klucz zapasowy to: " + key + "\n Zapisz go w bezpiecznym miejscu!";
            }
            else{
                EkranPrzywrocHaslo.message = "Wystąpił błąd przy próbie zmiany hasła!";
            }
            preparedState.close();
        }
        catch(SQLException except){
            System.out.println("Kod bledu: " + except);
            EkranPrzywrocHaslo.message = except.getMessage();
        }
    }
}
class zarzadzaj_baza extends baza_danych {
    zarzadzaj_baza(String option) {
        String users = "CREATE TABLE users(user_id NUMBER(6) PRIMARY KEY, user_nickname VARCHAR2(30), user_password VARCHAR2(30), user_coverage_key VARCHAR2(6) NOT NULL, user_logged VARCHAR2(3) DEFAULT 'no', user_rank VARCHAR2(30) DEFAULT 'user')";
        String users_data = "CREATE TABLE users_data(user_data_id NUMBER(6) PRIMARY KEY, user_name VARCHAR2(30), user_surname VARCHAR2(30), user_email VARCHAR2(30), user_phone_number NUMBER(12), user_id NUMBER(6) REFERENCES users(user_id))";
        String dvds_data = "CREATE TABLE dvds_data(dvd_id NUMBER(6) PRIMARY KEY, film_name VARCHAR2(50), film_direction VARCHAR2(50), film_type VARCHAR2(50), country VARCHAR2(30), production_year VARCHAR2(10), film_language VARCHAR2(30), video_length VARCHAR2(15), number_of_copies NUMBER(6) DEFAULT '0',day_payment VARCHAR2(30) DEFAULT '0 zl')";
        String sequence1 = "CREATE SEQUENCE user_id_seq START WITH 1";
        String sequence2 = "CREATE SEQUENCE user_data_id_seq START WITH 1";
        String sequence3 = "CREATE SEQUENCE dvd_id_seq START WITH 1";
        String drop_table_1 = "DROP TABLE users CASCADE CONSTRAINTS";
        String drop_table_2 = "DROP TABLE users_data CASCADE CONSTRAINTS";
        String drop_table_3 = "DROP TABLE dvds_data CASCADE CONSTRAINTS";
        String delete_from_table_1 = "DELETE FROM users WHERE user_rank != 'admin'";
        String delete_from_table_2 = "DELETE FROM users_data";
        String delete_from_table_3 = "DELETE FROM dvds_data";
        String drop_sequence_1 = "DROP SEQUENCE user_id_seq";
        String drop_sequence_2 = "DROP SEQUENCE user_data_id_seq";
        String drop_sequence_3 = "DROP SEQUENCE dvd_id_seq";
        try {
            switch (option) {
                case "CreateTables" -> {
                    state.executeUpdate(users);
                    state.executeUpdate(users_data);
                    state.executeUpdate(dvds_data);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono tabele!\n";
                }
                case "DropTables" -> {
                    state.executeUpdate(drop_table_3);
                    state.executeUpdate(drop_table_2);
                    state.executeUpdate(drop_table_1);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto tabele!\n";
                }
                case "DeleteFromTables" -> {
                    state.executeUpdate(delete_from_table_3);
                    state.executeUpdate(delete_from_table_2);
                    state.executeUpdate(delete_from_table_1);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto dane z tabel!\n";
                }
                case "CreateSequences" -> {
                    state.executeUpdate(sequence1);
                    state.executeUpdate(sequence2);
                    state.executeUpdate(sequence3);
                    EkranGlownyAdmin.message = "Pomyślnie utworzono sekwencje!\n";
                }
                case "DropSequences" -> {
                    state.executeUpdate(drop_sequence_1);
                    state.executeUpdate(drop_sequence_2);
                    state.executeUpdate(drop_sequence_3);
                    EkranGlownyAdmin.message = "Pomyślnie usunięto tabele!\n";
                }
                default -> System.out.print("Inna opcja");
            }
            String commit_query = "COMMIT";
            state.executeUpdate(commit_query);
        }
        catch (SQLException except) {
            System.out.println("Kod bledu: " + except);
            EkranGlownyAdmin.message = except.getMessage();
        }
    }
}