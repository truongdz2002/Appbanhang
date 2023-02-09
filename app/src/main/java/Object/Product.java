package Object;

import java.io.Serializable;
import java.util.Comparator;

public class Product implements Serializable {
    private int Id;
    private String Name_Product;
    private int Price_Product;
    private int Discount;
    private String Img_Product;

    public Product(int id, String name_Product, int price_Product, int discount, String img_Product) {
        Id = id;
        Name_Product = name_Product;
        Price_Product = price_Product;
        Discount = discount;
        Img_Product = img_Product;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName_Product() {
        return Name_Product;
    }

    public void setName_Product(String name_Product) {
        Name_Product = name_Product;
    }

    public int getPrice_Product() {
        return Price_Product;
    }

    public void setPrice_Product(int price_Product) {
        Price_Product = price_Product;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }
    public String getImg_Product() {
        return Img_Product;
    }
    public void setImg_Product(String img_Product) {
        Img_Product = img_Product;
    }

    public Product() {
    }
    public static Comparator<Product> Sort_Product_Price_Recrease=new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            return p2.getPrice_Product() - p1.getPrice_Product();
        }
    };
    public static Comparator<Product> Sort_Product_Price_Increase=new Comparator<Product>() {
        @Override
        public int compare(Product product, Product t1) {
            return product.getPrice_Product()-t1.getPrice_Product();
        }
    };

}
