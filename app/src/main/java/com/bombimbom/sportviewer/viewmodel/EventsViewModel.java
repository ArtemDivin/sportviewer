package com.bombimbom.sportviewer.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;

import com.bombimbom.sportviewer.api.LocalRepository;
import com.bombimbom.sportviewer.api.RemoteRepository;
import com.bombimbom.sportviewer.api.Resource;
import com.bombimbom.sportviewer.model.Lesson;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class EventsViewModel extends ViewModel {

    private RemoteRepository remoteRepository;
    private LocalRepository localRepository;
    private SingleLiveEvent<Resource<List<Lesson>>> singleLiveLesson = new SingleLiveEvent<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EventsViewModel() {
        remoteRepository = RemoteRepository.getInstance();
        localRepository = LocalRepository.getInstance();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
    public void getLesson() {

        Flowable<Resource<List<Lesson>>> lessonRemote =
                remoteRepository.getLesson()
                        .doOnNext(listResource -> {
                            if (listResource.status.equals(Resource.Status.SUCCESS)){
                                localRepository.saveAllLessons(listResource.data);
                            }})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Flowable<Resource<List<Lesson>>> lessonLocal= localRepository
                .getAll()
                .filter(lessons -> lessons != null && !lessons.isEmpty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Resource::success)
                .toFlowable();

        Disposable subscribe = lessonLocal
                .mergeWith(lessonRemote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleLiveLesson::setValue);

        compositeDisposable.add(subscribe);

        //.subscribe(singleLiveLesson::setValue);
        //compositeDisposable.add(disposable);
    }

    public SingleLiveEvent<Resource<List<Lesson>>> getSingleLiveLesson() {
        return singleLiveLesson;
    }
}
