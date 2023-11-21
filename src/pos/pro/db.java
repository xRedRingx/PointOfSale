package pos.pro;


import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException;
/**
 *
 * @author Liamani
 */
public class db {
    
    public static Connection mycon(){
        
        
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pos","root","");
            return con;
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
            return null;
            
        }
    }
    
    
    
}
/**
 *
 * @author Liamani
 */