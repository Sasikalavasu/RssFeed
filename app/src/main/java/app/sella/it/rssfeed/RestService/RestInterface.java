package app.sella.it.rssfeed.RestService;


import java.util.List;
import java.util.Map;

import app.sella.it.rssfeed.Model.FeedTypeView;
import app.sella.it.rssfeed.Model.RssFeedView;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestInterface {
    @POST("/tomcat/SellaCast/rest/sellacastrestservice/fetchAliveFeeds")
    Call<List<RssFeedView>> getAllFeedForGivenFeedType(@Body Map<String, String> body);

    @GET("/tomcat/SellaCast/rest/sellacastadminservice/getallfeedtypes")
    Call<List<FeedTypeView>> getAllFeedTypes();

}
