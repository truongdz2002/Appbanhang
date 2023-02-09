package Object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Imageadvertisement implements Serializable {
    private int Id;
    @SerializedName("Advertisement")
    private String LinkImageAdvertisement;

    public Imageadvertisement(int id, String linkImageAdvertisement) {
        Id = id;
        LinkImageAdvertisement = linkImageAdvertisement;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLinkImageAdvertisement() {
        return LinkImageAdvertisement;
    }

    public void setLinkImageAdvertisement(String linkImageAdvertisement) {
        LinkImageAdvertisement = linkImageAdvertisement;
    }
}
