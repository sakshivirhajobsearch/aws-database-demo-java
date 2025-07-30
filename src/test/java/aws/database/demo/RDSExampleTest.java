package aws.database.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

public class RDSExampleTest {

	@Test
	void testRdsConnectionAndQuery() {
		String jdbcUrl = "jdbc:mysql://your-rds-endpoint.amazonaws.com:3306/your_db";
		String username = "admin";
		String password = "yourPassword";

		try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
				Statement stmt = conn.createStatement()) {

			assertNotNull(conn, "Connection should not be null");

			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM employees");
			assertTrue(rs.next(), "Result set should contain at least one row");

			int count = rs.getInt("total");
			System.out.println("Total employees: " + count);
			assertTrue(count >= 0, "Employee count should be non-negative");

		} catch (Exception e) {
			fail("Exception during RDS test: " + e.getMessage());
		}
	}
}
