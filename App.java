import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.time.LocalDateTime;  
//import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
//import admin_mode;
public class App extends sqlite_handler {
  private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

//..........................method to add employee in employeeDetailsTable....................................................

  public static void Add_employee(Connection admConnection){
         Scanner sc=new Scanner(System.in);
         System.out.println("Enter employee id");
         int empid=sc.nextInt();
         //System.out.println();
         System.out.println("enter name");
         String name1=sc.next();
         System.out.println("enter address");
         String address=sc.next();
         System.out.println("enter contact no");
         long contact=sc.nextLong();
         System.out.println("enter salary");
         int salary=sc.nextInt();
         System.out.println("enter password");
         String password=sc.next ();
                
         //Adding new user sql
         String sql="insert into employeeDetailsTable (employeeId,name,address,contact,salary,password) values("+empid + ", '" + name1 + "', '" + address + "'," + contact + "," + salary +",'" + password +"');";
         System.out.println(sql);
         runStatement(admConnection, sql);
      sc.close();
}
//...........................................End add employee method.......................................

                                 //method to delete employee from employeeDetailsTable
                    //admin employee id daalega toh employeeDetailsTable se wo employee details delete ho jayaega
//.............................delete employee method.......................................................
public static void deleteEmployee(Connection admConnection){
   Scanner sc=new Scanner(System.in);
   System.out.println("Enter the employee id whose details you want to delete");
   int empId=sc.nextInt();
   sc.close();

   //deleting the user whose employee id is given
   String sql="delete from employeeDetailsTable where employeeId = "+empId;
   runStatement(admConnection, sql); 
}
//.....................................End of delete employee method......................................................

  public static void Employee_login(Connection c1,String Employee_username,String Employee_password){
    Scanner sc=new Scanner(System.in);
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    System.out.println(timestamp);
    String sql="select * from employeeDetailsTable";
    ResultSet rs1=runStatement(c1,sql);
    String tablename=java.time.LocalDate.now()+"_attendance";
    String tablename1="t"+tablename.replace("-","");
    int t=0;
    try {
      while (rs1.next()) {  
        String employeeId=rs1.getString("employeeId");
        String password1=rs1.getString("password");
    //  System.out.println(employeeId);
      //System.out.println(password1);

      if(employeeId.equals(Employee_username)&&password1.equals(Employee_password)){
             t=1;
            System.out.println("welcome"+" "+Employee_username);
            //ek naya database create karenge if not exist and jiska nam hoga date_attendance.db 
            //schema employee id , entry time ,attendancetype by default absent
            //usme employee id ke saath entry time or present insert krenge
          // Connection connEmployee= create_database(java.time.LocalDate.now()+"_attendance.db");
          // String tablename=java.time.LocalDate.now()+"_attendance";
          //  String tablename1="t"+tablename.replace("-","");

         // System.out.println(tablename1);
           String sql1="create table if not exists "+tablename1 + " (\n" 
           + " employeeId int primary key not null," 
           + " entryTime text,\n"  
           + " attendancetype text default 'Absent' NOT NULL\n"    
           + ");";   
           System.out.println(sql1); 
           runStatement(c1,sql1); 
           String sql3="insert into "+tablename1 +" (employeeid,entrytime,attendancetype) values("+rs1.getString("employeeId") + ",'" + sdf1.format(timestamp) + "','" + "Present" + "')"; 
           //System.out.println(sql3);
           runStatement(c1, sql3);
      }
    }
    if(t==0){
      System.out.println("Invalid username and password");
    }
    sc.close();


      //trying
        String sql2="select * from "+ tablename1;
         ResultSet rs2=runStatement(c1,sql2);
          try {
          while (rs2.next()) {  
                           int employeeid=rs2.getInt("employeeid");
                           String entrytime=rs2.getString("entrytime");
                           String attendancetype=rs2.getString("attendancetype");
                           System.out.println(employeeid+" "+entrytime+" "+attendancetype);
                         }
                       }
                       catch (SQLException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                       }  
                   
  }
  catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }  

  }

