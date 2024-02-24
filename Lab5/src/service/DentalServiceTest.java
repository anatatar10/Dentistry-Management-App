package service;

import domain.Appointment;
import domain.Patient;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AppointmentRepository;
import repository.PatientRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DentalServiceTest {

    private DentalService dentalService;

    @BeforeEach
    void setUp() {
        PatientRepository mockPatientRepository = new PatientRepository();
        AppointmentRepository mockAppointmentRepository = new AppointmentRepository();

//        dentalService = new DentalService(mockPatientRepository, mockAppointmentRepository);
    }

    @Test
    void addPatient() throws DuplicateItemException {
        dentalService.addPatient("John", 1, "City", 123456789);
        try{
            dentalService.addPatient("John",1,"city",121);
        }catch (DuplicateItemException e)
        {
            System.out.printf("Item already exists");
        }
    }

    @Test
    void getPatientByID() {
        try
        {
            dentalService.getPatientByID(1);
        } catch (ItemNotFound e) {
            System.out.println("item not found");
        }
        assertDoesNotThrow(() -> dentalService.addPatient("Jane", 2, "New City", 987654321));
        assertDoesNotThrow(() -> assertNotNull(dentalService.getPatientByID(2)));
    }

    @Test
    void getAllPatientsFromThePatientsRepository() throws DuplicateItemException {
        assertNotNull(dentalService.getAllPatientsFromThePatientsRepository());
        assertEquals(0, ((Iterable<Patient>) dentalService.getAllPatientsFromThePatientsRepository()).spliterator().getExactSizeIfKnown());
        dentalService.addPatient("Jane", 2, "New City", 987654321);
        assertEquals(1, ((Iterable<Patient>) dentalService.getAllPatientsFromThePatientsRepository()).spliterator().getExactSizeIfKnown());
    }

    @Test
    void updatePatient() throws ItemNotFound, DuplicateItemException {
        try {
            dentalService.updatePatient("Jane", 2, "New City", 987654321);
            assertEquals("Jane", dentalService.getPatientByID(2));
        } catch (ItemNotFound e)
        {
            System.out.println("item not found");
        }

        dentalService.addPatient("John", 1, "City", 123456789);
        assertEquals("John", dentalService.getPatientByID(1).getName());

    }

    @Test
    void deletePatientByID() throws DuplicateItemException {
        try{
        dentalService.deletePatientByID(1);
        }catch (ItemNotFound e)
        {
            System.out.printf("No item");
        }

        // Add a patient and then delete
        dentalService.addPatient("John", 1, "City", 123456789);
        assertDoesNotThrow(() -> dentalService.deletePatientByID(1));


    }

    @Test
    void addAppointment() throws DuplicateItemException {
        Patient patient = new Patient("John", 1, "City", 123456789);
        LocalDate dateOfAppointment = LocalDate.now();
        dentalService.addAppointment(1, patient, dateOfAppointment);
        try{
            dentalService.addAppointment(1,patient,dateOfAppointment);
        }catch (DuplicateItemException e)
        {
            System.out.println("Already exists");
        }
    }

    @Test
    void getAppointmentByID() throws ItemNotFound, DuplicateItemException {
        try {
            dentalService.getAppointmentByID(1);
        }catch (ItemNotFound e)
        {
            System.out.println("item not found");
        }


        Patient patient = new Patient("John", 1, "City", 123456789);
        LocalDate dateOfAppointment = LocalDate.now();
        dentalService.addAppointment(1, patient, dateOfAppointment);

        assertEquals("John", dentalService.getAppointmentByID(1).getPatient().getName());
    }

    @Test
    void getAllAppointmentsFromTheAppointmentsRepository() throws DuplicateItemException {
        assertNotNull(dentalService.getAllAppointmentsFromTheAppointmentsRepository());
        assertEquals(0, ((Iterable<Appointment>) dentalService.getAllAppointmentsFromTheAppointmentsRepository()).spliterator().getExactSizeIfKnown());
        Patient patient = new Patient("John", 1, "City", 123456789);
        LocalDate dateOfAppointment = LocalDate.now();
        dentalService.addAppointment(1, patient, dateOfAppointment);
        assertEquals(1, ((Iterable<Appointment>) dentalService.getAllAppointmentsFromTheAppointmentsRepository()).spliterator().getExactSizeIfKnown());

    }

    @Test
    void updateAppointment() throws ItemNotFound, DuplicateItemException {
        try{
        dentalService.updateAppointment(1, LocalDate.now());
        } catch (ItemNotFound e)
        {
            System.out.println("No item");
        }

        Patient patient = new Patient("John", 1, "City", 123456789);
        LocalDate dateOfAppointment = LocalDate.now();
        dentalService.addAppointment(1, patient, dateOfAppointment);

        dentalService.updateAppointment(1, LocalDate.now().plusDays(1));


        Appointment updatedAppointment = dentalService.getAppointmentByID(1);
        assertEquals(LocalDate.now().plusDays(1), updatedAppointment.getDateOfAppointment());
    }

    @Test
    void deleteAppointmentByID() throws DuplicateItemException, ItemNotFound {
        assertThrows(ItemNotFound.class, () -> dentalService.deleteAppointmentByID(1));


        Patient patient = new Patient("John", 1, "City", 123456789);
        LocalDate dateOfAppointment = LocalDate.now();
        dentalService.addAppointment(1, patient, dateOfAppointment);
        dentalService.deleteAppointmentByID(1);

    }
}