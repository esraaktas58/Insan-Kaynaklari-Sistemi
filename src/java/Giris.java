import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
@ManagedBean
@RequestScoped
public class Giris{
    static public String userName;
    private String password;
    private String dbuserName;
  
    private String dbpassword;
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    String SQL;
     ArrayList formList ;
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getDbuserName() {
        return dbuserName;
    }
 
    public void setDbuserName(String dbuserName) {
        this.dbuserName = dbuserName;
    }
 
    public String getDbpassword() {
        return dbpassword;
    }
 
    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }

    public void dbData(String userName)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/User","root","");
            statement = connection.createStatement();
            SQL = "Select * from users where email like ('" + userName +"')"; //Select * from users where email like enes@enes.com
            resultSet = statement.executeQuery(SQL);    // id:4,name=enes,email=enes@enes.com,password:123;
            resultSet.next();
            dbuserName = resultSet.getString(3); //Tablodaki 3. satırda email olduğu için 3.yü getir diyoruz. Ve dbuserNameyi buna eşitliyoruz. //
            dbpassword = resultSet.getString(4); 
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Exception Occured in the process :" + ex);
        }
        
    }
    
    public String checkValidUser()
    {
        
        dbData(userName);
          // dbUserName = enes@enes.com;
          // userName   = enes@enes.com;
        if(userName.equalsIgnoreCase(dbuserName))
        {
            if(password.equals(dbpassword))
                
                return "anasayfa.xhtml?faces-redirect=true";
            else
            {
                return "Email veya Şifre Hatal";
            }
        }
        else
        {
            return "failure";
        }
    }
}