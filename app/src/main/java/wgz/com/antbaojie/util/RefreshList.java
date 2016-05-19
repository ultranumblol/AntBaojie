package wgz.com.antbaojie.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import wgz.com.antbaojie.MainActivity;


/**
 * Created by qwerr on 2016/5/11.
 */
public class RefreshList extends AsyncTask {
    private SwipeRefreshLayout mrefresh;
    private View root;
    private Context context;
    private RefreshListener mRefreshListener;

    public interface  RefreshListener{
        void Refresh();

    }

    /**
     *
     * @param mrefresh
     * @param root
     * @param context
     */
    public RefreshList(SwipeRefreshLayout mrefresh, View root, Context context) {
        this.mrefresh = mrefresh;
        this.root = root;
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            Thread.sleep(1000);
            if (mRefreshListener!=null){

                mRefreshListener.Refresh();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        mrefresh.setRefreshing(false);
        RefreshRadio();
        Snackbar.make(root, "刷新成功！", Snackbar.LENGTH_SHORT).show();
        super.onPostExecute(o);
    }
    public void RefreshRadio(){
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);
        Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // 通过Notification.Builder来创建通知，注意API Level
        // API16之后才支持
        Notification notify3 = null; // 需要注意build()是在API
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notify3 = new Notification.Builder(context)
                   /* .setSmallIcon(R.drawable.mabu)
                    .setTicker("TickerText:" + "您有新短消息，请注意查收！")
                    .setContentTitle("Notification Title")
                    .setContentText("This is the notification message")*/
                    /*.setContentIntent(pendingIntent3)*/.setSound(ringUri).build();
        }
        // level16及之后增加的，API11可以使用getNotificatin()来替代
        notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
        manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示


    }
    public  void setRefreshVoid(RefreshListener refreshListener){
        this.mRefreshListener = refreshListener;

    }
}
