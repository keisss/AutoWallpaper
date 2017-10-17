package cn.keiss.autowallpaper.modelImpl;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.keiss.autowallpaper.R;
import cn.keiss.autowallpaper.adapter.recyclerView.FolderGridViewAdapter;
import cn.keiss.autowallpaper.baseLib.BaseApplication;
import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.dataBase.PicFolderBean;
import cn.keiss.autowallpaper.dataBase.PicFolderBeanDao;
import cn.keiss.autowallpaper.modelInterface.MainDataSource;

/**
 * Created by hekai on 2017/10/15.
 * MainActivity类的model
 */

public class MainDataImpl implements MainDataSource {
    private List<FolderViewItem> folderViewItems = new ArrayList<>();
    private PicFolderBeanDao picFileBeanDao
                = BaseApplication.getDaoSession().getPicFolderBeanDao();





    @Override
    public List<FolderViewItem> setFolderViewItems() {
        List<PicFolderBean> picFolders = picFileBeanDao.loadAll();
        for (PicFolderBean bean: picFolders) {
            FolderViewItem item = new FolderViewItem();
            item.setFolderName(bean.getPicFolderName());
            item.setFolderPath(bean.getPicFolderPath());
            item.setId(bean.getId());
            folderViewItems.add(item);
        }
        return folderViewItems;
    }

    @Override
    public void updateFolderViewItems(FolderGridViewAdapter adapter) {
        //清空并重建
        folderViewItems.clear();
        //准备第一项
        prepareForDefaultFolder();

        List<PicFolderBean> picFolders = picFileBeanDao.loadAll();
        for (int i=0;i<picFolders.size();i++){
            FolderViewItem item = new FolderViewItem();
            PicFolderBean bean = picFolders.get(i);
            item.setFolderName(bean.getPicFolderName());
            item.setFolderPath(bean.getPicFolderPath());
            item.setId(bean.getId());
            folderViewItems.add(i+1,item);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addedFolderViewItems(FolderGridViewAdapter adapter) {
        // TODO
    }

    @Override
    public void deleteFolderViewItems(FolderGridViewAdapter adapter) {
        //TODO
    }

    @Override
    public boolean selectFolder(String folderPath) {
        String folderName = getFileName(folderPath);
        Log.e(folderPath+"",folderName+"");
        if (null !=folderName && null !=folderPath){
            PicFolderBean bean = new PicFolderBean();
            bean.setPicFolderName(folderName);
            bean.setPicFolderPath(folderPath);
            picFileBeanDao.insert(bean);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteFolder(int position,FolderGridViewAdapter adapter) {
        FolderViewItem item = folderViewItems.get(position);
        if (null != item){
            picFileBeanDao.deleteByKey(item.getId());
            adapter.notifyItemRemoved(position);
            return true;
        }else
            return false;
    }

    @Override
    public void prepareForDefaultFolder() {
        FolderViewItem item = new FolderViewItem();
        item.setFolderPath(null);
        item.setId(-1);
        item.setFolderName("选择的图片");
        item.setFolderSrc(R.drawable.ic_folder_special_black_24dp);
        folderViewItems.add(0,item);
    }

    private String getFileName(String path){

        int start=path.lastIndexOf("/");
        int end=path.length();
        if(start!=-1 && end!=-1){
            return path.substring(start+1,end);
        }else{
            return null;
        }

    }

}
