package hospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.println("Enter patient name: ");
        String name = scanner.next();
        System.out.println("Enter patient age: ");
        int age = scanner.nextInt();
        System.out.println("Enter patient Gender: ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patient(name , age, gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient added Successfully");
            } else {
                System.out.println("failed to add Patient!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        String query = "select * from patient";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patient: ");
            System.out.println("+------------+----------------------+--------+----------------+");
            System.out.println("| Patient id | Name                 | Age    | Gender          ");
            System.out.println("+------------+----------------------+--------+----------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getNString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getNString("gender");
                System.out.printf("| %-11s | %-20s | %-8s | %-15s |\n", id, name, age,gender);
                System.out.println("+------------+----------------------+--------+----------------+");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "Select * FROM patient Where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}