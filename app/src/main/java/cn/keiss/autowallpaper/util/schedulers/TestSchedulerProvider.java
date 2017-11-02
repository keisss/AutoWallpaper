package cn.keiss.autowallpaper.util.schedulers;

import android.support.annotation.NonNull;

import cn.keiss.autowallpaper.util.schedulers.BaseSchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * @author hekai
 * @date 2017/11/2
 */

public class TestSchedulerProvider implements BaseSchedulerProvider {
    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
