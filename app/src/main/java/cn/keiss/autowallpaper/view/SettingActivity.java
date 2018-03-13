package cn.keiss.autowallpaper.view;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.print.PrintJob;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import cn.keiss.autowallpaper.customview.AnimImageView;
import cn.keiss.autowallpaper.R;
import cn.keiss.autowallpaper.adapter.recyclerview.FolderGridViewAdapter;
import cn.keiss.autowallpaper.baselib.BaseActivity;
import cn.keiss.autowallpaper.baselib.BasePresenter;
import cn.keiss.autowallpaper.bean.FolderViewItem;
import cn.keiss.autowallpaper.contract.MainContract;
import cn.keiss.autowallpaper.data.Fields;

/**
 * 用来设置各种特效的Activity
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private ImageButton mIbnClose;
    private ImageButton mIbnRevert;
    private ImageButton mIbnConfirm;
    private AnimImageView mIvPreview;


    private Button mBtnSwitchInOrder;
    private Button mBtnSwitchRandom;

    private Button mBtnSwitchFadeOver;
    private Button mBtnSwitchPage;
    private Button mBtnSwitchStack;
    private Button mBtnSwitchRotate;
    private Button mBtnSwitchCube;
    private Button mBtnSwitchJalousie;

    private TextView mTvSwitchTime;
    private CheckBox mCheckBox;
    private SeekBar mSeekBar;


    private Button mBtnDisplayEffectFrostedGlass;
    private Button mBtnDisplayEffectBlackWhite;
    private Button mBtnDisplayEffectMoveWithScreen;



    private Button mBtnDisplayPatternFill;
    private Button mBtnDisplayPatternFit;
    private Button mBtnDisplayPatternStretch;
    private Button mBtnDisplayPatternTile;
    private Button mBtnDisplayPatternMagicEffect;


    private Button mBtnOthersOnlyFitScale;
    private Button mBtnOthersOnlyFitResolution;
    private Button mBtnOthersGif;
    private Button mBtnOthersVideo;


    private LinearLayout mBarDisplayEffect;
    //切换相关设置的bar
    private LinearLayout mBarSwitchSetting;
    private LinearLayout mBarDisplayPattern;
    private LinearLayout mBarOthers;

    //切换设置bar的btn
    private Button mBtnSwitchOrder;
    private Button mBtnSwitchEffect;
    private Button mBtnSwitchTime;
    private LinearLayout mSecondBarSwitchOrder;
    private LinearLayout mSecondBarSwitchEffect;
    private RelativeLayout mSecondBarSwitchTime;



    //三级设置bar
    private HorizontalScrollView mSettingBar;
    private HorizontalScrollView mSettingSecondBar;
    private HorizontalScrollView mSettingThirdBar;
    private Button mBtnSettingDisplayEffect;
    //切换设置
    private Button mBtnSettingSwitch;


    private Button mBtnSettingDisplayType;
    private Button mBtnSettingOthers;

    private @Fields.SWITCH_EFFECT int switchEffect ;





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

        mBtnDisplayEffectBlackWhite.setOnClickListener(this);
        mBtnDisplayEffectFrostedGlass.setOnClickListener(this);
        mBtnDisplayEffectMoveWithScreen.setOnClickListener(this);
        mBtnDisplayPatternFill.setOnClickListener(this);
        mBtnDisplayPatternFit.setOnClickListener(this);
        mBtnDisplayPatternMagicEffect.setOnClickListener(this);
        mBtnDisplayPatternStretch.setOnClickListener(this);
        mBtnDisplayPatternTile.setOnClickListener(this);
        mBtnOthersOnlyFitScale.setOnClickListener(this);
        mBtnOthersOnlyFitResolution.setOnClickListener(this);
        mBtnOthersGif.setOnClickListener(this);
        mBtnOthersVideo.setOnClickListener(this);
        mBtnSwitchInOrder.setOnClickListener(this);
        mBtnSwitchRandom.setOnClickListener(this);
        mBtnSwitchFadeOver.setOnClickListener(this);
        mBtnSwitchPage.setOnClickListener(this);
        mBtnSwitchStack.setOnClickListener(this);
        mBtnSwitchRotate.setOnClickListener(this);
        mBtnSwitchCube.setOnClickListener(this);
        mBtnSwitchJalousie.setOnClickListener(this);

    }

    @Override
    protected void loadData() {
        mIvPreview.setBitmapIn(BitmapFactory.decodeResource(getResources(),R.drawable.hezhipin610039));
        mIvPreview.setBitmapOut(BitmapFactory.decodeResource(getResources(),R.drawable.jike));


    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return new MainContract.Presenter() {
            @Override
            public List<FolderViewItem> setFolderViewItems() {
                return null;
            }

            @Override
            public void updateFolderViewItems(FolderGridViewAdapter adapter) {

            }

            @Override
            public void addedFolderViewItems(FolderGridViewAdapter adapter) {

            }

            @Override
            public void deleteFolderViewItems(FolderGridViewAdapter adapter) {

            }

            @Override
            public void selectFolder(String folderPath) {

            }

            @Override
            public void deleteFolder(int position, FolderGridViewAdapter adapter) {

            }

            @Override
            public void prepareForDefaultFolder() {

            }

            @Override
            public void subscribe() {

            }

            @Override
            public void unSubscribe() {

            }
        };
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
            case R.id.btn_setting_switch:
                //显示切换设置bar
                setVisibleBar(mBarSwitchSetting);
                break;
            case R.id.btn_setting_display_effect:
                setVisibleBar(mBarDisplayEffect);
                ViewGroup
                break;
            case R.id.btn_setting_display_type:
                setVisibleBar(mBarDisplayPattern);
                break;
            case R.id.btn_setting_others:
                setVisibleBar(mBarOthers);
                break;
            case R.id.btn_switch_effect:
                setVisibleSecondBar(mSecondBarSwitchEffect);
                break;
            case R.id.btn_switch_order:
                setVisibleSecondBar(mSecondBarSwitchOrder);
                break;
            case R.id.btn_switch_time:
                setVisibleSecondBar(mSecondBarSwitchTime);
                break;
            case R.id.btn_switch_fade_over:
                onDisplayEffectSet(Fields.SWITCH_EFFECT_FADE_OVER);
                break;
            case R.id.btn_switch_page:
                onDisplayEffectSet(Fields.SWITCH_EFFECT_PAGE);
                break;
            case R.id.btn_switch_cube:
                onDisplayEffectSet(Fields.SWITCH_EFFECT_CUBE);
                break;
            default: break;
        }
    }

    //设置一级bar的显示
    private void setVisibleBar(LinearLayout linearLayout){
        mBarSwitchSetting.setVisibility(View.GONE);
        mBarDisplayEffect.setVisibility(View.GONE);
        mBarDisplayPattern.setVisibility(View.GONE);
        mBarOthers.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }


    private void setVisibleSecondBar(View view){
        mSecondBarSwitchEffect.setVisibility(View.GONE);
        mSecondBarSwitchOrder.setVisibility(View.GONE);
        mSecondBarSwitchTime.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);

    }


    //按下退出键
    private void onClose(){

    }
    //按下确认设置
    private void onConfirm(){

    }

    private void onDisplayEffectSet(@Fields.SWITCH_EFFECT int switchEffect){
        mIvPreview.setAnimType(switchEffect);
        mIvPreview.startAnim();
    }



}
