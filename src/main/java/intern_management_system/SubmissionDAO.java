package intern_management_system;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubmissionDAO {
    /************************ Excercise 14 ************************/
    public static void insertSubmission(int internId, int projectId, int score) throws SQLException {
        String sql = "insert into project_submissions (intern_id, project_id, score) values (?, ?, ?)";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, internId);
            ps.setInt(2, projectId);
            ps.setInt(3, score);

            int rowsInserted = ps.executeUpdate();
            System.out.println("Number of inserted rows: " + rowsInserted);
        }
    }


    public static void displayAllSubmissions() throws SQLException {
        String sql = "select project_submissions.submission_id, intern.name, projects.name, " +
                "project_submissions.submitted_at, project_submissions.score " +
                "from project_submissions, intern, projects " +
                "where project_submissions.intern_id = intern.id " +
                "and project_submissions.project_id = projects.project_id";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet result = ps.executeQuery()
        ) {
            while (result.next()) {
                int submissionId = result.getInt(1);
                String internName = result.getString(2);
                String projectName = result.getString(3);
                java.sql.Timestamp submittedAt = result.getTimestamp(4);
                int score = result.getInt(5);

                System.out.println("Sub ID: " + submissionId +
                        ", Intern: " + internName +
                        ", Project: " + projectName +
                        ", Submitted At: " + submittedAt +
                        ", Score: " + score);
            }
        }
    }
}
