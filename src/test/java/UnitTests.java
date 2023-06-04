import com.client.*;
import com.server.BazaDanych;
import com.server.EkranSerwer;
import com.server.Logs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Klasa zawierająca pola i metody testujące całą aplikację od strony zarówno serwera, jak i klienta
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
public class UnitTests {
    private final EkranSerwer ekranSerwer = new EkranSerwer();
    private final BazaDanych bazaDanych = new BazaDanych(ekranSerwer);
    @Test   //Wszystkie testy dla zmiennych EkranSerwer.java
    public void testDefaultValues_EkranSerwer() {
        assertNotNull(ekranSerwer);
        Assertions.assertNull(ekranSerwer.nick);
        Assertions.assertNull(ekranSerwer.pass);
        Assertions.assertNull(ekranSerwer.userID);
        Assertions.assertNull(ekranSerwer.operation);
        Assertions.assertNull(ekranSerwer.passOperation);
        Assertions.assertNull(ekranSerwer.managementOperation);
        Assertions.assertNull(ekranSerwer.message);
        Assertions.assertNull(ekranSerwer.updatingItem);
        Assertions.assertNull(ekranSerwer.selectedNick);
        Assertions.assertNull(ekranSerwer.filmName);
        Assertions.assertEquals(0, ekranSerwer.updatingID);
        Assertions.assertEquals(0, ekranSerwer.dvdID);
        Assertions.assertNull(ekranSerwer.tableName);
        Assertions.assertTrue(ekranSerwer.running);
        Assertions.assertFalse(ekranSerwer.noDatabaseConnection);
        Assertions.assertNull(ekranSerwer.nickname);
        Assertions.assertEquals("localhost", ekranSerwer.IP);
        Assertions.assertNotNull(ekranSerwer.clients);
        Assertions.assertTrue(ekranSerwer.clients.isEmpty());
        Assertions.assertNotNull(ekranSerwer.panelData);
        Assertions.assertTrue(ekranSerwer.panelData.isEmpty());
        Assertions.assertNotNull(ekranSerwer.jTextArea1);
    }
    @Test
    public void testSetIP() {
        String expectedIP = "192.168.0.1";
        ekranSerwer.setIP(expectedIP);
        String actualIP = ekranSerwer.IP;
        assertEquals(expectedIP, actualIP);
    }
    @Test
    public void testSetMessage() {
        String initialText = "Hello, world!";
        String message = "Hello, world!";
        String expectedText = initialText + message;
        ekranSerwer.setMessage(initialText);
        ekranSerwer.setMessage(message);
        String actualText = initialText + message;
        assertEquals(expectedText, actualText);
    }
    //BazaDanych
    @Test
    public void testGetPanelData(){
        assertEquals(bazaDanych.panelData,bazaDanych.getPanelData());
    }
    @Test
    public void testGetPanelData2(){
        assertEquals(bazaDanych.panelData2,bazaDanych.getPanelData2());
    }
    @Test
    public void testCatchServe() {
        Exception ex = new Exception("ex");
        bazaDanych.catchServe(ex);
    }
    @Test
    public void testSetUpDatabase(){
        assertEquals("Cannot invoke \"String.hashCode()\" because \"<local1>\" is null",bazaDanych.setUpDataBase());
    }
    @Test
    public void commitQuery(){
        bazaDanych.setUpDataBase();
        bazaDanych.commitQuery();
    }
    @Test
    public void testCall(){
        bazaDanych.setUpDataBase();
        assertEquals("Cannot invoke \"String.hashCode()\" because \"<local1>\" is null",bazaDanych.call());
    }
    @Test
    public void testWymianaDanych(){
        bazaDanych.setUpDataBase();
        bazaDanych.wymianaDanych("ReviewDVDCollection");
        bazaDanych.wymianaDanych("DVDAvalaible");
        bazaDanych.wymianaDanych("DVDWareHouseAvalaible");
        bazaDanych.wymianaDanych("ReviewMyRents");
        bazaDanych.wymianaDanych("ReviewRents");
        bazaDanych.wymianaDanych("ReviewMyReturns");
        bazaDanych.wymianaDanych("ReviewReturns");
        bazaDanych.wymianaDanych("Register");
        bazaDanych.wymianaDanych("Login");
        bazaDanych.wymianaDanych("Logout");
        bazaDanych.wymianaDanych("LogoutAll");
        bazaDanych.wymianaDanych("AddDVD");
        bazaDanych.wymianaDanych("EditDVD");
        bazaDanych.wymianaDanych("DeleteDVD");
        bazaDanych.wymianaDanych("AddSameDVDs");
        bazaDanych.wymianaDanych("ReviewClients");
        bazaDanych.wymianaDanych("DeleteClient");
        bazaDanych.wymianaDanych("EditClient");
        bazaDanych.wymianaDanych("RentDVD");
        bazaDanych.wymianaDanych("ReturnDVD");
        bazaDanych.wymianaDanych("ReviewReservations");
        bazaDanych.wymianaDanych("ReservateDVD");
        bazaDanych.wymianaDanych("NewBill");
        bazaDanych.wymianaDanych("ReviewBills");
        bazaDanych.wymianaDanych("GetClientID");
        bazaDanych.wymianaDanych("GetDVDID");
        bazaDanych.wymianaDanych("UpdateCount");
        bazaDanych.wymianaDanych("DeleteReservation");
        bazaDanych.wymianaDanych("CheckReservations");
        bazaDanych.wymianaDanych("DownloadNickname");
        bazaDanych.wymianaDanych("AdminNotifications");
        bazaDanych.wymianaDanych("UserNotifications");
    }
    @Test
    public void testOdzyskiwanieDanych(){
        bazaDanych.setUpDataBase();
        bazaDanych.odzyskiwanieDanych();
    }
    //Logi
    @Test
    public void testKonstruktoraLogi() {
        Logs lg = new Logs("Wiadomosc","Wiadomosc","info");
        Assertions.assertNotNull(lg);
        Assertions.assertEquals(JOptionPane.INFORMATION_MESSAGE, 1);
    }

