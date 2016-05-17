package wgz.com.antbaojie;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import wgz.com.antbaojie.adapter.MyFragmentPagerAdapter;
import wgz.com.antbaojie.fragment.Fragment1;
import wgz.com.antbaojie.fragment.Fragment2;
import wgz.com.antbaojie.fragment.Fragment3;
import wgz.com.antbaojie.view.CustomViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter pageAdapter;
    private CustomViewPager viewPager;
    private ImageView bar1,bar2,bar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOverflowMenu();
        init();
    }

    private void init() {
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        bar1 = (ImageView) findViewById(R.id.bar1);
        bar2 = (ImageView) findViewById(R.id.bar2);
        bar3 = (ImageView) findViewById(R.id.bar4);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        pageAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(0);
        bar1.setImageResource(R.drawable.foot_letter_pressed);
        setTitle("消息");
    }
    public void BarClick(View view){
        bar1.setImageResource(R.drawable.foot_letter_normal);
        bar2.setImageResource(R.drawable.foot_position_normal2);
        bar3.setImageResource(R.drawable.foot_myself_normal);


        int id = view.getId();
        switch (id){
            case R.id.bar1:
                viewPager.setCurrentItem(0);
                bar1.setImageResource(R.drawable.foot_letter_pressed);
                setTitle("消息");

                break;
            case R.id.bar2:
                viewPager.setCurrentItem(1);
                bar2.setImageResource(R.drawable.foot_position_pressed2);
                setTitle("订单");

                break;
            case R.id.bar4:
                viewPager.setCurrentItem(2);
                bar3.setImageResource(R.drawable.foot_myself_pressed);
                setTitle("个人");

                break;
            default:
                break;

        }


    }
    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("确认退出").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }
}
