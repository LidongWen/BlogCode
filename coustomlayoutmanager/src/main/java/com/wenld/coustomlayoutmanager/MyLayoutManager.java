package com.wenld.coustomlayoutmanager;

import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wenld on 2017/8/6.
 */

public class MyLayoutManager extends LayoutManager {
    LayoutState layoutState = new LayoutState();

    int rowCount = 4;     //双行的数量


    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
//  1、入口：onLayoutChildren
//	2、回收所有view: removerRecyclerAllviews
//    回收单个或多个view : removeAndRecycleViewAt(i, recycler);
//    得到view ： recycler.getviewForPositon
//
//	3、 canScrollerVertically()  return true;
//	4、 scrollerVertically( dy  ...)

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {


        //回收所有的view
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }

        // 计算各种数据： 可用空间 当前下标 开始坐标
        layoutState.initAndClear();
        layoutState.mItemDirection = layoutState.downDirection;
        layoutState.startPoint = new Point(getPaddingLeft(), getPaddingTop());
        layoutState.mAvailable = getHeight() - getPaddingTop() - getPaddingBottom();


        if (state.getItemCount() > 0) {
            // 布局view  回收view
            fill(layoutState, recycler, state);
        }
    }


    /**
     * 填充view  回收view
     *
     * @param layoutState
     * @param recycler
     * @param state
     * @return 实际消耗的偏移量 / 实际移动的距离
     */
    private int fill(LayoutState layoutState, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //
        int mConsumed = 0;

        boolean isNeedAdd = true;


        final int count = getChildCount();
        //  根据方向  指定的view;

        if (count > 0) {
            isNeedAdd=false;
            if (layoutState.mItemDirection == layoutState.downDirection) {
//                遍历 chilren 得到坐标 与偏移量 做对比 判断是否执行  recylerchildren


                //得到最后一个item 在屏幕外占的高度
                int lastItemOutHeight = getChildAt(count - 1).getBottom() - getHeight();
                View childView;
                for (int i = count - 2; i >= 0; i--) {
                    // 判断  得到最后一个item
                    if (layoutState.mAvailable < lastItemOutHeight) {
                        childView = getChildAt(i);
                        //判断 item 的高度如果减去偏移量是否 在屏幕外
                        if (childView.getBottom() - layoutState.mAvailable < 0) {
                            //
                            for (int j = 0; j <= i; j++) {
                                removeAndRecycleViewAt(j, recycler);
                            }
                            mConsumed += layoutState.mAvailable;
                            break;
                        }
                    }
                }
                if (lastItemOutHeight < layoutState.mAvailable) {
                    isNeedAdd = true;
                }

            } else {
//                遍历 chilren 得到坐标 与偏移量 做对比 判断是否执行  recylerchildren

                //得到最后一个item 在屏幕外占的高度
                int lastItemOutHeight = getChildAt(0).getTop();
                View childView;
                for (int i = 0; i < count - 1; i++) {
                    // 判断  得到最后一个item
                    if (layoutState.mAvailable < lastItemOutHeight * layoutState.mItemDirection) {
                        childView = getChildAt(i);
                        //判断 item 的高度如果减去偏移量是否 在屏幕外
                        if (childView.getTop() - layoutState.mAvailable < 0) {
                            //
                            for (int j = count - 1; j >= i; j--) {
                                removeAndRecycleViewAt(j, recycler);
                            }
                            mConsumed += layoutState.mAvailable;
                            break;
                        } else {

                        }
                    }
                }
                if (lastItemOutHeight < layoutState.mAvailable) {
                    isNeedAdd = true;
                }
            }
        }


        int mAvailable = layoutState.mAvailable;
        while (layoutState.hasMore(state) && mAvailable > 0 && isNeedAdd) {
            int mItemConsumed = 0;
            // 自上而下填充
            if (layoutState.mItemDirection == layoutState.downDirection) {
                //      分单双行
                //      双行
                if (layoutState.mCurrentPosition == 0
                        || (layoutState.mCurrentPosition % (2 * rowCount - 1) < rowCount)) {

                    //          下标为0的view
                    if (layoutState.mCurrentPosition == 0) {
                        Log.e("  下标为0的view ",""+layoutState.mCurrentPosition);
                        View view = layoutState.next(recycler);
                        addView(view);
                        measureChildWithMargins(view, 0, 0);
                        mItemConsumed = view.getMeasuredHeight();
                        //布局view
                        int left, top, right, bottom;
                        left = layoutState.startPoint.x;
                        top = layoutState.startPoint.y;
                        right = left + view.getMeasuredWidth();
                        bottom = top + view.getMeasuredHeight();
                        layoutDecoratedWithMargins(view, left, top, right, bottom);

                    } else if (layoutState.mCurrentPosition % (2 * rowCount - 1) == 0) {   //每行的表头 第一个view  布局
                        Log.e("  双行 每行的表头 ",""+layoutState.mCurrentPosition);
                        //   坐标是与上一个view挂钩的;
                        View lastView = getChildAt(getChildCount() - 1);
                        View view = layoutState.next(recycler);
                        addView(view);
                        measureChildWithMargins(view, 0, 0);

                        mItemConsumed =  view.getMeasuredHeight()/2;

                        int left, top, right, bottom;
                        top = (int) lastView.getBottom()- mItemConsumed;
                        left = getPaddingLeft();
                        right = left + view.getMeasuredWidth();
                        bottom = top + view.getMeasuredHeight();

                        layoutDecoratedWithMargins(view, left, top, right, bottom);

                    } else {
                        Log.e("  双行 其他的view ",""+layoutState.mCurrentPosition);
                        //          其他的view
                        View lastView = getChildAt(getChildCount() - 1);
                        View view = layoutState.next(recycler);
                        addView(view);
                        measureChildWithMargins(view, 0, 0);
                        mItemConsumed = 0;
                        int left, top, right, bottom;
                        top = (int) lastView.getY();
                        left = lastView.getRight();
                        right = left + view.getMeasuredWidth();
                        bottom = top + view.getMeasuredHeight();
                        layoutDecoratedWithMargins(view, left, top, right, bottom);
                    }


                } else {
                    //      单行
                    if (layoutState.mCurrentPosition % (rowCount) == 0){
                        Log.e("  单行 行头view  ",""+layoutState.mCurrentPosition);
                        //          行头view   该行的第一个view
                        View lastView = getChildAt(getChildCount() - 1);
                        View view = layoutState.next(recycler);
                        addView(view);
                        measureChildWithMargins(view, 0, 0);
                        mItemConsumed =  view.getMeasuredHeight()/2;
                        int left, top, right, bottom;
                        top = (int) lastView.getBottom()- mItemConsumed;
                        left = mItemConsumed;
                        right = left + view.getMeasuredWidth();
                        bottom = top + view.getMeasuredHeight();
                        layoutDecoratedWithMargins(view, left, top, right, bottom);
                    }else{
                        Log.e("  单行  其他的view  ",""+layoutState.mCurrentPosition);
                        //          其他的view
                        View lastView = getChildAt(getChildCount() - 1);
                        View view = layoutState.next(recycler);
                        addView(view);
                        measureChildWithMargins(view, 0, 0);
                        mItemConsumed = 0;
                        int left, top, right, bottom;
                        top = (int) lastView.getY();
                        left = lastView.getRight();
                        right = left + view.getMeasuredWidth();
                        bottom = top + view.getMeasuredHeight();
                        layoutDecoratedWithMargins(view, left, top, right, bottom);
                    }

                }
            } else {
                // 自下而上填充
                //      分单双行
                //      双行
                //      单行
            }

        }

        //计算消耗的偏移量
        return 0;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // 根据偏移量 计算开始布局的坐标点 各种数据
        // 布局view  回收view
        fill(layoutState, recycler, state);
        // 计算实际消耗的偏移量

        // 平移children
        //返回实际消耗的偏移量
        return 0;
    }


    /**
     * Helper class that keeps temporary state while {LayoutManager} is filling out the empty
     * space.
     */
    static class LayoutState {
        //布局的方向
        int mItemDirection;   // 1: 从上自下方向   -1 : 自下而上 方向
        static final int downDirection = 1; //向上滑动，向下填充
        static final int uoDirection = -1;    // 向下滑动，向上填充

        // 开始填充的view的原点
        Point startPoint = new Point(0, 0);

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

        public void initAndClear() {
            startPoint.set(0, 0);
            mAvailable = 0;
            mOffset = 0;
            mItemDirection = downDirection;
        }

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
