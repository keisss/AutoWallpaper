package cn.keiss.autowallpaper.modelInterface;

import java.util.List;

import cn.keiss.autowallpaper.bean.FolderViewItem;

/**
 * Created by hekai on 2017/10/15.
 */

public interface MainDataSource {
    List<FolderViewItem> setFolderViewItems();
    void updateFolderViewItems();
}
