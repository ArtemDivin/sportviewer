package com.bombimbom.sportviewer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombimbom.sportviewer.R;
import com.bombimbom.sportviewer.helper.CropSquareTransformation;
import com.bombimbom.sportviewer.model.Events;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subjects.PublishSubject;

public class EventAdapter extends AbstractRecyclerViewAdapter<Events, EventAdapter.ViewHolder>{

    private EventClick eventClick;

    public EventAdapter(EventClick eventClick) {
        this.eventClick = eventClick;
    }

    @Override
    protected ViewHolder getViewHolder(View parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_events, null);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, Events val) {
        holder.tvKoff.setText(val.getCoefficient());
        holder.tvPreview.setText(val.getPreview());
        holder.tvTime.setText(val.getTime());
        holder.tvTitle.setText(val.getTitle());
        holder.tvPlace.setText(val.getPlace());

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_koff)
        TextView tvKoff;
        @BindView(R.id.tv_preview)
        TextView tvPreview;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_place)
        TextView tvPlace;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Events event = getData(position);
            eventClick.onEventClick(event.getArticle());
        }
    }
}
