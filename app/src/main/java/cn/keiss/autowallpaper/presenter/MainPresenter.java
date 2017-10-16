package cn.keiss.autowallpaper.presenter;

import java.util.List;

import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.modelImpl.MainDataImpl;
import cn.keiss.autowallpaper.modelInterface.MainDataSource;
import cn.keiss.autowallpaper.viewInterface.MainViewListener;

/**
 * Created by hekai on 2017/10/15.
 */

public class MainPresenter {
    private MainViewListener mainViewListener;
    private MainDataSource mainDataSource;

    public MainPresenter(MainViewListener mainViewListener){
        this.mainViewListener = mainViewListener;
        mainDataSource = new MainDataImpl();
    }


    public void setFolderViewItemData(){
       List<FolderViewItem> items =  mainDataSource.setFolderViewItems();
        mainViewListener.setFolderDataList(items);
    }

    public void updateFolderViewItemsData(){
        mainDataSource.updateFolderViewItems();
    }
}