package Object;

public class Notification_object {
    private int Id;
    private int Uid;
    private String Message_Notification;
    private String View;
    private String Send;
    private String Title_Notification;
    private String Time;

    public Notification_object(int uid, String message_Notification, String view, String title_Notification,String send,String Time) {
        Uid = uid;
        Message_Notification = message_Notification;
        View = view;
        Title_Notification = title_Notification;
        Send=send;
        Time=Time;
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

    public String getMessage_Notification() {
        return Message_Notification;
    }

    public void setMessage_Notification(String message_Notification) {
        Message_Notification = message_Notification;
    }

    public String getView() {
        return View;
    }

    public void setView(String view) {
        View = view;
    }

    public String getTitle_Notification() {
        return Title_Notification;
    }

    public void setTitle_Notification(String title_Notification) {
        Title_Notification = title_Notification;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSend() {
        return Send;
    }

    public void setSend(String send) {
        Send = send;
    }
}
