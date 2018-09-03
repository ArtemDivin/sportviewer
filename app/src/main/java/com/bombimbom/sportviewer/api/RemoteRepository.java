package com.bombimbom.sportviewer.api;


import com.bombimbom.sportviewer.model.About;
import com.bombimbom.sportviewer.model.Events;
import com.bombimbom.sportviewer.model.Item;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository {
    private final static String BASE_URL = " http://mikonatoruri.win/";
    private Api api;
    private static RemoteRepository instance;

    public static RemoteRepository getInstance() {
        if (instance == null){
            instance = new RemoteRepository();
        }
        return instance;
    }

    private RemoteRepository() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.api = retrofit.create(Api.class);
    }

    public Flowable<Resource<List<Events>>> getEventsResourceByCategory(String category) {
        return api.getEvents(category)
                .map(Item::getEvents)
                .map(Resource::success)
                .startWith(Resource.loading(null))
                .onErrorReturn(Resource::error)
                .subscribeOn(Schedulers.io());

    }

    public Flowable<Resource<About>> getArticle(String urlAbout) {
        return api.getArticle(urlAbout)
                .map(Resource::success)
                .startWith(Resource.loading(null))
                .onErrorReturn(Resource::error)
                .subscribeOn(Schedulers.io());
    }
}
