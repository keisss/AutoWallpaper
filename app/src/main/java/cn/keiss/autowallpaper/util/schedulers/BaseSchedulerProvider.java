package cn.keiss.autowallpaper.util.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * @author hekai
 * @date 2017/11/2
 * 提供不同的调度器
 */

public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
