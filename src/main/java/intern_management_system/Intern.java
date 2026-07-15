package intern_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Intern {
    private int id;
    private String name;
    private String email;
    private String birthDate;
    private String faculty;
    private int mentor_id;
    private int track_id;

    public Intern(int id,String name, String email, int mentor_id, int track_id) {
        this.id = id;
        this.name = name;
        this.mentor_id = mentor_id;
        this.track_id = track_id;
        this.email = email;
    }

    public void  displayIntern() {
        System.out.println("ID: " +id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Mento ID: " + mentor_id);
        System.out.println("Track ID: " + track_id);
    }



}
