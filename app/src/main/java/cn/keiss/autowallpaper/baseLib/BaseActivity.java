package cn.keiss.autowallpaper.baseLib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hekai on 2017/10/12.
 * Activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    //参数初始化
    protected abstract void initVariables();
    //view初始化
    protected abstract void initViews(Bundle savedInstanceState);

    //加载数据
    protected abstract void loadData();

}
