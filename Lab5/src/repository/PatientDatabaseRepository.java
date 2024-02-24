package repository;
import domain.Patient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientDatabaseRepository extends DatabaseRepository<Patient,Integer> {
    public PatientDatabaseRepository(String tableName) {
        super(tableName);
        getData();
    }

    @Override
    public void getData() {
        try {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int id = resultSet.getInt("id");
                    String city = resultSet.getString("city");
                    int phoneNumber = resultSet.getInt("phoneNumber");
                    Patient patient = new Patient(name, id, city, phoneNumber);
                    listOfItems.put(id, patient);
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

    @Override
    public Iterable<Patient> getAllItems() {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        try {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int id = resultSet.getInt("id");
                    String city = resultSet.getString("city");
                    int phoneNumber = resultSet.getInt("phoneNumber");
                    Patient patient = new Patient(name, id, city, phoneNumber);
                    patients.add(patient);
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
        return patients;
    }


    @Override
    public void addItem(Patient item) {
        try {
            openConnection();
            if (exists(item.getId())) {
                System.out.println("Patient with ID " + item.getId() + " already exists.");
                return;
            }
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?, ?);";
            try (PreparedStatement preparedStatement = conn.prepareStatement(insertString)) {
                preparedStatement.setString(1, item.getName());
                preparedStatement.setInt(2, item.getId());
                preparedStatement.setString(3, item.getCity());
                preparedStatement.setInt(4, item.getPhoneNumber());
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

    private boolean exists(int patientId) throws SQLException {
        String existsString = "SELECT COUNT(*) FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(existsString)) {
            preparedStatement.setInt(1, patientId);
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
    public void deleteItemById(Integer id){
        try{
            openConnection();
            String deleteString = "DELETE FROM " + tableName + " WHERE id = ?;";
            try(PreparedStatement preparedStatement = conn.prepareStatement(deleteString))
            {
                preparedStatement.setInt(1,id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Patient getItemById(Integer id){
        try{
            openConnection();
            String getString = "SELECT * FROM " + tableName + " WHERE id = ?;";
            try(PreparedStatement preparedStatement = conn.prepareStatement(getString))
            {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                String name = resultSet.getString("name");
                int ID = resultSet.getInt("id");
                String city = resultSet.getString("city");
                int phoneNumber = resultSet.getInt("phoneNumber");
                Patient patient = new Patient(name, id, city, phoneNumber);
                return patient;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateItemById(Integer id, Patient newPatient) {
        try {
            openConnection();
            String updateString = "UPDATE " + tableName + " SET name = ?, phoneNumber = ?, city = ? WHERE id = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(updateString)) {
                preparedStatement.setString(1, newPatient.getName());
                preparedStatement.setInt(2, newPatient.getPhoneNumber());  // Assuming phoneNumber is an Integer
                preparedStatement.setString(3, newPatient.getCity());
                preparedStatement.setInt(4, id);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0) {
                    System.out.println("Patient with ID " + id + " not found");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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