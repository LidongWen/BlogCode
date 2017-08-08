package com.wenld.coustomlayoutmanager;

import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wenld on 2017/8/6.
 */

public class MyLayoutManager extends LayoutManager {
    LayoutState layoutState = new LayoutState();

    int rowCount = 4;     //双行的数量
    int halfLineHeight = 0;     //半行高

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
        removeAndRecycleAllViews(recycler);
        // 计算各种数据： 可用空间 当前下标 开始坐标
        layoutState.initAndClear();
        layoutState.mItemDirection = layoutState.downDirection;
        layoutState.startPoint = new Point(getPaddingLeft(), getPaddingTop());
        layoutState.mAvailable = getHeight() - getPaddingTop() - getPaddingBottom();


        if (state.getItemCount() > 0) {
            // 布局view  回收view
            int consumed = fill(layoutState, recycler, state);
            Log.e("消耗的高度：", "" + consumed);
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
        boolean isNeedAdd = true;
        final int count = getChildCount();
        //  根据方向  指定的view;

        if (count > 0) {
            isNeedAdd = false;
            if (layoutState.mItemDirection == layoutState.downDirection) {
//                遍历 chilren 得到坐标 与偏移量 做对比 判断是否执行  recylerchildren
                //得到最后一个item 在屏幕外占的高度
                int lastItemOutHeight = getChildAt(count - 1).getBottom() - getHeight();
                View childView;
                for (int i = count - 2; i >= 0; i--) {
                    childView = getChildAt(i);
                    if (childView.getBottom() < lastItemOutHeight) {
                        //判断 item 的高度如果减去偏移量是否 在屏幕外
                        if (childView.getBottom() - layoutState.mAvailable < getPaddingTop()) {
                            //
                            for (int j = i; j >=0 ; j--) {
                                Log.e("上滑回收","  " + getPosition(getChildAt(j))+"     " +j );
                                removeAndRecycleViewAt(j, recycler);
                            }
                            break;

                        }
                    }
                }
                if (lastItemOutHeight - layoutState.mAvailable < halfLineHeight) {
                    isNeedAdd = true;
                }
                layoutState.mOffset = Math.min(lastItemOutHeight, layoutState.mAvailable);
            } else {
//                遍历 chilren 得到坐标 与偏移量 做对比 判断是否执行  recylerchildren

                //得到最后一个item 在屏幕外占的高度
                int lastItemOutHeight = Math.abs(getChildAt(0).getTop());
                View childView;
                for (int i = 0; i < count - 1; i++) {
                    // 判断  得到最后一个item
                    if (layoutState.mAvailable < lastItemOutHeight) {
                        childView = getChildAt(i);
                        //判断 item 的高度如果减去偏移量是否 在屏幕外
                        if (childView.getTop() + layoutState.mAvailable > getHeight() - getPaddingBottom()) {
                            //
                            for (int j = count - 1; j >= i; j--) {
                                Log.e("下滑回收","  " + getPosition(getChildAt(j))+"     " +j );
                                removeAndRecycleViewAt(j, recycler);
                            }
                            break;
                        }
                    }
                }
                if (lastItemOutHeight - layoutState.mAvailable < halfLineHeight) {
                    isNeedAdd = true;
                }
                layoutState.mOffset= Math.min(lastItemOutHeight, layoutState.mAvailable);
            }
        }


        int mAvailable = layoutState.mAvailable - layoutState.mOffset;

        while (layoutState.hasMore(state) && mAvailable > -halfLineHeight && isNeedAdd) {
            int mItemConsumed;
            View child = null;
            // 自上而下填充
            if (layoutState.mItemDirection == layoutState.downDirection) {
                //      分单双行

                //      双行
                if (layoutState.mCurrentPosition == 0
                        || (layoutState.mCurrentPosition % (2 * rowCount - 1) < rowCount)) {

                    //          下标为0的view
                    if (layoutState.mCurrentPosition == 0) {
                        child = layoutCommonChild(layoutState, recycler, layoutState.startPoint.x, layoutState.startPoint.y);
                        halfLineHeight = child.getMeasuredHeight() / 2;
                    } else if (layoutState.mCurrentPosition % (2 * rowCount - 1) == 0) {   //每行的表头 第一个view  布局
                        //   双行 每行的表头
                        View lastView = getChildAt(getChildCount() - 1);
                        child = layoutCommonChild(layoutState, recycler, lastView.getBottom() - halfLineHeight, getPaddingLeft());
                    } else {
                        //          其他的view
                        View lastView = getChildAt(getChildCount() - 1);
                        child = layoutCommonChild(layoutState, recycler, (int) lastView.getTop(), lastView.getRight());
                    }
                } else {
                    //      单行
                    if (layoutState.mCurrentPosition % (2 * rowCount - 1) == rowCount) {
                        View lastView = getChildAt(getChildCount() - 1);
                        child = layoutCommonChild(layoutState, recycler,
                                lastView.getBottom() - halfLineHeight, lastView.getMeasuredWidth() / 2);
                    } else {
                        View lastView = getChildAt(getChildCount() - 1);
                        //          其他的view
                        child = layoutCommonChild(layoutState, recycler, (int) lastView.getTop(), lastView.getRight());
                    }
                }
            } else {
                // 自下而上填充
                //      双行
                if (layoutState.mCurrentPosition == 0
                        || (layoutState.mCurrentPosition % (2 * rowCount - 1) < rowCount)) {
                    //表尾
                    if (layoutState.mCurrentPosition % (2*rowCount - 1) == rowCount-1) {
                        View lastView = getChildAt(0);
                        child = layoutCommonChild(layoutState, recycler, lastView.getTop() - halfLineHeight,
                                getPaddingLeft() + (rowCount - 1) * lastView.getMeasuredWidth());
                    } else {
                        //其他
                        View lastView = getChildAt(0);
                        child = layoutCommonChild(layoutState, recycler, lastView.getTop(), lastView.getLeft() - lastView.getWidth());
                    }
                } else {
                    //表尾
                    if (layoutState.mCurrentPosition % (2 * rowCount - 1) == 2 * rowCount - 2) {
                        View lastView = getChildAt(0);
                        child = layoutCommonChild(layoutState, recycler, lastView.getTop() - halfLineHeight,
                                (int) ((rowCount - 1.5) * lastView.getMeasuredWidth()));
                    } else {
                        //其他
                        View lastView = getChildAt(0);
                        child = layoutCommonChild(layoutState, recycler, lastView.getTop(), lastView.getLeft() - lastView.getWidth());
                    }
                }
            }
            mItemConsumed = calculationConsumed(state, child);

            mAvailable -= mItemConsumed;
            layoutState.mOffset += mItemConsumed;
        }

        //计算消耗的偏移量
        return Math.min(layoutState.mOffset , layoutState.mAvailable);
    }

