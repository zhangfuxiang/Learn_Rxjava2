package com.example.fuxiangzhang.learn_rxjava2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Fuxiang.Zhang on 2017/9/5.
 */

public class MainActivity extends AppCompatActivity {
    public static final String TAG ="aaa" ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 通过Map, 可以将上游发来的事件转换为任意的类型, 可以是一个Object, 也可以是一个集合
         */
        /*Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> mObservableEmitter) throws Exception {
                mObservableEmitter.onNext(1);
                mObservableEmitter.onNext(2);
                mObservableEmitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer mInteger) throws Exception {
                return "this is result"+mInteger;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                Log.e("aaa", "accept: "+mS );
            }
        });*/

        /**
         * flatmap的使用
         */
/*        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> mObservableEmitter) throws Exception {
                mObservableEmitter.onNext(1);
                mObservableEmitter.onNext(2);
                mObservableEmitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer mInteger) throws Exception {
                final List<String> mList=new ArrayList<>();
                for (int mI = 0; mI < 3; mI++) {
                    mList.add("I am value"+mInteger);
                }
                return Observable.fromIterable(mList).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String mS) throws Exception {
                Log.e("aaa", "accept: "+mS );
            }
        });*/


        /**
         * zip得使用
         */
/*        Observable<Integer> mObservable1=Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Thread.sleep(1000);

                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Thread.sleep(1000);

                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Thread.sleep(1000);

                Log.d(TAG, "emit 4");
                emitter.onNext(4);
                Thread.sleep(1000);

*//*                Log.d(TAG, "emit complete1");
                emitter.onComplete();*//*


            }
        }).subscribeOn(Schedulers.io());

        Observable<String> mObservable2=Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emit A");
                emitter.onNext("A");
                Thread.sleep(1000);

                Log.d(TAG, "emit B");
                emitter.onNext("B");
                Thread.sleep(1000);

                Log.d(TAG, "emit C");
                emitter.onNext("C");
                Thread.sleep(1000);

*//*                Log.d(TAG, "emit complete2");
                emitter.onComplete();*//*


            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(mObservable1, mObservable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer mInteger, String mS) throws Exception {
                return mInteger+mS;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String mO) throws Exception {
                Log.e(TAG, "accept: "+mO );
            }
        });*/

        /**
         * 背压flowable使用
         */
        Flowable.create(new FlowableOnSubscribe<Integer>() {

            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
   /*             Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
                Log.d(TAG, "emit complete");
                emitter.onComplete();*/
                for (int i=0;i<10;i++){
                    emitter.onNext(i);
                    Log.d(TAG, "emit "+i);
                }

            }
        }, BackpressureStrategy.DROP)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription mSubscription) {
                Log.d(TAG, "onSubscribe");
                mSubscription = mSubscription;
                mSubscription.request(5);
            }

            @Override
            public void onNext(Integer mInteger) {
                Log.d(TAG, "onNext: " + mInteger);
            }

            @Override
            public void onError(Throwable mThrowable) {
                Log.w(TAG, "onError: ", mThrowable);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }


}
