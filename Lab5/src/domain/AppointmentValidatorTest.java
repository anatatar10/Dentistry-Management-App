package domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;


class AppointmentValidatorTest {
    @Test
    public void validateAppointmentNumberTest()
    {
        Appointment appointment = new Appointment(1,new Patient("ana", 1, "Cluj",0744),LocalDate.of(2023,11,11));
        try {
            AppointmentValidator.validateAppointment(appointment);
        }catch (IllegalArgumentException e)
        {
            System.out.printf("Illegal arguments");
        }
        Appointment appointment1 = new Appointment(-1,new Patient("ana", 1, "Cluj",0744),LocalDate.of(2023,11,11));
        try {
            AppointmentValidator.validateAppointment(appointment1);
        }catch (IllegalArgumentException e)
        {
            System.out.printf("Illegal arguments");
        }
    }

    @Test
    public  void validatePatientTest()
    {
        Appointment appointment = new Appointment(1,new Patient("ana", 1, "Cluj",0744),LocalDate.of(2023,11,11));
        try {
            AppointmentValidator.validateAppointment(appointment);
        }catch (IllegalArgumentException e)
        {
            System.out.printf("Illegal arguments");
        }
        Appointment appointment1 = new Appointment(-1,null,LocalDate.of(2023,11,11));
        try {
            AppointmentValidator.validateAppointment(appointment1);
        }catch (IllegalArgumentException e)
        {
            System.out.printf("Illegal arguments");
        }
    }
    @Test
    public void validateDateOfAppointmentTest() {
        Appointment appointment = new Appointment(1,new Patient("ana", 1, "Cluj",0744),LocalDate.of(2023,11,11));
        try {
            AppointmentValidator.validateAppointment(appointment);
        }catch (IllegalArgumentException e)
        {
            System.out.printf("Illegal arguments");
        }
        Appointment appointment1 = new Appointment(-1,null,LocalDate.of(2020,11,11));
        try {
            AppointmentValidator.validateAppointment(appointment1);
        }catch (IllegalArgumentException e)
        {
            System.out.printf("Illegal arguments");

        }
    }
}