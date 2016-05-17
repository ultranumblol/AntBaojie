package wgz.com.antbaojie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import wgz.com.antbaojie.OrderActivity;
import wgz.com.antbaojie.R;
import wgz.com.antbaojie.adapter.OrderRecyclerViewAdapter;
import wgz.com.antbaojie.adapter.RycViewOnItemClickListener;
import wgz.com.antbaojie.util.RefreshList;


import java.util.List;
import java.util.Map;

/**
 * Created by qwerr on 2016/5/9.
 */
public class Fragment2 extends Fragment {
    private List<Map<String,Object>> mList;
    private RecyclerView mRecyclerView;
    private OrderRecyclerViewAdapter adapter;
    private SwipeRefreshLayout mrefresh;
    private LinearLayout root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mrefresh = (SwipeRefreshLayout) view.findViewById(R.id.id_fragm2_refresh);
        mrefresh.setColorSchemeResources(R.color.colorPrimary,R.color.red2);
        mrefresh.setSize(SwipeRefreshLayout.LARGE);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_fragm2_rycview);
        root = (LinearLayout) view.findViewById(R.id.id_fragm2_rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshList refreshList = new RefreshList(mrefresh,root,getActivity());
                refreshList.execute();
            }
        });
        adapter = new OrderRecyclerViewAdapter(mList,getActivity());
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RycViewOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), OrderActivity.class));
                //Toast.makeText(getActivity(), "点击了：" + position + "号", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
