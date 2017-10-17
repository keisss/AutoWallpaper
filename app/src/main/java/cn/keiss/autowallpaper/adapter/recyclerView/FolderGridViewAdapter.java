package cn.keiss.autowallpaper.adapter.recyclerView;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.keiss.autowallpaper.R;
import cn.keiss.autowallpaper.bean.FolderViewItem;

/**
 * Created by hekai on 2017/10/15.
 * 用于给文件夹视图下的recyclerView的adapter
 */

public class FolderGridViewAdapter extends BaseQuickAdapter<FolderViewItem,FolderGridViewAdapter.FolderViewHolder>{


    public  FolderGridViewAdapter(@LayoutRes int layoutResId, @Nullable List<FolderViewItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(FolderViewHolder helper, FolderViewItem item) {
        helper.tvFolderName.setText(item.getFolderName());
        if(-1 != item.getFolderSrc()){
            helper.ivFolderPic.setImageResource(item.getFolderSrc());
        }
        if (item.isTagDelete()){
            helper.ivDeleteTag.setVisibility(View.VISIBLE);
        }else
            helper.ivDeleteTag.setVisibility(View.GONE);
    }




    class FolderViewHolder extends BaseViewHolder{
         private TextView tvFolderName;
         private ImageView ivFolderPic;
         private ImageView ivDeleteTag;

         public FolderViewHolder(View view) {
            super(view);
             tvFolderName = view.findViewById(R.id.tv_folder_name);
             ivDeleteTag = view.findViewById(R.id.iv_delete_tag);
             ivFolderPic = view.findViewById(R.id.iv_folder_src);
        }
     }


}
