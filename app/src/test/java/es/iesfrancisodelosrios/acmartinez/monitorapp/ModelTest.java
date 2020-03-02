package es.iesfrancisodelosrios.acmartinez.monitorapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import es.iesfrancisodelosrios.acmartinez.monitorapp.model.Person;

public class ModelTest {


    private Person p;

    @Before
    public void setUp() {
        this.p = new Person();
    }

    @Test
    public void personCategoria_isCorrect() {
        assertEquals(true, this.p.setName("Carlos"));
        assertEquals(false, this.p.setName("123123"));
        assertEquals("Carlos", this.p.getName());
        assertEquals(true, this.p.setEmail("carmavi97@gmail.com"));
        assertEquals(false, this.p.setEmail("carmavigmail.com"));
        assertEquals("carmavi97@gmail.com", this.p.getEmail());
        assertEquals(true, this.p.setNif("46271355r"));
        assertEquals(false, this.p.setNif("123rt6789"));
        assertEquals("46271355r", this.p.getNif());
        assertEquals(true, this.p.setBirthDate("01/12/1997"));
        assertEquals(false, this.p.setBirthDate("1-12-1997"));
        assertEquals("01/12/1997", this.p.getBirthDate());
        assertEquals(true, this.p.setLastName("Martinez"));
        assertEquals(false, this.p.setLastName("23"));
        assertEquals("Martinez", this.p.getLastName());
        assertEquals(true, this.p.setPhone("617337861"));
        assertEquals(false, this.p.setPhone("6173aed61"));
        assertEquals("617337861", this.p.getPhone());
        assertEquals(true, this.p.setSection("Tropa"));
        assertEquals(false, this.p.setSection("69"));
        assertEquals("Tropa", this.p.getSection());
    }
}
