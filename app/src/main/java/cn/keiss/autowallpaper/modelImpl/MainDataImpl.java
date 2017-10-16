package cn.keiss.autowallpaper.modelImpl;

import java.util.ArrayList;
import java.util.List;

import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.modelInterface.MainDataSource;

/**
 * Created by hekai on 2017/10/15.
 */

public class MainDataImpl implements MainDataSource {
    List<FolderViewItem> folderViewItems = new ArrayList<>();
    @Override
    public List<FolderViewItem> setFolderViewItems() {
        for (int i=0;i<40;i++){
            FolderViewItem item = new FolderViewItem();
            item.setFolderName(i+"namewerwertwertwertwertewr");
            folderViewItems.add(item);
        }
        return folderViewItems;
    }

    @Override
    public void updateFolderViewItems() {

    }
}