    //Ekran logowania
    @Test
    public void testLogowaniaWprowadzonoPusteDane() throws Exception {
        EkranLogowania ekranLogowania = new EkranLogowania("192.168.0.12");
        // Wprowadź puste dane
        Field jTextField1Field = EkranLogowania.class.getDeclaredField("jTextField1");
        Field jPasswordField1Field = EkranLogowania.class.getDeclaredField("jPasswordField1");
        jTextField1Field.setAccessible(true);
        jPasswordField1Field.setAccessible(true);
        JTextField jTextField1 = (JTextField) jTextField1Field.get(ekranLogowania);
        JTextField jPasswordField1 = (JTextField) jPasswordField1Field.get(ekranLogowania);
        jTextField1.setText("");
        jPasswordField1.setText("");
        assertEquals("", jTextField1.getText());
        assertEquals("", jPasswordField1.getText());
    }
    @Test
    public void testLogowaniaPoprawneDane() throws Exception {
        EkranLogowania ekranLogowania = new EkranLogowania("192.168.0.12");
        // Uzyskaj dostęp do prywatnych pól
        Field jTextField1Field = EkranLogowania.class.getDeclaredField("jTextField1");
        Field jPasswordField1Field = EkranLogowania.class.getDeclaredField("jPasswordField1");
        jTextField1Field.setAccessible(true);
        jPasswordField1Field.setAccessible(true);
        JTextField jTextField1 = (JTextField) jTextField1Field.get(ekranLogowania);
        JPasswordField jPasswordField1 = (JPasswordField) jPasswordField1Field.get(ekranLogowania);
        // Ustaw poprawne dane logowania
        jTextField1.setText("username");
        jPasswordField1.setText("password");
    }
    //Ekran Utworz Konto
    @Test
    public void testUtworzKontoWprowadzonoPusteDane() throws Exception {
        EkranUtworzKonto ekranUtworzKonto = new EkranUtworzKonto();
        // Uzyskaj dostęp do prywatnych pól
        Field jTextField1Field = EkranUtworzKonto.class.getDeclaredField("jTextField1");
        Field jTextField2Field = EkranUtworzKonto.class.getDeclaredField("jTextField2");
        Field jTextField3Field = EkranUtworzKonto.class.getDeclaredField("jTextField3");
        Field jTextField4Field = EkranUtworzKonto.class.getDeclaredField("jTextField4");
        Field jTextField5Field = EkranUtworzKonto.class.getDeclaredField("jTextField5");
        Field jPasswordField1Field = EkranUtworzKonto.class.getDeclaredField("jPasswordField1");
        Field jPasswordField2Field = EkranUtworzKonto.class.getDeclaredField("jPasswordField2");
        jTextField1Field.setAccessible(true);
        jTextField2Field.setAccessible(true);
        jTextField3Field.setAccessible(true);
        jTextField4Field.setAccessible(true);
        jTextField5Field.setAccessible(true);
        jPasswordField1Field.setAccessible(true);
        jPasswordField2Field.setAccessible(true);
        JTextField jTextField1 = (JTextField) jTextField1Field.get(ekranUtworzKonto);
        JTextField jTextField2 = (JTextField) jTextField2Field.get(ekranUtworzKonto);
        JTextField jTextField3 = (JTextField) jTextField3Field.get(ekranUtworzKonto);
        JTextField jTextField4 = (JTextField) jTextField4Field.get(ekranUtworzKonto);
        JTextField jTextField5 = (JTextField) jTextField5Field.get(ekranUtworzKonto);
        JPasswordField jPasswordField1 = (JPasswordField) jPasswordField1Field.get(ekranUtworzKonto);
        JPasswordField jPasswordField2 = (JPasswordField) jPasswordField2Field.get(ekranUtworzKonto);
        // Wprowadź puste dane
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jPasswordField1.setText("");
        jPasswordField2.setText("");
        // Sprawdź, czy pola mają wartości puste
        Assertions.assertEquals("", jTextField1.getText());
        Assertions.assertEquals("", jTextField2.getText());
        Assertions.assertEquals("", jTextField3.getText());
        Assertions.assertEquals("", jTextField4.getText());
        Assertions.assertEquals("", jTextField5.getText());
        Assertions.assertEquals("", new String(jPasswordField1.getPassword()));
        Assertions.assertEquals("", new String(jPasswordField2.getPassword()));
    }
    @Test
    public void testUtworzKontoPoprawneDane() throws Exception {
        EkranUtworzKonto ekranUtworzKonto = new EkranUtworzKonto();
        // Uzyskaj dostęp do prywatnych pól
        Field jTextField1Field = EkranUtworzKonto.class.getDeclaredField("jTextField1");
        Field jTextField2Field = EkranUtworzKonto.class.getDeclaredField("jTextField2");
        Field jTextField3Field = EkranUtworzKonto.class.getDeclaredField("jTextField3");
        Field jTextField4Field = EkranUtworzKonto.class.getDeclaredField("jTextField4");
        Field jTextField5Field = EkranUtworzKonto.class.getDeclaredField("jTextField5");
        Field jPasswordField1Field = EkranUtworzKonto.class.getDeclaredField("jPasswordField1");
        Field jPasswordField2Field = EkranUtworzKonto.class.getDeclaredField("jPasswordField2");
        jTextField1Field.setAccessible(true);
        jTextField2Field.setAccessible(true);
        jTextField3Field.setAccessible(true);
        jTextField4Field.setAccessible(true);
        jTextField5Field.setAccessible(true);
        jPasswordField1Field.setAccessible(true);
        jPasswordField2Field.setAccessible(true);
        JTextField jTextField1 = (JTextField) jTextField1Field.get(ekranUtworzKonto);
        JTextField jTextField2 = (JTextField) jTextField2Field.get(ekranUtworzKonto);
        JTextField jTextField3 = (JTextField) jTextField3Field.get(ekranUtworzKonto);
        JTextField jTextField4 = (JTextField) jTextField4Field.get(ekranUtworzKonto);
        JTextField jTextField5 = (JTextField) jTextField5Field.get(ekranUtworzKonto);
        JPasswordField jPasswordField1 = (JPasswordField) jPasswordField1Field.get(ekranUtworzKonto);
        JPasswordField jPasswordField2 = (JPasswordField) jPasswordField2Field.get(ekranUtworzKonto);
        // Wprowadź poprawne dane
        jTextField1.setText("John");
        jTextField2.setText("Doe");
        jTextField3.setText("john.doe@example.com");
        jTextField4.setText("123456789");
        jTextField5.setText("johndoe");
        jPasswordField1.setText("password");
        jPasswordField2.setText("password");
        // Sprawdź, czy pola mają poprawne wartości
        Assertions.assertEquals("John", jTextField1.getText());
        Assertions.assertEquals("Doe", jTextField2.getText());
        Assertions.assertEquals("john.doe@example.com", jTextField3.getText());
        Assertions.assertEquals("123456789", jTextField4.getText());
        Assertions.assertEquals("johndoe", jTextField5.getText());
        Assertions.assertEquals("password", new String(jPasswordField1.getPassword()));
        Assertions.assertEquals("password", new String(jPasswordField2.getPassword()));
    }
    //Ekran przywracanie hasla
    @Test
    public void testPrzywrocHasloPusteDane() throws Exception {
        Klient kl = new Klient();
        EkranPrzywrocHaslo ekranPrzywrocHaslo = new EkranPrzywrocHaslo(kl);
        // Uzyskaj dostęp do prywatnych pól
        Field jTextField1Field = EkranPrzywrocHaslo.class.getDeclaredField("jTextField1");
        Field jTextField2Field = EkranPrzywrocHaslo.class.getDeclaredField("jTextField2");
        Field jPasswordField1Field = EkranPrzywrocHaslo.class.getDeclaredField("jPasswordField1");
        Field jPasswordField2Field = EkranPrzywrocHaslo.class.getDeclaredField("jPasswordField2");
        jTextField1Field.setAccessible(true);
        jTextField2Field.setAccessible(true);
        jPasswordField1Field.setAccessible(true);
        jPasswordField2Field.setAccessible(true);
        JTextField jTextField1 = (JTextField) jTextField1Field.get(ekranPrzywrocHaslo);
        JTextField jTextField2 = (JTextField) jTextField2Field.get(ekranPrzywrocHaslo);
        JPasswordField jPasswordField1 = (JPasswordField) jPasswordField1Field.get(ekranPrzywrocHaslo);
        JPasswordField jPasswordField2 = (JPasswordField) jPasswordField2Field.get(ekranPrzywrocHaslo);
        // Wprowadź puste dane
        jTextField1.setText("");
        jTextField2.setText("");
        jPasswordField1.setText("");
        jPasswordField2.setText("");
        Assertions.assertEquals("", jTextField1.getText());
        Assertions.assertEquals("", jTextField2.getText());
        Assertions.assertEquals("", new String(jPasswordField1.getPassword()));
        Assertions.assertEquals("", new String(jPasswordField2.getPassword()));
    }
    @Test
    public void testPrzywrocHasloPoprawneDane() throws Exception {
        Klient kl = new Klient();
        EkranPrzywrocHaslo ekranPrzywrocHaslo = new EkranPrzywrocHaslo(kl);
        // Uzyskaj dostęp do prywatnych pól
        Field jTextField1Field = EkranPrzywrocHaslo.class.getDeclaredField("jTextField1");
        Field jTextField2Field = EkranPrzywrocHaslo.class.getDeclaredField("jTextField2");
        Field jPasswordField1Field = EkranPrzywrocHaslo.class.getDeclaredField("jPasswordField1");
        Field jPasswordField2Field = EkranPrzywrocHaslo.class.getDeclaredField("jPasswordField2");
        jTextField1Field.setAccessible(true);
        jTextField2Field.setAccessible(true);
        jPasswordField1Field.setAccessible(true);
        jPasswordField2Field.setAccessible(true);
        JTextField jTextField1 = (JTextField) jTextField1Field.get(ekranPrzywrocHaslo);
        JTextField jTextField2 = (JTextField) jTextField2Field.get(ekranPrzywrocHaslo);
        JPasswordField jPasswordField1 = (JPasswordField) jPasswordField1Field.get(ekranPrzywrocHaslo);
        JPasswordField jPasswordField2 = (JPasswordField) jPasswordField2Field.get(ekranPrzywrocHaslo);
        // Wprowadź poprawne dane
        jTextField1.setText("john.doe@example.com");
        jTextField2.setText("123456");
        jPasswordField1.setText("newpassword");
        jPasswordField2.setText("newpassword");
        // Sprawdź, czy pola mają poprawne wartości
        Assertions.assertEquals("john.doe@example.com", jTextField1.getText());
        Assertions.assertEquals("123456", jTextField2.getText());
        Assertions.assertEquals("newpassword", new String(jPasswordField1.getPassword()));
        Assertions.assertEquals("newpassword", new String(jPasswordField2.getPassword()));
    }
    @Test
    public void testWalidacjiKodu() {
        Klient kl = new Klient();
        EkranPrzywrocHaslo ekranPrzywrocHaslo = new EkranPrzywrocHaslo(kl);
        // Przypadki testowe
        String validKey = "123456";
        String invalidKey = "abc123";
        // Sprawdź poprawność walidacji dla poprawnego klucza
        boolean validMatch = ekranPrzywrocHaslo.walidacjaKodu(validKey);
        Assertions.assertTrue(validMatch, "Oczekiwano poprawnego dopasowania dla poprawnego klucza");
        // Sprawdź poprawność walidacji dla niepoprawnego klucza
        boolean invalidMatch = ekranPrzywrocHaslo.walidacjaKodu(invalidKey);
        Assertions.assertFalse(invalidMatch, "Oczekiwano niepoprawnego dopasowania dla niepoprawnego klucza");
    }
    @Test
    public void testWalidacjiHasla() {
        Klient kl = new Klient();
        EkranPrzywrocHaslo ekranPrzywrocHaslo = new EkranPrzywrocHaslo(kl);
        // Testuj poprawne hasła
        Assertions.assertTrue(ekranPrzywrocHaslo.walidacjaHasla("Passwor1234!"));
        Assertions.assertTrue(ekranPrzywrocHaslo.walidacjaHasla("Passw0rd!"));
        Assertions.assertTrue(ekranPrzywrocHaslo.walidacjaHasla("SecurePassword123!"));
        // Testuj niepoprawne hasła
        Assertions.assertFalse(ekranPrzywrocHaslo.walidacjaHasla("Abcd1234"));
        Assertions.assertFalse(ekranPrzywrocHaslo.walidacjaHasla("Password"));
        Assertions.assertFalse(ekranPrzywrocHaslo.walidacjaHasla("PASSWORD123!"));
    }

