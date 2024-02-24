package domain;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class Appointment implements Identifiable<Integer>, Serializable {
    private int appointmentNumber;
    private Patient patient;
    private LocalDate dateOfAppointment;

    public Appointment(int appointmentNumber, Patient patient, LocalDate dateOfTheAppointment) {
        this.appointmentNumber = appointmentNumber;
        this.patient = patient;
        this.dateOfAppointment = dateOfTheAppointment;
    }

    @Override
    public Integer getId() {
        return appointmentNumber;
    }
    @Override
    public void setId(Integer appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(LocalDate dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentNumber + "\n" +
                patient.toString() + ' ' + "\n" +
                "Date: " + dateOfAppointment + "\n";
    }

    @Override
    public boolean equals(Object objectToCompare)
    {
        if (this == objectToCompare) return true;
        if (objectToCompare == null || getClass() != objectToCompare.getClass()) return false;
        Appointment appointment  = (Appointment) objectToCompare;
        return Objects.equals(appointmentNumber, appointment.appointmentNumber);
    }
}

