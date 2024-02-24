package repository;

import domain.Patient;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//delete the content from testPatients.txt before testing
class FileRepositoryTest {
    private FileRepository<Patient,Integer> patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository = new PatientRepositoryTextFile("testPatients.txt");
    }

    @Test
    void readFromFile() {
    }

    @Test
    void writeFile() {
    }

    @Test
    void addItem() throws DuplicateItemException {
        Patient patient1 = new Patient("John", 1, "City", 123456789);
        Patient patient2 = new Patient("John", 1, "City", 123456789);
        patientRepository.addItem(patient1);
        Iterable<Patient> listPatients = patientRepository.getAllItems();
        boolean found = false;
        for(Patient patient: listPatients)
        {
            if(patient.getId() == 1)
                found = true;
        }
        assertTrue(found);
        try{
            patientRepository.addItem(patient2);
        }catch (DuplicateItemException e)
        {
            System.out.println("Item already exists");
        }
    }

    @Test
    void deleteItemById() throws DuplicateItemException, ItemNotFound {
        assert patientRepository.listOfItems.size() == 2;
        Patient patient3 = new Patient("Chris", 3, "City", 123456789);
        patientRepository.addItem(patient3);
        try{
            patientRepository.deleteItemById(10);
        } catch (ItemNotFound e)
        {
            System.out.println("item not found");
        }

        try{
            patientRepository.deleteItemById(3);
        } catch (ItemNotFound e)
        {
            System.out.println("item not found");
        }
    }

    @Test
    void getItemById() throws DuplicateItemException {
        Patient patient1 = new Patient("John", 1, "City1", 123456789);
        Patient patient2 = new Patient("Ana", 2, "City2", 987654321);
        try {
            patientRepository.addItem(patient1);
        }catch (DuplicateItemException e)
        {
            System.out.println("Item already exists");
        }
        try {
            patientRepository.addItem(patient2);
        }catch (DuplicateItemException e)
        {
            System.out.println("Item already exists");
        }
        Iterable<Patient> listPatients = patientRepository.getAllItems();
        boolean found = false;
        for(Patient patient: listPatients)
        {
            if(patient.getId() == 1)
                found = true;
        }
        assertTrue(found);
        try{
            patientRepository.getItemById(10);
        }catch (ItemNotFound e)
        {
            System.out.println("item not found");
        }
    }

    @Test
    void updateItemById() throws ItemNotFound {
        Patient patientToUpdate = new Patient("Lewis" ,1,"Monaco",2132);
        patientRepository.updateItemById(1, patientToUpdate);
        assertEquals("Lewis", patientRepository.getItemById(1).getName());
    }

    @Test
    void getAllItems() throws ItemNotFound {
        Iterable<Patient> listOfPatients = patientRepository.getAllItems();
        int counter = 0;
        for(Patient patient: listOfPatients)
        {
            counter++;
        }
        assert counter == 2;

    }
}