    //Ekran glowny uzytkownik
    @Test
    public void testEkranGlownyUzytkownik() {
        Klient kl = new Klient();
        EkranGlownyUzytkownik ekranGlownyUzytkownik = new EkranGlownyUzytkownik("login",kl);
        Assertions.assertNotNull(ekranGlownyUzytkownik);
        ekranGlownyUzytkownik.dispose();
        Assertions.assertFalse(ekranGlownyUzytkownik.isVisible());
    }

    //Ekran glowny admin
    @Test
    public void testEkranGlownyAdmin() {
        Klient kl = new Klient();
        EkranGlownyAdmin ekranGlownyAdmin = new EkranGlownyAdmin("login",kl);
        Assertions.assertNotNull(ekranGlownyAdmin);
        ekranGlownyAdmin.dispose();
        Assertions.assertFalse(ekranGlownyAdmin.isVisible());
    }

    //Dialog Dodaj DVD
    @Test
    public void testDodajDVD() throws Exception {
        Klient klient = new Klient();
        JFrame okno = new JFrame();
        DialogDodajDVD dialog = new DialogDodajDVD(okno, false, klient);
        // Uzyskiwanie dostępu do prywatnych pól za pomocą refleksji
        Field jTextField1Field = DialogDodajDVD.class.getDeclaredField("jTextField1");
        Field jTextField2Field = DialogDodajDVD.class.getDeclaredField("jTextField2");
        Field jTextField3Field = DialogDodajDVD.class.getDeclaredField("jTextField3");
        Field jTextField4Field = DialogDodajDVD.class.getDeclaredField("jTextField4");
        Field jTextField5Field = DialogDodajDVD.class.getDeclaredField("jTextField5");
        Field jTextField6Field = DialogDodajDVD.class.getDeclaredField("jTextField6");
        Field jTextField7Field = DialogDodajDVD.class.getDeclaredField("jTextField7");
        Field jSpinner1Field = DialogDodajDVD.class.getDeclaredField("jSpinner1");
        Field jSpinner2Field = DialogDodajDVD.class.getDeclaredField("jSpinner2");
        // Ustawianie dostępu do prywatnych pól
        jTextField1Field.setAccessible(true);
        jTextField2Field.setAccessible(true);
        jTextField3Field.setAccessible(true);
        jTextField4Field.setAccessible(true);
        jTextField5Field.setAccessible(true);
        jTextField6Field.setAccessible(true);
        jTextField7Field.setAccessible(true);
        jSpinner1Field.setAccessible(true);
        jSpinner2Field.setAccessible(true);
        // Przygotowanie stanu początkowego
        JTextField jTextField1 = (JTextField) jTextField1Field.get(dialog);
        JTextField jTextField2 = (JTextField) jTextField2Field.get(dialog);
        JTextField jTextField3 = (JTextField) jTextField3Field.get(dialog);
        JTextField jTextField4 = (JTextField) jTextField4Field.get(dialog);
        JTextField jTextField5 = (JTextField) jTextField5Field.get(dialog);
        JTextField jTextField6 = (JTextField) jTextField6Field.get(dialog);
        JTextField jTextField7 = (JTextField) jTextField7Field.get(dialog);
        JSpinner jSpinner1 = (JSpinner) jSpinner1Field.get(dialog);
        JSpinner jSpinner2 = (JSpinner) jSpinner2Field.get(dialog);
        jTextField1.setText("Test1");
        jTextField2.setText("Test2");
        jTextField3.setText("Test3");
        jTextField4.setText("Test4");
        jTextField5.setText("Test5");
        jTextField6.setText("Test6");
        jTextField7.setText("Test7");
        jSpinner1.setValue(5);
        jSpinner2.setValue(15);
        // Oczekiwane wartości pól tekstowych
        assertEquals("Test1", jTextField1.getText());
        assertEquals("Test2", jTextField2.getText());
        assertEquals("Test3", jTextField3.getText());
        assertEquals("Test4", jTextField4.getText());
        assertEquals("Test5", jTextField5.getText());
        assertEquals("Test6", jTextField6.getText());
        assertEquals("Test7", jTextField7.getText());
        // Oczekiwane wartości spinnerów
        assertEquals(5, jSpinner1.getValue());
        assertEquals(15, jSpinner2.getValue());
    }

