package cn.keiss.autowallpaper.viewInterface;

import java.util.List;

import cn.keiss.autowallpaper.adapter.recyclerView.FolderGridViewAdapter;
import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.dataBase.PicFolderBean;

/**
 * Created by hekai on 2017/10/15.
 */

public interface MainViewListener {
    void setFolderDataList(List<FolderViewItem> folderDataList);
}
