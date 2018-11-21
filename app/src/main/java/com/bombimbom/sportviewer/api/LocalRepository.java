package com.bombimbom.sportviewer.api;

import android.annotation.SuppressLint;

import com.bombimbom.sportviewer.App;
import com.bombimbom.sportviewer.model.Lesson;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class LocalRepository {

    private static LocalRepository instance;
    private AppDatabase db;

    public static LocalRepository getInstance() {
        if (instance == null) {
            instance = new LocalRepository();
        }
        return instance;
    }

    private LocalRepository() {
        db = App.getInstance().getDatabase();
    }

    @SuppressLint("CheckResult")
    public void saveAllLessons(List<Lesson> lessons){
        db.lessonDao().saveAll(lessons);

    }

    public Maybe<List<Lesson>> getAll(){
        return db.lessonDao().getAll();
    }
}
