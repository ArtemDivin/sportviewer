package com.bombimbom.sportviewer;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bombimbom.sportviewer.adapter.CategoryAdapter;
import com.bombimbom.sportviewer.adapter.CategoryClick;
import com.bombimbom.sportviewer.adapter.EventAdapter;
import com.bombimbom.sportviewer.adapter.EventClick;
import com.bombimbom.sportviewer.helper.UIHelper;
import com.bombimbom.sportviewer.model.Events;
import com.bombimbom.sportviewer.viewmodel.EventsViewModel;
import com.bombimbom.sportviewer.api.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements EventClick, CategoryClick {

    @BindView(R.id.rv) RecyclerView recyclerView;
    @BindView(R.id.rv_category) RecyclerView rvCategory;
    @BindView(R.id.pb) ProgressBar pb;


    private EventsViewModel eventViewModel;
    private EventAdapter eventsAdapter;
    private CategoryAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initCategoryRV();
        initRecyclerView();

        eventViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        eventViewModel.getEvents(Events.Category.football);
        eventViewModel.getSingleLiveEvent().observe(this, this::handleResource);

    }

    private void handleResource(Resource<List<Events>> resource) {
        {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        pb.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        pb.setVisibility(View.INVISIBLE);
                        eventsAdapter.setData(resource.data);
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

    private void initCategoryRV() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryAdapter = new CategoryAdapter(this);
        rvCategory.setLayoutManager(manager);
        rvCategory.setAdapter(categoryAdapter);

        List<Events.Category> list = new ArrayList<>(Arrays.asList(Events.Category.values()));
        categoryAdapter.setData(list);
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        eventsAdapter = new EventAdapter(this);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,manager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(eventsAdapter);
    }


    @Override
    public void onEventClick(String about) {

        AboutActivity.start(this, about);

    }

    @Override
    public void onCategoryClick(Events.Category category) {

        eventViewModel.getEvents(category);
    }
}
