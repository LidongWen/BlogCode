package com.wenld.coustomlayoutmanager;

import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wenld on 2017/8/5.
 */

public class MyLayoutManager_one extends RecyclerView.LayoutManager {
    LayoutState mLayoutState = new LayoutState();
    int rowCount = 5;
    int rowHeight = 0;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
//    入口： onlayoutchildrren
//    //  fill()   给它一个开始坐标点 结束坐标点
//    //  	回收布局  、获取布局   方法：
//    回收所有view   removeAndRecycleAllViews(recycler);
//    回收单个伙多个view         recycleChildren(recycler, childCount - 1, i);
//    得到view  : recycler.getViewForPosition(mCurrentPosition);
//
//    是否允许竖直方向的滑动  canScrollVertically  返回true;
//    竖直方向滑动时会调用这个方法 ： scrollVerticallyBy  返回实际消耗的偏移量

    LayoutState layoutState;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //  第一 回收 所有view
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        mLayoutState.mAvailable = getHeight();
        mLayoutState.startPoint = new Point(getPaddingLeft(), getPaddingTop()); //从左上角坐标开始
        mLayoutState.mItemDirection = 1;
        mLayoutState.mCurrentPosition = 0;
        mLayoutState.mOffset = 0;
        // 布局 当前区域的view
        fill(mLayoutState, recycler, state);
    }

    //

    /**
     * 布局指定区域的view   回收一些view
     *
     * @param layoutState 布局的一些信息  （当前view的下标，可用空间 ，开始填充的坐标，填充的方向（从下往上 从上自下 填充））
     * @param recycler
     * @param state
     * @return 返回实际填充的区域
     */
    private int fill(LayoutState layoutState, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //  循环 从第一开始    如果 我的可用空间大于0 以及 还有下一个view
        // 可用空间
        int freeSpace = layoutState.mAvailable;
        int mConsumed;
        while (freeSpace > 0 && layoutState.hasMore(state)) {
            mConsumed = 0;
            //判断单双行
            if (layoutState.mCurrentPosition == 0 || (layoutState.mCurrentPosition % (rowCount + rowCount - 1) < rowCount)) {
                // 布局view
                //判断是否是0
                if(layoutState.mCurrentPosition == 0 ){
                    final View view = layoutState.next(recycler);
                    if (view != null) {
                        // 添加view
                        // 测量view
                        addView(view);
                        measureChildWithMargins(view, getWidth() / rowCount, getWidth() / rowCount);
                        mConsumed = rowHeight = view.getMeasuredHeight();
                        // 计算实际偏移量
                        int left = layoutState.startPoint.x;
                        int top = layoutState.startPoint.y;
                        int right = left + view.getMeasuredWidth();
                        int bottom = top + rowHeight;
                        layoutDecoratedWithMargins(view, left, top, right, bottom);

                    }
                }else if (layoutState.mCurrentPosition % (rowCount + rowCount - 1) == 1) {  //判断是否是行头
                    final View view = layoutState.next(recycler);
                    if (view != null) {
                        // 添加view
                        // 测量view
                        addView(view);
                        measureChildWithMargins(view, getWidth() / rowCount, getWidth() / rowCount);
                        mConsumed = rowHeight = view.getMeasuredHeight();
                    }
                    // 计算实际偏移量
                    int left = layoutState.startPoint.x;
                    int top = layoutState.startPoint.y;
                    int right = left + view.getMeasuredWidth();
                    int bottom = top + rowHeight;

                } else {

                    mConsumed=0;
                }

//                final View view = layoutState.next(recycler);
//                if (view != null) {
//                    addView(view);
//                    measureChildWithMargins(view, getWidth() / 5, getWidth() / 5);
//                    itemHeight = view.getMeasuredHeight();
//                    rowHeight = itemHeight * 3 / 2;
//                    int left = layoutState.startPoint.x;
//                    int top = layoutState.startPoint.y;
//                    int right = left + view.getMeasuredWidth();
//                    int bottom = top + itemHeight;
//                    layoutDecoratedWithMargins(view, left, top, right, bottom);
//                    layoutState.mOffset += (rowHeight);
//                }
            } else {     //单行
                //判断是否是行头
                if (layoutState.mCurrentPosition % rowCount == 1) {

                } else {

                }
            }
            layoutState.mOffset += (mConsumed)*layoutState.mItemDirection;
            layoutState.mAvailable -= mConsumed;
            freeSpace -= mConsumed;
        }

        return 0;
    }


    @Override
    public boolean canScrollVertically() {
        //允许竖直方向的滑动
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 计算需要填充的空间

        // 回收一部分view   填充一部分view
        int trueComsumed = fill(mLayoutState, recycler, state);
        // 对view进行实际的偏移
        // 返回 实际消耗的偏移量
        return trueComsumed;
    }


    /**
     * Helper class that keeps temporary state while {LayoutManager} is filling out the empty
     * space.
     */
    static class LayoutState {
        //布局的方向
        int mItemDirection;   // 1: 从上自下方向   -1 : 自下而上 方向
        // 开始填充的view的原点
        Point startPoint;

        // 可用高度
        int mAvailable;
        /**
         * Pixel offset where layout should start
         */
        int mOffset;
        /**
         * Current position on the adapter to get the next item.
         */
        public int mCurrentPosition;

        boolean hasMore(RecyclerView.State state) {
            return mCurrentPosition >= 0 && mCurrentPosition < state.getItemCount();
        }

        View next(RecyclerView.Recycler recycler) {

            final View view = recycler.getViewForPosition(mCurrentPosition);
            mCurrentPosition += mItemDirection;
            return view;
        }
    }
}
