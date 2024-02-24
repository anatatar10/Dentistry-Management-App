package domain;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.Objects;


public class Patient implements Identifiable<Integer>, Serializable {

    private int id;
    private int phoneNumber;
    private String name;
    private String city;

    public Patient(String name, int id, String city, int phoneNumber) {
        this.name = name;
        this.id = id;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer idToSet) {
        this.id = idToSet;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumberToSet) {
        this.phoneNumber = phoneNumberToSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameToSet) {
        this.name = nameToSet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String cityToSet) {
        this.city = cityToSet;
    }

    @Override
    public String toString() {
        return "Patient { name = '" + this.name + "\'" +
                ", id = " + id + ", city = " + city + ", phone number = " + phoneNumber + " }";
    }

    @Override
    public boolean equals(Object objectToCompare)
    {
        if (this == objectToCompare) return true;
        if (objectToCompare == null || getClass() != objectToCompare.getClass()) return false;
        Patient patient = (Patient) objectToCompare;
        return Objects.equals(id, patient.id);
    }
}

