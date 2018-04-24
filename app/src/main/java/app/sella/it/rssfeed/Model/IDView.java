package app.sella.it.rssfeed.Model;

import com.google.gson.annotations.SerializedName;

public class IDView {
    @SerializedName("$oid")
    private String $oid;

    public String get$oid() {
        return $oid;
    }

    public void set$oid(String $oid) {
        this.$oid = $oid;
    }
}
