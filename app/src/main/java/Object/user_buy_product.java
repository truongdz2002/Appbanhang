package Object;

public class user_buy_product {
    int Id;
    private int Uid;
    private String User_Name;
    private String Address;
    private String TelephoneNumber;
    private String Product_name_buy;
    private int Money_buy_product;
    private String delivery;

    public user_buy_product(int id, int uid, String user_Name, String address, String telephoneNumber, String product_name_buy, int money_buy_product, String delivery) {
        Id = id;
        Uid = uid;
        User_Name = user_Name;
        Address = address;
        TelephoneNumber = telephoneNumber;
        Product_name_buy = product_name_buy;
        Money_buy_product = money_buy_product;
        this.delivery = delivery;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        TelephoneNumber = telephoneNumber;
    }

    public String getProduct_name_buy() {
        return Product_name_buy;
    }

    public void setProduct_name_buy(String product_name_buy) {
        Product_name_buy = product_name_buy;
    }

    public int getMoney_buy_product() {
        return Money_buy_product;
    }

    public void setMoney_buy_product(int money_buy_product) {
        Money_buy_product = money_buy_product;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
