/**
 * @file Logs.java
 * @brief Plik zawierający metody, pola i klasy, które pozwalają na logowanie zdarzeń aplikacji do pliku
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
package com.server;
import org.apache.log4j.*;
import java.io.File;
import java.io.FileWriter;
/**
 * Klasa zawierająca pola i metody umożliwiające zapis zdarzeń generowanych przez aplikację do pliku
 */
public class Logs {
    /**
     * Atrybut określający wiadomość zdarzenia
     */
    private static String logMessage;
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
     * @param logMessage parametr określający wiadomość zdarzenia
     * @param className parametr określający nazwę klasy, z której aktualnie pobierane są zdarzenia
     * @param messageType parametr określający typ zdarzenia
     */
    public Logs(String logMessage, String className, String messageType) {
        Logs.logMessage = logMessage;
        this.className = className;
        this.log = LogManager.getLogger(className+".class");
        writeMessage(messageType);
    }
    /**
     * Metoda zwracająca wiadomość loga
     * @return Zwraca wiadomość loga
     */
    public static String getLogMessage(){
        return logMessage;
    }
    /**
     * Metoda zwracająca nazwę klasy
     * @return Zwraca nazwę klasy
     */
    public String getClassName(){
        return className;
    }
    /**
     * Metoda odpowiedzialna za zapis zdarzenia do pliku
     * @param messageType parametr określający typ zdarzenia
     */
    private void writeMessage(String messageType){
        try {
            File folder = new File("logs");
            if(!folder.exists()){
                if(folder.mkdir()){
                    EkranSerwer.setMessage("Utworzono folder dla logów");
                }
            }
            File file = new File("logs/logs.log");
            FileWriter fileWriter = new FileWriter(file,true);
            switch(messageType){
                case "info" -> log.info(this.logMessage);
                case "warn" -> log.warn(this.logMessage);
                case "error" -> log.error(this.logMessage);
                case "fatal" -> log.fatal(this.logMessage);
            }
            fileWriter.write("[" + messageType.toUpperCase() + "] " + this.className + ": " + this.logMessage+"\n");
            fileWriter.close();
        }
        catch (Exception e){
            EkranSerwer.setMessage(e.getMessage());
            log.fatal("[ " + new java.util.Date() + " ] " + "Error while writing log to file");
        }
    }
}
