package plateform.tinyapp.com.tinyappplateform.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tinyapp.tinyappplateform.R;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.utils.common.FileUtil;
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
    private List<AppListBean> list;
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mItemOnClickListener=onItemClickListener;
    }
    public WeexRecycleViewApapter(Context context, List<AppListBean> list, DragRecyclerView.LayoutManager layoutManager){
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
    public void onBindViewHolder(MainContentViewHolder holder, int position) {
        AppListBean bean=list.get(position);
        final String id=bean.id+"";
        Log.i("zf",FileUtil.getAbsolutePath(context)+bean.maincardfile);
        if(null==weexViews.get(id)){
            final String strPath= FileUtil.getAbsolutePath(context)+bean.maincardfile;
            WxRvUtils.getJsForAdapter(context, strPath, holder.weexLin,position, id, new WxRvUtils.CallBackWithView() {
                @Override
                public void onFailed() {
                    Log.i("zf","id_onFailed:"+id+"   strPath:"+strPath);
                }
                @Override
                public void onSuccess(View view, LinearLayout viewGroup, int index, String info) {
                    Log.i("zf","id_onSuccess:"+id);
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
        if(list!=null)
            return list.size();
        else{
            return 0;
        }
    }

    class MainContentViewHolder extends DragRecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
        LinearLayout weexLin;
        public MainContentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemOnClickListener.onItemClick(v,getAdapterPosition());
                }
            });
            weexLin= (LinearLayout) itemView.findViewById(R.id.ll_weex);
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
    }

    public void addView(ViewGroup container, View view){
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
