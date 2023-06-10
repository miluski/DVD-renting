package com.server;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Klasa pozwalająca na działanie wątków niezależnie od GUI serwera
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class BackgroundHandler extends SwingWorker<Void, Void> implements Serializable {
    /**
     * Instancja klasy EkranSerwer
     */
    private final EkranSerwer ekranSerwer;
    /**
     * Konstruktur umożliwiający inicjację działania wątku w tle
     * @param ekranSerwer Instancja klasy EkranSerwer
     */
    public BackgroundHandler(EkranSerwer ekranSerwer){
        this.ekranSerwer = ekranSerwer;
    }
    /**
     * Klasa implementująca interfejs Runnable tak, aby można było tworzyć nowe wątki na serwerze
     */
    private class ClientHandler implements Runnable, Serializable {
        /**
         * Przesłonięcie metody konwertującej dane na typ łańcucha znaków
         */
        @Override
        public String toString(){
            return super.toString();
        }

        /**
         * Metoda, której zadaniem jest próba podłączenia się do bazy danych i zwrócenie rezultatu
         * @return Zwraca odebraną listę danych od bazy danych
         * @throws ExecutionException Wyjątek występujący przy wykonywaniu
         * @throws InterruptedException Wyjątek występujący przy przerwaniu
         * @throws TimeoutException Wyjątek występujący przy przekroczeniu czasu oczekiwania
         */
        private List<String> transferDataWithDatabase() throws ExecutionException, InterruptedException, TimeoutException {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
            Future<String> future = executor.submit(bazaDanych);
            future.get(2, TimeUnit.SECONDS);
            return new ArrayList<>(bazaDanych.getPanelData());
        }

        /**
         * Metoda wysyłająca dane do klienta
         * @param dataList Wysyłana lista danych
         * @throws IOException Wyjątek następujący przy problemach wejścia/wyjścia
         */
        private void sendDataToClient(List<String> dataList) throws IOException {
            OutputStream socketSend = ekranSerwer.client.getOutputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(dataList);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            socketSend.write(byteArray);
            socketSend.flush();
        }
        /**
         * Przesłonięcie metody run, obsługującej uruchomiony wątek
         */
        @Override
        public void run() {
            try {
                InputStream socketReceive = ekranSerwer.client.getInputStream();
                byte[] receivedBytes = new byte[2048];
                Object receivedObject = new ObjectInputStream(new ByteArrayInputStream(receivedBytes, 0, socketReceive.read(receivedBytes))).readObject();
                ekranSerwer.panelData.clear();
                ekranSerwer.panelData.addAll((List<String>) receivedObject);
                sendDataToClient(transferDataWithDatabase());
            } catch (Exception except) {
                ekranSerwer.setMessage(except.getMessage());
                new Logs("[ " + new java.util.Date() + " ] " + except.getMessage(), "EkranSerwer", "fatal");
            }
        }
    }
    /**
     * Przesłonięcie metody doInBackground, dzięki czemu wątki mogą działać w tle nie wpływając na GUI serwera
     * @return Nie zwraca nic
     */
    @Override
    protected Void doInBackground() {
        Thread thread = new Thread(new ClientHandler());
        ekranSerwer.clients.add(thread);
        thread.start();
        new Logs("[ " + new java.util.Date() + " ]" + " New thread called " + thread.getName() + " was created ", "EkranSerwer", "info");
        return null;
    }
}
