package wgz.com.antbaojie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import wgz.com.antbaojie.R;


import java.util.List;
import java.util.Map;

/**
 * Created by qwerr on 2016/5/9.
 */
public class MsgRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Map<String ,Object>> data;
    private Context context;
    private RycViewOnItemClickListener onItemClickListener;
    public MsgRecyclerViewAdapter(List<Map<String, Object>> data, Context context) {
        this.data = data;
        this.context = context;
    }


    public void setOnItemClickListener(RycViewOnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Myviewholder holder = new Myviewholder(LayoutInflater.from(context).inflate(R.layout.msg_item,null));

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);

                }
            });

        }
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    private static class Myviewholder extends RecyclerView.ViewHolder {
        private TextView name,date,state,address;

        public Myviewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.id_msgitem_name);
            date = (TextView) itemView.findViewById(R.id.id_msgitem_time);
            state = (TextView) itemView.findViewById(R.id.id_msgitem_state);
            address = (TextView) itemView.findViewById(R.id.id_msgitem_address);
        }
    }
}
