package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientValidatorTest {

    @Test
    public void validateIdTest()
    {
        Patient patient = new Patient("John", 1, "Cluj", 0744);
        try{
            PatientValidator.validatePatient(patient);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Patient patient1 = new Patient("John", -3, "Cluj", 0744);
        try{
            PatientValidator.validatePatient(patient1);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }

    @Test
    public void validateNameTest() {
        Patient patient = new Patient("John", 1, "Cluj", 0744);
        try{
            PatientValidator.validatePatient(patient);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Patient patient1 = new Patient("", 1, "Cluj", 0744);
        try{
            PatientValidator.validatePatient(patient1);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }

    @Test
    public void validateCityTest() {
        Patient patient = new Patient("John", 1, "Cluj", 0744);
        try{
            PatientValidator.validatePatient(patient);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Patient patient1 = new Patient("John", 1, "", 0744);
        try{
            PatientValidator.validatePatient(patient1);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }

    @Test
    public void validatePhoneNumber() {
        Patient patient = new Patient("John", 1, "Cluj", 0744);
        try{
            PatientValidator.validatePatient(patient);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
        Patient patient1 = new Patient("John", 1, "Cluj", -232);
        try{
            PatientValidator.validatePatient(patient1);
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }
}