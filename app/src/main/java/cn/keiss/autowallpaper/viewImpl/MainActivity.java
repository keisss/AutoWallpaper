package cn.keiss.autowallpaper.viewImpl;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import java.util.List;
import cn.keiss.autowallpaper.R;
import cn.keiss.autowallpaper.adapter.recyclerView.FolderGridViewAdapter;
import cn.keiss.autowallpaper.baseLib.BaseActivity;
import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.presenter.MainPresenter;
import cn.keiss.autowallpaper.service.SwitchWallpaperService;
import cn.keiss.autowallpaper.viewInterface.MainViewListener;

public class MainActivity extends BaseActivity implements View.OnClickListener,MainViewListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFabAddPic;
    private ImageButton mIbnSetting;

    private FolderGridViewAdapter adapter;

    private MainPresenter mainPresenter;

    //是否已经长按进入选择状态
    private boolean isLongClickedStatus = false;
    private SparseBooleanArray selectDeleteItems ;

    @Override
    protected void initVariables() {
        selectDeleteItems = new SparseBooleanArray();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mToolbar =  findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mFabAddPic = findViewById(R.id.fab_add_pic);
        mIbnSetting =  findViewById(R.id.ibn_setting);
        mIbnSetting.setOnClickListener(this);
        mFabAddPic.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        mainPresenter = new MainPresenter(this);
        mainPresenter.setFolderViewItemData();

        setRecyclerView();

    }


    /*初始化RecyclerView*/
    private void setRecyclerView(){
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //短按

                if (isLongClickedStatus){
                    Log.e("dwd","dw");
                    changeItemDeleteStatus(position);
                }else {

                }

            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                if (!isLongClickedStatus){
                    isLongClickedStatus = true;
                    changeButtonsDeleteSrc();
                    changeItemDeleteStatus(position);

                }
                //长按
                return true;
            }
        });
    }



    /**
     * 启动动态壁纸预览并进行选择
     */
    private void startWallpaperPreview(){
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);

        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(MainActivity.this, SwitchWallpaperService.class));

        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        MainActivity.this.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent ;
        switch (view.getId()){
            case R.id.ibn_setting:
                //跳转到预览页面设置壁纸
                intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.fab_add_pic:
                //跳转到选择图片或者文件夹页面
//                intent = new Intent(MainActivity.this,AddPicActivity.class);
//                startActivity(intent);
                Matisse.from(MainActivity.this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(9)
                        .gridExpectedSize(122)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(12);

                break;
        }
    }

    @Override
    public void setFolderDataList(List<FolderViewItem> folderDataList) {
        adapter = new FolderGridViewAdapter(R.layout.item_sub_folder_view,folderDataList);
    }

    @Override
    public void updateFolderDataList() {

    }

    public void showDeleteTag(int position) {
        adapter.getItem(position).setTagDelete(true);
        adapter.notifyItemChanged(position);
    }

    public void hideDeleteTag(int position) {
        adapter.getItem(position).setTagDelete(false);
        adapter.notifyItemChanged(position);
    }

    private void changeItemDeleteStatus(int position){
        if (selectDeleteItems.get(position,false)) {
            selectDeleteItems.put(position,false);
            hideDeleteTag(position);

        }else{
            selectDeleteItems.put(position,true);
            showDeleteTag(position);
        }
    }


    private void changeButtonsDeleteSrc(){
        if (isLongClickedStatus){
            mIbnSetting.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_delete_forever_black_24dp,null));
            mFabAddPic.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_close_black_24dp,null));
        }else {
            mIbnSetting.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_settings_black_24dp,null));
            mFabAddPic.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_add_black_24dp,null));
        }
    }
}
