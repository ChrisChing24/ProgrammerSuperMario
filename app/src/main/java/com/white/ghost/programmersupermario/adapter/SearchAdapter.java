package com.white.ghost.programmersupermario.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.white.ghost.programmersupermario.R;
import com.white.ghost.programmersupermario.bean.SearchBean;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Function:
 * Author Name: Chris
 * Date: 2019/5/16 10:12
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<SearchBean> mSearchList;

    public SearchAdapter(List<SearchBean> searchList) {
        this.mSearchList = searchList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        SearchBean searchBean = mSearchList.get(position);
        holder.mTvSearchName.setText(searchBean.getName());
    }

    @Override
    public int getItemCount() {
        return mSearchList == null ? 0 : mSearchList.size();
    }

    public void update(List<SearchBean> searchList) {
        this.mSearchList = searchList;
        notifyDataSetChanged();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_search_name)
        TextView mTvSearchName;

        SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
