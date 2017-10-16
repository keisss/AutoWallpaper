package cn.keiss.autowallpaper.modelManger;

import cn.keiss.autowallpaper.modelInterface.MainDataSource;

/**
 * Created by hekai on 2017/10/15.
 */

public class MainManger {
    private MainDataSource mainDataSource;

    public MainManger(MainDataSource mainDataSource){
        this.mainDataSource = mainDataSource;
    }
}
