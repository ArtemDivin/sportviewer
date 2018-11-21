package com.bombimbom.sportviewer.api.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bombimbom.sportviewer.model.Lesson;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public abstract class LessonDao {

    @Query("SELECT * FROM lesson")
    public  abstract Maybe<List<Lesson>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  abstract void saveAll(List<Lesson> lessons);

}
