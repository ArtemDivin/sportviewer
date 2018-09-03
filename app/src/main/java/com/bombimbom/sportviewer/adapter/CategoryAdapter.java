package com.bombimbom.sportviewer.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombimbom.sportviewer.R;
import com.bombimbom.sportviewer.model.Events;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends AbstractRecyclerViewAdapter<Events.Category, CategoryAdapter.ViewHolder> {
    private CategoryClick categoryClick;
    private int activeCategory = 0;

    public CategoryAdapter(CategoryClick categoryClick) {
        this.categoryClick = categoryClick;
    }

    @Override
    protected ViewHolder getViewHolder(View parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null);
        return new CategoryAdapter.ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, Events.Category val) {

        holder.tvCategory.setText(val.name());
        holder.tvCategory.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        if (holder.getAdapterPosition() == activeCategory) {
            holder.tvCategory.setTypeface(holder.tvCategory.getTypeface(), Typeface.BOLD);
        }

    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_category)
        TextView tvCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Events.Category category = getData(position);
            if (position != activeCategory) {
                categoryClick.onCategoryClick(category);
                activeCategory = position;
                notifyDataSetChanged();
            }
        }

    }
}
