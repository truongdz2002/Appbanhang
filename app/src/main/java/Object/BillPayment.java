package Object;

import java.io.Serializable;
import java.util.Comparator;

public class BillPayment implements Serializable {
    private int Id;
    private String UserName;
    private String AddressUser;
    private String TelephoneNumber;
    private String PaymentMethod;
    private String OrderDate;
    private String ProductName;
    private int Uid;
    private int TotalMoney;
    private String Img_Product;

    public BillPayment() {
    }

    public BillPayment(int id, String userName, String addressUser, String telephoneNumber, String paymentMethod, String orderDate, String productName, int uid, int totalMoney, String Img_Product) {
        Id = id;
        UserName = userName;
        AddressUser = addressUser;
        TelephoneNumber = telephoneNumber;
        PaymentMethod = paymentMethod;
        OrderDate = orderDate;
        ProductName = productName;
        Uid = uid;
        TotalMoney = totalMoney;
        Img_Product=Img_Product;
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

    public String getAddressUser() {
        return AddressUser;
    }

    public void setAddressUser(String addressUser) {
        AddressUser = addressUser;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        TelephoneNumber = telephoneNumber;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public int getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        TotalMoney = totalMoney;
    }

    public String getImg_Product() {
        return Img_Product;
    }

    public void setImg_Product(String img_Product) {
        Img_Product = img_Product;
    }
    public static Comparator<BillPayment> billPaymentSortCodeBill=new Comparator<BillPayment>() {
        @Override
        public int compare(BillPayment billPayment_1, BillPayment billPayment_2) {
            return billPayment_2.getId()-billPayment_1.getId();
        }
    };
}
