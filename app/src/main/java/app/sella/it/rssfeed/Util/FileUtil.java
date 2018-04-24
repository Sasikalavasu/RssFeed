package app.sella.it.rssfeed.Util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.sella.it.rssfeed.Model.FeedTypeView;
import app.sella.it.rssfeed.Model.RssFeedView;

public class FileUtil {
    private static FileUtil _instance = new FileUtil();
    private static final String TAG = FileUtil.class.getName();

    public static FileUtil getInstance(){
        return _instance;
    }

    public void writeToFile(final List lstOfFeedData, final String fileName, Context ctx){
        Log.d(TAG,":::::::: Write feed data to file name ::::"+fileName);
        FileOutputStream fOutStream = null;
        try {
            Gson gsonBuilder = new GsonBuilder().create();
            final String jsonFeedStr = gsonBuilder.toJson(lstOfFeedData);
            fOutStream = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            fOutStream.write(jsonFeedStr.getBytes());
            Log.d(TAG,":::::::::: Write data to file success :::::::");
        }
        catch (Exception e) {
            Log.d(TAG,"::::::::: Write data ti file - failure ::::"+e);
            e.printStackTrace();
        } finally {
            try {
                if (fOutStream != null) {
                    fOutStream.close();
                }
            } catch (final IOException exp) {
                exp.printStackTrace();
            }
        }
    }

    public List<RssFeedView> readFeedFromFile(final String fileName,Context ctx){
        List<RssFeedView> listOfFeeds =  new ArrayList();
        JsonReader reader = null;
        try {
            Gson gsonBuilder = new GsonBuilder().create();
            reader = new JsonReader(new InputStreamReader(ctx.openFileInput(fileName)));
            Type rssFeedList = new TypeToken<ArrayList<RssFeedView>>(){}.getType();
            listOfFeeds = gsonBuilder.fromJson(reader,rssFeedList);
            Log.d(TAG,":::::::::: Read feed data from file success ::::::::::");
        }catch (final FileNotFoundException exp) {
            Log.d(TAG,"::::::::: File not found exception :::::::"+exp);
            listOfFeeds =  new ArrayList();
            exp.printStackTrace();
        } finally {
            try{
                if (reader!=null) {
                    reader.close();
                }
            } catch (final IOException exp) {
                exp.printStackTrace();
            }
        }
        return listOfFeeds;
    }

    public List<FeedTypeView> readFeedTypeFromFile(final String fileName, Context ctx){
        List<FeedTypeView> listOfFeeds =  new ArrayList();
        JsonReader reader = null;
        try {
            Gson gsonBuilder = new GsonBuilder().create();
            reader = new JsonReader(new InputStreamReader(ctx.openFileInput(fileName)));
            Type rssFeedTypeList = new TypeToken<ArrayList<FeedTypeView>>(){}.getType();
            listOfFeeds = gsonBuilder.fromJson(reader,rssFeedTypeList);
            Log.d(TAG,":::::::: Read FeedTypes from file success :::::::::");
        }catch (final FileNotFoundException exp) {
            Log.d(TAG,"::::::::: File not found exception ::::::::"+exp);
            listOfFeeds =  new ArrayList();
            exp.printStackTrace();
        } finally {
            try{
                if (reader!=null) {
                    reader.close();
                }
            } catch (final IOException exp) {
                exp.printStackTrace();
            }
        }
        return listOfFeeds;
    }
}
