package Object;

public class HistorySearch {
    private int Id;
    private int Uid;
    private String TextSearch;

    public HistorySearch(int id, int uid, String textSearch) {
        Id = id;
        Uid = uid;
        TextSearch = textSearch;
    }

    public HistorySearch() {
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

    public String getTextSearch() {
        return TextSearch;
    }

    public void setTextSearch(String textSearch) {
        TextSearch = textSearch;
    }
}
