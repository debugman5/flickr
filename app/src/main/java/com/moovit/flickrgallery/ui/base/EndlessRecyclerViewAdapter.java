package com.moovit.flickrgallery.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.moovit.flickrgallery.R;


public abstract class EndlessRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    private static final int PROGRESS_VIEW_TYPE = R.id.adapter_progress_view_type;

    private boolean mShowLoadMoreProgressView;
    private View mLoadMoreProgressView;

    public EndlessRecyclerViewAdapter(View loadMoreProgressView) {
        mLoadMoreProgressView = loadMoreProgressView;
    }

    public abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindItemViewHolder(VH holder, int position);

    public abstract int getBasicItemCount();

    public int getBasicItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( viewType == PROGRESS_VIEW_TYPE )
        {
            return new UnRecyclableViewHolder(mLoadMoreProgressView);
        }
        else
        {
            return onCreateItemViewHolder(parent, viewType);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ( getItemViewType(position) != PROGRESS_VIEW_TYPE )
        {
            onBindItemViewHolder((VH)holder, position);
        }
    }

    @Override
    public final int getItemCount() {
        return getBasicItemCount() + (mShowLoadMoreProgressView ? 1 : 0);
    }

    @Override
    public final int getItemViewType(int position) {
        if ( mShowLoadMoreProgressView && position >= getBasicItemCount() )
            return PROGRESS_VIEW_TYPE;
        else
            return getBasicItemViewType(position);
    }

    public void showLoadMoreProgressView() {
        if ( !mShowLoadMoreProgressView )
        {
            mShowLoadMoreProgressView = true;
            notifyItemInserted(getItemCount());
        }
    }

    public void hideLoadMoreProgressView() {
        if ( mShowLoadMoreProgressView )
        {
            notifyItemRemoved(getItemCount());
            mShowLoadMoreProgressView = false;
        }
    }

    static class UnRecyclableViewHolder extends RecyclerView.ViewHolder{
        public UnRecyclableViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
        }
    }
}
