package service;

import domain.Appointment;
import domain.AppointmentValidator;
import domain.Patient;
import domain.PatientValidator;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;
import repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DentalService {
    protected IRepository<Patient,Integer> patientsRepository;
    protected IRepository<Appointment,Integer> appointmentsRepository;


    public enum RepositoryType {
        MEMORY,
        TEXT,
        BINARY,
        DATABASE
    }

    private RepositoryType currentRepositoryType;

    public DentalService() {
        patientsRepository = new MemoryRepository<>();
        appointmentsRepository = new MemoryRepository<>();
    }

    public void setRepositoryType(RepositoryType type) {
        currentRepositoryType = type;
        switch (type) {
            case MEMORY:
                patientsRepository = new MemoryRepository<>();
                appointmentsRepository = new MemoryRepository<>();
                break;
            case TEXT:
                patientsRepository = new PatientRepositoryTextFile("patients.txt");
                appointmentsRepository = new AppointmentRepositoryTextFile("appointments.txt");
                break;
            case BINARY:
                patientsRepository = new PatientRepositoryBinaryFile("patients.bin");
                appointmentsRepository = new AppointmentRepositoryBinaryFile("appointments.bin");
                break;
            case DATABASE:
                patientsRepository = new PatientDatabaseRepository("patients");
                appointmentsRepository = new AppointmentsDatabaseRepository("appointments");
        }

        try {
            addPatient("John Doe", 1, "City1", 123456789);
            addPatient("Jane Smith", 2, "City2", 987654321);
            addPatient("Christian Jack", 3, "City3", 47274224);

            addAppointment(1, new Patient("John Doe", 1, "City1", 123456789), LocalDate.of(2024, 05, 30));
            addAppointment(2, new Patient("Jane Smith", 2, "City2", 987654321), LocalDate.of(2024, 03, 28));
            addAppointment(3, new Patient("Christian Jack", 3, "City3", 47274224), LocalDate.of(2024, 04, 23));
            addAppointment(4, new Patient("Christian Jack", 3, "City3", 47274224), LocalDate.of(2025, 05, 25));
        } catch (DuplicateItemException e) {
            throw new RuntimeException("Error adding hardcoded sample data.", e);
        }
    }

    public RepositoryType getRepositoryType(){
        return currentRepositoryType;
    }


    // CRUD Operations for Patient
    public void addPatient(String name, int id, String city, int phoneNumber) throws DuplicateItemException {
        Patient patient = new Patient(name, id, city, phoneNumber);
        try {
            PatientValidator.validatePatient(patient);
            this.patientsRepository.addItem(patient);

        }catch (IllegalArgumentException e)
        {
            System.out.println("Incorrect input");
        }
    }

    public Patient getPatientByID(int idToSearchBy) throws ItemNotFound {

        return patientsRepository.getItemById(idToSearchBy);
    }

    public ArrayList<Patient> getAllPatientsFromThePatientsRepository() {
        ArrayList patientsList = new ArrayList<>((Collection) this.patientsRepository.getAllItems());
        return new ArrayList<>(patientsList);
    }


    public void updatePatient(String newName, int id, String newCity, int newPhoneNumber) throws ItemNotFound {
        Patient patientToUpdate = patientsRepository.getItemById(id);

        Patient updatedPatient = new Patient(newName, id, newCity, newPhoneNumber);
        try {
            PatientValidator.validatePatient(updatedPatient);
        }catch (IllegalArgumentException e)
        {
            System.out.printf("Incorrect arguments");
            return;
        }
        patientToUpdate.setName(newName);
        patientToUpdate.setPhoneNumber(newPhoneNumber);
        patientToUpdate.setCity(newCity);
        this.patientsRepository.updateItemById(id, patientToUpdate);
    }

    public void deletePatientByID(int id) throws ItemNotFound {
        Patient patientToDelete = patientsRepository.getItemById(id);
        this.patientsRepository.deleteItemById(id);
    }

    // CRUD Operations for Appointment

    public void addAppointment(Integer appointmentNumber, Patient patient, LocalDate dateOfAppointment) throws DuplicateItemException {

        Appointment appointment = new Appointment(appointmentNumber, patient, dateOfAppointment);
        try {
            AppointmentValidator.validateAppointment(appointment);
            this.appointmentsRepository.addItem(appointment);
        }catch (IllegalArgumentException e)
        {
            System.out.printf("Incorrect arguments");
        }
    }

    public Appointment getAppointmentByID(int idToSearchBy) throws ItemNotFound {

        return appointmentsRepository.getItemById(idToSearchBy);
    }

    public ArrayList<Appointment> getAllAppointmentsFromTheAppointmentsRepository() {
        ArrayList appointmentsList = new ArrayList<>((Collection) this.appointmentsRepository.getAllItems());
        return new ArrayList<>(appointmentsList);
    }

    public void updateAppointment(Integer appointmentNumber, LocalDate newDateOfAppointment) throws ItemNotFound {
        Appointment appointmentToUpdate = getAppointmentByID(appointmentNumber);
        appointmentToUpdate.setDateOfAppointment(newDateOfAppointment);
        appointmentsRepository.updateItemById(appointmentToUpdate.getId(),appointmentToUpdate);

    }

    public void deleteAppointmentByID(int id) throws ItemNotFound {
        Appointment appointmentToDelete = appointmentsRepository.getItemById(id);
        this.appointmentsRepository.deleteItemById(id);
    }

    public ArrayList<Patient> showPatientsFromAGivenCityOrderedById(String givenCity)
    {
        List<Patient> listOfPatients = getAllPatientsFromThePatientsRepository();
        ArrayList<Patient> patientsFromAGivenCity = (ArrayList<Patient>) listOfPatients.stream()
                .filter(patient -> patient.getCity().toLowerCase().contains(givenCity.toLowerCase()))
                .sorted((patient1, patient2)-> {
                    return (patient1.getId().compareTo(patient2.getId()));
                })
                .collect(Collectors.toList());
        return patientsFromAGivenCity;
    }

    public ArrayList<Patient> showPatientsEndingWithAGivenLetter(String letter)
    {
        List<Patient> listOfPatients = new ArrayList<>((Collection) patientsRepository.getAllItems());
        ArrayList<Patient> patientsEndingWithALetter;
        patientsEndingWithALetter = (ArrayList<Patient>) listOfPatients.stream()
                .filter(patient -> patient.getName().endsWith(String.valueOf(letter)))
                .collect(Collectors.toList());
        return patientsEndingWithALetter;
    }

    public Integer getPhoneNumberById(int patientId) {
        List<Patient> listOfPatients = new ArrayList<>((Collection) patientsRepository.getAllItems());
        int foundPhoneNumber;
        foundPhoneNumber = listOfPatients.stream()
                .filter(patient -> patient.getId() == patientId)
                .map(Patient::getPhoneNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalThreadStateException("Patient with ID " + patientId + " not found"));
        return foundPhoneNumber;
    }

    public ArrayList<Appointment> showAllAppointmentsOfAPatientByAGivenId(int id)
    {
        List<Appointment> listOfAppointments = new ArrayList<>((Collection) appointmentsRepository.getAllItems());
        ArrayList<Appointment> listOfAppointmentsOfAPatient;
        listOfAppointmentsOfAPatient = (ArrayList<Appointment>) listOfAppointments.stream()
                .filter(appointment -> appointment.getPatient().getId() == id)
                .collect(Collectors.toList());
        return listOfAppointmentsOfAPatient;
    }

    public ArrayList<Appointment> filteringAppointmentsBeforeDate(LocalDate date)
    {
        List<Appointment> listOfAppointments = new ArrayList<>((Collection) appointmentsRepository.getAllItems());
        ArrayList<Appointment> listOfAppointmentsBeforeDate;
        listOfAppointmentsBeforeDate = (ArrayList<Appointment>) listOfAppointments.stream()
                .filter(appointment -> appointment.getDateOfAppointment().isBefore(date))
                .collect(Collectors.toList());
        return listOfAppointmentsBeforeDate;
    }

}
