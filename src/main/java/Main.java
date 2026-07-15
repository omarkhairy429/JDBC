import config.DatabaseConfig;
import intern_management_system.Intern;
import intern_management_system.InternOperation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println();
            Intern intern = InternOperation.findByID(4);
            intern.displayIntern();
            System.out.println("######################## \n");


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
