package cn.keiss.autowallpaper.presenter;

import java.util.List;

import cn.keiss.autowallpaper.adapter.recyclerview.FolderGridViewAdapter;
import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.listener.OnAddFolderListener;
import cn.keiss.autowallpaper.modelimpl.MainDataImpl;
import cn.keiss.autowallpaper.modelinterface.MainDataSource;
import cn.keiss.autowallpaper.viewinterface.MainViewListener;

/**
 * @author hekai
 * @date 2017/10/15
 */

public class MainPresenterOlder {
    private MainViewListener mainViewListener;
    private MainDataSource mainDataSource;

    public MainPresenterOlder(MainViewListener mainViewListener){
        this.mainViewListener = mainViewListener;
        mainDataSource = new MainDataImpl();
    }


    public void setFolderViewItemData(){
       List<FolderViewItem> items =  mainDataSource.setFolderViewItems();
        mainViewListener.setFolderDataList(items);
    }

    public void updateFolderViewItemsData(FolderGridViewAdapter adapter){
        mainDataSource.updateFolderViewItems(adapter);
    }


    public void selectFolder(String folderPath){
        mainDataSource.selectFolder(folderPath);
    }

    public boolean deleteFolder(int position,FolderGridViewAdapter adapter){
        return mainDataSource.deleteFolder(position,adapter);
    }


    public void prepareForDefaultFolder(){
        mainDataSource.prepareForDefaultFolder();
    }


}
