package com.act

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.com.myapplication.databinding.ActivityMainBinding
import com.lxj.xpopup.XPopup
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.operators.observable.*
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }

    override fun onContentChanged() {
        super.onContentChanged()
        binding.apply {

            creation.setOnClickListener {
                action.text = ""
                XPopup.Builder(this@MainActivity).asCenterList(
                    creation.text,
                    CREATE_LIST
                ) { _, text ->
                    val sb = StringBuilder()
                    sb.append("$text :").appendLine()
                    when (text) {
                        "create" -> {
                            Observable.create<MutableList<String>> {
                                action.text = sb.append("发射开始 ${Thread.currentThread().name}")
                                it.onError(java.lang.NullPointerException())
                                it.onNext(mutableListOf("20", "32", "34"))
                                it.onComplete()
                                action.text = sb.append("发射完成")
                            }.observeOn(AndroidSchedulers.mainThread())
                                .doOnNext {
                                    action.text = sb.append("doOnNext ${it[0]}")
                                        .appendLine()
                                }
                                .doAfterNext {
                                    action.text = sb.append("doAfterNext ${it[0]}")
                                        .appendLine()
                                }
                                .doOnComplete {
                                    action.text = sb.append("doOnComplete $it")
                                        .appendLine()
                                }
                                .doOnError {
                                    action.text = sb.append("doOnError $it")
                                        .appendLine()
                                }
                                .onErrorComplete {
                                    action.text = sb.append("onErrorComplete $it")
                                        .appendLine()
                                    false
                                }
                                .subscribe(object : Observer<List<String>> {
                                    override fun onSubscribe(d: Disposable) {
                                        action.text = sb.append("onSubscribe ${d.isDisposed}")
                                            .appendLine()
                                    }

                                    override fun onNext(t: List<String>) {
                                        action.text = sb.append("onNext ${t.get(0)}").appendLine()
                                    }

                                    override fun onError(e: Throwable) {
                                        action.text = sb.append("onError $e").appendLine()
                                    }

                                    override fun onComplete() {
                                        action.text = sb.append("onComplete").appendLine()
                                    }

                                })

                        }
                        "just" -> {
                            ObservableJust(arrayListOf("1", "2", "3"))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnNext {
                                    action.text = sb.append("doOnNext ${it[0]}")
                                        .appendLine()
                                }
                                .doAfterNext {
                                    action.text = sb.append("doAfterNext ${it[0]}")
                                        .appendLine()
                                }
                                .doOnComplete {
                                    action.text = sb.append("doOnComplete $it")
                                        .appendLine()
                                }
                                .doOnError {
                                    action.text = sb.append("doOnError $it")
                                        .appendLine()
                                }
                                .onErrorComplete {
                                    action.text = sb.append("onErrorComplete $it")
                                        .appendLine()
                                    false
                                }
                                .subscribe(object : Observer<List<String>> {
                                    override fun onSubscribe(d: Disposable) {
                                        action.text = sb.append("onSubscribe ${d.isDisposed}")
                                            .appendLine()
                                    }

                                    override fun onNext(t: List<String>) {
                                        action.text = sb.append("onNext ${t[0]}").appendLine()
                                    }

                                    override fun onError(e: Throwable) {
                                        action.text = sb.append("onError $e").appendLine()
                                    }

                                    override fun onComplete() {
                                        action.text = sb.append("onComplete").appendLine()
                                    }

                                })
                        }
                        "timer" -> {
                            ObservableTimer(1000, TimeUnit.MILLISECONDS, Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Observer<Long> {
                                    override fun onSubscribe(d: Disposable) {
                                        action.text = sb.append("onSubscribe ${d.isDisposed}")
                                            .appendLine()
                                    }

                                    override fun onNext(t: Long) {
                                        action.text = sb.append("onNext $t").appendLine()
                                    }

                                    override fun onError(e: Throwable) {
                                        action.text = sb.append("onError $e").appendLine()
                                    }

                                    override fun onComplete() {
                                        action.text = sb.append("onComplete").appendLine()
                                    }

                                })
                        }
                        "interval" -> {
                            var da: Disposable? = null
                            ObservableInterval(
                                1,
                                2,
                                TimeUnit.SECONDS,
                                Schedulers.io()
                            ).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Observer<Long> {
                                    override fun onSubscribe(d: Disposable) {
                                        da = d
                                        action.text = sb.append("onSubscribe ${d.isDisposed}")
                                            .appendLine()
                                    }

                                    override fun onNext(t: Long) {
                                        action.text = sb.append("onNext $t").appendLine()
                                        if (t > 10) {
                                            da?.dispose()
                                        }
                                    }

                                    override fun onError(e: Throwable) {
                                        action.text = sb.append("onError $e").appendLine()
                                    }

                                    override fun onComplete() {
                                        action.text = sb.append("onComplete").appendLine()
                                    }

                                })
                        }
                        "rang" -> {
                            ObservableRange(1, 10).observeOn(AndroidSchedulers.mainThread())
                                .doOnNext {
                                    action.text = sb.append("doOnNext $it")
                                        .appendLine()
                                }
                                .doAfterNext {
                                    action.text = sb.append("doAfterNext $it")
                                        .appendLine()
                                }
                                .doOnComplete {
                                    action.text = sb.append("doOnComplete $it")
                                        .appendLine()
                                }
                                .doOnError {
                                    action.text = sb.append("doOnError $it")
                                        .appendLine()
                                }
                                .onErrorComplete {
                                    action.text = sb.append("onErrorComplete $it")
                                        .appendLine()
                                    false
                                }
                                .subscribe(object : Observer<Int> {
                                    override fun onSubscribe(d: Disposable) {
                                        action.text = sb.append("onSubscribe ${d.isDisposed}")
                                            .appendLine()
                                    }

                                    override fun onNext(t: Int) {
                                        action.text = sb.append("onNext $t").appendLine()
                                    }

                                    override fun onError(e: Throwable) {
                                        action.text = sb.append("onError $e").appendLine()
                                    }

                                    override fun onComplete() {
                                        action.text = sb.append("onComplete").appendLine()
                                    }

                                })

                        }
                        "fromArray" -> {
                            ObservableFromArray(arrayOf("2", "4", "6")).subscribe(object :
                                Observer<String> {
                                override fun onSubscribe(d: Disposable) {
                                    action.text = sb.append("onSubscribe ${d.isDisposed}")
                                        .appendLine()
                                }

                                override fun onNext(t: String) {
                                    action.text = sb.append("onNext $t").appendLine()
                                }

                                override fun onError(e: Throwable) {
                                    action.text = sb.append("onError $e").appendLine()
                                }

                                override fun onComplete() {
                                    action.text = sb.append("onComplete").appendLine()
                                }
                            })
                        }
                        "fromIterable" -> {
                            ObservableFromIterable(arrayListOf("c", "d")).subscribe(object :
                                Observer<String> {
                                override fun onSubscribe(d: Disposable) {
                                    action.text = sb.append("onSubscribe ${d.isDisposed}")
                                        .appendLine()
                                }

                                override fun onNext(t: String) {
                                    action.text = sb.append("onNext $t").appendLine()
                                }

                                override fun onError(e: Throwable) {
                                    action.text = sb.append("onError $e").appendLine()
                                }

                                override fun onComplete() {
                                    action.text = sb.append("onComplete").appendLine()
                                }
                            })
                        }
                        "fromAction" -> {
                            ObservableFromAction<String> {
                                sb.append("Action").appendLine()
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object :
                                    Observer<String> {
                                    override fun onSubscribe(d: Disposable) {
                                        action.text = sb.append("onSubscribe ${d.isDisposed}")
                                            .appendLine()
                                    }

                                    override fun onNext(t: String) {
                                        action.text = sb.append("onNext $t").appendLine()
                                    }

                                    override fun onError(e: Throwable) {
                                        action.text = sb.append("onError $e").appendLine()
                                    }

                                    override fun onComplete() {
                                        action.text = sb.append("onComplete").appendLine()
                                    }
                                })
                        }

                        "fromFuture" -> {
                            ObservableFromFuture(
                                Executors.newSingleThreadScheduledExecutor()
                                    .submit<String> {
                                        sb.append("FromFuture").appendLine()
                                        "aaa"
                                    }, 10, TimeUnit.SECONDS
                            ).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object :
                                    Observer<String> {
                                    override fun onSubscribe(d: Disposable) {
                                        action.text = sb.append("onSubscribe ${d.isDisposed}")
                                            .appendLine()
                                    }

                                    override fun onNext(t: String) {
                                        action.text = sb.append("onNext $t").appendLine()
                                    }

                                    override fun onError(e: Throwable) {
                                        action.text = sb.append("onError $e").appendLine()
                                    }

                                    override fun onComplete() {
                                        action.text = sb.append("onComplete").appendLine()
                                    }
                                })
                        }
                        "fromCallable" -> {
                            ObservableFromCallable {
                                "FromCallable"
                            }.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object :
                                    Observer<String> {
                                    override fun onSubscribe(d: Disposable) {
                                        action.text = sb.append("onSubscribe ${d.isDisposed}")
                                            .appendLine()
                                    }

                                    override fun onNext(t: String) {
                                        action.text = sb.append("onNext $t").appendLine()
                                    }

                                    override fun onError(e: Throwable) {
                                        action.text = sb.append("onError $e").appendLine()
                                    }

                                    override fun onComplete() {
                                        action.text = sb.append("onComplete").appendLine()
                                    }
                                })
                        }
                        "error" -> {


                        }
                        "empty" -> {
                            ObservableEmpty.INSTANCE.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object :
                                    Observer<Any> {
                                    override fun onSubscribe(d: Disposable) {
                                        action.text = sb.append("onSubscribe ${d.isDisposed}")
                                            .appendLine()
                                    }

                                    override fun onNext(t: Any) {
                                        action.text = sb.append("onNext $t").appendLine()
                                    }

                                    override fun onError(e: Throwable) {
                                        action.text = sb.append("onError $e").appendLine()
                                    }

                                    override fun onComplete() {
                                        action.text = sb.append("onComplete").appendLine()
                                    }
                                })
                        }
                    }
                }.show()
            }

            transformation.setOnClickListener {
                action.text = ""
                XPopup.Builder(this@MainActivity).asCenterList(
                    transformation.text,
                    TRANSFORMATION_LIST
                ) { _, text ->
                    val sb = StringBuilder()
                    sb.append("$text :").appendLine()
                    when (text) {
                        "map" -> {
                            Observable.just(1, 2, 3)
                                .map {
                                    sb.append("map 发射 $it").appendLine()
                                    "map---$it"
                                }.subscribe {
                                    action.text = sb.append("onNext $it")
                                        .appendLine()
                                }
                        }
                        "flatMap" -> {//无序
                            Observable.just("A", "B", "C")
                                .flatMap { a: String ->
                                    sb.append("Sa= $a").appendLine()
                                    Observable.intervalRange(
                                        1,
                                        3,
                                        0,
                                        0,
                                        TimeUnit.SECONDS
                                    ).map { b: Long -> "($a, $b)" }
                                }.observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    action.text = sb.append("onNext $it")
                                        .appendLine()
                                }
                        }
                        "concatMap" -> {//有序
                            Observable.just("A", "B", "C")
                                .concatMap { a: String ->
                                    sb.append("Sa= $a").appendLine()
                                    Observable.intervalRange(
                                        1,
                                        3,
                                        0,
                                        0,
                                        TimeUnit.SECONDS
                                    ).map { b: Long -> "($a, $b)" }
                                }.observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    action.text = sb.append("onNext $it")
                                        .appendLine()
                                }
                        }
                        "switchMap" -> {//只返回最后一个
                            Observable.just("A", "B", "C")
                                .switchMap { a: String ->
                                    sb.append("Sa= $a").appendLine()
                                    Observable.intervalRange(
                                        1,
                                        3,
                                        0,
                                        0,
                                        TimeUnit.SECONDS
                                    ).map { b: Long -> "($a, $b)" }
                                }.observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    action.text = sb.append("onNext $it")
                                        .appendLine()
                                }
                        }
                        "scan" -> {//连续地对数据序列的每一项应用一个函数，然后连续发射结果
                            Observable.just(1, 2, 3)
                                .scan { s, s1 ->
                                    s + s1
                                }.observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                    action.text = sb.append("onNext $it")
                                        .appendLine()
                                }
                        }
                        "cast" -> {
                            Observable.just(1, 2, 3, 4)
                                .cast(Integer::class.java)
                                .subscribe {
                                    action.text = sb.append("onNext $it")
                                        .appendLine()
                                }
                        }
                        "groupBy" -> {//将一个Observable按某个规则进行分组。分拆为一些Observables集合，它们中的每一个发射原始Observable的一个子集
                            Observable.just(1, 2, 3, 1, 2, 3, 4)
                                .groupBy {
                                    it > 2
                                }
                                .subscribe {
                                    action.text = sb.append("start1111:").appendLine()
                                    it.subscribe(object : Observer<Int> {
                                        override fun onSubscribe(d: Disposable) {
                                            action.text = sb.append("start:").appendLine()
                                        }

                                        override fun onNext(t: Int) {
                                            action.text = sb.append("onNext: $t").appendLine()
                                        }

                                        override fun onError(e: Throwable) {

                                        }

                                        override fun onComplete() {
                                            action.text = sb.append("end:").appendLine()
                                        }
                                    })
                                    action.text = sb.append("end1111:").appendLine()
                                }
                        }
                        "buffer" -> {
                            Observable.just(1, 2, 3, 4)
                                .buffer(5)
                                .subscribe {
                                    action.text = sb.append("onNext $it")
                                        .appendLine()
                                }
                        }
                        "window" -> {
                            Observable.range(1, 10) // skip=2代表原数据源每发射到第2个时跳过，并新开一个窗口
                                // count=3代表每个窗口最多发3个数据项后，就关闭窗口并新开一个窗口（窗体大小）
                                .window(3, 2)
                                .flatMapSingle { obj: Observable<Int> -> obj.toList() }
                                .subscribe {
                                    action.text = sb.append("onNext $it")
                                        .appendLine()
                                }
                        }
                    }
                }.show()
            }

        }
    }

    companion object {
        val CREATE_LIST =
            arrayOf(
                "create",
                "just",
                "timer",
                "interval",
                "rang",
                "fromArray",
                "fromIterable",
                "fromAction",
                "fromFuture",
                "fromCallable",
                "error",
                "empty"
            )

        val TRANSFORMATION_LIST =
            arrayOf(
                "map",
                "flatMap",
                "concatMap",
                "switchMap",
                "scan",
                "cast",
                "groupBy",
                "buffer",
                "window"
            )
    }

}
