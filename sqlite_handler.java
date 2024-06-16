import java.sql.Connection;  
import java.sql.DatabaseMetaData;  
import java.sql.DriverManager;  
import java.sql.SQLException; 
import java.sql.Statement; 
import java.sql.ResultSet;
public class sqlite_handler{
    public static Connection create_database(String database_name){
        // String databasepath="jdbc:sqlite:/C:\\Users\\Dell\\Desktop\\java_project\\project\\database\\"+database_name+".db";
        
        String databasepath="jdbc:sqlite:/D:\\javaProject-main\\project\\database\\"+database_name+".db";
        try {  
            Connection conn = DriverManager.getConnection(databasepath);  
            if (conn != null) {  
                DatabaseMetaData meta = conn.getMetaData();  
               System.out.println("The driver name is " + meta.getDriverName());  
             //   System.out.println("A new database has been created.");  
            }  
            return conn;
   
        } catch (SQLException e) {  
            System.out.println(e.getMessage()); 
            return null; 
        }   
        
    } 
        public static ResultSet runStatement(Connection c1,String sqlcommand) {  
            // SQLite connection string  
            //String url = "jdbc:sqlite:C://sqlite/SSSIT.db";  
              
            // SQL statement for creating a new table  
            // String sql = "CREATE TABLE IF NOT EXISTS admin (\n"  
            //         + " id integer PRIMARY KEY,\n"  
            //         + " name text NOT NULL,\n"  
            //         + " capacity real\n"  
            //         + ");";  
              
            try{  
               // Connection conn = DriverManager.getConnection(url);  
                Statement stmt = c1.createStatement();  
                ResultSet rs=stmt.executeQuery(sqlcommand);  
                System.out.println("command executed Succesfully");
                return rs;
            } catch (SQLException e) {  
                System.out.println(e.getMessage()); 
                return null; 
            }  
        }
         
   
}