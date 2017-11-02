package cn.keiss.autowallpaper.baselib;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author hekai
 * @date 2017/10/28
 */

public interface BasePresenter {
    void subscribe();
    void unSubscribe();


}
