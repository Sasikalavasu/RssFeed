package app.sella.it.rssfeed.Model;

import com.google.gson.annotations.SerializedName;

public class FeedTypeView {
    @SerializedName("feedtypename")
    private String feedtypename;
    @SerializedName("feedtypedesc")
    private String feedtypedesc;
    @SerializedName("_id")
    private IDView id;
    @SerializedName("retentiontime")
    private Integer retentiontime;

    public String getFeedtypename() {
        return feedtypename;
    }

    public void setFeedtypename(String feedtypename) {
        this.feedtypename = feedtypename;
    }

    public String getFeedtypedesc() {
        return feedtypedesc;
    }

    public void setFeedtypedesc(String feedtypedesc) {
        this.feedtypedesc = feedtypedesc;
    }

    public IDView getId() {
        return id;
    }

    public void setId(IDView id) {
        this.id = id;
    }

    public Integer getRetentiontime() {
        return retentiontime;
    }

    public void setRetentiontime(Integer retentiontime) {
        this.retentiontime = retentiontime;
    }
}
