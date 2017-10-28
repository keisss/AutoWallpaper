package cn.keiss.autowallpaper.viewinterface;

import java.util.List;

import cn.keiss.autowallpaper.bean.FolderViewItem;

/**
 * Created by hekai on 2017/10/15.
 */

public interface MainViewListener {
    void setFolderDataList(List<FolderViewItem> folderDataList);
    //添加文件夹完成的回调
    boolean addFolderFinish();
}
