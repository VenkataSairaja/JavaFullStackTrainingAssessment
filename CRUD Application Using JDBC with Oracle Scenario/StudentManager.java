//It is the implementation of the Student class and the StudentManager class in Java using JDBC to interact with the Oracle database
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Student {
    private int id;
    private String name;
    private int age;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class StudentManager {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public StudentManager() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
        String username = "your_username";
        String password = "your_password";

        connection = DriverManager.getConnection(url, username, password);
    }

    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO Students (id, name, age) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, student.getId());
        preparedStatement.setString(2, student.getName());
        preparedStatement.setInt(3, student.getAge());
        preparedStatement.executeUpdate();
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Students";
        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Student student = new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"));
            students.add(student);
        }

        return students;
    }

    public Student getStudentById(int id) throws SQLException {
        String query = "SELECT * FROM Students WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Student(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("age"));
        }

        return null;
    }

    public void updateStudent(int id, String name, int age) throws SQLException {
        String query = "UPDATE Students SET name = ?, age = ? WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    public void deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM Students WHERE id = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void close() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}