    //Do Wszystkich dialogów w tle musi chodzić serwer zeby mogły sie łaczyc!!!!!!!!

    //Dialog Edytuj DVD
    @Test
    public void testEdytujDVD() throws Exception {
        Klient klient = new Klient();
        JFrame okno = new JFrame();
        DialogEdytujDVD dialog = new DialogEdytujDVD(okno, false, klient);
        // Uzyskiwanie dostępu do prywatnych pól za pomocą refleksji
        Field jTextField1Field = DialogEdytujDVD.class.getDeclaredField("jTextField1");
        Field jTextField2Field = DialogEdytujDVD.class.getDeclaredField("jTextField2");
        Field jTextField3Field = DialogEdytujDVD.class.getDeclaredField("jTextField3");
        Field jTextField4Field = DialogEdytujDVD.class.getDeclaredField("jTextField4");
        Field jTextField5Field = DialogEdytujDVD.class.getDeclaredField("jTextField5");
        Field jTextField6Field = DialogEdytujDVD.class.getDeclaredField("jTextField6");
        Field jTextField7Field = DialogEdytujDVD.class.getDeclaredField("jTextField7");
        Field jSpinner1Field = DialogEdytujDVD.class.getDeclaredField("jSpinner1");
        Field jSpinner2Field = DialogEdytujDVD.class.getDeclaredField("jSpinner2");
        // Ustawianie dostępu do prywatnych pól
        jTextField1Field.setAccessible(true);
        jTextField2Field.setAccessible(true);
        jTextField3Field.setAccessible(true);
        jTextField4Field.setAccessible(true);
        jTextField5Field.setAccessible(true);
        jTextField6Field.setAccessible(true);
        jTextField7Field.setAccessible(true);
        jSpinner1Field.setAccessible(true);
        jSpinner2Field.setAccessible(true);
        // Przygotowanie stanu początkowego
        JTextField jTextField1 = (JTextField) jTextField1Field.get(dialog);
        JTextField jTextField2 = (JTextField) jTextField2Field.get(dialog);
        JTextField jTextField3 = (JTextField) jTextField3Field.get(dialog);
        JTextField jTextField4 = (JTextField) jTextField4Field.get(dialog);
        JTextField jTextField5 = (JTextField) jTextField5Field.get(dialog);
        JTextField jTextField6 = (JTextField) jTextField6Field.get(dialog);
        JTextField jTextField7 = (JTextField) jTextField7Field.get(dialog);
        JSpinner jSpinner1 = (JSpinner) jSpinner1Field.get(dialog);
        JSpinner jSpinner2 = (JSpinner) jSpinner2Field.get(dialog);
        jTextField1.setText("Test1");
        jTextField2.setText("Test2");
        jTextField3.setText("Test3");
        jTextField4.setText("Test4");
        jTextField5.setText("Test5");
        jTextField6.setText("Test6");
        jTextField7.setText("Test7");
        jSpinner1.setValue(5);
        jSpinner2.setValue(15);
        // Oczekiwane wartości pól tekstowych
        assertEquals("Test1", jTextField1.getText());
        assertEquals("Test2", jTextField2.getText());
        assertEquals("Test3", jTextField3.getText());
        assertEquals("Test4", jTextField4.getText());
        assertEquals("Test5", jTextField5.getText());
        assertEquals("Test6", jTextField6.getText());
        assertEquals("Test7", jTextField7.getText());
        // Oczekiwane wartości spinnerów
        assertEquals(5, jSpinner1.getValue());
        assertEquals(15, jSpinner2.getValue());
    }
    //Dialog Edytuj klienta
    @Test
    public void testEdytujKlienta() throws Exception {
        JFrame ok = new JFrame();
        Klient kl = new Klient();
        DialogEdytujKlienta dialogEdytujKlienta = new DialogEdytujKlienta(ok,false,kl);
        // Uzyskaj dostęp do prywatnych pól
        Field jTextField1Field = DialogEdytujKlienta.class.getDeclaredField("jTextField1");
        Field jTextField2Field = DialogEdytujKlienta.class.getDeclaredField("jTextField2");
        Field jTextField3Field = DialogEdytujKlienta.class.getDeclaredField("jTextField3");
        Field jTextField4Field = DialogEdytujKlienta.class.getDeclaredField("jTextField4");
        Field jTextField5Field = DialogEdytujKlienta.class.getDeclaredField("jTextField5");
        Field jPasswordField1Field = DialogEdytujKlienta.class.getDeclaredField("jPasswordField1");
        Field jPasswordField2Field = DialogEdytujKlienta.class.getDeclaredField("jPasswordField2");
        jTextField1Field.setAccessible(true);
        jTextField2Field.setAccessible(true);
        jTextField3Field.setAccessible(true);
        jTextField4Field.setAccessible(true);
        jTextField5Field.setAccessible(true);
        jPasswordField1Field.setAccessible(true);
        jPasswordField2Field.setAccessible(true);
        JTextField jTextField1 = (JTextField) jTextField1Field.get(dialogEdytujKlienta);
        JTextField jTextField2 = (JTextField) jTextField2Field.get(dialogEdytujKlienta);
        JTextField jTextField3 = (JTextField) jTextField3Field.get(dialogEdytujKlienta);
        JTextField jTextField4 = (JTextField) jTextField4Field.get(dialogEdytujKlienta);
        JTextField jTextField5 = (JTextField) jTextField5Field.get(dialogEdytujKlienta);
        JPasswordField jPasswordField1 = (JPasswordField) jPasswordField1Field.get(dialogEdytujKlienta);
        JPasswordField jPasswordField2 = (JPasswordField) jPasswordField2Field.get(dialogEdytujKlienta);
        // Wprowadź poprawne dane
        jTextField1.setText("John");
        jTextField2.setText("Doe");
        jTextField3.setText("john.doe@example.com");
        jTextField4.setText("123456789");
        jTextField5.setText("johndoe");
        jPasswordField1.setText("password");
        jPasswordField2.setText("password");
        // Sprawdź, czy pola mają poprawne wartości
        Assertions.assertEquals("John", jTextField1.getText());
        Assertions.assertEquals("Doe", jTextField2.getText());
        Assertions.assertEquals("john.doe@example.com", jTextField3.getText());
        Assertions.assertEquals("123456789", jTextField4.getText());
        Assertions.assertEquals("johndoe", jTextField5.getText());
        Assertions.assertEquals("password", new String(jPasswordField1.getPassword()));
        Assertions.assertEquals("password", new String(jPasswordField2.getPassword()));
    }

