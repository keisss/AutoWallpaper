package cn.keiss.autowallpaper.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import static cn.keiss.autowallpaper.data.Fields.DISPLAY_EFFECT;
import static cn.keiss.autowallpaper.data.Fields.DISPLAY_EFFECT_NORMAL;
import static cn.keiss.autowallpaper.data.Fields.DISPLAY_TYPE;
import static cn.keiss.autowallpaper.data.Fields.DISPLAY_TYPE_FILL;
import static cn.keiss.autowallpaper.data.Fields.SWITCH_EFFECT;
import static cn.keiss.autowallpaper.data.Fields.SWITCH_EFFECT_FADE_OVER;
import static cn.keiss.autowallpaper.data.Fields.SWITCH_ORDER;
import static cn.keiss.autowallpaper.data.Fields.SWITCH_ORDER_IN_TURN;
import static cn.keiss.autowallpaper.data.Fields.SWITCH_TIME;

/**
 *
 * @author hekai
 * @date 2017/10/20
 *  对设置进行处理保存
 */

public class SharePreferenceControl {
    private  SharedPreferences preferences;
    private  SharedPreferences.Editor editor;


    private static SharePreferenceControl control;

    public static SharePreferenceControl getInstance(){
        if (control == null){
            control = new SharePreferenceControl();
        }
        return control;
    }

    public  void setPreferences(Context context){
        if (preferences == null && context instanceof Application){
            String userSharePreferenceName = "sharePreference";
            preferences = context.getSharedPreferences(userSharePreferenceName,0);
        }
    }

    private  SharedPreferences getUserPreferences(){
        return preferences;
    }

    private  SharedPreferences.Editor getEditor(){
        if (editor ==null){
            editor = getUserPreferences().edit();
        }
        return editor;
    }


    public  void setDisplayEffect(@DISPLAY_EFFECT int displayEffectId){
        SharedPreferences.Editor editor = getEditor() ;
        editor.putInt(DISPLAY_EFFECT,displayEffectId);
        editor.apply();
    }

    @DISPLAY_EFFECT
    public int getDisplayEffect(){
        return getUserPreferences().getInt(DISPLAY_EFFECT,DISPLAY_EFFECT_NORMAL);
    }
    public void setSwitchEffect(int switchEffectId){
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(SWITCH_EFFECT,switchEffectId);
        editor.apply();
    }
    public int getSwitchEffect(){
        return   getUserPreferences().getInt(SWITCH_EFFECT,SWITCH_EFFECT_FADE_OVER);
    }
    public void setSwitchOrder(int orderId){
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(SWITCH_ORDER,orderId);
        editor.apply();
    }
    public int getSwitchOrder(){
        return getUserPreferences().getInt(SWITCH_ORDER,SWITCH_ORDER_IN_TURN);
    }
    public void setSwitchTime(int switchTime){
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(SWITCH_TIME,switchTime);
        editor.apply();
    }
    public int getSwitchTime(){
        return getUserPreferences().getInt(SWITCH_TIME,10000);
    }
    public void setDisplayType(int displayTypeId){
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(DISPLAY_TYPE,displayTypeId);
        editor.apply();
    }
    public int getDisplayType(){
        return getUserPreferences().getInt(DISPLAY_TYPE,DISPLAY_TYPE_FILL);
    }

    public void setListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        getUserPreferences().registerOnSharedPreferenceChangeListener(listener);
    }



}
