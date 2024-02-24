package domain;

import domain.Appointment;
import domain.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    @Test
    void testConstructorAndGetters() {
        Patient patient = new Patient("John", 1, "City", 123456789);

        assertEquals("John", patient.getName());
        assertEquals(1, patient.getId());
        assertEquals("City", patient.getCity());
        assertEquals(123456789, patient.getPhoneNumber());
    }

    @Test
    void testSetters() {
        Patient patient = new Patient("John", 1, "City", 123456789);

        patient.setName("Jane");
        patient.setId(2);
        patient.setCity("New City");
        patient.setPhoneNumber(987654321);

        assertEquals("Jane", patient.getName());
        assertEquals(2, patient.getId());
        assertEquals("New City", patient.getCity());
        assertEquals(987654321, patient.getPhoneNumber());
    }

    @Test
    void testToString() {
        Patient patient = new Patient("John", 1, "City", 123456789);

        String expected = "Patient { name = 'John', id = 1, city = City, phone number = 123456789 }";
        assertEquals(expected, patient.toString());
    }

    @Test
    void testEquals() {
        Patient patient1 = new Patient("John", 1, "City", 123456789);
        Patient patient2 = new Patient("Jane", 3, "New City", 987654321);
        Patient patient3 = new Patient("John", 1, "City", 123456789);
        Patient patient4 = new Patient("John", 4, "City", 555555555); // Different phone number
        Appointment appointment1 = new Appointment(12, patient4, LocalDate.of(2024, 05, 30));
        // Test equal patients
        assertEquals(patient1, patient3);
        assertTrue(patient1.equals(patient1));
        // Test not equal patients
        assertNotEquals(patient1, patient2);
        assertNotEquals(patient2, patient3);
        assertNotEquals(patient1, appointment1);
    }

}
