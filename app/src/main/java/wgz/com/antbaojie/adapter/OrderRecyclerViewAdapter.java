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
public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.Myviewholder> {
    private List<Map<String ,Object>> data;
    private Context context;
    private RycViewOnItemClickListener onItemClickListener;
    public OrderRecyclerViewAdapter(List<Map<String, Object>> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public OrderRecyclerViewAdapter.Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Myviewholder holder = new Myviewholder(LayoutInflater.from(context).inflate(R.layout.order_item,null));

        return holder;
    }

    @Override
    public void onBindViewHolder(final OrderRecyclerViewAdapter.Myviewholder holder, int position) {
        holder.name.setText(data.get(position).get("customName").toString());
        holder.address.setText(data.get(position).get("customAddress").toString());
        holder.date.setText(data.get(position).get("date").toString());
        holder.state.setText(data.get(position).get("order_state").toString());

        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView,pos);
                }
            });

        }
    }

    public void setOnItemClickListener(RycViewOnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder {
        private TextView name,date,state,address;

        public Myviewholder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.id_orderitem_name);
            date = (TextView) itemView.findViewById(R.id.id_orderitem_time);
            state = (TextView) itemView.findViewById(R.id.id_orderitem_state);
            address = (TextView) itemView.findViewById(R.id.id_orderitem_address);
        }
    }
}
