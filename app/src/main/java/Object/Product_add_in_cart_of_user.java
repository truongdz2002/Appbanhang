package Object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product_add_in_cart_of_user implements Serializable {

        private int Id;
        @SerializedName("Uid")
        private int Uid;
        @SerializedName("Img_product")
        private String Img_product_add_in_cart;
        @SerializedName("Name_product")
        private String Name_product_add_in_cart;
        @SerializedName("Price_Product")
        private int Price_product_add_in_cart;

        public Product_add_in_cart_of_user ( int Uid,String img_product_add_in_cart, String name_product_add_in_cart, int price_product_add_in_cart) {
            Uid=Uid;
            Img_product_add_in_cart = img_product_add_in_cart;
            Name_product_add_in_cart = name_product_add_in_cart;
            Price_product_add_in_cart = price_product_add_in_cart;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }
        public String getImg_product_add_in_cart() {
            return Img_product_add_in_cart;
        }

        public void setImg_product_add_in_cart(String img_product_add_in_cart) {
            Img_product_add_in_cart = img_product_add_in_cart;
        }

        public String getName_product_add_in_cart() {
            return Name_product_add_in_cart;
        }

        public void setName_product_add_in_cart(String name_product_add_in_cart) {
            Name_product_add_in_cart = name_product_add_in_cart;
        }

        public int getPrice_product_add_in_cart() {
            return Price_product_add_in_cart;
        }

        public void setPrice_product_add_in_cart(int price_product_add_in_cart) {
            Price_product_add_in_cart = price_product_add_in_cart;
        }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }
}


