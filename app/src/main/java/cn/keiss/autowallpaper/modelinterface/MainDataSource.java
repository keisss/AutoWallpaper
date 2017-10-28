package cn.keiss.autowallpaper.modelinterface;

import java.util.List;

import cn.keiss.autowallpaper.adapter.recyclerview.FolderGridViewAdapter;
import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.listener.OnAddFolderListener;

/**
 * Created by hekai on 2017/10/15.
 */

public interface MainDataSource {
    /**
     * 设置文件夹item并显示
     * @return 文件夹item集合
     */
    List<FolderViewItem> setFolderViewItems();


    /**
     * 更新文件夹item和view
     */
    void updateFolderViewItems(FolderGridViewAdapter adapter);
    void addedFolderViewItems(FolderGridViewAdapter adapter);
    void deleteFolderViewItems(FolderGridViewAdapter adapter);

    /**
     * 添加文件夹后
     * @param folderPath 文件夹路径
     * @param listener 添加文件夹的回调
     * @return 是否成功添加
     */
    void selectFolder(String folderPath, OnAddFolderListener listener);

    boolean deleteFolder(int position,FolderGridViewAdapter adapter);

    /**
     * 显示存放独立选择的图片的文件夹
     */
    void prepareForDefaultFolder();
}
