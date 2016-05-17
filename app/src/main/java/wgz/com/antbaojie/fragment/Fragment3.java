package wgz.com.antbaojie.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import wgz.com.antbaojie.R;
import wgz.com.antbaojie.adapter.MyFragmentPagerAdapter;
import wgz.com.antbaojie.view.CustomViewPager;

import java.util.ArrayList;


/**
 * Created by qwerr on 2016/5/9.
 */
public class Fragment3 extends Fragment{



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment3,null);
        initview(view);
        return  view;
    }

    private void initview(View view) {


    }
}
