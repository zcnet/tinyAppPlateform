package com.aoshuotec.voiceassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aoshuotec.voiceassistant.R;
import com.contacts.PinYinModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2018/9/20
 */

public class MInfoModuleAdapter extends RecyclerView.Adapter<MInfoModuleAdapter.MInfoModuleBean>{

    public interface IRvItemClickListener{
        void onItemClick(int position,PinYinModule module);
    }

    private List<PinYinModule> mList= new ArrayList();
    private Context mCtx;
    private IRvItemClickListener mListener;

    public MInfoModuleAdapter(List<PinYinModule> list, Context ctx,IRvItemClickListener listener ) {
        this.mList.clear();
        if(list==null||list.size()==0){

        }else{
            mList.addAll(list);
        }
        this.mCtx = ctx;
        mListener = listener;
    }

    @Override
    public MInfoModuleBean onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MInfoModuleBean(LayoutInflater.from(mCtx).inflate(R.layout.assistant_module_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MInfoModuleBean holder, int position) {
        View view = holder.itemView;
        StringBuilder builder = new StringBuilder();
        builder.append(holder.getAdapterPosition()).append(":").append(mList.get(holder.getAdapterPosition()).getName());
        ((TextView) view.findViewById(R.id.as_module_list_item_tv)).setText(builder.toString());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MInfoModuleBean extends RecyclerView.ViewHolder{

        public MInfoModuleBean(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(getAdapterPosition(),mList.get(getAdapterPosition()));
                }
            });
        }
    }
}
