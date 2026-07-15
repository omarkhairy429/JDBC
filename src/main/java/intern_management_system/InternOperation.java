package intern_management_system;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InternOperation {

    /************************ Excercise 2 ************************/
    public static List<Intern> findAllInterns() {
        List<Intern> interns = new ArrayList<>();
        String sql = "select * from intern";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery())
        {
                // Need to Implement
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String email = result.getString("email");
                    int mentor_id = result.getInt("mentor_id");
                    int track_id = result.getInt("track_id");
                    Intern intern = new Intern(id, name, email, mentor_id, track_id);
                    interns.add(intern);
                }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return interns;
    }


    /************************ Excercise 3 ************************/
    public static void displayInternTrackMentor() throws SQLException{
        String sql = "select intern.name, intern_tracks.name, mentors.name\n" +
                "                     from intern, intern_tracks, mentors\n" +
                "                      where intern.Track_id = intern_tracks.track_id and intern.mentor_id = mentors.mentor_id";
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet result = ps.executeQuery();
        )
        {
            while (result.next()) {
                String name = result.getString(1);
                String track_name = result.getString(2);
                String mentor_name = result.getString(3);

                System.out.println("Name: " + name + ", track: " + track_name + ", mentor: " + mentor_name);
            }
        }
    }

    /************************ Excercise 4 ************************/
    public static Intern findByID(int id) throws SQLException{
        String sql = "select * from intern where intern.id = ?";
        try (
                Connection connection = DatabaseConfig.getConnection();
                )
        {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet result = ps.executeQuery();
                if (result.next()) {
                    int intern_id = result.getInt("id");
                    String name = result.getString("name");
                    String email = result.getString("email");
                    int mentor_id = result.getInt("mentor_id");
                    int track_id = result.getInt("track_id");
                    Intern intern = new Intern(intern_id, name, email, mentor_id, track_id);
                    return intern;
                }
            }
        }
        return null;
    }

    /************************ Excercise 5 ************************/
    public static void insertIntern(String name, String email, LocalDate birthDate, String faculty, int trackID, int mentorID) throws SQLException {
        String sql =
                "Insert into intern(name, email, birth_date, faculty, track_id, mentor_id)" +
                        "Values(?, ?, ?, ?, ?, ?)";
        try (
                Connection connection = DatabaseConfig.getConnection();
                )
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setDate(3, java.sql.Date.valueOf(birthDate));
                ps.setString(4, faculty);
                ps.setInt(5, trackID);
                ps.setInt(6, mentorID);
                int rowsUpdated = ps.executeUpdate();
                System.out.println("Number of Rows Updated: " + rowsUpdated);
            }
        }
    }


    /************************ Excercise 6 ************************/
    public static void updateInternMentor(int internId, int mentorId) throws SQLException {
        String sql = "UPDATE intern SET mentor_id = ? WHERE id = ?";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, mentorId);

            ps.setInt(2, internId);

            int rowsUpdated = ps.executeUpdate();

            System.out.println("Number of updated rows: " + rowsUpdated);
        }
    }

    /************************ Excercise 7 ************************/
    public static void deleteInternById(int id) throws SQLException {
        String sql = "DELETE FROM intern WHERE id = ?";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();

            System.out.println("Number of deleted rows: " + rowsDeleted);
        }
    }

    /************************ Excercise 8 ************************/
    public static List<Intern> findInternsByTrackName(String trackName) throws SQLException {
        List<Intern> interns = new ArrayList<>();

        String sql = "select intern.id, intern.name, intern.email, intern.mentor_id, intern.track_id " +
                "from intern, intern_tracks " +
                "where intern.track_id = intern_tracks.track_id and intern_tracks.name = ?";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, trackName);

            try (ResultSet result = ps.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String email = result.getString("email");
                    int mentorId = result.getInt("mentor_id");
                    int trackId = result.getInt("track_id");

                    Intern intern = new Intern(id, name, email, mentorId, trackId);
                    interns.add(intern);
                }
            }
        }
        return interns;
    }

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

    /************************ Excercise 10 ************************/
    public static void countInternsPerTrack() throws SQLException {
        // Group by track name and count matching intern IDs
        String sql = "select intern_tracks.name, count(intern.id) " +
                "from intern, intern_tracks " +
                "where intern.track_id = intern_tracks.track_id " +
                "group by intern_tracks.name";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet result = ps.executeQuery()
        ) {
            while (result.next()) {
                String trackName = result.getString(1);
                int count = result.getInt(2);
                System.out.println("Track Name: " + trackName + ", Intern Count: " + count);
            }
        }
    }

    /************************ Excercise 11 ************************/
    public static void countInternsPerMentor() throws SQLException {
        String sql = "select mentors.name, count(intern.id) " +
                "from intern, mentors " +
                "where intern.mentor_id = mentors.mentor_id " +
                "group by mentors.name";

        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet result = ps.executeQuery()
        ) {
            while (result.next()) {
                String mentorName = result.getString(1);
                int count = result.getInt(2);
                System.out.println("Mentor Name: " + mentorName + ", Intern Count: " + count);
            }
        }
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


}
