package app.sella.it.rssfeed;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.sella.it.rssfeed.Model.FeedTypeView;
import app.sella.it.rssfeed.Model.RssFeedView;
import app.sella.it.rssfeed.RestService.RestInterface;
import app.sella.it.rssfeed.RestService.RetrofitClient;
import app.sella.it.rssfeed.Util.FileUtil;
import app.sella.it.rssfeed.Util.ValidationUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsyncFileTask extends AsyncTask<List<FeedTypeView>, Void, Void> {
    private static final String TAG = AsyncFileTask.class.getName();
    List<RssFeedView> listOfFeedItems = new ArrayList<>();
    private static final String FEEDTYPES_FILENAME="FEEDTYPES.json";
    private final Context mContext;

    public AsyncFileTask(final Context context) {
        mContext = context;
    }

    /**
     * `doInBackground` is run on a separate, background thread
     * (not on the main/ui thread). DO NOT try to update the ui
     * from here.
     */
    @Override
    protected Void doInBackground(List<FeedTypeView>... feedTypeArr) {
        List<FeedTypeView> inputFeedTypeArr = feedTypeArr[0];
        Log.d(TAG,":::::::::: List of feed types ::::::"+inputFeedTypeArr);
        if (ValidationUtil.isNotEmpty(inputFeedTypeArr)) {
            List<FeedTypeView> feedTypeList = FileUtil.getInstance().readFeedTypeFromFile(FEEDTYPES_FILENAME,mContext);
            if (ValidationUtil.isNotEmpty(feedTypeList)) {
                for (FeedTypeView fd : feedTypeList) {
                    mContext.deleteFile(fd.getFeedtypename());
                }
            }
            FileUtil.getInstance().writeToFile(inputFeedTypeArr, FEEDTYPES_FILENAME, mContext);
            for (FeedTypeView fdv : inputFeedTypeArr) {
                Log.d(TAG,":::::::::: Inside feed type to write in file :::::::"+fdv.getFeedtypename());
                writeDataToFile(fdv.getFeedtypename(),fdv.getFeedtypename()+".json");
            }
        }
        return null;
    }

    public void writeDataToFile(final String feedType,final String fileName){
        Map<String,String> feedTypeMap = new HashMap<>();
        feedTypeMap.put("feedtypename", feedType);
        RestInterface feedInterface = RetrofitClient.getClient(mContext.getResources().getString(R.string.base_url)).create(RestInterface.class);
        Call<List<RssFeedView>> call = feedInterface.getAllFeedForGivenFeedType(feedTypeMap);

        call.enqueue(new Callback<List<RssFeedView>>() {
            @Override
            public void onResponse(Call<List<RssFeedView>> call, Response<List<RssFeedView>> response) {
                listOfFeedItems = response.body();
                if (ValidationUtil.isNotEmpty(listOfFeedItems) && listOfFeedItems.size()>0) {
                    Log.d(TAG,"::::::::::: No.of.items of feeds received to write in file :::::"+ listOfFeedItems.size());
                    FileUtil.getInstance().writeToFile(listOfFeedItems, fileName, mContext);
                    Log.d(TAG,"::::::::::: Write feed to file success :::::"+ listOfFeedItems.size());
                }
            }
            @Override
            public void onFailure(Call<List<RssFeedView>> call, Throwable t) {
                Log.d(TAG,":::::::::::: Failure on receiving data to write in file :::::::"+t.toString());
            }
        });
    }
}
