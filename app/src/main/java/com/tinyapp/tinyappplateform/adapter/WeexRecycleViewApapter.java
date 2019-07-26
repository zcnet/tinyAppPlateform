package com.tinyapp.tinyappplateform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tinyapp.tinyappplateform.R;
import com.tinyapp.tinyappplateform.main.TouchLinearLayout;
import com.tinyapp.tinyappplateform.weexapps.SmallCardItem;
import com.z.tinyapp.utils.common.FileUtil;
import com.z.tinyapp.utils.logs.sLog;
import com.sun.dragrv.DragRecyclerView;
import com.sun.dragrv.LinearLayoutManager;
import com.sun.dragrv.helper.ItemTouchHelper;
import com.sun.weexandroid_module.WxRvUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhengfei on 2018/8/30.
 */

public class WeexRecycleViewApapter extends DragRecyclerView.Adapter<WeexRecycleViewApapter.MainContentViewHolder>  {
    Context context;
    private LayoutInflater mInflater;
    private Map<String,View> weexViews=new HashMap<>();
    private List<SmallCardItem> list;
    LinearLayoutManager linearManager;
    /**
     * Item拖拽滑动帮助
     */
    private ItemTouchHelper itemTouchHelper;
    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper){
        this.itemTouchHelper=itemTouchHelper;

    }
    /**
     * Item点击监听
     */
    private OnItemClickListener mItemOnClickListener;

    private void setHeight(LinearLayout ll, int height){
        ViewGroup.LayoutParams lp = ll.getLayoutParams();
        lp.height = height;
        ll.setLayoutParams(lp);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mItemOnClickListener=onItemClickListener;
    }
    public WeexRecycleViewApapter(Context context, List<SmallCardItem> list, DragRecyclerView.LayoutManager layoutManager){
        mInflater = LayoutInflater.from(context);
        this.context=context;
        this.list=list;
        this.linearManager = (LinearLayoutManager) layoutManager;
    }
    @Override
    public MainContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainContentViewHolder(mInflater.inflate(R.layout.layout_weex_item,null));
    }

    @Override
    public void onBindViewHolder(final MainContentViewHolder holder, int position) {
        SmallCardItem s=list.get(position);
        final String id=s.app.id+"";
        //sLog.i("zf","onBindViewHolder:"+FileUtil.getAbsolutePath(context)+bean.appInfo.apppath);
        if(null==weexViews.get(id) && null != s){
            final String strPath= FileUtil.getAbsolutePath(context)+s.card.path;
            int height = Integer.parseInt(s.card.height);
            setHeight(holder.weexLin, height);
            if (null == s.card.touchstart || null == s.card.touchwidth){
                holder.setMyOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        holder.setAllowClick(false);
                        return false;
                    }
                });
            } else {
                final int touchstart = Integer.parseInt(s.card.touchstart);
                final int touchwidth = Integer.parseInt(s.card.touchwidth);
                holder.setMyOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int x = (int)(event.getX());
                        if (x > touchstart && x < touchstart+touchwidth){
                            holder.setAllowClick(true);
                            return true;
                        }
                        holder.setAllowClick(false);
                        return false;
                    }
                });
            }


            WxRvUtils.getJsForAdapter(context, strPath, holder.weexLin,position, id, new WxRvUtils.CallBackWithView() {
                @Override
                public void onFailed() {
                    sLog.i("zf","id_onFailed:"+id+"   strPath:"+strPath);
                    sLog.printCallStatck();
                }
                @Override
                public void onSuccess(View view, final LinearLayout viewGroup, int index, String info) {
                    sLog.i("zf","id_onSuccess:"+id);
                    weexViews.put(info,view);
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if(index>=firstItemPosition&&index<=lastItemPosition){
                        viewGroup.removeAllViews();
                        addView(viewGroup,weexViews.get(id));
                    }
                }
            });
        }else{
            addView(holder.weexLin,weexViews.get(id));
        }
    }

    @Override
    public int getItemCount() {
        if(list!=null) {
            sLog.i(null, "WeexRecycleViewApapter getItemCount:"+list.size());
            return list.size();
        }
        else{
            return 0;
        }
    }

    class MainContentViewHolder extends DragRecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
        TouchLinearLayout weexLin;
        boolean allowClick = false;
        public MainContentViewHolder(View itemView) {
            super(itemView);
            weexLin= (TouchLinearLayout) itemView.findViewById(R.id.ll_weex);
            weexLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (allowClick)
                        mItemOnClickListener.onItemClick(v,getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(v==itemView){
                itemTouchHelper.startDrag(this);
            }
            return false;
        }

        public  void setMyOnTouchListener(View.OnTouchListener listener){
            if (null != weexLin){
                weexLin.setMyOnTouchListener(listener);
            }
        }

        public void setAllowClick(boolean allowClick) {
            this.allowClick = allowClick;
        }
    }

    public void addView(ViewGroup container,View view){
        container.removeAllViews();
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(view);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
