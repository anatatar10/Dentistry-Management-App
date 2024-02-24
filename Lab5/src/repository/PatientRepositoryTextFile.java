package repository;

import domain.Patient;

import java.io.*;

public class PatientRepositoryTextFile extends FileRepository<Patient, Integer>  {
    public PatientRepositoryTextFile(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String line = null;
            while((line = bufferedReader.readLine()) != null)
            {
                String[] stringOfData = line.split(",");
                if(stringOfData.length != 4) {
                    continue;
                } else {
                    Patient patientRead = new Patient(
                            stringOfData[0].trim(),
                            Integer.parseInt(stringOfData[1].trim()),
                            stringOfData[2].trim(),
                            Integer.parseInt(stringOfData[3].trim()));
                    listOfItems.put(Integer.parseInt(stringOfData[1].trim()), patientRead);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeFile() {
        try( BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            Iterable<Patient> listOfPatients = getAllItems();
            for(Patient patient: listOfPatients)
            {
                bufferedWriter.write(patient.getName() + ", " +
                        patient.getId() + ", " +
                        patient.getCity() + ", " +
                        patient.getPhoneNumber() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
