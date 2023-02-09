package Object;

public class Personal_Information {
    private int Id;
    private int Uid_Login;
    private String Img_Avatar;
    private String FullName;
    private String Date_Of_Birth;
    private String Telephone_Number;
    private String Address;

    public Personal_Information(int id, int uid_Login, String img_Avatar, String fullName, String date_Of_Birth, String telephone_Number, String address) {
        Id = id;
        Uid_Login = uid_Login;
        Img_Avatar = img_Avatar;
        FullName = fullName;
        Date_Of_Birth = date_Of_Birth;
        Telephone_Number = telephone_Number;
        Address = address;
    }

    public Personal_Information() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUid_Login() {
        return Uid_Login;
    }

    public void setUid_Login(int uid_Login) {
        Uid_Login = uid_Login;
    }

    public String getImg_Avatar() {
        return Img_Avatar;
    }

    public void setImg_Avatar(String img_Avatar) {
        Img_Avatar = img_Avatar;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(String date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
    }

    public String getTelephone_Number() {
        return Telephone_Number;
    }

    public void setTelephone_Number(String telephone_Number) {
        Telephone_Number = telephone_Number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