    private View layoutCommonChild(LayoutState layoutState, RecyclerView.Recycler recycler, int top, int left) {

        View view = layoutState.next(recycler);
        if (layoutState.mItemDirection == layoutState.downDirection) {
            addView(view);
        } else {
            addView(view, 0);
        }
        measureChildWithMargins(view, 0, 0);
        int right, bottom;

        right = left + view.getMeasuredWidth();
        bottom = top + view.getMeasuredHeight();
        layoutDecoratedWithMargins(view, left, top, right, bottom);
        return view;
    }

    /**
     * 计算消耗高度
     *
     * @param state
     * @param view
     * @return
     */
    private int calculationConsumed(RecyclerView.State state, View view) {
        int mItemConsumed = 0;
        //没有更多item的情况下
        int postion = getPosition(view);
        if (layoutState.mItemDirection == LayoutState.downDirection) {
   
            if (!layoutState.hasMore(state)) {
                //第一行
                if (postion < rowCount) {
                    mItemConsumed = view.getMeasuredHeight();
                } else {
                    //其它行
                    mItemConsumed = halfLineHeight;
                }
            } //行末的情况下
            else if (postion != 0 && (postion % (2 * rowCount - 1) == (rowCount - 1) || postion % (2 * rowCount - 1) == (2 * rowCount - 2))) {
                //第一行
                if (postion <= rowCount) {
                    mItemConsumed = view.getMeasuredHeight();
                } else {    //其它行
                    mItemConsumed = halfLineHeight;
                }
            }
            Log.e("消耗的高度：", "" + mItemConsumed + "   postion:" + postion);
        }else{
            // 表头消耗
            if(postion % (2 * rowCount - 1)==rowCount|| postion % (2 * rowCount - 1)==0){
                mItemConsumed = halfLineHeight;
            }
        }
        return mItemConsumed;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || dy == 0) {
            return 0;
        }
        // 根据偏移量 计算开始布局的坐标点 各种数据
        layoutState.initAndClear();
        layoutState.mAvailable = Math.abs(dy);
        if (dy > 0) {
            layoutState.mItemDirection = layoutState.downDirection;
            layoutState.mCurrentPosition = getPosition(getChildAt(getChildCount() - 1)) + 1;
        } else {
            layoutState.mItemDirection = layoutState.upDirection;
            layoutState.mCurrentPosition = getPosition(getChildAt(0)) - 1;
        }

        // 布局view  回收view
        int consumed = fill(layoutState, recycler, state);
        // 计算实际消耗的偏移量

        // 平移children
        final int scrolled = Math.abs(dy) > consumed ? layoutState.mItemDirection * consumed : dy;
        offsetChildrenVertical(-scrolled);
        //返回实际消耗的偏移量
        return scrolled;
    }


    /**
     * Helper class that keeps temporary state while {LayoutManager} is filling out the empty
     * space.
     */
    static class LayoutState {
        //布局的方向
        int mItemDirection;   // 1: 从上自下方向   -1 : 自下而上 方向
        static final int downDirection = 1; //向上滑动，向下填充
        static final int upDirection = -1;    // 向下滑动，向上填充

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
            mCurrentPosition = 0;
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
