package cn.keiss.autowallpaper.viewInterface;

import java.util.List;

import cn.keiss.autowallpaper.bean.FolderViewItem;

/**
 * Created by hekai on 2017/10/15.
 */

public interface MainViewListener {
    void setFolderDataList(List<FolderViewItem> folderDataList);
    void updateFolderDataList();
}
