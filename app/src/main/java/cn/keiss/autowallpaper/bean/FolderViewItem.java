package cn.keiss.autowallpaper.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by hekai on 2017/10/15.
 */

public class FolderViewItem {
    private String folderName;
    private String folderPath;
    private boolean isTagDelete = false;
    private long id;
    private int folderSrc = -1;

    public int getFolderSrc() {
        return folderSrc;
    }

    public void setFolderSrc(int folderSrc) {
        this.folderSrc = folderSrc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }




    public boolean isTagDelete() {
        return isTagDelete;
    }

    public void setTagDelete(boolean tagDelete) {
        isTagDelete = tagDelete;
    }



    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
