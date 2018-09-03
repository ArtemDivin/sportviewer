package com.bombimbom.sportviewer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bombimbom.sportviewer.R;
import com.bombimbom.sportviewer.model.Article;
import com.bombimbom.sportviewer.model.Events;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends AbstractRecyclerViewAdapter<Article, ArticleAdapter.ViewHolder> {
    @Override
    protected ArticleAdapter.ViewHolder getViewHolder(View parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, null);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(ArticleAdapter.ViewHolder holder, Article val) {

        holder.tvTitle.setText(val.getHeader());
        holder.tvTitle.setVisibility(val.getHeader().isEmpty() ? View.GONE : View.VISIBLE );

        holder.tvDescription.setText(val.getText());

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_description)
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}

