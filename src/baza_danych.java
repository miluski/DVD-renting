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
    static String nick, pass, name, surname, email, phone_number;
    /** Metoda polacz_z_baza pozwala na zainicjowanie parametrow polaczenia z baza danych **/
    public static void polacz_z_baza() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect = DriverManager.getConnection("jdbc:oracle:thin:@192.168.137.1:1521:xe", "system", "admin");
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
                String query = "INSERT INTO users (user_nickname, user_password, user_id) VALUES (?, ?, user_id_seq.nextval)";
                String query2 = "INSERT INTO users_data (user_name, user_surname, user_email, user_phone_number, user_data_id, user_id) VALUES (?, ?, ?, ?, user_data_id_seq.nextval, user_id_seq.currval)";
                PreparedStatement preparedstate = connect.prepareStatement(query);
                preparedstate.setString(1, nick);
                preparedstate.setString(2, pass);
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
        try {
            String query = "SELECT * FROM users WHERE user_nickname = ? AND user_password = ?";
            PreparedStatement preparedstate = connect.prepareStatement(query);
            preparedstate.setString(1, serwer.nick);
            preparedstate.setString(2, serwer.pass);
            ResultSet result = preparedstate.executeQuery();
            if (result.next()) {
                String rank = result.getString("user_rank");
                System.out.print("Pomyslnie zalogowano! ");
                if (rank.equals("admin")) {
                    System.out.print("Uzytkownik to administrator \n");
                    serwer.rank = rank;
                } else if (rank.equals("user")) {
                    System.out.print("To zwykly uzytkownik \n");
                    serwer.rank = rank;
                }
                EkranLogowania.wiadomosc = " ";
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

class zarzadzaj_baza extends baza_danych {
    zarzadzaj_baza(int option) {
        String users = "CREATE TABLE users(\"" +
                "user_id NUMBER(6) PRIMARY KEY,\"" +
                "user_nickname VARCHAR2(30),\"" +
                "user_password VARCHAR2(30)," +
                "user_rank VARCHAR2(30) DEFAULT 'user')";
        String users_data = "CREATE TABLE users_data(\"" +
                            "user_data_id NUMBER(6) PRIMARY KEY,\""+
                            "user_name VARCHAR2(30),\"" +
                            "user_surname VARCHAR2(30),\"" +
                            "user_email VARCHAR2(30),\"" +
                            "user_phone_number NUMBER(12),\"" +
                            "user_id NUMBER(6) REFERENCES users(user_id))";
        String sequence1 = "CREATE SEQUENCE user_id_seq START WITH 1;";
        String sequence2 = "CREATE SEQUENCE user_data_id_seq START WITH 1;";
        try {
            switch(option) {
                case 1:
                    state.executeUpdate(users);
                case 2:
                    state.executeUpdate(users_data);
                case 3:
                    state.executeUpdate(sequence1);
                    state.executeUpdate(sequence2);
            }
            String commit_query = "COMMIT";
            state.executeUpdate(commit_query);
        }
        catch (SQLException except) {
            System.out.println("Kod bledu: " + except);
        }
    }
}