//.......................................method for all employee details...........................................

  public static void allEmployeeDetails(Connection admConnection){
    String sql1="select * from employeeDetailsTable";
    ResultSet rs1=runStatement(admConnection,sql1);
    try {
      while (rs1.next()) {  
        System.out.println(rs1.getInt("employeeId") +  "\t" +
                             rs1.getString("name") + "\t" + 
                             rs1.getString("address") + "\t" +
                             rs1.getInt("contact") + "\t" +
                             rs1.getInt("salary") + "\t" +
                             rs1.getString("password"));
      }
     }
     catch (SQLException e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
     }
  }

  //................................End of allEmployeeDetails method................................................

                               //method for seeing the details of specific employee
                        //admin employee id daalega then he will get details of specific employee
  //..............................method for specificEmployeeDetails................................................

  public static void specificEmployeeDetails(Connection admConnection){
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter the employee id whose details you want to fetch");
    int empId=sc.nextInt();
    sc.close();
    String sql1="select * from employeeDetailsTable where employeeId = "+empId;
    ResultSet rs1=runStatement(admConnection,sql1);
    try {
      while (rs1.next()) {  
        System.out.println(rs1.getInt("employeeId") +  "\t" +
                             rs1.getString("name") + "\t" + 
                             rs1.getString("address") + "\t" +
                             rs1.getInt("contact") + "\t" +
                             rs1.getInt("salary") + "\t" +
                             rs1.getString("password"));
      }
     }
     catch (SQLException e) {
       // TODO Auto-generated catch block
       e.printStackTrace();
     }
  }

  //.......................................End of specificEmployeeDetails method.........................................

  //..................................method for seeing the details of employee........................................

  public static void employeeDetails(Connection admConnection){
    System.out.println();
    System.out.println("a..All Employee Details");
    System.out.println("b..Specific Employee Details");
    Scanner sc=new Scanner(System.in);
    System.out.println();
    System.out.println("Enter the choice");
    char choice =sc.next().charAt(0);
    sc.close();
    switch(choice){
      case 'a':
           allEmployeeDetails(admConnection);
           break;
      case 'b':
           specificEmployeeDetails(admConnection);
           break;
    }
  }
  //......................................End of this method.........................................................

  //.....................................method for updating employee.................................................
  
  public static void updateEmployeeDetails(Connection admConnection){
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter the employee Id whose details you want to update");
    int empId=sc.nextInt();
    System.out.println("What you want to update");
    System.out.println();
    System.out.println("1.Address");
    System.out.println("2.contact");
    System.out.println("3.salary");
    System.out.println("4.password");

    System.out.println("Enter your choice");
    int choice=sc.nextInt();
    if(choice==1){
     System.out.println("Enter new Address");
     String newAddress=sc.next();
     String sql="update employeeDetailsTable set address = '" + newAddress + "'" + "where employeeId = " + empId;
     runStatement(admConnection, sql);
    }
    else if(choice==2){
      System.out.println("Enter new Contact");
      int newContact=sc.nextInt();
      String sql="update employeeDetailsTable set contact = " + newContact + "where employeeId = " + empId;
      runStatement(admConnection, sql);

    }
    else if(choice==3){
      System.out.println("Enter new salary");
      int newSalary=sc.nextInt();
      String sql="update employeeDetailsTable set salary = " + newSalary + "where employeeId = " + empId;
      runStatement(admConnection, sql);

    }
    else if(choice==4){
      System.out.println("enter new password");
      String newPassword=sc.next();
      String sql="update employeeDetailsTable set password = '" + newPassword + "'"+ "where employeeId = " + empId;
      runStatement(admConnection, sql);
    }
    else{
      System.out.println("please enter valid choice");
    }
    sc.close();
  }
//.......................................................End of updateEmployeeDetails method..............................

