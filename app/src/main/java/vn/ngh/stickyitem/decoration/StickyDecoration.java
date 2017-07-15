package vn.ngh.stickyitem.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import vn.ngh.stickyitem.interfaces.StickyAdapter;

/**
 * Created by Ngh on 7/13/2017.
 */

public class StickyDecoration extends RecyclerView.ItemDecoration {
    private ViewHolder mViewHolderCache;
    private StickyAdapter mAdapter;
    private Rect mRect;
    private OnPositionListener mOnPositionListener;

    public StickyDecoration(StickyAdapter adapter) {
        mAdapter = adapter;
        mViewHolderCache = null;
        mRect = new Rect();
    }

    public void setOnPostitionListener(OnPositionListener event) {
        this.mOnPositionListener = event;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int headerHeight = 0;
        if (position != RecyclerView.NO_POSITION && mAdapter.getStickyIndex() >= 0) {
            View header = getSticky(parent, position).itemView;
            headerHeight = header.getHeight();
            if (position == mAdapter.getStickyIndex()) {
                outRect.set(0, headerHeight, 0, 0);
                return;
            }
            if (position >= mAdapter.getDataCount() - 1 && mCurrentPosition == Position.BOTTOM) {
                outRect.set(0, 0, 0, headerHeight);
                return;
            }
        }
        outRect.set(0, 0, 0, 0);
    }

    public StickyAdapter getAdapter() {
        return mAdapter;
    }

    public Rect getStickyRect() {
        return mRect;
    }

    public ViewHolder getViewHolder() {
        return mViewHolderCache;
    }

    private RecyclerView.ViewHolder getSticky(RecyclerView parent, int position) {
        int key = mAdapter.getStickyIndex();

        if (key >= 0) {
            if (!mAdapter.getEnableUpdate()) {
                return mViewHolderCache;
            } else {
                RecyclerView.ViewHolder holder = mAdapter.onCreateStickyViewHolder(parent);
                View sticky = holder.itemView;

                mAdapter.onBindStickyViewHolder(holder, position);
                int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
                int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

                int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                        parent.getPaddingLeft() + parent.getPaddingRight(), sticky.getLayoutParams().width);
                int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                        parent.getPaddingTop() + parent.getPaddingBottom(), sticky.getLayoutParams().height);

                sticky.measure(childWidth, childHeight);
                sticky.layout(0, 0, sticky.getMeasuredWidth(), sticky.getMeasuredHeight());

                // save cache
                mViewHolderCache = holder;

                // update done so don't need create view any more
                mAdapter.setEnableUpdate(false);

                return holder;
            }
        }
        return null;
    }

    private Position mCurrentPosition = Position.BOTTOM;

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int count = parent.getChildCount();

        int lastVisibleItemPosition =
                ((LinearLayoutManager) parent.getLayoutManager()).findLastVisibleItemPosition();
        int firstVisiblePosition =
                ((LinearLayoutManager) parent.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();
        int paddingTop = parent.getPaddingTop();
        int paddingBottom = parent.getPaddingBottom();

        int indexSticky = mAdapter.getStickyIndex();

        if (indexSticky >= 0) {
            for (int layoutPos = 0; layoutPos < count; layoutPos++) {
                final View child = parent.getChildAt(layoutPos);

                final int adapterPos = parent.getChildAdapterPosition(child);

                if (adapterPos != RecyclerView.NO_POSITION) {
                    View header = getSticky(parent, adapterPos).itemView;
                    //TODO: find view to click (finding another way)
                    //View reset = header.findViewById(R.id.button_reset_or_luck_rank);
                    c.save();
                    int left = child.getLeft();
                    int top = 0;
                    if (firstVisiblePosition <= indexSticky && indexSticky <= lastVisibleItemPosition) {
                        if (adapterPos == indexSticky) {
                            top = getStickyTop(parent, child);
                            setPosition(Position.IN_LIST);
                        } else {
                            continue;
                        }
                    } else if (firstVisiblePosition > indexSticky) {
                        top = paddingTop;
                        setPosition(Position.TOP);
                    } else {
                        setPosition(Position.BOTTOM);
                        top = parent.getHeight() - header.getHeight() - paddingBottom;
                    }
                    c.translate(left, top);
                    header.draw(c);
                    c.restore();
                    // find location
//                    mRect.set(header.getWidth() - reset.getWidth(), top, header.getRight(),
//                            top + header.getHeight() - reset.getHeight());
                }
            }
        }
    }

    private void setPosition(Position position) {
        mCurrentPosition = position;
        if (mOnPositionListener != null) {
            mOnPositionListener.onSticky(position);
        }
    }

    private int getStickyTop(RecyclerView parent, View child) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        int paddingTop = parent.getPaddingTop();
        int paddingBottom = parent.getPaddingBottom();
        int maxBottom = parent.getHeight() - child.getHeight() - paddingBottom - paddingTop;
        int pos = layoutManager.getDecoratedTop(child);
        int result = 0;

        if (pos < 0) {
            result = 0;
        } else if (pos > maxBottom) {
            result = maxBottom;
        } else {
            result = pos;
        }
        return result;
    }

    public void clearStickyCache() {
        mViewHolderCache = null;
    }

    public interface OnPositionListener {
        void onSticky(Position pos);
    }

    public enum Position {
        TOP, BOTTOM, IN_LIST
    }
}
