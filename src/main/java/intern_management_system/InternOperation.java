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
                ps.executeUpdate();
            }
        }
    }


}