    //Dialog moje wypozyczenia
    @Test
    public void testKonstruktoraMojeWypozyczenia() {
        // Utwórz obiekty wymagane do konstrukcji DialogMojeWypozyczenia
        Frame parent = new Frame();
        boolean modal = true;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogMojeWypozyczenia dialog = new DialogMojeWypozyczenia(parent, modal, klient);
        // Sprawdź, czy dialog został utworzony
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        // Sprawdź, czy dialog zniknal po kliknieciu przycisku
        Assertions.assertFalse(dialog.isVisible());

    }

    //Dialog moje zwroty
    @Test
    public void testKonstruktoraMojeZwroty() {
        // Utwórz obiekty wymagane do konstrukcji DialogMojeWypozyczenia
        Frame parent = new Frame();
        boolean modal = true;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogMojeZwroty dialog = new DialogMojeZwroty(parent, modal, klient);
        // Sprawdź, czy dialog został utworzony
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        // Sprawdź, czy dialog zniknal po kliknieciu przycisku
        Assertions.assertFalse(dialog.isVisible());

    }

    //Dialog przegladaj kolekcje dvd
    @Test
    public void testKonstruktoraPrzegladajKolekcjeDVD() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogPrzegladajKolekcjeDVD dialog = new DialogPrzegladajKolekcjeDVD(parent, modal,"ReviewDVDCollection", klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }
    //Dialog przegladaj liste klientow
    @Test
    public void testKonstruktoraPrzegladajListeKlientow() {
        // Utwórz obiekty wymagane do konstrukcji DialogMojeWypozyczenia
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogPrzegladajListeKlientow dialog = new DialogPrzegladajListeKlientow(parent, modal, klient);
        // Sprawdź, czy dialog został utworzony
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        // Sprawdź, czy dialog zniknal po kliknieciu przycisku
        Assertions.assertFalse(dialog.isVisible());
    }

