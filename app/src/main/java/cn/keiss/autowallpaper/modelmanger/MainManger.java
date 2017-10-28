package cn.keiss.autowallpaper.modelmanger;

import cn.keiss.autowallpaper.modelinterface.MainDataSource;

/**
 * Created by hekai on 2017/10/15.
 */

public class MainManger {
    private MainDataSource mainDataSource;

    public MainManger(MainDataSource mainDataSource){
        this.mainDataSource = mainDataSource;
    }
}
