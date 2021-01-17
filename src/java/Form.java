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
public class Form{
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
     Giris giris=new Giris();
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
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");   
                connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/User","root",""); //localhost Mysql 3306 User tablosu root şifre boş
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
            ResultSet rs=stmt.executeQuery("select * from form"); 
            while(rs.next()){
                Form form = new Form();
                form.setId(rs.getInt("id"));
                form.setName(rs.getString("name"));
                form.setDiller(rs.getString("diller"));
                form.setTelno(rs.getString("telno"));
                form.setEgitim(rs.getString("egitim"));
                form.setDogumtarihi(rs.getString("dogumtarihi"));
                form.setCinsiyet(rs.getString("cinsiyet"));
                form.setAskerlik(rs.getString("askerlik"));
                form.setEmail(rs.getString("email"));
                form.setDepartman(rs.getString("departman"));
                form.setReferans(rs.getString("referans"));
                form.setHakkinda(rs.getString("hakkinda"));
                formList.add(form);          
            }
            connection.close();        
        }catch(Exception e){
            System.out.println(e);
        }
        return formList;
    }
  
    // Used to save user record
    public String save(){  // Veritabanına Kayıt işlemini burada yapıyoruz. 
        String Email= giris.getUserName();
        if(Email.equalsIgnoreCase(email))
        {
           int result = 0; // Gerekli sayfa yönlendirmesi yapabilmek çin tanımlıyoruz.
        try{
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into form(name,diller,telno,egitim,dogumtarihi,cinsiyet,askerlik,email,departman,referans,hakkinda) values(?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, diller);
            stmt.setString(3, telno);
            stmt.setString(4, egitim);
            stmt.setString(5, dogumtarihi);
            stmt.setString(6, cinsiyet);
            stmt.setString(7, askerlik);
            stmt.setString(8, email);
            stmt.setString(9, departman);
            stmt.setString(10, referans);
            stmt.setString(11, hakkinda);
            result = stmt.executeUpdate();
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }
        if(result !=0)
            return "anasayfa.xhtml?faces-redirect=true";
        else return "form.xhtml?faces-redirect=true";
        }
        else
        {
            return "Lütfen giriş yaparken kullandığınız emaili kullanınız.";
        }
        
    }
     public String edit(int id){  // Güncelle Butonuna bastığında input değerlerini doldurmamıza yarıyor.
        User user = null;
        System.out.println(id);
        try{
            connection = getConnection();
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from form where id = "+(id));
            while(rs.next()){
                Form form = new Form();
                form.setId(rs.getInt("id"));
                form.setName(rs.getString("name"));
                form.setDiller(rs.getString("diller"));
                form.setTelno(rs.getString("telno"));
                form.setEgitim(rs.getString("egitim"));
                form.setDogumtarihi(rs.getString("dogumtarihi"));
                form.setCinsiyet(rs.getString("cinsiyet"));
                form.setAskerlik(rs.getString("askerlik"));
                form.setEmail(rs.getString("email"));
                form.setDepartman(rs.getString("departman"));
                form.setReferans(rs.getString("referans"));
                form.setHakkinda(rs.getString("hakkinda"));
                sessionMap.put("editForm", form); // editform'u formedit sayfasında inputların value değerini değiştirebilmek için kullanıyoruz.
                formList.add(form);          
            }        
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }       
        return "/formedit.xhtml?faces-redirect=true";
    }
    // Used to update user record
    public String update(Form u){ // Güncelle Butonuna bastığında Güncelleme işlemini yapıyor.
        //int result = 0;
        try{
            connection = getConnection();  
            PreparedStatement stmt=connection.prepareStatement("update form set name=?,diller=?,telno=?,egitim=?,dogumtarihi=?,cinsiyet=?,askerlik=?,email=?,departman=?,referans=?,hakkinda=? where id=?");  
            stmt.setString(1,u.getName());  
            stmt.setString(2,u.getDiller());  
            stmt.setString(3,u.getTelno());  
            stmt.setString(4,u.getEgitim());  
            stmt.setString(5,u.getDogumtarihi());
            stmt.setString(6,u.getCinsiyet()); 
            stmt.setString(7,u.getAskerlik()); 
            stmt.setString(8,u.getEmail()); 
            stmt.setString(9,u.getDepartman()); 
            stmt.setString(10,u.getReferans()); 
            stmt.setString(11,u.getHakkinda()); 
            stmt.setInt(12,u.getId());  
            stmt.executeUpdate();
            connection.close();
        }catch(Exception e){
            System.out.println();
        }
        return "/anasayfa.xhtml?faces-redirect=true";      
    }
     
     public ArrayList formUpdate(){
        try{
            String Email= giris.getUserName(); // Nesne Tanımlaması yapıyoruz. Email=enes@enes.com
            formList = new ArrayList(); 
            connection = getConnection();
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("Select * from form where email like ('"+ Email +"')"); //Form tablosundaki emaili=enes@enes.com olanları bana getir.
            while(rs.next()){ //id=2,name=enes,email=kjslkj
                Form form = new Form();
                form.setId(rs.getInt("id"));
                form.setName(rs.getString("name"));
                form.setDiller(rs.getString("diller"));
                form.setTelno(rs.getString("telno"));
                form.setEgitim(rs.getString("egitim"));
                form.setDogumtarihi(rs.getString("dogumtarihi"));
                form.setCinsiyet(rs.getString("cinsiyet"));
                form.setAskerlik(rs.getString("askerlik"));
                form.setEmail(rs.getString("email"));
                form.setDepartman(rs.getString("departman"));
                form.setReferans(rs.getString("referans"));
                form.setHakkinda(rs.getString("hakkinda"));
                formList.add(form);
            }
            connection.close();        
        }catch(Exception e){
            System.out.println(e);
        }
        return formList;
    }
    public String saveKabul(int id,String name,String diller,String telno,String egitim,String dogumtarihi,String cinsiyet,String askerlik,String email,String departman,String referans,String hakkinda){
        int result = 0;
        try{
            connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into is_kabul(name,diller,telno,egitim,dogumtarihi,cinsiyet,askerlik,email,departman,referans,hakkinda) values(?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, diller);
            stmt.setString(3, telno);
            stmt.setString(4, egitim);
            stmt.setString(5, dogumtarihi);
            stmt.setString(6, cinsiyet);
            stmt.setString(7, askerlik);
            stmt.setString(8, email);
            stmt.setString(9, departman);
            stmt.setString(10, referans);
            stmt.setString(11, hakkinda);
            result = stmt.executeUpdate();
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }
        try{
            connection = getConnection();   // Burda Silme işlemini gerçekleştiriyoruz .Bu sayede kabul edilen kullanıcı tekrar basvurudegerlendir sayfasında gözükmüyor.
            PreparedStatement stmt2 = connection.prepareStatement("delete from form where id = "+id);  
            stmt2.executeUpdate();  
        }catch(Exception e){
            System.out.println(e);
        }
        
        if(result !=0)
            return "basvurudegerlendir.xhtml?faces-redirect=true"; 
        else return "basvurudegerlendir.xhtml?faces-redirect=true";
    }
    public void delete(int id){
        try{
            connection = getConnection();  
            PreparedStatement stmt = connection.prepareStatement("delete from form where id = "+id);  
            stmt.executeUpdate();  
        }catch(Exception e){
            System.out.println(e);
        }
    }
  
}