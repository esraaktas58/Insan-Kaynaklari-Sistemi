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
public class Register{
    int id;
    String name;
    String email;
    String password;
    ArrayList usersList ;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    Connection connection;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
   
    // Used to establish connection
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");   
            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/User","root","");
        }catch(Exception e){
            System.out.println(e);
        }
        return connection;
    }
    // Used to fetch all records
    public ArrayList usersList(){
        try{
            usersList = new ArrayList(); // Burada List tanımlaması yapıyoruz.
            connection = getConnection(); // Burada bağlantı fonksiyonunu çağırıp connection değişenine atıyoruz.
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from yonetici");  // Burdaki sql kodunu çalıştırıyoruz.
            while(rs.next()){   // Eğer kod başarılı bir şekilde çalışırsa while döngüsüne giriyor.
                Register user = new Register(); // Burada nesne tanımlaması yapıyoruz. 
                user.setId(rs.getInt("id")); // Veritabanından dönen id değerlerini bu sayede user.SetId ile user nesnesine atamış oluyoruz.
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                usersList.add(user); // Son olarak burada usersList listesine user nesnesini eklemiş oluyoruz.
            }
            connection.close();        
        }catch(Exception e){
            System.out.println(e);
        }
        return usersList; // Burada fonksiyon başarılı bir şekilde çalışırsa usersList listesini döndürüyoruz.
    }
    // Used to save user record
    public String save(){
        int result = 0;
        try{
            connection = getConnection(); 
            PreparedStatement stmt = connection.prepareStatement("insert into yonetici(name,email,password) values(?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password); 
            result = stmt.executeUpdate();
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }
        if(result !=0)
            return "basvurudegerlendir.xhtml?faces-redirect=true";
        else return "register.xhtml?faces-redirect=true";
    }
    
    // Used to set user gender
   
}