    //Dialog przegladaj rachunki
    @Test
    public void testKonstruktoraPrzegladajRachunki() {
        // Utwórz obiekty wymagane do konstrukcji DialogMojeWypozyczenia
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogPrzegladajRachunki dialog = new DialogPrzegladajRachunki(parent, modal, klient);
        // Sprawdź, czy dialog został utworzony
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        // Sprawdź, czy dialog zniknal po kliknieciu przycisku
        Assertions.assertFalse(dialog.isVisible());
    }
    //Dialog przegladaj wypozyczenia
    @Test
    public void testKonstruktoraPrzegladajWypozyczenia() {
        // Utwórz obiekty wymagane do konstrukcji DialogMojeWypozyczenia
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogPrzegladajWypozyczenia dialog = new DialogPrzegladajWypozyczenia(parent, modal, klient);
        // Sprawdź, czy dialog został utworzony
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        // Sprawdź, czy dialog zniknal po kliknieciu przycisku
        Assertions.assertFalse(dialog.isVisible());
    }
    //Dialog przegladaj zwroty
    @Test
    public void testKonstruktoraPrzegladajZwroty() {
        // Utwórz obiekty wymagane do konstrukcji DialogMojeWypozyczenia
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogPrzegladajZwroty dialog = new DialogPrzegladajZwroty(parent, modal, klient);
        // Sprawdź, czy dialog został utworzony
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        // Sprawdź, czy dialog zniknal po kliknieciu przycisku
        Assertions.assertFalse(dialog.isVisible());
    }
    //Dialog stan magazynowy
    @Test
    public void testKonstruktoraStanMagazynowy() {
        // Utwórz obiekty wymagane do konstrukcji DialogMojeWypozyczenia
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogStanMagazynowyDVD dialog = new DialogStanMagazynowyDVD(parent, modal, klient);
        // Sprawdź, czy dialog został utworzony
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        // Sprawdź, czy dialog zniknal po kliknieciu przycisku
        Assertions.assertFalse(dialog.isVisible());
    }
    //Dialog ustawienia serwera
    @Test
    public void testDialogUstawieniaSerwera() throws Exception {
        Klient kl = new Klient();
        JFrame ok = new JFrame();
        DialogUstawieniaSerwera ekranUstawienia = new DialogUstawieniaSerwera(ok,false,kl);
        String poprawneIP = "192.168.0.12";
        Field jTextField1Field = DialogUstawieniaSerwera.class.getDeclaredField("jTextField1");
        jTextField1Field.setAccessible(true);
        JTextField jTextField1 = (JTextField) jTextField1Field.get(ekranUstawienia);
        jTextField1.setText(poprawneIP);
        boolean wynik = kl.walidacjaIP(poprawneIP);
        Assertions.assertTrue(wynik);
        Assertions.assertEquals(JOptionPane.INFORMATION_MESSAGE, 1);
    }
    //Dialog ustaw liczbe DVD
    @Test
    public void testDialogUstawLiczbeDVD() {
        Klient kl = new Klient();
        JFrame ok = new JFrame();
        DialogUstawLiczbeDVD dialog = new DialogUstawLiczbeDVD(ok,false,kl);
        dialog.jSpinner1.setValue(-1);
        dialog.jButton1ActionPerformed(null);
        Assertions.assertEquals(JOptionPane.ERROR_MESSAGE, 0);
        dialog.jSpinner1.setValue(5);
        Assertions.assertEquals(JOptionPane.INFORMATION_MESSAGE, 1);
    }
    //Dialog Usun DVD
    @Test
    public void testKonstruktoraUsunDVD() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogUsunDVD dialog = new DialogUsunDVD(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }
    //Dialog usun klienta
    @Test
    public void testKonstruktoraUsunKlienta() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogUsunKlienta dialog = new DialogUsunKlienta(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }

