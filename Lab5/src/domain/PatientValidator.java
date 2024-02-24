package domain;

import domain.Patient;

public class PatientValidator {

    public static void validatePatient(Patient patient) {
        validateId(patient.getId());
        validateName(patient.getName());
        validateCity(patient.getCity());
        validatePhoneNumber(patient.getPhoneNumber());
    }
    private static void validateId(int id)
    {
        if(id <= 0)
            throw new IllegalArgumentException("Id must be greater than 0");
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
    }

    private static void validateCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty.");
        }
    }

    private static void validatePhoneNumber(int phoneNumber) {
        if (phoneNumber <= 0 )
            throw new IllegalArgumentException("Phone number must be positive");
    }

}
