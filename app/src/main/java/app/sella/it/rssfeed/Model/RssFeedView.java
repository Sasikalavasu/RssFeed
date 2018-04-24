package app.sella.it.rssfeed.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RssFeedView {

    @SerializedName("image")
    private String image;
    @SerializedName("audience")
    private List<String> audience = null;
    @SerializedName("idfeed")
    private String idfeed;
    @SerializedName("link")
    private String link;
    @SerializedName("description")
    private String description;
    @SerializedName("source")
    private String source;
    @SerializedName("startdate")
    private String startdate;
    @SerializedName("likecount")
    private String likecount;
    @SerializedName("enddate")
    private String enddate;
    @SerializedName("iscurated")
    private Boolean iscurated;
    @SerializedName("unlikecount")
    private String unlikecount;
    @SerializedName("_id")
    private IDView id;
    @SerializedName("publishedDate")
    private String publishedDate;
    @SerializedName("categories")
    private String categories;
    @SerializedName("headline")
    private String headline;
    @SerializedName("retentiontime")
    private String retentiontime;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getAudience() {
        return audience;
    }

    public void setAudience(List<String> audience) {
        this.audience = audience;
    }

    public String getIdfeed() {
        return idfeed;
    }

    public void setIdfeed(String idfeed) {
        this.idfeed = idfeed;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getLikecount() {
        return likecount;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Boolean getIscurated() {
        return iscurated;
    }

    public void setIscurated(Boolean iscurated) {
        this.iscurated = iscurated;
    }

    public String getUnlikecount() {
        return unlikecount;
    }

    public void setUnlikecount(String unlikecount) {
        this.unlikecount = unlikecount;
    }

    public IDView getId() {
        return id;
    }

    public void setId(IDView id) {
        this.id = id;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getRetentiontime() {
        return retentiontime;
    }

    public void setRetentiontime(String retentiontime) {
        this.retentiontime = retentiontime;
    }

}
