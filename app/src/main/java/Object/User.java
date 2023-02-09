package Object;

public class User {
    private int Id;
    private String UserName;
    private String Password;

    public User(int id, String userName, String password) {
        Id = id;
        UserName = userName;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
