package cn.keiss.autowallpaper.dataBase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hekai on 2017/10/15.
 */
@Entity
public class PicFileBean {
    @Id
    private Long id;
    @Property(nameInDb = "FILE_PATH")
    private String picFilePath;
    @Property(nameInDb = "FILE_NAME")
    private String picFIleName;
    @Property(nameInDb = "SUPER_FOLDER_ID")
    private Long superFolderId;
    @Generated(hash = 151231883)
    public PicFileBean(Long id, String picFilePath, String picFIleName,
            Long superFolderId) {
        this.id = id;
        this.picFilePath = picFilePath;
        this.picFIleName = picFIleName;
        this.superFolderId = superFolderId;
    }
    @Generated(hash = 1977934184)
    public PicFileBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPicFilePath() {
        return this.picFilePath;
    }
    public void setPicFilePath(String picFilePath) {
        this.picFilePath = picFilePath;
    }
    public String getPicFIleName() {
        return this.picFIleName;
    }
    public void setPicFIleName(String picFIleName) {
        this.picFIleName = picFIleName;
    }

    public Long getSuperFolderId() {
        return this.superFolderId;
    }
    public void setSuperFolderId(Long superFolderId) {
        this.superFolderId = superFolderId;
    }
}
