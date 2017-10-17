package cn.keiss.autowallpaper.viewImpl;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.codekidlabs.storagechooser.StorageChooser;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import java.util.List;
import cn.keiss.autowallpaper.R;
import cn.keiss.autowallpaper.adapter.recyclerView.FolderGridViewAdapter;
import cn.keiss.autowallpaper.baseLib.BaseActivity;
import cn.keiss.autowallpaper.baseLib.ToastUtil;
import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.presenter.MainPresenter;
import cn.keiss.autowallpaper.service.SwitchWallpaperService;
import cn.keiss.autowallpaper.viewInterface.MainViewListener;
import cn.keiss.menufab.listener.OnFloatActionButtonClickListener;
import cn.keiss.menufab.listener.OnMenuItemClickListener;
import cn.keiss.menufab.view.MenuFloatingActionButton;
import cn.keiss.menufab.view.MenuView;

public class MainActivity extends BaseActivity implements View.OnClickListener,MainViewListener {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private MenuFloatingActionButton mFabAddPic;
    private ImageButton mIbnSetting;

    private FolderGridViewAdapter adapter;

    private MainPresenter mainPresenter;

    //fab菜单是否打开的标记
    private boolean isFabMenuOpen = false;
    //是否已经长按进入选择状态
    private boolean isLongClickedStatus = false;
    private SparseBooleanArray selectDeleteItems;



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
        //向presenter请求加载文件夹
        mainPresenter.prepareForDefaultFolder();
        mainPresenter.setFolderViewItemData();
        setRecyclerView();
        setMenuFab();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibn_setting:
                clickSettingBtn();
                break;
        }
    }


    /*初始化RecyclerView*/
    private void setRecyclerView(){
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter.bindToRecyclerView(mRecyclerView);
        adapter.setEmptyView(R.layout.recycler_view_empty_view);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isLongClickedStatus){
                    //如果进入多选状态
                    if (position != 0){
                        changeItemDeleteStatus(position);
                    }else {
                        ToastUtil.showToast(MainActivity.this,"不能删除默认文件夹");
                    }
                }else {
                    //不在多选状态短按
                }

            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                if (!isLongClickedStatus){

                    if (position != 0){
                        isLongClickedStatus = true;
                        changeButtonsDeleteSrc();
                        changeItemDeleteStatus(position);
                    }else {
                        ToastUtil.showToast(MainActivity.this,"不能删除默认文件夹");
                    }

                }
                //长按
                return true;
            }
        });
    }


    /*初始化fabMenu*/
    private void setMenuFab(){
        mFabAddPic.setOnFabClickListener(new OnFloatActionButtonClickListener() {
            @Override
            public void onClick() {
                if (isLongClickedStatus){
                    isLongClickedStatus = false;
                    hideAllDeleteTag();
                    changeButtonsDeleteSrc();

                }else {
                    isFabMenuOpen = !isFabMenuOpen;
                }

            }
        });
        mFabAddPic.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onClick(MenuView menuView, int i) {
                switch (i){
                    case 0:
                        //folder
                       selectFolder();
                        break;
                    case 1:
                        //pic
                        selectPic();
                        break;
                }
            }
        });
    }


    /*点击设置按钮*/
    private void clickSettingBtn(){
        if (isLongClickedStatus){
            execDelete();
        }else {
            //跳转到预览页面设置壁纸
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        }
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
    public void setFolderDataList(List<FolderViewItem> folderDataList) {
        adapter = new FolderGridViewAdapter(R.layout.item_sub_folder_view,folderDataList);
    }

    @Override
    public void onBackPressed() {
        if (isFabMenuOpen){
           //TODO 关闭菜单
            Log.e("fab","open");
        }else{
            super.onBackPressed();
        }

    }

    //选择文件夹
    private void selectFolder(){
        StorageChooser chooser = new StorageChooser.Builder()
                .withActivity(MainActivity.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(false)
                .allowCustomPath(true)
                .setType(StorageChooser.DIRECTORY_CHOOSER)
                .build();
        chooser.show();
        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String s) {
                onSelectedFolder(s);
            }
        });
    }
    //选择图片
    private void selectPic(){
        Matisse.from(MainActivity.this)
                .choose(MimeType.ofAll(), false) // 选择 mime 的类型
                .countable(true)
                .maxSelectable(9) // 图片选择的最多数量
                .gridExpectedSize(12)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(11); // 设置作为标记的请求码
    }

    //选择添加文件夹之后的回调
    private void onSelectedFolder(String folderPath){
         if (!mainPresenter.selectFolder(folderPath)){
             //添加文件夹错误
             ToastUtil.showToast(this,getString(R.string.add_folder_err));
         }else {
             mainPresenter.updateFolderViewItemsData(adapter);
         }
     }

    //执行删除操作
    private void execDelete(){
        for (int i=0; i<adapter.getItemCount();i++){
            if (selectDeleteItems.get(i)){
                mainPresenter.deleteFolder(i,adapter);
            }
        }

    }

    //显示确认删除小红点
    private void showDeleteTag(int position) {
        FolderViewItem item =  adapter.getItem(position);
        if (null != item){
            item.setTagDelete(true);
            adapter.notifyItemChanged(position);
        }
    }
    //隐藏确认删除小红点
    private void hideDeleteTag(int position) {
        FolderViewItem item =  adapter.getItem(position);
        if (null != item){
            item.setTagDelete(false);
            adapter.notifyItemChanged(position);
        }
    }
    // 隐藏所有删除小点
    private void hideAllDeleteTag(){
        for (int i=0; i<adapter.getItemCount();i++){
            if (selectDeleteItems.get(i)){
                changeItemDeleteStatus(i);
            }
        }
    }

    // 切换是否删除的状态
    private void changeItemDeleteStatus(int position){
        if (selectDeleteItems.get(position,false)) {
            selectDeleteItems.put(position,false);
            hideDeleteTag(position);

        }else{
            selectDeleteItems.put(position,true);
            showDeleteTag(position);
        }
    }
    //改变删除和正常按钮的显示状态
    private void changeButtonsDeleteSrc(){
        if (isLongClickedStatus){
            mIbnSetting.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_delete_forever_black_24dp,null));
            mFabAddPic.setMenuEnable(false);
            mFabAddPic.rotateFabOpen();
        }else {
            mIbnSetting.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_settings_black_24dp,null));
            mFabAddPic.setMenuEnable(true);
            mFabAddPic.rotateFabClose();
        }
    }

}
