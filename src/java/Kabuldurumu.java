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
public class Kabuldurumu{
    int id;
    String name;
    String diller;
    String telno;
    String egitim;
    String dogumtarihi;
    String cinsiyet;
    String askerlik;
    String email;
    String departman;
    String referans;
    String hakkinda;
    ArrayList formList ;
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
     public String getDiller() {
        return diller;
    }
    public void setDiller(String diller) {
        this.diller = diller;
    }
    public String getTelno() {
        return telno;
    }
    public void setTelno(String telno) {
        this.telno = telno;
    }
    public String getEgitim() {
        return egitim;
    }
    public void setEgitim(String egitim) {
        this.egitim = egitim;
    }
    public String getDogumtarihi() {
        return dogumtarihi;
    }
    public void setDogumtarihi(String dogumtarihi) {
        this.dogumtarihi = dogumtarihi;
    }
    public String getCinsiyet() {
        return cinsiyet;
    }
    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }
    public String getAskerlik() {
        return askerlik;
    }

    public void setAskerlik(String askerlik) {
        this.askerlik = askerlik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }

    public String getReferans() {
        return referans;
    }

    public void setReferans(String referans) {
        this.referans = referans;
    }

    public String getHakkinda() {
        return hakkinda;
    }

    public void setHakkinda(String hakkinda) {
        this.hakkinda = hakkinda;
    }
    // Used to establish connection
    public Connection getConnection(){ // Burası Veritabanı bağlantı fonksiyonudur. Sonrasında getConnection() çağırarak kullanabilirz.
        try{
            Class.forName("com.mysql.jdbc.Driver");   
            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/User","root","");
        }catch(Exception e){
            System.out.println(e);
        }
        return connection;
    }
    // Used to fetch all records
    public ArrayList formList(){
        try{
            formList = new ArrayList();
            connection = getConnection();
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from is_kabul");  // Burda seleckt * from ile bütün verileri tabloda göstermiş oluyoruz.
            while(rs.next()){
                Kabuldurumu kabul = new Kabuldurumu();
                kabul.setId(rs.getInt("id"));
                kabul.setName(rs.getString("name"));
                kabul.setDiller(rs.getString("diller"));
                kabul.setTelno(rs.getString("telno"));
                kabul.setEgitim(rs.getString("egitim"));
                kabul.setDogumtarihi(rs.getString("dogumtarihi"));
                kabul.setCinsiyet(rs.getString("cinsiyet"));
                kabul.setAskerlik(rs.getString("askerlik"));
                kabul.setEmail(rs.getString("email"));
                kabul.setDepartman(rs.getString("departman"));
                kabul.setReferans(rs.getString("referans"));
                kabul.setHakkinda(rs.getString("hakkinda"));
                formList.add(kabul);
            }
            connection.close();        
        }catch(Exception e){
            System.out.println(e);
        }
        return formList;
    }

    public void delete(int id){
        try{
            connection = getConnection();  
            PreparedStatement stmt = connection.prepareStatement("delete from is_kabul where id = "+id);  
            stmt.executeUpdate();  
        }catch(Exception e){
            System.out.println(e);
        }
    } 
}