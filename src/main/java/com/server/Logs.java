package com.server;

import org.apache.log4j.*;

import java.io.File;
import java.io.FileWriter;

/**
 * Klasa zawierająca pola i metody umożliwiające zapis zdarzeń generowanych przez aplikację do pliku
 *
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class Logs {
    /**
     * Atrybut określający wiadomość zdarzenia
     */
    private final String logMessage;
    /**
     * Atrybut służący do ustawienia odpowiedniego typu loga zdarzenia
     */
    private final Logger log;
    /**
     * Atrybut określający nazwę klasy, z której aktualnie pobierane są zdarzenia
     */
    private final String className;

    /**
     * Konstruktor umożliwiający inicjację pól finalnych i wywołujący metodę odpowiedzialną za zapis zdarzenia do pliku
     *
     * @param logMessage  parametr określający wiadomość zdarzenia
     * @param className   parametr określający nazwę klasy, z której aktualnie pobierane są zdarzenia
     * @param messageType parametr określający typ zdarzenia
     */
    public Logs(String logMessage, String className, String messageType) {
        this.logMessage = logMessage;
        this.className = className;
        this.log = LogManager.getLogger(className + ".class");
        writeMessage(messageType);
    }

    /**
     * Metoda odpowiedzialna za zapis zdarzenia do pliku
     *
     * @param messageType parametr określający typ zdarzenia
     */
    private void writeMessage(String messageType) {
        try {
            File folder = new File("logs");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File file = new File("logs/logs.log");
            FileWriter fileWriter = new FileWriter(file, true);
            switch (messageType) {
                case "info" -> log.info(this.logMessage);
                case "warn" -> log.warn(this.logMessage);
                case "error" -> log.error(this.logMessage);
                case "fatal" -> log.fatal(this.logMessage);
            }
            fileWriter.write("[" + messageType.toUpperCase() + "] " + this.className + ": " + this.logMessage + "\n");
            fileWriter.close();
        } catch (Exception e) {
            log.fatal("[ " + new java.util.Date() + " ] " + "Error while writing log to file");
        }
    }
}
