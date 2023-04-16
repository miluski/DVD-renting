/**
 * @file Tests.java
 * @brief Plik zawierający kod testów jednostkowych aplikacji
 * @author Jakub Szczur
 * @author Maksymilian Sowula
 * @version 1.0.0-alpha
 */
import com.server.*;
import com.client.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Klasa zawierająca pola i metody testujące całą aplikację od strony zarówno serwera, jak i klienta
 */
public class UnitTests {
    @Test
    public void testInfoMessage() {
        Logs log = new Logs("Info message", "LogsTest", "info");
        assertEquals("Info message", Logs.getLogMessage());
        assertEquals("LogsTest", log.getClassName());
    }
    @Test
    public void testWarnMessage() {
        Logs log = new Logs("Warning message", "LogsTest", "warn");
        assertEquals("Warning message", Logs.getLogMessage());
        assertEquals("LogsTest", log.getClassName());
    }
    @Test
    public void testErrorMessage() {
        Logs log = new Logs("Error message", "LogsTest", "error");
        assertEquals("Error message", Logs.getLogMessage());
        assertEquals("LogsTest", log.getClassName());
    }
    @Test
    public void testFatalMessage() {
        Logs log = new Logs("Fatal message", "LogsTest", "fatal");
        assertEquals("Fatal message", Logs.getLogMessage());
        assertEquals("LogsTest", log.getClassName());
    }
    @Test
    public void testConstructor() {
        EkranSerwer ekranSerwer = new EkranSerwer();
        assertNotNull(ekranSerwer);
        assertFalse(EkranSerwer.noDatabaseConnection);
        assertTrue(EkranSerwer.running);
        assertEquals("localhost", EkranSerwer.IP);
        assertTrue(EkranSerwer.clients.isEmpty());
        assertTrue(EkranSerwer.panelData.isEmpty());
    }

    @Test
    public void testStaticVariables() {
        assertNull(EkranSerwer.nick);
        assertNull(EkranSerwer.pass);
        assertNull(EkranSerwer.rank);
        assertNull(EkranSerwer.userID);
        assertNull(EkranSerwer.operation);
        assertNull(EkranSerwer.passOperation);
        assertNull(EkranSerwer.managementOperation);
        assertNull(EkranSerwer.message);
        assertNull(EkranSerwer.updatingItem);
        assertNull(EkranSerwer.selectedNick);
        assertNull(EkranSerwer.whichClient);
        assertNull(EkranSerwer.filmName);
        assertEquals(0, EkranSerwer.updatingID);
        assertEquals(0, EkranSerwer.dvdID);
        assertNull(EkranSerwer.tableName);
    }
    @Test
    public void testSetIP() {
        EkranSerwer.setIP("192.168.0.12");
        assertEquals("192.168.0.12", EkranSerwer.IP);
    }
    @Test
    public void testCatchServe() {
        Exception ex = new Exception("Test exception");
        BazaDanych.catchServe(ex);
        String expected = ex.getMessage();
        String actual = EkranSerwer.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerujKlucz() {
        int key = EkranUtworzKonto.generujKlucz();
        assertTrue(key >= 100000 && key <= 999999);
    }

    @Test
    public void testWalidacjaHasla() {
        assertTrue(EkranUtworzKonto.walidacjaHasla("ValidPassword@123"));
        assertFalse(EkranUtworzKonto.walidacjaHasla("nieprawidlowehaslo"));
    }

    @Test
    public void testWalidacjaEmail() {
        assertTrue(EkranUtworzKonto.walidacjaEmail("test@example.com"));
        assertFalse(EkranUtworzKonto.walidacjaEmail("invalid"));
    }

    @Test
    public void testWalidacjaTelefonu() {
        assertFalse(EkranUtworzKonto.walidacjaTelefonu("+123456789"));
        assertTrue(EkranUtworzKonto.walidacjaTelefonu("123456789"));
    }

    @Test
    public void testWyczyscPola() {
        new EkranUtworzKonto("rank");
        EkranUtworzKonto.jTextField1.setText("field1");
        EkranUtworzKonto.jTextField2.setText("field2");
        EkranUtworzKonto.jTextField3.setText("field3");
        EkranUtworzKonto.jTextField4.setText("field4");
        EkranUtworzKonto.jTextField5.setText("field5");
        EkranUtworzKonto.jPasswordField1.setText("password1");
        EkranUtworzKonto.jPasswordField2.setText("password2");
        EkranUtworzKonto.wyczyscPola();
        assertEquals("", EkranUtworzKonto.jTextField1.getText());
        assertEquals("", EkranUtworzKonto.jTextField2.getText());
        assertEquals("", EkranUtworzKonto.jTextField3.getText());
        assertEquals("", EkranUtworzKonto.jTextField4.getText());
        assertEquals("", EkranUtworzKonto.jTextField5.getText());
        assertEquals("", String.valueOf(EkranUtworzKonto.jPasswordField1.getPassword()));
        assertEquals("", String.valueOf(EkranUtworzKonto.jPasswordField1.getPassword()));
    }

    @Test
    public void testWalidacjaHasla2() {
        String validPass = "StrongPassword@123";
        boolean result1 = EkranPrzywrocHaslo.walidacjaHasla(validPass);
        Assertions.assertTrue(result1);
        String invalidPass = "weakpass";
        boolean result2 = EkranPrzywrocHaslo.walidacjaHasla(invalidPass);
        Assertions.assertFalse(result2);
    }

    @Test
    public void testWalidacjaKodu() {
        String validKey = "123456";
        boolean result1 = EkranPrzywrocHaslo.walidacjaKodu(validKey);
        Assertions.assertTrue(result1);
        String invalidKey = "12three";
        boolean result2 = EkranPrzywrocHaslo.walidacjaKodu(invalidKey);
        Assertions.assertFalse(result2);
    }
}
