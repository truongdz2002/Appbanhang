package Object;


public class GetApiSuccess {
    private int  Ok;
    private String  Message;

    public GetApiSuccess(int ok, String message) {
        Ok = ok;
        Message = message;
    }

    public int getOk() {
        return Ok;
    }

    public void setOk(int ok) {
        Ok = ok;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
