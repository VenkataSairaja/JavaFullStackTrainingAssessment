import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeManager {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addEmployee(Employee employee) {
        String sql = "INSERT INTO Employees (name, department) VALUES (?,?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getDepartment());
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM Employees";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setDepartment(rs.getString("department"));
            return employee;
        });
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM Employees WHERE id =?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setDepartment(rs.getString("department"));
            return employee;
        });
    }

    public void updateEmployee(Employee employee) {
        String sql = "UPDATE Employees SET name =?, department =? WHERE id =?";
        jdbcTemplate.update(sql, employee.getName(), employee.getDepartment(), employee.getId());
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM Employees WHERE id =?";
        jdbcTemplate.update(sql, id);
    }
}