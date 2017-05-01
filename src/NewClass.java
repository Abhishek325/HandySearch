
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abhishek Kumar Singh
 */
public class NewClass {
    static com.mysql.jdbc.Connection con;
    static com.mysql.jdbc.Statement stmt;
    static ResultSet rs;
    public static void connect()
    {
       try 
            {
                Class.forName("java.sql.DriverManager");
                con = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/recommend", "root", "");
                stmt = (com.mysql.jdbc.Statement) con.createStatement();  
             }
         catch (Exception e)
            {   
                e.printStackTrace();
            }
    }
    public static void main(String args[]) throws IOException, SQLException
    {
        connect();
         inject();
    }
    public static void inject()throws IOException, SQLException
    {
        BufferedReader br=new BufferedReader(new FileReader("E:\\interest.txt"));
        String str; 
        int i=0;
        while((str=br.readLine())!=null)
        {
            i++;
            System.out.println("Attempting "+i+".... "+str);
            String query="INSERT INTO `recommend`.`interest` (`itemId`,`itemName`) VALUES ("+i+",'"+str+"');";   
             stmt.executeUpdate(query);
        }
        br.close();
    }
}
