package domain;

import domain.Appointment;
import domain.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AppointmentTest {

    @Test
    void testConstructorAndGetters() {
        Patient patient = new Patient("John", 1, "City", 123456789);
        LocalDate dateOfAppointment = LocalDate.of(2023, 11, 1);
        Appointment appointment = new Appointment(1, patient, dateOfAppointment);

        assertEquals(1, appointment.getId());
        assertEquals(patient, appointment.getPatient());
        assertEquals(dateOfAppointment, appointment.getDateOfAppointment());
    }

    @Test
    void testSetters() {
        Patient patient1 = new Patient("John", 1, "City", 123456789);
        LocalDate dateOfAppointment1 = LocalDate.of(2023, 11, 1);
        Appointment appointment = new Appointment(1, patient1, dateOfAppointment1);

        Patient patient2 = new Patient("Jane", 2, "New City", 987654321);
        LocalDate dateOfAppointment2 = LocalDate.of(2023, 11, 2);

        appointment.setId(2);
        appointment.setPatient(patient2);
        appointment.setDateOfAppointment(dateOfAppointment2);

        assertEquals(2, appointment.getId());
        assertEquals(patient2, appointment.getPatient());
        assertEquals(dateOfAppointment2, appointment.getDateOfAppointment());
    }

    @Test
    void testToString() {
        Patient patient = new Patient("John", 1, "City", 123456789);
        LocalDate dateOfAppointment = LocalDate.of(2023, 11, 1);
        Appointment appointment = new Appointment(1, patient, dateOfAppointment);

        String expected = "Appointment ID: 1\n" +
                "Patient { name = 'John', id = 1, city = City, phone number = 123456789 } \n" +
                "Date: 2023-11-01\n";

        assertEquals(expected, appointment.toString());
    }

    @Test
    void testEquals() {
        Appointment appointment1 = new Appointment(1, new Patient("John", 1, "City", 123456789), LocalDate.of(2023, 11, 1));
        Appointment appointment2 = new Appointment(2, new Patient("Jane", 2, "New City", 987654321), LocalDate.of(2023, 11, 2));
        Appointment appointment3 = new Appointment(1, new Patient("John", 1, "City", 123456789), LocalDate.of(2023, 11, 1));
        Patient patient1 = new Patient("John", 1, "City", 123456789);
        assertEquals(appointment1, appointment1);
        assertEquals(appointment1, appointment3);
        assertNotEquals(appointment2, appointment3);
        appointment1.equals(patient1);
    }
}
