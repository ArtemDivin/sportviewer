package com.bombimbom.sportviewer.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.ViewModel;

import com.bombimbom.sportviewer.api.RemoteRepository;
import com.bombimbom.sportviewer.api.Resource;
import com.bombimbom.sportviewer.model.About;
import com.bombimbom.sportviewer.model.Events;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class EventsViewModel extends ViewModel {

    private RemoteRepository remoteRepository;
    private SingleLiveEvent<Resource<List<Events>>> singleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Resource<About>> singleLiveAbout = new SingleLiveEvent<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EventsViewModel() {
        remoteRepository = RemoteRepository.getInstance();
    }


    @SuppressLint("CheckResult")
    public void getEvents(Events.Category category) {
        Disposable disposable = remoteRepository
                .getEventsResourceByCategory(category.name())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleLiveEvent::setValue);
        compositeDisposable.add(disposable);
    }

    public SingleLiveEvent<Resource<List<Events>>> getSingleLiveEvent() {
        return singleLiveEvent;
    }

    public SingleLiveEvent<Resource<About>> getSingleLiveAbout() {
        return singleLiveAbout;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void getArticle(String urlAbout) {
        Disposable disposable = remoteRepository.getArticle(urlAbout)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleLiveAbout::setValue);
        compositeDisposable.add(disposable);
    }
}
