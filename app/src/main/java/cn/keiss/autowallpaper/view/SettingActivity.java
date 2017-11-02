package cn.keiss.autowallpaper.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import cn.keiss.autowallpaper.R;
import cn.keiss.autowallpaper.baselib.BaseActivity;

/**
 * 用来设置各种特效的Activity
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private ImageButton mIbnClose;
    private ImageButton mIbnRevert;
    private ImageButton mIbnConfirm;
    private ImageView mIvPreview;
    private HorizontalScrollView mSettingThirdBar;
    private LinearLayout mSecondBarSwitchOrder;
    private Button mBtnSwitchInOrder;
    private Button mBtnSwitchRandom;
    private LinearLayout mSecondBarSwitchEffect;
    private Button mBtnSwitchFadeOver;
    private Button mBtnSwitchPage;
    private Button mBtnSwitchStack;
    private Button mBtnSwitchRotate;
    private Button mBtnSwitchCube;
    private Button mBtnSwitchJalousie;
    private RelativeLayout mSecondBarSwitchTime;
    private TextView mTvSwitchTime;
    private CheckBox mCheckBox;
    private SeekBar mSeekBar;
    private HorizontalScrollView mSettingSecondBar;
    private LinearLayout mBarDisplayEffect;
    private Button mBtnDisplayEffectFrostedGlass;
    private Button mBtnDisplayEffectBlackWhite;
    private Button mBtnDisplayEffectMoveWithScreen;
    private LinearLayout mBarSwitchSetting;
    private Button mBtnSwitchOrder;
    private Button mBtnSwitchEffect;
    private Button mBtnSwitchTime;
    private LinearLayout mBarDisplayPattern;
    private Button mBtnDisplayPatternFill;
    private Button mBtnDisplayPatternFit;
    private Button mBtnDisplayPatternStretch;
    private Button mBtnDisplayPatternTile;
    private Button mBtnDisplayPatternMagicEffect;
    private LinearLayout mBarOthers;
    private Button mBtnOthersOnlyFitScale;
    private Button mBtnOthersOnlyFitResolution;
    private Button mBtnOthersGif;
    private Button mBtnOthersVideo;
    private HorizontalScrollView mSettingBar;
    private Button mBtnSettingDisplayEffect;
    private Button mBtnSettingSwitch;
    private Button mBtnSettingDisplayType;
    private Button mBtnSettingOthers;







    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);

        mToolbar = findViewById(R.id.toolbar);
        mIbnClose = findViewById(R.id.ibn_close);
        mIbnRevert = findViewById(R.id.ibn_revert);
        mIbnConfirm = findViewById(R.id.ibn_confirm);
        mIvPreview = findViewById(R.id.iv_preview);
        mSettingThirdBar = findViewById(R.id.setting_third_bar);
        mSecondBarSwitchOrder = findViewById(R.id.second_bar_switch_order);
        mBtnSwitchInOrder = findViewById(R.id.btn_switch_in_order);
        mBtnSwitchRandom = findViewById(R.id.btn_switch_random);
        mSecondBarSwitchEffect = findViewById(R.id.second_bar_switch_effect);
        mBtnSwitchFadeOver = findViewById(R.id.btn_switch_fade_over);
        mBtnSwitchPage = findViewById(R.id.btn_switch_page);
        mBtnSwitchStack = findViewById(R.id.btn_switch_stack);
        mBtnSwitchRotate = findViewById(R.id.btn_switch_rotate);
        mBtnSwitchCube = findViewById(R.id.btn_switch_cube);
        mBtnSwitchJalousie = findViewById(R.id.btn_switch_jalousie);
        mSecondBarSwitchTime = findViewById(R.id.second_bar_switch_time);
        mTvSwitchTime = findViewById(R.id.tv_switch_time);
        mCheckBox = findViewById(R.id.checkBox);
        mSeekBar = findViewById(R.id.seekBar);
        mSettingSecondBar = findViewById(R.id.setting_second_bar);
        mBarDisplayEffect = findViewById(R.id.bar_display_effect);
        mBtnDisplayEffectFrostedGlass = findViewById(R.id.btn_display_effect_frosted_glass);
        mBtnDisplayEffectBlackWhite = findViewById(R.id.btn_display_effect_black_white);
        mBtnDisplayEffectMoveWithScreen = findViewById(R.id.btn_display_effect_move_with_screen);
        mBarSwitchSetting = findViewById(R.id.bar_switch_setting);
        mBtnSwitchOrder = findViewById(R.id.btn_switch_order);
        mBtnSwitchEffect = findViewById(R.id.btn_switch_effect);
        mBtnSwitchTime = findViewById(R.id.btn_switch_time);
        mBarDisplayPattern = findViewById(R.id.bar_display_pattern);
        mBtnDisplayPatternFill = findViewById(R.id.btn_display_pattern_fill);
        mBtnDisplayPatternFit = findViewById(R.id.btn_display_pattern_fit);
        mBtnDisplayPatternStretch = findViewById(R.id.btn_display_pattern_stretch);
        mBtnDisplayPatternTile = findViewById(R.id.btn_display_pattern_tile);
        mBtnDisplayPatternMagicEffect = findViewById(R.id.btn_display_pattern_magic_effect);
        mBarOthers = findViewById(R.id.bar_others);
        mBtnOthersOnlyFitScale = findViewById(R.id.btn_others_only_fit_scale);
        mBtnOthersOnlyFitResolution = findViewById(R.id.btn_others_only_fit_resolution);
        mBtnOthersGif = findViewById(R.id.btn_others_gif);
        mBtnOthersVideo = findViewById(R.id.btn_others_video);
        mSettingBar = findViewById(R.id.setting_bar);
        mBtnSettingDisplayEffect = findViewById(R.id.btn_setting_display_effect);
        mBtnSettingSwitch = findViewById(R.id.btn_setting_switch);
        mBtnSettingDisplayType = findViewById(R.id.btn_setting_display_type);
        mBtnSettingOthers = findViewById(R.id.btn_setting_others);



    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibn_close:
                onClose();
                break;
            case R.id.ibn_revert:
                //退回
                break;
            case R.id.ibn_confirm:
                onConfirm();
                break;

        }
    }


    //按下退出键
    private void onClose(){

    }
    //按下确认设置
    private void onConfirm(){

    }

    private void onDisplayEffectSet(){

    }
}
