package com.bombimbom.sportviewer;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bombimbom.sportviewer.adapter.LessonAdapter;
import com.bombimbom.sportviewer.api.Resource;
import com.bombimbom.sportviewer.helper.UIHelper;
import com.bombimbom.sportviewer.model.Lesson;
import com.bombimbom.sportviewer.viewmodel.EventsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShedulerActivity extends AppCompatActivity {

    @BindView(R.id.pb) ProgressBar pb;
    @BindView(R.id.rv) RecyclerView recyclerView;

    EventsViewModel eventsViewModel;
    LessonAdapter lessonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shedule);
        ButterKnife.bind(this);

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        eventsViewModel.getLesson();
        eventsViewModel.getSingleLiveLesson().observe(this, this::showLesson);

        initRecyclerView();

    }

    private void showLesson(Resource<List<Lesson>> resource) {
        {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        pb.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        pb.setVisibility(View.INVISIBLE);
                        lessonAdapter.setData(resource.data);
                        break;
                    case ERROR:
                        pb.setVisibility(View.INVISIBLE);
                        String message = resource.error != null ? resource.error.getMessage() : getString(R.string.error_message);
                        UIHelper.showDialog(this, message);
                        break;
                }
            }
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        lessonAdapter = new LessonAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(lessonAdapter);
    }


}
