package plateform.tinyapp.com.tinyappplateform.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tinyapp.tinyappplateform.R;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.utils.common.FileUtil;
import com.sun.weexandroid_module.WxRvUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengfei on 2018/8/15.
 */

public class WeexListAdapter extends BaseAdapter {
    private List<AppListBean> list;
    private LayoutInflater mInflater;
    private Context context;
    private Map<String,View> weexViews=new HashMap<>();
    private ListView listView;
    public WeexListAdapter(Context context, List<AppListBean> list, ListView listView){
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
        AppListBean bean=list.get(position);
        final String id=bean.id+"";
        LinearLayout weexLin=null;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.layout_weex_item,null);
            weexLin= (LinearLayout) convertView.findViewById(R.id.ll_weex);
            convertView.setTag(weexLin);
            try {
                weexLin.removeAllViews();
                addView(weexLin,weexViews.get(id));
            }catch (Exception e){
                e.printStackTrace();
                Log.i("zf","error:"+e.toString());
            }
        }else{
            try {
                weexLin= (LinearLayout) convertView.getTag();
                weexLin.removeAllViews();
                addView(weexLin,weexViews.get(id));
            }catch (Exception e){
                e.printStackTrace();
                Log.i("zf","error1:"+e.toString());
            }
        }
        if(null==weexViews.get(id)){
            final String strPath=FileUtil.getAbsolutePath(context)+bean.maincardfile;
            WxRvUtils.getJsForAdapter(context, strPath, weexLin,position, id, new WxRvUtils.CallBackWithView() {
                @Override
                public void onFailed() {
                    Log.i("zf","id_onFailed:"+id+"   strPath:"+strPath);
                }
                @Override
                public void onSuccess(View view, LinearLayout viewGroup, int index, String info) {
                    Log.i("zf","id_onSuccess:"+id);
                    weexViews.put(info,view);
                    if(index>=listView.getFirstVisiblePosition()&&index<=listView.getLastVisiblePosition()){
                        viewGroup.removeAllViews();
                        addView(viewGroup,weexViews.get(id));
                    }
                }
            });
        }
        return convertView;
    }
    public void addView(ViewGroup container, View view){
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        container.addView(view);
    }
}
