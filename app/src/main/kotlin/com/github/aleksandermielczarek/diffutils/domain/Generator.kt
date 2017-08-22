package com.github.aleksandermielczarek.diffutils.domain

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
class Generator {

    private val entityIdProcessor: FlowableProcessor<List<EntityId>> = PublishProcessor.create()
    private val entityNoIdProcessor: FlowableProcessor<List<EntityNoId>> = PublishProcessor.create()
    val idEntities: Flowable<List<EntityId>> = entityIdProcessor.observeOn(AndroidSchedulers.mainThread())
    val noIdEntities: Flowable<List<EntityNoId>> = entityNoIdProcessor.observeOn(AndroidSchedulers.mainThread())

    fun generateId() {
        Completable.fromCallable {
            val entities = Flowable.range(0, Random().nextInt(10) + 1)
                    .map { EntityId(it, "Data$it", "Data$it", "Data$it") }
                    .toList()
                    .blockingGet()
            Collections.shuffle(entities)
            entityIdProcessor.onNext(entities)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun generateNoId() {
        Completable.fromCallable {
            val entities = Flowable.range(0, Random().nextInt(10) + 1)
                    .map { EntityNoId("Data$it", "Data$it", "Data$it") }
                    .toList()
                    .blockingGet()
            Collections.shuffle(entities)
            entityNoIdProcessor.onNext(entities)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}