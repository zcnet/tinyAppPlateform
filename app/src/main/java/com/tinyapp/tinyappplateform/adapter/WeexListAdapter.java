package com.tinyapp.tinyappplateform.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tinyapp.tinyappplateform.R;
import com.tinyapp.tinyappplateform.main.TouchLinearLayout;
import com.tinyapp.tinyappplateform.weexapps.InfoFlowItem;
import com.z.tinyapp.utils.common.FileUtil;
import com.z.tinyapp.utils.logs.sLog;
import com.sun.weexandroid_module.WxRvUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengfei on 2018/8/15.
 */

public class WeexListAdapter extends BaseAdapter {
    private List<InfoFlowItem> list;
    private LayoutInflater mInflater;
    private Context context;
    private Map<String,View> weexViews=new HashMap<>();
    private ListView listView;
    private int selectIdx = -1;
    public void setSelectItem(int index){

        String id = ""+index;
        String oid = "" + selectIdx;
        selectIdx = index;
        View ov = weexViews.get(oid);
        View v = weexViews.get(id);
        if(null != ov){
            ((View)ov.getParent().getParent()).setBackgroundResource(R.drawable.if_item_bg_normal);
        }
        if(null != v){
            ((View)v.getParent().getParent()).setBackgroundResource(R.drawable.if_item_bg);
        }

    }
    public WeexListAdapter(Context context,List<InfoFlowItem> list,ListView listView){
        mInflater = LayoutInflater.from(context);
        this.context=context;
        this.list=list;
        this.listView=listView;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        InfoFlowItem item=list.get(position);
        final String id= "" + position;//item.getAppInfoBean().getGuid() + "";
        ViewHolder holder = null;
        if(convertView==null){
            TouchLinearLayout weexLin=null;
            LinearLayout title = null;
            convertView=mInflater.inflate(R.layout.layout_infoflow_item,null);
            weexLin= (TouchLinearLayout) convertView.findViewById(R.id.ll_weex);
            title = (LinearLayout) convertView.findViewById(R.id.ll_title);
            title.setTag(new ViewHolder2(weexLin,
                    (ImageView) convertView.findViewById(R.id.iv_arrow_in),
                    (ImageView) convertView.findViewById(R.id.iv_arrow_dn)));
            weexLin.setMyOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()== MotionEvent.ACTION_DOWN){
                        hitInfoFlowItem(position);


                    }
                    return false;
                }
            });
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hitInfoFlowItem(position);
                    final ViewHolder2 vh1 = (ViewHolder2)v.getTag();
                    if (null != vh1.weexLin){
                        ViewGroup.LayoutParams lp = vh1.weexLin.getLayoutParams();
                        if (lp.height <= 300) {
                            lp.height = 500;
                            vh1.weexLin.setLayoutParams(lp);
                            vh1.ivIn.setVisibility(View.VISIBLE);
                            vh1.ivDn.setVisibility(View.GONE);
                        }
                        else {
                            lp.height = 217;
                            vh1.weexLin.setLayoutParams(lp);
                            vh1.ivIn.setVisibility(View.GONE);
                            vh1.ivDn.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
            holder = new ViewHolder(weexLin, title);
            convertView.setTag(holder);
            try {
                weexLin.removeAllViews();
                addView(weexLin,weexViews.get(id));
            }catch (Exception e){
                e.printStackTrace();
                sLog.i("zf","error:"+e.toString());
            }
        }else{
            try {
                holder= (ViewHolder) convertView.getTag();
                holder.weexLin.removeAllViews();
                addView(holder.weexLin,weexViews.get(id));
            }catch (Exception e){
                e.printStackTrace();
                sLog.i("zf","error1:"+e.toString());
            }
        }
        if(null==weexViews.get(id)){
            final String strPath=FileUtil.getAbsolutePath(context)+item.getPath();
            TextView tvTitle = (TextView) holder.title.findViewById(R.id.tv_title);
            if (null != tvTitle){
                tvTitle.setText(item.getFriendName());
            }
            WxRvUtils.getJsForAdapter(context, strPath, holder.weexLin,position, id, new WxRvUtils.CallBackWithView() {
                @Override
                public void onFailed() {
                    sLog.i("zf","id_onFailed:"+id+"   strPath:"+strPath);
                }
                @Override
                public void onSuccess(View view, LinearLayout viewGroup, int index,String info) {
                    sLog.i("zf","id_onSuccess:"+id);
                    weexViews.put(info,view);
                    if(index>=listView.getFirstVisiblePosition()&&index<=listView.getLastVisiblePosition()){
                        viewGroup.removeAllViews();
                        addView(viewGroup,weexViews.get(id));
                    }
                }
            });
        }
        if (selectIdx == position){
            convertView.findViewById(R.id.ll_container).setBackgroundResource(R.drawable.if_item_bg);
        } else {
            convertView.findViewById(R.id.ll_container).setBackgroundResource(R.drawable.if_item_bg_normal);
        }
        return convertView;
    }

    private void hitInfoFlowItem(int position) {
        InfoFlowItem ifi = list.get(position);
        if (ifi!=null && ifi.isAllowBigCard()) {
            sLog.i(null, "onTouch:" + position);
            if (position == selectIdx) {
                setSelectItem(position);//-1
            } else {
                setSelectItem(position);
            }
        }
    }

    public void addView(ViewGroup container,View view){
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(view);
    }



    static class ViewHolder{
        LinearLayout weexLin=null;
        LinearLayout title = null;
        ViewHolder(LinearLayout weexLin, LinearLayout title){
            this.weexLin = weexLin;
            this.title = title;
        }
    }
    static class ViewHolder2 {
        TouchLinearLayout weexLin=null;
        ImageView ivIn = null;
        ImageView ivDn = null;
        ViewHolder2(TouchLinearLayout weexLin, ImageView ivIn, ImageView ivDn){
            this.weexLin = weexLin;
            this.ivIn = ivIn;
            this.ivDn = ivDn;
        }
    }
}
