package wgz.com.antbaojie.util;

import android.os.AsyncTask;

import java.util.List;
import java.util.Map;

/**
 * Created by qwerr on 2016/5/19.
 */
public class InitListData extends AsyncTask {
    private List<Map<String ,Object>> mList;
    private InitData initData;
    private DataFinishListener dataFinishListener;


    public interface  DataFinishListener{
        void success(Object o);
        void faild();

    }

    public interface InitData{
        List<Map<String,Object>> initData();

    };
    public void setOnDataFinishListener(DataFinishListener dataFinishListener){
        this.dataFinishListener = dataFinishListener;

    }
    public void setInitDataListener(InitData initData){
       this.initData = initData;

    }
    @Override
    protected Object doInBackground(Object[] params) {
        mList = initData.initData();
        return mList;
    }

    @Override
    protected void onPostExecute(Object o) {
        List<Map<String ,Object>> result = (List<Map<String, Object>>) o;
        if (result.size()>0){
            dataFinishListener.success(result);

        }else {
            dataFinishListener.faild();
        }
    }
}
