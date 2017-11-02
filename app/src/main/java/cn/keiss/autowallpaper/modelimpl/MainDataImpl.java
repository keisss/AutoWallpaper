package cn.keiss.autowallpaper.modelimpl;

import android.os.Handler;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.keiss.autowallpaper.R;
import cn.keiss.autowallpaper.adapter.recyclerview.FolderGridViewAdapter;
import cn.keiss.autowallpaper.baselib.BaseApplication;
import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.database.PicFileBean;
import cn.keiss.autowallpaper.database.PicFileBeanDao;
import cn.keiss.autowallpaper.database.PicFolderBean;
import cn.keiss.autowallpaper.database.PicFolderBeanDao;
import cn.keiss.autowallpaper.listener.OnAddFolderListener;
import cn.keiss.autowallpaper.modelinterface.MainDataSource;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hekai on 2017/10/15.
 * MainActivity类的model
 */

public class MainDataImpl implements MainDataSource {
    private List<FolderViewItem> folderViewItems = new ArrayList<>();
    private PicFolderBeanDao picFolderBeanDao
                = BaseApplication.getDaoSession().getPicFolderBeanDao();
    private PicFileBeanDao picFileBeanDao = BaseApplication.getDaoSession().getPicFileBeanDao();
    private final String[] format = new String[]{".jpg",".jpeg",".png",".gif",".bmp",".mp4"};
    //gif format
    private final String[] gifFormat = new String[]{".gif"};
    //Video format
    private final String[] videoFormat = new String[]{".mp4"};






    @Override
    public List<FolderViewItem> setFolderViewItems() {
        List<PicFolderBean> picFolders = picFolderBeanDao.loadAll();
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

        List<PicFolderBean> picFolders = picFolderBeanDao.loadAll();
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
    public void selectFolder(final String folderPath) {
//        final String folderName = getFileName(folderPath);
//        Log.e(folderPath+"",folderName+"");
//        if (null !=folderName && null !=folderPath){
//            execAddFolderAndPic(folderName,folderPath,listener);
//
//        }else {
//            listener.onFailed();
//        }
    }

    @Override
    public boolean deleteFolder(int position,FolderGridViewAdapter adapter) {
        FolderViewItem item = folderViewItems.get(position);
        if (null != item){
            picFolderBeanDao.deleteByKey(item.getId());
            adapter.notifyItemRemoved(position);
            return true;
        }else {
            return false;
        }
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

    //获取文件名
    private String getFileName(String path){

        int start=path.lastIndexOf("/");
        int end=path.length();
        if(start!=-1 && end!=-1){
            return path.substring(start+1,end);
        }else{
            return null;
        }

    }


    private void execAddFolderAndPic(final String folderName, final String folderPath, final OnAddFolderListener listener){
        final Handler handler = new Handler();

        //输入合法
        if (checkHaveAdded(folderPath)){
            //还没有添加
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PicFolderBean bean = new PicFolderBean();
                    bean.setPicFolderName(folderName);
                    bean.setPicFolderPath(folderPath);
                    picFolderBeanDao.insert(bean);
                    addSelectedFolderPicToDb(folderPath);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess();
                        }
                    });
                }
            }).run();
        }else {
            //已经添加
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onHaveAdded();
                }
            });
        }


    }


    //在添加文件夹时添加文件夹中的图片到数据库
    private void addSelectedFolderPicToDb(String folderPath){

        List<String> filesPath = getAllWallpaperFiles(folderPath,format);
        for (String path : filesPath){
            PicFileBean bean = new PicFileBean();
            bean.setIsGif(checkFileType(path,gifFormat));
            bean.setIsVideo(checkFileType(path,videoFormat));
            bean.setPicFilePath(path);
            bean.setPicFIleName(getFileName(path));
            picFileBeanDao.insert(bean);
        }


    }


    /**
     * 获取文件夹下所有的符合条件的文件
     * @param rootFilePath 文件夹路径
     * @return 所有文件的路径
     */
    private List<String> getAllWallpaperFiles(String rootFilePath,String[] format){
        File rootFile = new File(rootFilePath);
        List<String> filesPath = new ArrayList<>();

        if (rootFile.isDirectory()){
            File[] fs = rootFile.listFiles();
            for (File file : fs) {
                String path = file.getPath();
                if (!file.isDirectory()) {
                    if (checkFileType(path,format)){
                        filesPath.add(path);
                    }
                }
                //else {
                    // 是否遍历的开关
//                    List<String> subFilesPath = getAllWallpaperFiles(path,format);
//                    for (int j = 0; j < subFilesPath.size(); j++) {
//                        if (checkFileType(subFilesPath.get(j),format)){
//                            filesPath.add(subFilesPath.get(j));
//                        }
//
//                    }
               // }
            }
        }
        return filesPath;
    }

    /**
     * 检测文件是否符合添加标准
     * @param filePath 文件路径
     * @param format 文件格式集合
     * @return true符合
     */
    private boolean checkFileType(String filePath,String[] format){
        if (null == filePath){
            return false;
        }else {
            int idx = filePath.lastIndexOf(".");
            Log.v("idx:", String.valueOf(idx));
            if (idx <= 0) {
                return false;
            }
            String suffix = filePath.substring(idx);
            suffix = suffix.toLowerCase(Locale.PRC);
            /*
             * format可以是".jpg"、".jpeg"等等，例如suffix.toLowerCase().equals(".jpeg")
             */
            for (String aFormat : format) {
                if (suffix.equals(aFormat)) {
                    return true;
                }
            }
            return false;
        }
    }

    //检查是否已经添加过这个文件夹
    private boolean checkHaveAdded(String folderPath){
        List<PicFolderBean> picFileBeen = picFolderBeanDao.queryBuilder()
                .where(PicFolderBeanDao.Properties.PicFolderPath.eq(folderPath))
                .list();
        return  picFileBeen.size() == 0;
    }

}
