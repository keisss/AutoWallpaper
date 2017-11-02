package cn.keiss.autowallpaper.baselib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @author hekai
 * @date 2017/10/12
 * Activity的基类
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected  P mPresenter;





    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
        if (null != mPresenter){
            mPresenter.subscribe();
        }else {
            throw new NullPointerException("null presenter created");
        }

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.unSubscribe();
        }
    }

    /**
     * 参数初始化
     */
    protected abstract void initVariables();


    /**
     * view初始化
     * @param savedInstanceState 恢复数据
     */
    protected abstract void initViews(Bundle savedInstanceState);


    /**
     * 加载数据
     */
    protected abstract void loadData();


    protected abstract P onCreatePresenter();
}
