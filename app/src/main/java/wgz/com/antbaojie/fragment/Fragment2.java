package wgz.com.antbaojie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import wgz.com.antbaojie.OrderActivity;
import wgz.com.antbaojie.R;
import wgz.com.antbaojie.adapter.MsgRecyclerViewAdapter;
import wgz.com.antbaojie.adapter.OrderRecyclerViewAdapter;
import wgz.com.antbaojie.adapter.RycViewOnItemClickListener;
import wgz.com.antbaojie.util.*;


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
        initData();

        mrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshList refreshList = new RefreshList(mrefresh, root, getActivity());
                refreshList.setRefreshVoid(new RefreshList.RefreshListener() {
                    @Override
                    public void Refresh() {
                        initData();

                    }
                });
                refreshList.execute();
            }
        });

    }

    private void initData() {
        InitListData initListData = new InitListData();
        initListData.setInitDataListener(new InitListData.InitData() {
            @Override
            public List<Map<String, Object>> initData() {
                List<Map<String ,Object>> list;
                String jsonstr = httpUtil.getStr(new PathMaker().getQueryFinishedPath(),"UTF_8");
                Log.i("listdata",jsonstr);
                FastJsonTools fastJsonTools = new FastJsonTools();
                list = fastJsonTools.getlistmap(jsonstr);
                Log.i("listdata",list.toString());
                return list;
            }
        });
        initListData.execute();
        initListData.setOnDataFinishListener(new InitListData.DataFinishListener() {
            @Override
            public void success(Object o) {
                adapter =new OrderRecyclerViewAdapter((List<Map<String, Object>>) o,getActivity());
                adapter.setOnItemClickListener(new RycViewOnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startActivity(new Intent(getActivity(), OrderActivity.class));
                        //Toast.makeText(getActivity(), "点击了：" + position + "号", Toast.LENGTH_SHORT).show();
                    }
                });
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void faild() {
                Snackbar.make(root,"没有业务！",Snackbar.LENGTH_SHORT).show();
            }
        });

    }


}