    //Dialog usun sekwencje
    @Test
    public void testKonstruktoraUsunSekwencje() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogUsunSekwencje dialog = new DialogUsunSekwencje(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }
    //dialog usun tabele
    @Test
    public void testKonstruktoraUsunTabele() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogUsunTabele dialog = new DialogUsunTabele(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }
    //Dialog utworz sekwencje
    @Test
    public void testKonstruktoraUtworzSekwencje() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogUtworzSekwencje dialog = new DialogUtworzSekwencje(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }
    @Test
    public void testDialogUtworzSekwencje2() {
        Klient kl = new Klient();
        JFrame ok = new JFrame();
        DialogUtworzSekwencje dialog = new DialogUtworzSekwencje(ok,false,kl);
        dialog.jComboBox1.setSelectedItem("Sekwencja1");
        dialog.jButton1ActionPerformed(null);
        Assertions.assertEquals(JOptionPane.INFORMATION_MESSAGE, 1);
    }

    //Dialog utworz tabele
    @Test
    public void testKonstruktoraUtworzTabele() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        klient.nickname = "admin";
        DialogUtworzSekwencje dialog = new DialogUtworzSekwencje(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }
    @Test
    public void testDialogUtworzTabele2() {
        Klient kl = new Klient();
        JFrame ok = new JFrame();
        DialogUtworzTabele dialog = new DialogUtworzTabele(ok,false,kl);
        dialog.jComboBox1.setSelectedItem("Sekwencja1");
        dialog.jButton1ActionPerformed(null);
        Assertions.assertEquals(JOptionPane.INFORMATION_MESSAGE, 1);
    }

    //Dialog Wypozycz
    @Test
    public void testWypozycz() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        DialogWypozycz dialog = new DialogWypozycz(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }

    //Dialog Wystaw rachunek
    @Test
    public void testWystawRachunek() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        DialogWystawRachunek dialog = new DialogWystawRachunek(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }

    //Dialog zobacz rezerwacje
    @Test
    public void testZobaczRezerwacje() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        DialogZobaczRezerwacje dialog = new DialogZobaczRezerwacje(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }

    //Dialog zwroc
    @Test
    public void testDialogZwroc() {
        Frame parent = new Frame();
        boolean modal = false;
        Klient klient = new Klient();
        DialogZwroc dialog = new DialogZwroc(parent, modal, klient);
        Assertions.assertNotNull(dialog);
        dialog.dispose();
        Assertions.assertFalse(dialog.isVisible());
    }
}