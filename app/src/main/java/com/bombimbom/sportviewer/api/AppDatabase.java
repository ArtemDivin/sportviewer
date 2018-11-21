package com.bombimbom.sportviewer.api;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import com.bombimbom.sportviewer.api.dao.LessonDao;
import com.bombimbom.sportviewer.model.Lesson;

@Database(entities = {Lesson.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract LessonDao lessonDao();
}
