package cn.keiss.autowallpaper.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hekai on 2017/10/15.
 * 图片文件在数据库的实体类
 */
@Entity
public class PicFileBean {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "FILE_PATH")
    private String picFilePath;
    @Property(nameInDb = "FILE_NAME")
    private String picFIleName;
    @Property(nameInDb = "SUPER_FOLDER_ID")
    private Long superFolderId;
    @Property(nameInDb = "IS_GIF")
    private Boolean isGif;
    @Property(nameInDb = "IS_VIDEO")
    private Boolean isVideo;
    @Property(nameInDb = "IS_FIT_XY")
    private boolean isFitXy;
    @Property(nameInDb = "IS_FIT_RESOLUTION")
    private boolean isFitResolution;
    @Generated(hash = 57705868)
    public PicFileBean(Long id, String picFilePath, String picFIleName,
            Long superFolderId, Boolean isGif, Boolean isVideo, boolean isFitXy,
            boolean isFitResolution) {
        this.id = id;
        this.picFilePath = picFilePath;
        this.picFIleName = picFIleName;
        this.superFolderId = superFolderId;
        this.isGif = isGif;
        this.isVideo = isVideo;
        this.isFitXy = isFitXy;
        this.isFitResolution = isFitResolution;
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
    public Boolean getIsGif() {
        return this.isGif;
    }
    public void setIsGif(Boolean isGif) {
        this.isGif = isGif;
    }
    public Boolean getIsVideo() {
        return this.isVideo;
    }
    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }
    public boolean getIsFitXy() {
        return this.isFitXy;
    }
    public void setIsFitXy(boolean isFitXy) {
        this.isFitXy = isFitXy;
    }
    public boolean getIsFitResolution() {
        return this.isFitResolution;
    }
    public void setIsFitResolution(boolean isFitResolution) {
        this.isFitResolution = isFitResolution;
    }
}
