package app.sella.it.rssfeed;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.sella.it.rssfeed.Listener.OnSwipeTouchListener;
import app.sella.it.rssfeed.Model.FeedTypeView;
import app.sella.it.rssfeed.Model.RssFeedView;
import app.sella.it.rssfeed.RestService.RetrofitClient;
import app.sella.it.rssfeed.RestService.RestInterface;
import app.sella.it.rssfeed.Util.FileUtil;
import app.sella.it.rssfeed.Util.InternetConnectionUtil;
import app.sella.it.rssfeed.Util.ValidationUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowFeedActivity extends AppCompatActivity {
    TextView titleTV;
    ImageView imgvw;
    TextView descTV;
    LinearLayout parentLayout;

    private static final String TAG = ShowFeedActivity.class.getName();
    List<RssFeedView> lst = new ArrayList<>();
    List<FeedTypeView> feedTypesList = new ArrayList<>();
    int i = 0;
    private static final String BUSINESS = "BUSINESS";
    private static final String TECHNICAL = "TECHNICAL";
    private static final String FEEDTYPES_FILENAME="FEEDTYPES.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_feed_activity);
        titleTV =(TextView)findViewById(R.id.txtVwTitle);
        imgvw = (ImageView) findViewById(R.id.imgView);
        descTV = (TextView)findViewById(R.id.txtVwDesc);
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);

        if (isTablet(this)) {
            Log.d(TAG,"Device detected - Tablet");
            titleTV.setTextAppearance(this,R.style.TabletTitle);
            descTV.setTextAppearance(this,R.style.TabletDesc);
        } else {
            Log.d(TAG,"Device detected - SmartPhone");
            titleTV.setTextAppearance(this,R.style.smartPhoneTitle);
            descTV.setTextAppearance(this,R.style.smartPhoneDesc);
        }

        // Check the whether mobile data/wifi connected
        final boolean isConnOnline = InternetConnectionUtil.checkConnection(this);
        Log.d(TAG,"isConnOnline"+isConnOnline);
        getAllFeedTypes(isConnOnline);

        parentLayout.setOnTouchListener(new OnSwipeTouchListener(ShowFeedActivity.this) {
            public void onSwipeTop() {
                Log.d(TAG,"::::::::: Detected swipe up ::::::::::"+i);
                if ((i+1)==lst.size()) {
                    i = 0;
                } else {
                    i = i+1;
                }
                Log.d(TAG,":::::: I value ::::::"+i);
                setDataToView(i);
            }
            public void onSwipeRight() {
            }
            public void onSwipeLeft() {
            }
            public void onSwipeBottom() {
                Log.d(TAG,":::::::: Detected swipe down ::::::::"+i);
                if ((i-1)<0) {
                    i = lst.size()-1;
                } else {
                    i=i-1;
                }
                Log.d(TAG,"::::::::::::: I value ::::::::::"+i);
                setDataToView(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.feed_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (feedTypesList!=null && !feedTypesList.isEmpty()) {
            menu.clear();
            for (int i=0;i<feedTypesList.size();i++) {
                FeedTypeView feedType = feedTypesList.get(i);
                menu.add(0, i, i, feedType.getFeedtypename());
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        i=0;
        String feedType = item.getTitle().toString();
        readDataFromFile(feedType);
        return super.onOptionsItemSelected(item);
    }

    // Get the techinal feeds by default for the first time when user opens the app
    private void getFeedForGivenFeedType(final String feedType,final boolean isConnOnline) {
        lst.clear();
        if (isConnOnline) {
            Log.d(TAG, "Inside logic to get feed items");
            Map<String, String> feedTypeMap = new HashMap<>();
            feedTypeMap.put("feedtypename", feedType);
            RestInterface feedInterface = RetrofitClient.getClient(getResources().getString(R.string.base_url)).create(RestInterface.class);
            Call<List<RssFeedView>> call = feedInterface.getAllFeedForGivenFeedType(feedTypeMap);

            call.enqueue(new Callback<List<RssFeedView>>() {
                @Override
                public void onResponse(Call<List<RssFeedView>> call, Response<List<RssFeedView>> response) {
                    lst = response.body();
                    if (ValidationUtil.isNotEmpty(lst)) {
                        Log.d(TAG, "::::::: No.of.items received :::::" + lst.size());
                        setDataToView(i);
                    } else {
                        readDataFromFile(feedType);
                    }
                }

                @Override
                public void onFailure(Call<List<RssFeedView>> call, Throwable t) {
                    Log.d(TAG, ":::::: Failure on receiving feed items :::::" + t.toString());
                }
            });
        } else {
            // Read data from file stored internally
            readDataFromFile(feedType);
        }
    }

    private void getAllFeedTypes(final boolean isConnOnline) {
        Log.d(TAG,":::::: Inside rest call to fetch feed types :::::");
        if (isConnOnline) {
            RestInterface feedInterface = RetrofitClient.getClient(getResources().getString(R.string.base_url)).create(RestInterface.class);
            Call<List<FeedTypeView>> call = feedInterface.getAllFeedTypes();

            call.enqueue(new Callback<List<FeedTypeView>>() {
                @Override
                public void onResponse(Call<List<FeedTypeView>> call, Response<List<FeedTypeView>> response) {
                    Log.d(TAG, ":::::: No.of.feed types before calling service :::::::" + feedTypesList.size());
                    feedTypesList = response.body();
                    if (ValidationUtil.isNotEmpty(feedTypesList)) {
                        Log.d(TAG, ":::::: No.of.feed types after calling service :::::::" + feedTypesList.size());
                        for (FeedTypeView fdv : feedTypesList) {
                            if ("PUBLIC".equalsIgnoreCase(fdv.getFeedtypename())) {
                                feedTypesList.remove(fdv);
                            }
                        }
                        getFeedForGivenFeedType(feedTypesList.get(0).getFeedtypename(),isConnOnline);
                        new AsyncFileTask(getApplicationContext()).execute(feedTypesList);
                    }
                }

                @Override
                public void onFailure(Call<List<FeedTypeView>> call, Throwable t) {
                    Log.d(TAG, ":::::: Failure on receiving feed types ::::::" + t.toString());
                }
            });
        } else {
            feedTypesList = FileUtil.getInstance().readFeedTypeFromFile(FEEDTYPES_FILENAME,getApplicationContext());
            getFeedForGivenFeedType(feedTypesList.get(0).getFeedtypename(),isConnOnline);
        }
    }

    private void setDataToView(final int i) {
        Log.d(TAG,"::::: Item position to show :::::"+ i);
        RssFeedView item = lst.get(i);
        Log.d(TAG,"::::::: Image value ::::::"+ item.getImage());
        Log.d(TAG,"::::::: HeadLine value ::::::"+ item.getHeadline());
        Log.d(TAG,"::::::: Description value :::::::"+ item.getDescription());
        if (ValidationUtil.isNotNull(item.getImage())) {
            Picasso.with(this).load(item.getImage()).into(imgvw);
        } else {
            Log.d(TAG,"::::::: Category value ::::::"+ item.getCategories());
            if (BUSINESS.equalsIgnoreCase(item.getCategories())) {
                imgvw.setImageDrawable(getResources().getDrawable(R.mipmap.business));
            } else if (TECHNICAL.equalsIgnoreCase(item.getCategories())) {
                imgvw.setImageDrawable(getResources().getDrawable(R.mipmap.techincal));
            } else {
                imgvw.setImageDrawable(getResources().getDrawable(R.mipmap.news_feed_launcher));
            }
        }
        titleTV.setText(item.getHeadline());
        descTV.setText(item.getDescription());
    }

    private void readDataFromFile(final String feedType){
        lst = FileUtil.getInstance().readFeedFromFile(feedType+".json",getApplicationContext());
        if (ValidationUtil.isNotEmpty(lst)) {
            Log.d(TAG,":::::: No.of.feeds read from the file :::::"+lst.size());
            setDataToView(i);
        } else {
            Log.d(TAG,":::::: List from the file storage is empty, call the service ::::");
            final boolean isConnOnline = InternetConnectionUtil.checkConnection(this);
            //Call the rest service if internet connectivity,and do nothing if there is no internet access
            if (isConnOnline) {
                getFeedForGivenFeedType(feedType, isConnOnline);
            }
        }
    }

    public static boolean isTablet(Context ctx){
        return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
