package com.bombimbom.sportviewer;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bombimbom.sportviewer.adapter.ArticleAdapter;
import com.bombimbom.sportviewer.adapter.EventAdapter;
import com.bombimbom.sportviewer.api.Resource;
import com.bombimbom.sportviewer.helper.UIHelper;
import com.bombimbom.sportviewer.model.About;
import com.bombimbom.sportviewer.viewmodel.EventsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.pb) ProgressBar pb;
    @BindView(R.id.tv_prediction) TextView tvPrediction;
    @BindView(R.id.tv_time) TextView tvTime;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_place) TextView tvPlace;
    @BindView(R.id.tv_team_one) TextView tvOne;
    @BindView(R.id.tv_team_two) TextView tvTwo;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rv_article) RecyclerView recyclerView;

    EventsViewModel eventsViewModel;
    ArticleAdapter articleAdapter;

    public static final String ARG_ABOUT = "about";

    public static void start(Context context, String aboutUrl){
        Intent intent = new Intent(context, AboutActivity.class);
        intent.putExtra(ARG_ABOUT, aboutUrl);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);


        toolbar.setNavigationOnClickListener(v -> finish());

        String urlAbout = getIntent().getStringExtra(ARG_ABOUT);

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        eventsViewModel.getArticle(urlAbout);
        eventsViewModel.getSingleLiveAbout().observe(this, this::handleAbout);

        initRecyclerView();

    }

    private void handleAbout(Resource<About> resource) {
        {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        pb.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        pb.setVisibility(View.INVISIBLE);

                        About about = resource.data;
                        tvPlace.setText(about.getPlace());
                        tvPrediction.setText(about.getPrediction());
                        tvTime.setText(about.getTime());
                        tvTitle.setText(about.getTournament());
                        tvOne.setText(about.getTeam1());
                        tvTwo.setText(about.getTeam2());

                        articleAdapter.setData(about.getArticle());

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
        articleAdapter = new ArticleAdapter();
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,manager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(articleAdapter);
    }


}
