package cn.keiss.autowallpaper.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hekai on 2017/10/15.
 * 图片文件夹的数据库实体类
 */
@Entity
public class PicFolderBean {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "FOLDER_PATH")
    private String picFolderPath;
    @Property(nameInDb = "FOLDER_NAME")
    private String picFolderName;

    @Generated(hash = 81282262)
    public PicFolderBean(Long id, String picFolderPath, String picFolderName) {
        this.id = id;
        this.picFolderPath = picFolderPath;
        this.picFolderName = picFolderName;
    }
    @Generated(hash = 1648369821)
    public PicFolderBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPicFolderPath() {
        return this.picFolderPath;
    }
    public void setPicFolderPath(String picFolderPath) {
        this.picFolderPath = picFolderPath;
    }
    public String getPicFolderName() {
        return this.picFolderName;
    }
    public void setPicFolderName(String picFolderName) {
        this.picFolderName = picFolderName;
    }
}
