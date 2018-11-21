package com.bombimbom.sportviewer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bombimbom.sportviewer.R;
import com.bombimbom.sportviewer.model.Lesson;

import java.text.DateFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LessonAdapter extends AbstractRecyclerViewAdapter<Lesson, LessonAdapter.ViewHolder> {

    private static final String EMPTY = "";
    public static final String NO_DESCRIPTION = "Нет описания";

    @Override
    protected ViewHolder getViewHolder(View parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, (ViewGroup) parent, false);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, Lesson val) {
        holder.tvName.setText(val.getName());
        String description = val.getDescription();
        holder.getTvDescription.setText(description.equals(EMPTY) ? NO_DESCRIPTION : description);
        holder.tvStart.setText(val.getStartTime());
        holder.tvEnd.setText(val.getEndTime());
        holder.tvCoach.setText(val.getTeacher());
        holder.tvPlace.setText(val.getPlace());

        String dayName = new DateFormatSymbols().getWeekdays()[val.getWeekDay() - 1];
        holder.tvDay.setText(dayName);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_start)
        TextView tvStart;
        @BindView(R.id.tv_end)
        TextView tvEnd;
        @BindView(R.id.tv_description)
        TextView getTvDescription;
        @BindView(R.id.tv_coach)
        TextView tvCoach;
        @BindView(R.id.tv_place)
        TextView tvPlace;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_weekday)
        TextView tvDay;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
