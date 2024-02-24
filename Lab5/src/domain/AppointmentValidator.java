package domain;

import domain.Appointment;

import java.time.LocalDate;

public class AppointmentValidator {

    public static void validateAppointment(Appointment appointment) {
        validateAppointmentNumber(appointment.getId());
        validatePatient(appointment.getPatient());
    }

    private static void validateAppointmentNumber(int appointmentNumber)
    {
        if(appointmentNumber <= 0)
        {
            throw new IllegalArgumentException("Appointment must be greater than 0");
        }
    }

    private static void validatePatient(Patient patient)
    {
        if(patient == null)
            throw new IllegalArgumentException("Patient cannot be null");
    }

}
