import config.DatabaseConfig;
import intern_management_system.Intern;
import intern_management_system.InternOperation;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args){
        try ( Connection connection = DatabaseConfig.getConnection()) {

            System.out.println("Finding all interns \n");
            List<Intern> interns = new ArrayList<>();
            interns = InternOperation.findAllInterns();
            for (Intern intern: interns) {
                intern.displayIntern();
            }
            System.out.println("######################## \n");


            System.out.println("Displaying Intern, Track, Mentor \n");
            InternOperation.displayInternTrackMentor();
            System.out.println("######################## \n");


            System.out.println("Finding an intern by id \n");
            Intern intern = InternOperation.findByID(4);
            intern.displayIntern();
            System.out.println("######################## \n");


//            System.out.println("Inserting an Intern \n");
//            LocalDate birthDate = LocalDate.of(2003, 06, 23);
//            InternOperation.insertIntern("Omar Fayed", "omar.123.5832@gmail.com", birthDate, "Ain Shams",1, 1);
//            System.out.println("Intern inserted successfully");
//            System.out.println("######################## \n");

//            System.out.println("Updating mentor of an Intern \n");
//            InternOperation.updateInternMentor(9, 2);
//            System.out.println("######################## \n");

//            System.out.println("Deleting an Intern \n");
//            InternOperation.deleteInternById(13);
//            System.out.println("######################## \n");

            System.out.println("Finding interns by Track Name \n");
            List<Intern> trackInterns = InternOperation.findInternsByTrackName("Backend");
            for (Intern currentIntern : trackInterns) {
                currentIntern.displayIntern();
            }
            System.out.println("######################## \n");


            System.out.println("Showing projects assigned to intern \n");
            InternOperation.findProjectsByInternName("Omar Nabil");
            System.out.println("######################## \n");


            System.out.println("Counting interns per Track \n");
            InternOperation.countInternsPerTrack();
            System.out.println("######################## \n");

            System.out.println("Counting interns per Mentor \n");
            InternOperation.countInternsPerMentor();
            System.out.println("######################## \n");

            System.out.println("Assigning a Project to an Intern \n");
            InternOperation.assignProjectToIntern(4, 2);
            System.out.println("######################## \n");



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
