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
import android.util.Log;
import wgz.com.antbaojie.MainActivity;
import wgz.com.antbaojie.R;

import java.util.List;

/**
 * 请求消息服务
 * Created by qwerr on 2016/5/13.
 */
public class MsgService extends Service{
    private int MsgLength;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000*10*1);
                        //当前消息总数
                        MsgLength =9;
                        int mServiceMsgLength = getServiceMsgSize();
                        Log.i("msg",mServiceMsgLength+"");
                        if (mServiceMsgLength!=MsgLength&&mServiceMsgLength>MsgLength){
                            if (isActivityRunning(getApplicationContext())){
                                Intent intent = new Intent();
                                intent.putExtra("msg","newmsg");
                                intent.setAction("com.ant.wgz.service.MsgService");
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
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();


    }
//返回一个随机数
    private int getServiceMsgSize() {
        int size = (int) (Math.random()*10+1);//返回一个1-10的随机数

        return size;
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
