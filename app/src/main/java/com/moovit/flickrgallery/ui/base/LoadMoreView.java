package com.moovit.flickrgallery.ui.base;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.infinite.LoadMore;
import com.moovit.flickrgallery.R;

@Layout(R.layout.progress_dialog)
public class LoadMoreView {

    public interface LoadMoreListener {
        void onLoadMore();
    }

    private LoadMoreListener mLoadMoreListener;

    public LoadMoreView(LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    @LoadMore
    protected void onLoadMore() {
        if ( mLoadMoreListener != null ) {
            mLoadMoreListener.onLoadMore();
        }
    }
}
