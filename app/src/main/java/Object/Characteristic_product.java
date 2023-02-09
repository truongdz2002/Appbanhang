package Object;

public class Characteristic_product {
    private int Code_product;
    private int Amount;
    private String Color_product;
    private String Img_product;

    public Characteristic_product(int code_product, int amount, String color_product, String img_product) {
        Code_product = code_product;
        Amount = amount;
        Color_product = color_product;
        Img_product = img_product;
    }

    public int getCode_product() {
        return Code_product;
    }

    public void setCode_product(int code_product) {
        Code_product = code_product;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getColor_product() {
        return Color_product;
    }

    public void setColor_product(String color_product) {
        Color_product = color_product;
    }

    public String getImg_product() {
        return Img_product;
    }

    public void setImg_product(String img_product) {
        Img_product = img_product;
    }
}
