package cn.keiss.autowallpaper.contract;

import java.util.List;

import cn.keiss.autowallpaper.adapter.recyclerview.FolderGridViewAdapter;
import cn.keiss.autowallpaper.baselib.BasePresenter;
import cn.keiss.autowallpaper.baselib.BaseView;
import cn.keiss.autowallpaper.bean.FolderViewItem;

/**
 * @author hekai
 * @date 2017/10/28
 */

public interface MainContract {

    interface View extends BaseView{
        void setFolderDataList(List<FolderViewItem> folderDataList);
        //添加文件夹完成的回调
        void addFolderSuccess();
        void addFolderHaveAdded();
        void addFolderFailed();

        void deleteFolderSuccess();
        void deleteFolderFailed();
    }

    interface Presenter extends BasePresenter{

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
         * @return 是否成功添加
         */
        void selectFolder(String folderPath);


        void deleteFolder(int position,FolderGridViewAdapter adapter);

        /**
         * 显示存放独立选择的图片的文件夹
         */
        void prepareForDefaultFolder();

    }

}
