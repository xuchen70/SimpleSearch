package com.mark.ss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Administrator on 2017/1/6.
 */

public class MyAdapter extends BaseAdapter {
    private List<Data> datas;
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public Data getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder ;
        if (view == null){
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_list,null);
            holder.context = (TextView) view.findViewById(R.id.text_context);
            holder.time = (TextView) view.findViewById(R.id.text_time);
            view.setTag(holder);
        }
        holder = (Holder) view.getTag();
        Data data = getItem(i);
        holder.time.setText(data.getTime());
        holder.context.setText(data.getContext());

        if (i == 0){
            holder.time.setTextColor(context.getResources().getColor(R.color.newStatus));
            holder.context.setTextColor(context.getResources().getColor(R.color.newStatus));
        }else {
            holder.time.setTextColor(context.getResources().getColor(R.color.lightBlack));
            holder.context.setTextColor(context.getResources().getColor(R.color.lightBlack));
        }

        return view;
    }

    private class Holder{
        private TextView time;
        private TextView context;
    }
}
