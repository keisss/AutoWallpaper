package cn.keiss.autowallpaper.bean;

/**
 * Created by hekai on 2017/10/15.
 */

public class FolderViewItem {
    private String folderName;
    private boolean isTagDelete = false;


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
