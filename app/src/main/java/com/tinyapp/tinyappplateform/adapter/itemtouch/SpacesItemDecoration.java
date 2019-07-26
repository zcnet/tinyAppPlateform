package com.tinyapp.tinyappplateform.adapter.itemtouch;

import android.graphics.Rect;
import android.view.View;

import com.sun.dragrv.DragRecyclerView;

/**
 * Created by sun on 2018/11/16
 */

public class SpacesItemDecoration extends DragRecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, DragRecyclerView parent, DragRecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0) outRect.top = space;
    }
}

