package cn.keiss.autowallpaper.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hekai on 2017/10/20.
 *  对设置进行处理保存
 */

public class SharePreferenceControl {
    private  SharedPreferences preferences;
    private  SharedPreferences.Editor editor;
    private  final String UserSharePreferenceName = "sharePreference";


    @IntDef({DISPLAY_EFFECT_FROSTED_GLASS,
            DISPLAY_EFFECT_BLACK_WHITE,
            DISPLAY_EFFECT_MOVE_WITH_SCREEN,
            DISPLAY_EFFECT_VINTAGE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DISPLAY_EFFECT {}



    private static final int DISPLAY_EFFECT_FROSTED_GLASS = 1;
    private static final int DISPLAY_EFFECT_BLACK_WHITE = 2;
    private static final int DISPLAY_EFFECT_MOVE_WITH_SCREEN = 3;
    private static final int DISPLAY_EFFECT_VINTAGE = 4;
    private static final String DISPLAY_EFFECT = "display_effect";
    //淡出淡入
    private final int SWITCH_EFFECT_FADE_OVER = 1;
    //转盘
    private final int SWITCH_EFFECT_DIAL = 2;
    private final int SWITCH_EFFECT_PAGE = 3;
    //层叠
    private final int SWITCH_EFFECT_STACK = 4;
    private final int SWITCH_EFFECT_ROTATE = 5;
    private final int SWITCH_EFFECT_CUBE = 6;
    private final int SWITCH_EFFECT_JALOUSIE = 7;
    private final String SWITCH_EFFECT = "switch_effect";

    private final int SWITCH_ORDER_RANDOM = 1;
    private final int SWITCH_ORDER_IN_TURN = 2;

    private final String SWITCH_ORDER = "switch_order";
    private final String SWITCH_TIME  = "switch_time" ;
    private final int DISPLAY_TYPE_FILL = 1;
    private final int DISPLAY_TYPE_FIT = 2;
    //拉伸
    private final int DISPLAY_TYPE_STRETCH = 3;
    //平铺
    private final int DISPLAY_TYPE_TILE = 4;
    private final int DIAPLAY_TYPE_CENTER = 5;

    private final String DISPLAY_TYPE = "display_type";




    private   SharedPreferences getUserPreferences(Context context){
        if (preferences ==null){
            preferences = context.getSharedPreferences(UserSharePreferenceName,0);
        }
        return preferences;
    }

    private  SharedPreferences.Editor getEditor(Context context){
        if (editor ==null){
            editor = getUserPreferences(context).edit();
        }
        return editor;
    }


    public void setDisplayEffect(Context context,@DISPLAY_EFFECT int displayEffectId){
        SharedPreferences.Editor editor = getEditor(context) ;
        editor.putInt(DISPLAY_EFFECT,displayEffectId);
        editor.apply();
    }

    @DISPLAY_EFFECT
    public int getDisplayEffect(Context context){
        return getUserPreferences(context).getInt(DISPLAY_EFFECT,0);
    }
    public void setSwitchEffect(Context context,int switchEffectId){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(SWITCH_EFFECT,switchEffectId);
        editor.apply();
    }
    public int getSwitchEffect(Context context){
        return   getUserPreferences(context).getInt(SWITCH_EFFECT,0);
    }
    public void setSwitchOrder(Context context,int orderId){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(SWITCH_ORDER,orderId);
        editor.apply();
    }
    public int getSwitchOrder(Context context){
        return getUserPreferences(context).getInt(SWITCH_ORDER,0);
    }
    public void setSwitchTime(Context context,int switchTime){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(SWITCH_TIME,switchTime);
        editor.apply();
    }
    public int getSwitchTime(Context context){
        return getUserPreferences(context).getInt(SWITCH_TIME,0);
    }
    public void setDisplayType(Context context,int displayTypeId){
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(DISPLAY_TYPE,displayTypeId);
        editor.apply();
    }
    public int getDisplayType(Context context){
        return getUserPreferences(context).getInt(DISPLAY_TYPE,0);
    }

}
