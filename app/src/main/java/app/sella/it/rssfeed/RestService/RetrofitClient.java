package app.sella.it.rssfeed.RestService;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitClient = null;

    public static Retrofit getClient(final String base_url) {
        if (retrofitClient==null) {
            OkHttpClient okHttpClient = new Builder().connectTimeout(300, TimeUnit.SECONDS).readTimeout(2, TimeUnit.MINUTES).build();
            retrofitClient = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofitClient;
    }
}
