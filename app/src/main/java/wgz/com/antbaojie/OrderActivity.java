package wgz.com.antbaojie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;
import wgz.com.antbaojie.util.FastJsonTools;
import wgz.com.antbaojie.util.InitListData;
import wgz.com.antbaojie.util.PathMaker;
import wgz.com.antbaojie.util.httpUtil;

import java.util.List;
import java.util.Map;


public class OrderActivity extends AppCompatActivity {
    private TextView orderAddress,customName,order_time,order_id,order_state,order_type,order_money,customPhone,date,workerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("订单详情");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initdatas();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initdatas() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Log.i("msg",bundle.size()+"");

        orderAddress.setText(bundle.getString("customAddress"));
        customName.setText(bundle.getString("customName"));
        order_id.setText(bundle.getString("order_id"));
        order_state.setText(bundle.getString("order_state"));
        order_type.setText(bundle.getString("order_type"));
        order_time.setText(bundle.getString("order_time"));
        order_money.setText(bundle.getString("order_money"));
        customPhone.setText(bundle.getString("customPhone"));
        date.setText(bundle.getString("date"));
        InitListData initListData = new InitListData();
        initListData.setInitDataListener(new InitListData.InitData() {
            @Override
            public List<Map<String, Object>> initData() {
               List<Map<String,Object>> list = null;
               list = FastJsonTools.getlistmap(httpUtil.getStr(new PathMaker().getQueryWorkerPath(),"UTF-8"));
                return list;
            }
        });
        initListData.execute();
        initListData.setOnDataFinishListener(new InitListData.DataFinishListener() {
            @Override
            public void success(Object o) {
                List<Map<String,Object>> list = (List<Map<String, Object>>) o;
                String workname = "";
                int j = list.size();
                for (int i = 0; i <j; i++){
                    workname+= list.get(i).get("worker_name").toString()+" ";

                }
                workerName.setText(workname);
            }

            @Override
            public void faild() {
                workerName.setText("未找到");
            }
        });

    }

    private void initView() {
        orderAddress = (TextView) findViewById(R.id.id_order_address);
        customName = (TextView) findViewById(R.id.id_order_customname);
        order_id = (TextView) findViewById(R.id.id_order_orderID);
        order_state = (TextView) findViewById(R.id.id_order_orderState);
        order_type = (TextView) findViewById(R.id.id_id_order_orderType);
        order_time = (TextView) findViewById(R.id.id_order_orderTime);
        order_money = (TextView) findViewById(R.id.id_id_order_orderMoney);
        customPhone = (TextView) findViewById(R.id.id_order_customPhone);
        date = (TextView) findViewById(R.id.id_order_date);
        workerName = (TextView) findViewById(R.id.id_order_workerName);

        orderAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(OrderActivity.this,LoginActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        if (item.getItemId()==R.id.action_settings){


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
