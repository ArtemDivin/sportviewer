package com.bombimbom.sportviewer.api;


import com.bombimbom.sportviewer.model.Lesson;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("schedule/get_group_lessons_v2/4/")
    Flowable<List<Lesson>> getLesson();
}
