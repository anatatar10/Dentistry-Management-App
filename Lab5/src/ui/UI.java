package ui;

import domain.Appointment;
import domain.AppointmentValidator;
import domain.Patient;
import domain.PatientValidator;
import exceptions.DuplicateItemException;
import exceptions.ItemNotFound;
import service.DentalService;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class UI {
    private DentalService dentalService;

    public UI(DentalService dentalService) {
        this.dentalService = dentalService;
    }
    public void listAllPatients()
    {
        Iterable<Patient> listOfPatients = this.dentalService.getAllPatientsFromThePatientsRepository();
        System.out.println("\nList of patients:\n");
        for(Patient patient: listOfPatients)
        {
            System.out.println(patient);
        }
    }

    public void listAllAppointments()
    {
        Iterable<Appointment> listOfAppointments = this.dentalService.getAllAppointmentsFromTheAppointmentsRepository();
        System.out.println("\nList of appointments:\n");
        for(Appointment appointment: listOfAppointments)
        {
            System.out.println(appointment);
        }
    }

    public void addPatient() throws DuplicateItemException {
        Scanner addScanner = new Scanner(System.in);
        System.out.println("Enter patient name: ");
        String addName = addScanner.nextLine();

        int addId;
        while (true) {
            System.out.println("Enter patient id: ");
            if (addScanner.hasNextInt()) {
                addId = addScanner.nextInt();
                addScanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                addScanner.nextLine();
            }
        }

        System.out.println("Enter patient city: ");
        String addCity = addScanner.nextLine();


        int addPhoneNumber;
        while (true) {
            System.out.println("Enter patient phone number: ");
            if (addScanner.hasNextInt()) {
                addPhoneNumber = addScanner.nextInt();
                addScanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid phone number.");
                addScanner.nextLine();
            }
        }
        try {
            Patient patientToAdd = new Patient(addName, addId, addCity, addPhoneNumber);
            PatientValidator.validatePatient(patientToAdd);
            dentalService.addPatient(addName, addId, addCity, addPhoneNumber);
        }catch (DuplicateItemException e)
        {
            System.out.printf("A patient with id " + addId + " already exists");
        }
        catch (IllegalArgumentException e)
        {
            System.out.printf("Illegal arguments");
        }
    }

    public void addAppointment() throws DuplicateItemException, ItemNotFound {
        System.out.println("You have chosen to create a new appointment");
        System.out.println("Do you want to add the details of the patient now? (Y/N): ");
        Scanner scannerAnswer = new Scanner(System.in);
        String userInput = scannerAnswer.nextLine();
        if (userInput.equalsIgnoreCase("Y")) {
            Scanner addScanner = new Scanner(System.in);
            System.out.println("Enter patient name: ");
            String addName = addScanner.nextLine();

            int addId;
            while (true) {
                System.out.println("Enter patient id: ");
                if (addScanner.hasNextInt()) {
                    addId = addScanner.nextInt();
                    addScanner.nextLine();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    addScanner.nextLine();
                }
            }

            System.out.println("Enter patient city: ");
            String addCity = addScanner.nextLine();


            int addPhoneNumber;
            while (true) {
                System.out.println("Enter patient phone number: ");
                if (addScanner.hasNextInt()) {
                    addPhoneNumber = addScanner.nextInt();
                    addScanner.nextLine();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid phone number.");
                    addScanner.nextLine();
                }
            }
            try {
                dentalService.addPatient(addName, addId, addCity, addPhoneNumber);
            } catch (DuplicateItemException e) {
                System.out.printf("A patient with id " + addId + " already exists");
                return;
            }
            Patient patientToAdd = new Patient(addName, addId, addCity, addPhoneNumber);

            int appointmentNumberToAdd;
            while (true) {
                System.out.println("Enter appointment number: ");
                if (addScanner.hasNextInt()) {
                    appointmentNumberToAdd = addScanner.nextInt();
                    addScanner.nextLine();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    addScanner.nextLine();
                }
            }

            Scanner scannerDate = new Scanner(System.in);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            System.out.print("Please enter a date (YYYY-MM-DD): ");
            LocalDate dateOfAppointmentToAdd = null;
            while (dateOfAppointmentToAdd == null) {
                System.out.print("Please enter a date (YYYY-MM-DD): ");
                String userInputDate = scannerDate.nextLine();
                if (userInputDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    dateOfAppointmentToAdd = LocalDate.parse(userInputDate, dateFormatter);
                } else {
                    System.out.println("Invalid date format. Please enter a date in the format YYYY-MM-DD.");
                }
            }

            try {
                Appointment appointmentToAdd = new Appointment(appointmentNumberToAdd, patientToAdd, dateOfAppointmentToAdd);
                AppointmentValidator.validateAppointment(appointmentToAdd);
                dentalService.addAppointment(appointmentNumberToAdd, patientToAdd, dateOfAppointmentToAdd);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a date in the format YYYY-MM-DD.");
            }catch (DuplicateItemException e) {
                System.out.println("An appointment with the same id already exists");
            }catch (IllegalArgumentException e)
            {
                System.out.println("Illegal arguments");
            }
        }
        else {
            System.out.println("Choose the patient from the list below based on his id");
            listAllPatients();
            Scanner addScanner = new Scanner(System.in);
            int addId1;
            while (true) {
                if (addScanner.hasNextInt()) {
                    addId1 = addScanner.nextInt();
                    addScanner.nextLine();
                    try {
                        dentalService.getPatientByID(addId1);
                    }
                    catch(ItemNotFound e)
                    {
                        System.out.println("Patient with id " + addId1 + " not found. Please try again");
                    }
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    addScanner.nextLine();
                }
            }
            int appointmentNumberToAdd;
            while (true) {
                System.out.println("Enter appointment number: ");
                if (addScanner.hasNextInt()) {
                    appointmentNumberToAdd = addScanner.nextInt();
                    addScanner.nextLine();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    addScanner.nextLine();
                }
            }
            Patient patientToAdd = dentalService.getPatientByID(addId1);
            Scanner scannerDate = new Scanner(System.in);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            System.out.print("Please enter a date (YYYY-MM-DD): ");
            LocalDate dateOfAppointmentToAdd = null;
            while (dateOfAppointmentToAdd == null) {
                System.out.print("Please enter a date (YYYY-MM-DD): ");
                String userInputDate = scannerDate.nextLine();
                if (userInputDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    dateOfAppointmentToAdd = LocalDate.parse(userInputDate, dateFormatter);
                } else {
                    System.out.println("Invalid date format. Please enter a date in the format YYYY-MM-DD.");
                }
            }

            try {
                dentalService.addAppointment(appointmentNumberToAdd, patientToAdd, dateOfAppointmentToAdd);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a date in the format YYYY-MM-DD.");
            }catch (DuplicateItemException e) {
                System.out.println("An appointment with the same id already exists");
            }
        }

    }

    public void getPatientById() throws ItemNotFound {
        Scanner searchScanner = new Scanner(System.in);
        int searchId;
        while (true) {
            System.out.println("Enter patient id: ");
            if (searchScanner.hasNextInt()) {
                searchId = searchScanner.nextInt();
                searchScanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                searchScanner.nextLine();
            }
        }
        try {
            Patient patientFound = dentalService.getPatientByID(searchId);
            System.out.printf(patientFound.toString());
        }catch (ItemNotFound e)
        {
            System.out.println("No patient found with id " + searchId);
        }
    }

    public void getAppointmentById() throws ItemNotFound {
        Scanner searchScanner = new Scanner(System.in);
        int searchId;
        while (true) {
            System.out.println("Enter appointment number: ");
            if (searchScanner.hasNextInt()) {
                searchId = searchScanner.nextInt();
                searchScanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                searchScanner.nextLine();
            }
        }
        try {
            Appointment appointmentToSearchById = dentalService.getAppointmentByID(searchId);
            System.out.println(appointmentToSearchById);
        }catch (ItemNotFound e)
        {
            System.out.println("No appointment found with id " + searchId);
        }


    }
    public void updatePatient() throws ItemNotFound {
        System.out.println("List of patients: ");
        listAllPatients();
        Scanner updateScanner = new Scanner(System.in);
        int updateById;
        while (true) {
            System.out.println("Enter patient id: ");
            if (updateScanner.hasNextInt()) {
                updateById = updateScanner.nextInt();
                updateScanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                updateScanner.nextLine();
            }
        }
        System.out.println("Enter the new patient name: ");
        String updateName = updateScanner.nextLine();

        System.out.println("Enter the new patient city: ");
        String updateCity = updateScanner.nextLine();

        int updatePhoneNumber;
        while (true) {
            System.out.println("Enter patient phone number: ");
            if (updateScanner.hasNextInt()) {
                updatePhoneNumber = updateScanner.nextInt();
                updateScanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid phone number.");
                updateScanner.nextLine();
            }
        }
        try{
            Patient updatePatient = new Patient(updateName, updateById, updateCity, updatePhoneNumber);
            PatientValidator.validatePatient(updatePatient);
            dentalService.updatePatient(updateName, updateById, updateCity, updatePhoneNumber);
        }catch (ItemNotFound e)
        {
            System.out.println("Patient with id " + updateById + " not found");
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }

    public void updateAppointment() throws ItemNotFound {
        Scanner updateScanner = new Scanner(System.in);
        int updateByAppointmentNumber;
        while (true) {
            System.out.println("Enter appointment number: ");
            if (updateScanner.hasNextInt()) {
                updateByAppointmentNumber = updateScanner.nextInt();
                updateScanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                updateScanner.nextLine();
            }
        }
        System.out.println("Enter the new date: ");
        LocalDate updateDateOfAppointmentToAdd = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (updateDateOfAppointmentToAdd == null) {
            System.out.print("Please enter a date (YYYY-MM-DD): ");
            String userInputDate = updateScanner.nextLine();
            if (userInputDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                updateDateOfAppointmentToAdd = LocalDate.parse(userInputDate, dateFormatter);
            } else {
                System.out.println("Invalid date format. Please enter a date in the format YYYY-MM-DD.");
            }
        }
        try {
            Appointment appointmentToUpdate = new Appointment(updateByAppointmentNumber, dentalService.getAppointmentByID(updateByAppointmentNumber).getPatient(),updateDateOfAppointmentToAdd);
            dentalService.updateAppointment(updateByAppointmentNumber, updateDateOfAppointmentToAdd);
            System.out.println("Appointment was updated");
        }catch (ItemNotFound e)
        {
            System.out.println("Appointment with number " + updateByAppointmentNumber + " not found");
        }catch (IllegalArgumentException e)
        {
            System.out.println("Illegal arguments");
        }
    }


    public void deletePatientByID() throws ItemNotFound {
        Scanner deleteScanner = new Scanner(System.in);
        int deleteId;
        while (true) {
            System.out.println("Enter patient id: ");
            if (deleteScanner.hasNextInt()) {
                deleteId = deleteScanner.nextInt();
                deleteScanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                deleteScanner.nextLine();
            }
        }
        try {
            dentalService.deletePatientByID(deleteId);
            System.out.printf("Patient with id " + deleteId + " was deleted");
        }catch (ItemNotFound e)
        {
            System.out.println("No patient with id " + deleteId + " was found");
        }
    }

    public void deleteAppointmentByID() throws ItemNotFound {
        Scanner deleteScanner = new Scanner(System.in);
        int deleteAppointmentNumber;
        while (true) {
            System.out.println("Enter appointment number: ");
            if (deleteScanner.hasNextInt()) {
                deleteAppointmentNumber = deleteScanner.nextInt();
                deleteScanner.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                deleteScanner.nextLine();
            }
        }
        try {
            dentalService.deleteAppointmentByID(deleteAppointmentNumber);
            System.out.printf("Appointment with number " + deleteAppointmentNumber + " was canceled");
        }catch (ItemNotFound e)
        {
            System.out.println("No appointment with id " + deleteAppointmentNumber + " was found");
        }
    }
    public void showPatientsFromAGivenCityOrderedById()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the city: ");
        String city = scanner.nextLine();
        ArrayList<Patient> showPatientsFromAGivenCityOrderedById = dentalService.showPatientsFromAGivenCityOrderedById(city);
        for(Patient patient: showPatientsFromAGivenCityOrderedById)
        {
            System.out.println(patient.toString());
        }
    }
    public void showPatientsEndingWithAGivenLetter(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the letter: ");
        String letter = scanner.nextLine();
        ArrayList<Patient> showPatientsEndingWithAGivenLetter = dentalService.showPatientsEndingWithAGivenLetter(letter);
        for(Patient patient: showPatientsEndingWithAGivenLetter)
        {
            System.out.println(patient.toString());
        }
    }

    public void phoneNumberOfAPatientByAGivenId(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        int phoneNumberById = dentalService.getPhoneNumberById(id);
        System.out.printf("The phone number of patient with id "+ id + " is: " + String.valueOf(phoneNumberById));
    }

    public void showAllAppointmentsOfAPatientByAGivenId()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        ArrayList<Appointment> showAllAppointmentsOfAPatientByAGivenId = dentalService.showAllAppointmentsOfAPatientByAGivenId(id);
        for(Appointment appointment: showAllAppointmentsOfAPatientByAGivenId)
        {
            System.out.println(appointment);
        }
    }

    public void filteringAppointmentsByDataRange()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        ArrayList<Appointment> filteringAppointmentsByDataRange = dentalService.filteringAppointmentsBeforeDate(date);
        for(Appointment appointment: filteringAppointmentsByDataRange)
        {
            System.out.println(appointment);
        }

    }


    public void printMenu() {
        System.out.println("\n================");
        System.out.println("---Dentistry---");
        System.out.println("================\n");
        System.out.println("0 - Exit");
        System.out.println("1 - List all the patients");
        System.out.println("2 - Add a patient");
        System.out.println("3 - Delete a patient");
        System.out.println("4 - Update a patient");
        System.out.println("5 - Search a patient");
        System.out.println("6 - List all appointments");
        System.out.println("7 - Add an appointment");
        System.out.println("8 - Cancel an appointment");
        System.out.println("9 - Update an appointment");
        System.out.println("10 - Search an appointment");
        System.out.println("11 - Get all the patients from a given city ordered by id");
        System.out.println("12 - Patients whose name are ending with a given letter");
        System.out.println("13 - The phone number of a certain patient given by id");
        System.out.println("14 - All the appointments for a certain patient given by id");
        System.out.println("15 - The appointments before a date");
    }

    public void run() throws DuplicateItemException, ItemNotFound {
        while(true)
        {
            printMenu();
            System.out.print("Please input your option: ");
            Scanner scanCommand = new Scanner(System.in);
            int command = scanCommand.nextInt();
            switch (command)
            {
                case 0:
                    System.out.println("Exiting the program.");
                    return;
                case 1:
                    listAllPatients();
                    break;
                case 2:
                    addPatient();
                    break;
                case 3:
                    deletePatientByID();
                    break;
                case 4:
                    updatePatient();
                    break;
                case 5:
                    getPatientById();
                    break;
                case 6:
                    listAllAppointments();
                    break;
                case 7:
                    addAppointment();
                    break;
                case 8:
                    deleteAppointmentByID();
                    break;
                case 9:
                    updateAppointment();
                    break;
                case 10:
                    getAppointmentById();
                    break;
                case 11:
                    showPatientsFromAGivenCityOrderedById();
                    break;
                case 12:
                    showPatientsEndingWithAGivenLetter();
                    break;
                case 13:
                    phoneNumberOfAPatientByAGivenId();
                    break;
                case 14:
                    showAllAppointmentsOfAPatientByAGivenId();
                    break;
                case 15:
                    filteringAppointmentsByDataRange();
                    break;
            }
        }
    }
}
