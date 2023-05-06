package com.client;
import java.util.ArrayList;
import java.util.List;
/**
 * Klasa zawierająca pola pozwalające na przechowywanie powiadomień dla użytkowników/administratorów
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class Powiadomienia {
    /**
     * Konstruktor umożliwiający tworzenie instancji klasy
     */
    public Powiadomienia(){
    }
    /**
     * Atrybut będący listą ciągów znaków, który przechowuje powiadomienia dotyczące rezerwacji płyt DVD przez klientów
     */
    public static List<String> adminReservationNotifications = new ArrayList<>();
    /**
     * Atrybut będący listą ciągów znaków, który przechowuje powiadomienia dotyczące przetrzymywania płyt DVD przez wszystkich klientów
     */
    public static List<String> adminDetentionDVDsNotifications = new ArrayList<>();
    /**
     * Atrybut będący listą ciągów znaków, który przechowuje powiadomienia dotyczące przetrzymywania płyt DVD przez klientów
     */
    public static List<String> userDetentionDVDsNotifications = new ArrayList<>();
}
