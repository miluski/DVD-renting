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
    /** Metoda polacz_z_baza pozwala na zainicjowanie parametrow polaczenia z baza danych **/
    public static void polacz_z_baza() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin");
            state = connect.createStatement();
            if (serwer.serving == 2) {
                new wysylanie_danych();
            }
            if(serwer.receiving == 1) {
                new odbieranie_danych();
            }
            if(serwer.management == 3) {
                new zarzadzaj_baza(2);
            }
            if(serwer.recovering == 4){
                new odzyskiwanie_danych();
            }
            connect.close();
        }
        catch (Exception except) {
            System.out.println("Kod bledu: " + except);
        }
    }
}

class wysylanie_danych extends baza_danych {
    wysylanie_danych() {
        serwer.receiving = 2;
        serwer.serving = 2;
        serwer.recovering = 2;
        serwer.management = 2;
        try {
            String query0 = "SELECT * FROM users WHERE user_nickname = ?";
            PreparedStatement preparedstate0 = connect.prepareStatement(query0);
            preparedstate0.setString(1, nick);
            ResultSet result = preparedstate0.executeQuery();
            if (result.next()) {
                String nicknam = result.getString("user_nickname");
                EkranUtworzKonto.blad = "Podany uzytkownik istnieje juz w bazie danych!";
                System.out.print(nicknam);
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
        }
    }
}

class odbieranie_danych extends baza_danych {
    odbieranie_danych() {
        serwer.serving = 1;
        serwer.receiving = 1;
        serwer.recovering = 1;
        serwer.management = 1;
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
                    // TODO: po frontendzie glownej aplikacji gdzie bedzie mozliwosc wylogowywania sie tutaj dac user_logged = 'yes'
                    String query_logged = "UPDATE users SET user_logged = 'no' WHERE user_nickname = ? AND user_password = ?";
                    preparedstate = connect.prepareStatement(query_logged);
                    preparedstate.setString(1,serwer.nick);
                    preparedstate.setString(2,serwer.pass);
                    preparedstate.executeQuery();
                    if (rank.equals("admin")) {
                        System.out.print("Uzytkownik to administrator \n");
                        serwer.rank = rank;
                    } else if (rank.equals("user")) {
                        System.out.print("To zwykly uzytkownik \n");
                        serwer.rank = rank;
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
        }
        catch (SQLException except) {
            System.out.println("Kod bledu: " + except);
        }
    }
}
class odzyskiwanie_danych extends baza_danych {
    odzyskiwanie_danych() {
        serwer.receiving = 4;
        serwer.serving = 4;
        serwer.recovering = 4;
        serwer.management = 4;
        try {
            String query = "SELECT user_password FROM users WHERE user_nickname = ? AND user_coverage_key = ?";
            PreparedStatement preparedState = connect.prepareStatement(query);
            preparedState.setString(1,nick);
            preparedState.setString(2,coverage_key);
            ResultSet resultSet = preparedState.executeQuery();
            if(resultSet.next()){
                System.out.print("Mozna zmienic haslo");
            }
            else{
                System.out.print("Blad");
            }
        }
        catch(SQLException except){
            System.out.println("Kod bledu: " + except);
        }
    }
}
class zarzadzaj_baza extends baza_danych {
    zarzadzaj_baza(int option) {
        serwer.receiving = 3;
        serwer.serving = 3;
        serwer.recovering = 3;
        serwer.management = 3;
        String users = "CREATE TABLE users(\"" +
                "user_id NUMBER(6) PRIMARY KEY,\"" +
                "user_nickname VARCHAR2(30),\"" +
                "user_password VARCHAR2(30),\"" +
                "user_coverage_key VARCHAR2(6) NOT NULL,\"" +
                "user_logged VARCHAR2(3) DEFAULT 'no',\"" +
                "user_rank VARCHAR2(30) DEFAULT 'user')";
        String users_data = "CREATE TABLE users_data(\"" +
                            "user_data_id NUMBER(6) PRIMARY KEY,\""+
                            "user_name VARCHAR2(30),\"" +
                            "user_surname VARCHAR2(30),\"" +
                            "user_email VARCHAR2(30),\"" +
                            "user_phone_number NUMBER(12),\"" +
                            "user_id NUMBER(6) REFERENCES users(user_id))";
        String sequence1 = "CREATE SEQUENCE user_id_seq START WITH 1";
        String sequence2 = "CREATE SEQUENCE user_data_id_seq START WITH 1";
        try {
            switch (option) {
                case 1 -> state.executeUpdate(users);
                case 2 -> state.executeUpdate(users_data);
                case 3 -> {
                    state.executeUpdate(sequence1);
                    state.executeUpdate(sequence2);
                }
                default -> System.out.print("Inna opcja");
            }
            String commit_query = "COMMIT";
            state.executeUpdate(commit_query);
        }
        catch (SQLException except) {
            System.out.println("Kod bledu: " + except);
        }
    }
}