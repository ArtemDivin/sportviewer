package com.bombimbom.sportviewer.api;


import android.widget.ScrollView;

import com.bombimbom.sportviewer.model.About;
import com.bombimbom.sportviewer.model.Article;
import com.bombimbom.sportviewer.model.Events;
import com.bombimbom.sportviewer.model.Item;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("list.php")
    Flowable<Item> getEvents(@Query("category") String category );


    @GET("post.php")
    Flowable<About> getArticle(@Query("article") String urlAbout);
}
