package intern_management_system;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    /************************ Excercise 9 ************************/
    public static List<String> findProjectsByInternName(String fullName) throws SQLException {
        List<String> projectNames = new ArrayList<>();

        String sql = "select projects.name " +
                "from intern, intern_project, projects " +
                "where intern.id = intern_project.intern_id " +
                "and intern_project.project_id = projects.project_id " +
                "and intern.name = ?";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, fullName);

            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    String projectName = result.getString(1);

                    System.out.println("Intern Name: " + fullName + ", Project Name: " + projectName);

                    projectNames.add(projectName);
                }
            }
        }
        return projectNames;
    }

    /************************ Excercise 12 ************************/
    public static void assignProjectToIntern(int internId, int projectId) throws SQLException {
        String sql = "insert into intern_project(intern_id, project_id) values(?, ?)";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, internId);
            ps.setInt(2, projectId);

            int rowsInserted = ps.executeUpdate();

            System.out.println("Number of inserted rows: " + rowsInserted);
        }
    }

    /************************ Excercise 13 ************************/
    public static void showAllInternProjectAssignments() throws SQLException {
        String sql = "select intern.name, projects.name " +
                "from intern, intern_project, projects " +
                "where intern.id = intern_project.intern_id " +
                "and intern_project.project_id = projects.project_id";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet result = ps.executeQuery()
        ) {
            while (result.next()) {
                String internName = result.getString(1);
                String projectName = result.getString(2);

                System.out.println("Intern: " + internName + ", Project: " + projectName);
            }
        }
    }

    /************************ Excercise 15 ************************/
    public static void updateProjectStatus(int projectId, String status) throws SQLException {
        String sql = "update projects set status = ? where project_id = ?";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, status);
            ps.setInt(2, projectId);

            int rowsUpdated = ps.executeUpdate();
            System.out.println("Number of updated rows: " + rowsUpdated);
        }
    }
}
