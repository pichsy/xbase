package com.pichs.base.rxjava;

import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: wu
 */
public class RxjavaUtils {

    /**
     * 子线程中执行，主线程中回调
     */
    public static <T, R> void io2main(T t, RxAction<T, R> ioAction, final RxResult<R> result) {
        Observable
                .just(t)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<T, ObservableSource<R>>() {
                    @Override
                    public ObservableSource<R> apply(@NonNull T t) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<R>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<R> emitter) throws Exception {
                                emitter.onNext(ioAction.run(t));
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull R r) {
                        result.onResult(r);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 子线程中执行，主线程中回调
     */
    public static <R> void io2main(RxAction<Integer, R> ioAction, final RxResult<R> result) {
        Observable
                .just(1)
                .subscribeOn(Schedulers.io())
                .flatMap((Function<Integer, ObservableSource<R>>) t ->
                        Observable.create((ObservableOnSubscribe<R>) emitter ->
                                emitter.onNext(ioAction.run(t))))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull R r) {
                        result.onResult(r);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 主线程中执行，子线程中回调
     */
    public static <R> void main2io(RxAction<Integer, R> ioAction, final RxResult<R> result) {
        Observable
                .just(1)
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Integer, ObservableSource<R>>) t ->
                        Observable.create((ObservableOnSubscribe<R>) emitter ->
                                emitter.onNext(ioAction.run(t))))
                .observeOn(Schedulers.io())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull R r) {
                        result.onResult(r);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 子线程计时
     * 主线程回调
     *
     * @param times  倒计时的总秒数
     * @param result 结果
     */
    public static Disposable cutDownTimer(long times, RxResult<Long> result) {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(times + 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time -> result.onResult(times - time));
    }

    /**
     * 子线程计时
     * 主线程回调
     *
     * @param times  倒计时的总秒数
     * @param result 结果
     */
    public static Disposable cutDownTimer(long times, RxResult<Long> result, RxComplete complete) {
        return Observable.interval(0L, 1L, TimeUnit.SECONDS)
                .take(times + 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time -> result.onResult(times - time), error -> {
                }, complete::onComplete);
    }

    /**
     * 正计时，需要制定计时多少s
     * 主线程回调
     *
     * @param times  计时多少秒
     * @param result 回调
     */
    public static Disposable timer(long times, RxResult<Long> result) {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(times + 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result::onResult);
    }

    /**
     * 延迟做一些操作
     *
     * @param timeMills 毫秒级别延迟
     * @param result    延迟结束，回调
     */
    public static void delay(long timeMills, RxResult<Long> result) {
        Observable.timer(timeMills, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long time) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        result.onResult(timeMills);
                    }
                });
    }

    /**
     * 延迟做一些操作
     * 回调在io线程
     *
     * @param timeMills 毫秒级别延迟
     * @param result    延迟结束，回调
     */
    public static void delayInIO(long timeMills, RxResult<Long> result) {
        Observable.timer(timeMills, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long time) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        result.onResult(timeMills);
                    }
                });
    }


    /**
     * 使用背壓模式，这只是个例子，可自行扩展
     *
     * @param obj      泛型对象，需要处理的对象
     * @param ioAction 处理方法自己实现
     * @param result   处理结果
     * @param complete 处理完成
     * @param <T>      处理前对象类型
     * @param <R>      返回结果的对象类型
     *                 背压策略是最近最新的保留，其余忽略        BackpressureStrategy.LATEST
     */
    public static <T, R> void flow2Main(T obj, RxAction<T, R> ioAction, RxResult<R> result, RxComplete complete) {
        Flowable
                .just(obj)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<T, Flowable<R>>) t -> Flowable.create((FlowableOnSubscribe<R>) emitter -> emitter.onNext(ioAction.run(t)), BackpressureStrategy.LATEST))
                .subscribe(new FlowableSubscriber<R>() {
                    @Override
                    public void onSubscribe(@NotNull Subscription s) {

                    }

                    @Override
                    public void onNext(R r) {
                        result.onResult(r);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        complete.onComplete();
                    }
                });
    }

    public static <T, R> void flow2Main(BackpressureStrategy backpressureStrategy, T obj, RxAction<T, R> ioAction, RxResult<R> result, RxComplete complete) {
        Flowable.create((FlowableOnSubscribe<T>) emitter -> emitter.onNext(obj), backpressureStrategy).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<T, Flowable<R>>) t -> Flowable.create((FlowableOnSubscribe<R>) emitter -> emitter.onNext(ioAction.run(t)), backpressureStrategy))
                .subscribe(new FlowableSubscriber<R>() {
                    @Override
                    public void onSubscribe(@NotNull Subscription s) {

                    }

                    @Override
                    public void onNext(R r) {
                        result.onResult(r);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        complete.onComplete();
                    }
                });
    }

    public static void click(View view, View.OnClickListener listener) {
        Disposable subscribe = RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> listener.onClick(view));
    }

}
