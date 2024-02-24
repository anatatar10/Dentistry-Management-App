package repository;

import domain.Appointment;
import domain.Patient;

import java.io.*;
import java.time.LocalDate;

public class AppointmentRepositoryTextFile extends FileRepository<Appointment, Integer> {
    public AppointmentRepositoryTextFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] stringOfData = line.split(",");
                if (stringOfData.length != 6) {
                    continue;
                } else {
                    int appointmentNumber = Integer.parseInt(stringOfData[0].trim());
                    String patientName = stringOfData[1].trim();
                    int patientId = Integer.parseInt(stringOfData[2].trim().replaceAll("\\D", ""));
                    String patientCity = stringOfData[3].trim();

                    String phoneNumberString = stringOfData[4].trim();
                    int patientPhoneNumber = Integer.parseInt(phoneNumberString.replaceAll("\\D", ""));

                    LocalDate dateOfAppointment = LocalDate.parse(stringOfData[5].trim());

                    Appointment appointmentRead = new Appointment(
                            appointmentNumber,
                            new Patient(patientName, patientId, patientCity, patientPhoneNumber),
                            dateOfAppointment);

                    listOfItems.put(appointmentNumber, appointmentRead);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    protected void writeFile() {
        try( BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            Iterable<Appointment> listOfAppointments = getAllItems();
            for(Appointment appointment: listOfAppointments)
            {
                bufferedWriter.write(appointment.getId() + ", " +
                                        appointment.getPatient().getName() + ", " +
                                        appointment.getPatient().getId() + ", " +
                                        appointment.getPatient().getCity() + ", " +
                                        appointment.getPatient().getPhoneNumber() + ", "+
                                        appointment.getDateOfAppointment() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

