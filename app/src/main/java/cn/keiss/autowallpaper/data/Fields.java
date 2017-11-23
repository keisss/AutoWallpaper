package cn.keiss.autowallpaper.data;

import android.support.annotation.IntDef;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author hekai
 * @date 2017/11/22
 */

public  class Fields  {
    public static final int DISPLAY_EFFECT_NORMAL = 5;
    public static final int DISPLAY_EFFECT_FROSTED_GLASS = 1;
    public static final int DISPLAY_EFFECT_BLACK_WHITE = 2;
    public static final int DISPLAY_EFFECT_MOVE_WITH_SCREEN = 3;
    public static final int DISPLAY_EFFECT_VINTAGE = 4;
    public static final String DISPLAY_EFFECT = "display_effect";
    //淡出淡入
    public static final int SWITCH_EFFECT_FADE_OVER = 1;
    //转盘
    public static final int SWITCH_EFFECT_DIAL = 2;
    public static final int SWITCH_EFFECT_PAGE = 3;
    //层叠
    public static final int SWITCH_EFFECT_STACK = 4;
    public static final int SWITCH_EFFECT_ROTATE = 5;
    public static final int SWITCH_EFFECT_CUBE = 6;
    public static final int SWITCH_EFFECT_JALOUSIE = 7;
    public static final String SWITCH_EFFECT = "switch_effect";

    public static final int SWITCH_ORDER_RANDOM = 1;
    public static final int SWITCH_ORDER_IN_TURN = 2;

    public static final String SWITCH_ORDER = "switch_order";

    public static final String SWITCH_TIME  = "switch_time" ;

    public static final int DISPLAY_TYPE_FILL = 1;
    public static final int DISPLAY_TYPE_FIT = 2;
    //拉伸
    public static final int DISPLAY_TYPE_STRETCH = 3;
    //平铺
    public static final int DISPLAY_TYPE_TILE = 4;
    public static final int DIAPLAY_TYPE_CENTER = 5;

    public static final String DISPLAY_TYPE = "display_type";


    /**
     * 图片填充方式
     */
    @IntDef({DISPLAY_TYPE_FILL,
            DISPLAY_TYPE_FIT,
             DISPLAY_TYPE_STRETCH,
            DISPLAY_TYPE_TILE,
            DIAPLAY_TYPE_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DISPLAY_TYPE {}

    /**
     * 图片切换顺序
     */
    @IntDef({SWITCH_ORDER_IN_TURN,
            SWITCH_ORDER_RANDOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SWITCH_ORDER {}


    /**
     * 图片显示特效
     */
    @IntDef({DISPLAY_EFFECT_FROSTED_GLASS,
            DISPLAY_EFFECT_BLACK_WHITE,
            DISPLAY_EFFECT_MOVE_WITH_SCREEN,
            DISPLAY_EFFECT_VINTAGE,
            DISPLAY_EFFECT_NORMAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DISPLAY_EFFECT {}


    /**
     * 图片切换特效
     */
    @IntDef({SWITCH_EFFECT_FADE_OVER,
            SWITCH_EFFECT_DIAL,
            SWITCH_EFFECT_PAGE,
            SWITCH_EFFECT_STACK,
            SWITCH_EFFECT_ROTATE,
            SWITCH_EFFECT_CUBE,
            SWITCH_EFFECT_JALOUSIE,
            })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SWITCH_EFFECT {}


}
