package Object;

public class Money_user {
    private int  Id;
    private int Uid;
    private int Money;

    public Money_user(  int Uid, int money) {
        Uid = Uid;
        Money = money;
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


    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }
}
