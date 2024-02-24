package repository;

import domain.Appointment;
import domain.Patient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AppointmentsDatabaseRepository extends DatabaseRepository<Appointment,Integer> {

    public AppointmentsDatabaseRepository(String tableName) {
        super(tableName);
        getData();
    }

    @Override
    public void getData() {
        try {
            openConnection();
            String selectString = "SELECT a.appointmentNumber, a.dateOfAppointment, p.id as patientId, p.name, p.city, p.phoneNumber " +
                    "FROM " + tableName + " a " +
                    "JOIN patients p ON a.patientId = p.id;";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int appointmentNumber = resultSet.getInt("appointmentNumber");
                    String dateAsString = resultSet.getString("dateOfAppointment");
                    LocalDate dateOfAppointment = LocalDate.parse(dateAsString);
                    int patientId = resultSet.getInt("patientId");
                    String patientName = resultSet.getString("name");
                    String patientCity = resultSet.getString("city");
                    int patientPhoneNumber = resultSet.getInt("phoneNumber");

                    Patient patient = new Patient(patientName, patientId, patientCity, patientPhoneNumber);
                    Appointment appointment = new Appointment(appointmentNumber, patient, dateOfAppointment);
                    listOfItems.put(appointmentNumber, appointment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Appointment> getAllItems() {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        try {
            openConnection();
            String selectString = "SELECT a.appointmentNumber, a.dateOfAppointment, p.id as patientId, p.name, p.city, p.phoneNumber " +
                    "FROM " + tableName + " a " +
                    "JOIN patients p ON a.patientId = p.id;";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int appointmentNumber = resultSet.getInt("appointmentNumber");
                    String dateAsString = resultSet.getString("dateOfAppointment");
                    LocalDate dateOfAppointment = LocalDate.parse(dateAsString);
                    int patientId = resultSet.getInt("patientId");
                    String patientName = resultSet.getString("name");
                    String patientCity = resultSet.getString("city");
                    int patientPhoneNumber = resultSet.getInt("phoneNumber");

                    Patient patient = new Patient(patientName, patientId, patientCity, patientPhoneNumber);
                    Appointment appointment = new Appointment(appointmentNumber, patient, dateOfAppointment);
                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    @Override
    public void addItem(Appointment item) {
        try {
            openConnection();
            if (exists(item.getId())) {
                System.out.println("Appointment with ID " + item.getId() + " already exists.");
                return;
            }
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertString)) {
                preparedStatement.setInt(1, item.getId());
                preparedStatement.setInt(2, item.getPatient().getId());
                String dateString = item.getDateOfAppointment().toString();
                preparedStatement.setString(3, dateString);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private boolean exists(int appointmentId) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE appointmentNumber = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, appointmentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }



    @Override
    public void deleteItemById(Integer appointmentNumber) {
        try {
            openConnection();
            String deleteString = "DELETE FROM " + tableName + " WHERE appointmentNumber = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(deleteString)) {
                preparedStatement.setInt(1, appointmentNumber);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Appointment getItemById(Integer id) {
        try {
            openConnection();
            String getString = "SELECT a.appointmentNumber, a.dateOfAppointment, p.id as patientId, p.name, p.city, p.phoneNumber " +
                    "FROM " + tableName + " a " +
                    "JOIN patients p ON a.patientId = p.id " +
                    "WHERE a.appointmentNumber = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(getString)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                int appointmentNumber = resultSet.getInt("appointmentNumber");
                String dateAsString = resultSet.getString("dateOfAppointment");
                LocalDate dateOfAppointment = LocalDate.parse(dateAsString);
                int patientId = resultSet.getInt("patientId");
                String patientName = resultSet.getString("name");
                String patientCity = resultSet.getString("city");
                int patientPhoneNumber = resultSet.getInt("phoneNumber");

                Patient patient = new Patient(patientName, patientId, patientCity, patientPhoneNumber);
                Appointment appointment = new Appointment(appointmentNumber, patient, dateOfAppointment);
                return appointment;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateItemById(Integer id, Appointment newAppointment) {
        try {
            openConnection();
            String updateString = "UPDATE " + tableName + " SET dateOfAppointment = ?, patientId = ? WHERE appointmentNumber = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateString)) {
                preparedStatement.setString(1, newAppointment.getDateOfAppointment().toString());
                preparedStatement.setInt(2, newAppointment.getPatient().getId());
                preparedStatement.setInt(3, id);  // Specify the condition based on the appointmentNumber

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    System.out.println("Appointment with ID " + id + " not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
