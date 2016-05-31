package wgz.com.antbaojie.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import wgz.com.antbaojie.MainActivity;
import wgz.com.antbaojie.OrderActivity;
import wgz.com.antbaojie.R;
import wgz.com.antbaojie.adapter.MsgRecyclerViewAdapter;
import wgz.com.antbaojie.adapter.RycViewOnItemClickListener;
import wgz.com.antbaojie.util.FastJsonTools;
import wgz.com.antbaojie.util.InitListData;
import wgz.com.antbaojie.util.PathMaker;
import wgz.com.antbaojie.util.httpUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 请求消息服务
 * Created by qwerr on 2016/5/13.
 */
public class MsgService extends Service{
    private int MsgLength;
    private int mServiceMsgLength;
    private int latestID;
    private int BeforlatestID;
    private boolean isfirstIn=true;
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BeforlatestID = latestID;
                while (true){
                    try {
                        Thread.sleep(1000*10*1);
                        //当前消息总数

                        getServiceMsgID();
                        Log.i("msg","latestID: "+latestID);
                        Log.i("msg","BeforlatestID: "+BeforlatestID);
                        if (latestID>BeforlatestID){
                            BeforlatestID = latestID;
                            if (isfirstIn){
                                isfirstIn = false;

                            }else{
                                if (isActivityRunning(getApplicationContext())){
                                    Intent intent = new Intent();
                                    intent.putExtra("msg","newmsg");
                                    intent.setAction("service.MsgService");
                                    sendBroadcast(intent);

                                }else {
                                    NotificationManager manager = (NotificationManager)getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
                                    PendingIntent pendingIntent3 = PendingIntent.getActivity(getApplication(), 0,
                                            new Intent(getApplication(), MainActivity.class), 0);
                                    Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                    // 通过Notification.Builder来创建通知，注意API Level
                                    // API16之后才支持
                                    Notification notify3 = null; // 需要注意build()是在API
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                        notify3 = new Notification.Builder(getApplication())
                                                .setSmallIcon(R.drawable.mabu)
                                                .setTicker("蚂蚁保洁：" + "你有新的业务，请查看！")
                                                .setContentTitle("蚂蚁保洁")
                                                .setContentText("你有新的业务，请查看！")
                                                .setContentIntent(pendingIntent3).setSound(ringUri).build();
                                    }
                                    // level16及之后增加的，API11可以使用getNotificatin()来替代
                                    notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                                    manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
                                }

                            }


                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();


    }
//返回列表ID
    private int getServiceMsgID() {
       /* int size = (int) (Math.random()*10+1);//返回一个1-10的随机数

        return size;*/

        InitListData initListData = new InitListData();
        initListData.setInitDataListener(new InitListData.InitData() {
            @Override
            public List<Map<String, Object>> initData() {
                List<Map<String ,Object>> list;
                String jsonstr = httpUtil.getStr(new PathMaker().getQueryIngPath(),"UTF_8");
                //Log.i("listdata",jsonstr);
                FastJsonTools fastJsonTools = new FastJsonTools();
                list = fastJsonTools.getlistmap(jsonstr);
                //Log.i("listdata",list.toString());
                return list;
            }
        });
        initListData.execute();
        initListData.setOnDataFinishListener(new InitListData.DataFinishListener() {
            @Override
            public void success(Object o) {
                List<Map<String ,Object>> result = (List<Map<String, Object>>) o;
                mServiceMsgLength = result.size();
                latestID = (int) result.get(mServiceMsgLength-1).get("order_id");
                //Log.i("msg","latestID:"+latestID);
            }

            @Override
            public void faild() {
                mServiceMsgLength = 0;
            }
        });

        return latestID;
    }
    //判断一个activity是否在运行
    public  static  boolean isActivityRunning(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = activityManager.getRunningTasks(1);
        if (info!=null&&info.size()>0){
            ComponentName component = info.get(0).topActivity;
            if (MainActivity.class.getName().equals(component.getClassName())){
                return true;
            }
        }

        return false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
