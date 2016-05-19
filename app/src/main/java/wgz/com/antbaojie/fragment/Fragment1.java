package wgz.com.antbaojie.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
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
import wgz.com.antbaojie.MainActivity;
import wgz.com.antbaojie.OrderActivity;
import wgz.com.antbaojie.R;
import wgz.com.antbaojie.adapter.MsgRecyclerViewAdapter;
import wgz.com.antbaojie.adapter.RycViewOnItemClickListener;
import wgz.com.antbaojie.util.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 消息页面
 * Created by qwerr on 2016/5/9.
 */
public class Fragment1 extends Fragment {
    private List<Map<String,Object>> mList;
    private RecyclerView recyclerView;
    private MsgRecyclerViewAdapter adapter;
    private SwipeRefreshLayout mfresh;
    private LinearLayout root;
    private MsgReceiver receiver;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,null);
        initview(view);

        return view;
    }

    private void initview(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.id_fragm1_rycview);
        mfresh = (SwipeRefreshLayout) view.findViewById(R.id.id_frag1_refresh);
        root = (LinearLayout) view.findViewById(R.id.id_fragm1_rootView);
        mfresh.setColorSchemeResources(R.color.colorPrimary, R.color.red2);
        mfresh.setSize(SwipeRefreshLayout.LARGE);
        mfresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshList refreshList = new RefreshList(mfresh, root, getActivity());
                refreshList.setRefreshVoid(new RefreshList.RefreshListener() {
                    @Override
                    public void Refresh() {
                     initData();

                    }
                });
                refreshList.execute();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();


        //注册广播
        receiver = new MsgReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("service.MsgService");
        getActivity().registerReceiver(receiver, filter);
        Log.i("msg", "注册receiver成功！");



    }

    private void initData() {
        InitListData initListData = new InitListData();
        initListData.setInitDataListener(new InitListData.InitData() {
            @Override
            public List<Map<String, Object>> initData() {
                List<Map<String ,Object>> list;
                String jsonstr = httpUtil.getStr(new PathMaker().getQueryIngPath(),"UTF_8");
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
                adapter =new MsgRecyclerViewAdapter((List<Map<String, Object>>) o,getActivity());
                adapter.setOnItemClickListener(new RycViewOnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startActivity(new Intent(getActivity(), OrderActivity.class));
                        //Toast.makeText(getActivity(), "点击了：" + position + "号", Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void faild() {
                Snackbar.make(root,"没有业务！",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroy() {

        try {
            getActivity().unregisterReceiver(receiver);
            Log.i("msg", "receiver已经取消注册！");
        } catch (Exception e) {
            Log.i("msg", "没有注册receiver");
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private class MsgReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle  = intent.getExtras();
            String msg = bundle.getString("msg");
            if (msg.equals("newmsg")){

              recyclerView.setAdapter(adapter);
                NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0,
                        new Intent(context, MainActivity.class), 0);
                Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                // 通过Notification.Builder来创建通知，注意API Level
                // API16之后才支持
                Notification notify3 = null; // 需要注意build()是在APIlevel16及之后增加的，API11可以使用getNotificatin()来替代
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    notify3 = new Notification.Builder(context)
                            /*.setSmallIcon(R.drawable.mabu)
                            .setTicker("蚂蚁保洁：" + "你有新的业务，请查看！")
                            .setContentTitle("蚂蚁保洁")
                            .setContentText("你有新的业务，请查看！")
                            .setContentIntent(pendingIntent3).*/.setSound(ringUri).build();
                }

                notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示

                //Snackbar.make(root," 收到新消息！", Snackbar.LENGTH_SHORT).show();

            }
        }
    }

}
