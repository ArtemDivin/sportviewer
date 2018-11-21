package com.bombimbom.sportviewer.api;


import com.bombimbom.sportviewer.model.Lesson;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRepository {
    private final static String BASE_URL = "https://sample.fitnesskit-admin.ru/";
    private Api api;
    private static RemoteRepository instance;

    public static RemoteRepository getInstance() {
        if (instance == null){
            instance = new RemoteRepository();
        }
        return instance;
    }

    private RemoteRepository() {
        OkHttpClient okClient = UnsafeOkhttpClient.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.api = retrofit.create(Api.class);
    }

    public Flowable<Resource<List<Lesson>>> getLesson() {
        return api.getLesson()
                .map(Resource::success)
                .startWith(Resource.loading(null))
                .onErrorReturn(Resource::error)
                .subscribeOn(Schedulers.io());
    }
}