//.......................................................allEmployeeAttendance method......................................
public static void allEmployeeAttendance(Connection admConnection){
  System.out.println();
  Scanner sc=new Scanner(System.in);
  System.out.println("Please enter date in this format yyyy/mm/dd");
  String Date=sc.next();
  System.out.println();
  String Date2=Date.replaceAll("/","");
  String Date3="t" + Date2 + "_attendance";
  String sql1="select * from "+ Date3;
 // System.out.print(Date2);
  ResultSet rs1=runStatement(admConnection,sql1);
  try {
    while (rs1.next()) {  
      System.out.println(rs1.getInt("employeeId") +  "\t" +
                           rs1.getString("entryTime") + "\t" + 
                           rs1.getString("attendancetype"));
    }
   }
   catch (SQLException e) {
     // TODO Auto-generated catch block
    // System.out.println("No such table is present");    ///here.......
     e.printStackTrace();
   }
   sc.close();
}
//.....................................End of allEmployeeAttendance method............................................


//..............................................AttendanceDetails method...................................................

public static void attendanceDetails(Connection admConnection){
  System.out.println();
  Scanner sc=new Scanner(System.in);
  System.out.println("a..All Employee Attendance");
  System.out.println();
  System.out.println("Enter your choice");
  char choice=sc.next().charAt(0);
  switch(choice){
    case 'a':
     allEmployeeAttendance(admConnection);
     break;
    default:
    System.out.println("Please enter valid choice");
  }
  sc.close();
}

  public static void admin(Connection c1){
    System.out.println();
    Scanner sc=new Scanner(System.in);
    System.out.println("Enter username");
    String username=sc.next();
    System.out.println();
    System.out.println("Enter password");
    String password=sc.next();
    String sql="select * from admin";
    ResultSet rs1=runStatement(c1,sql);
    try {
      while (rs1.next()) {  
        String username1=rs1.getString("username");
        String password1=rs1.getString("password");
      System.out.println(username1);
      System.out.println(password1);
      System.out.println();
      if(username1.equals(username)&&password1.equals(password)){
            System.out.println("welcome"+" "+username);
            System.out.println("you are entered in adminstration mode");
            System.out.println();
            System.out.println("Press any key to go further");
            sc.next();
            System.out.println();
            System.out.println("1.Add Employee");
            System.out.println("2.Delete Employee");
            System.out.println("3.Employee Details");
            System.out.println("4.Update Employee");
            System.out.println("5.Attendance Details");
            System.out.println();
            System.out.println("Please enter your choice");
            int choice=sc.nextInt();
           // sc.close();
            switch(choice){
              case 1:
               Add_employee(c1);
               break;
              case 2:
               deleteEmployee(c1);
               break;
              case 3:
               employeeDetails(c1);
               break;
              case 4:
               updateEmployeeDetails(c1);
               break;
              case 5:
               attendanceDetails(c1);
               break;
              default:
              System.out.println("Please enter valid choice");
            }

       }
       else{
        System.out.println("Incorrect username and password");
       }
      sc.close();
 }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
}

    public static void main(String args[]){

        Connection c1=create_database("employee_details");
        String sql = "CREATE TABLE IF NOT EXISTS admin (\n"  
                    + " username text,\n"  
                    + " password text NOT NULL\n"    
                    + ");"; 

        String sql3 = "CREATE TABLE IF NOT EXISTS employeeDetailsTable (\n" 
                    + " employeeId int primary key not null," 
                    + " name text,\n"  
                    + " address text NOT NULL,\n"
                    +  "contact text,\n"
                    + "salary  int,\n"
                    + "password text \n"    
                    + ");";         
       String sql2="Insert into admin(username,password) values('vijay@gmail.com','00000')";

        // String sql3="schema admin";
        runStatement(c1,sql);
        runStatement(c1,sql3);
       runStatement(c1,sql2);
    
        Scanner sc=new Scanner(System.in);
         System.out.println("EMPLOYEE MANAGEMENT SYSTEM");
         System.out.println();
    
         System.out.println("In which mode you want to enter");
         System.out.println("1. Adminsistrative Mode");
         System.out.println("2. Employee Mode");
         System.out.println();
         System.out.println("Enter your choice");
         
         int choice=sc.nextInt();
         //sc.close();
         switch(choice){
          case 1:
              admin(c1);
              break;
          case 2:
                System.out.println();
                System.out.println("Enter username");
                String username=sc.next();
                System.out.println();
                System.out.println("Enter password");
                String password=sc.next();
                Employee_login(c1, username, password);
                break;
         }
         sc.close();
        }
        
}

