package cn.keiss.autowallpaper.database;


import java.util.List;

import cn.keiss.autowallpaper.baselib.BaseApplication;

/**
 * @author hekai
 * @date 2017/11/12
 */

public class FleshPicManager {
    private PicFileBeanDao picFileBeanDao;

    public FleshPicManager(){
        picFileBeanDao = BaseApplication.getDaoSession().getPicFileBeanDao();
    }

    public List<PicFileBean> getAllPic(){
         return picFileBeanDao.loadAll();
}

    public void deleteUselessPic(long id){
        picFileBeanDao.deleteByKey(id);
    }

    public PicFileBean getPic(int turn){
        List<PicFileBean> fileBeans = getAllPic();
        if (fileBeans == null){
            return null;
        }else {
            return fileBeans.get(turn);
        }
    }

    public int getPicNum(){
        return getAllPic().size();
    }

    public PicFileBean getInTurnNextPic(long id){
        if (id == -1){
            return picFileBeanDao.queryBuilder().where(PicFileBeanDao.Properties.Id.gt(0)).limit(1).unique();
        }else {
            PicFileBean bean =  picFileBeanDao.queryBuilder().where(PicFileBeanDao.Properties.Id.gt(id))
                    .limit(1).unique();
            if (bean == null){
                return getInTurnNextPic(-1);
            }else {
                return bean;
            }
        }
    }


}
