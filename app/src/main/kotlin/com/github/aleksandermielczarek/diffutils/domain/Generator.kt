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
        generate(entityIdProcessor) { id, data -> EntityId(id, data, data, data) }
    }

    fun generateNoId() {
        generate(entityNoIdProcessor) { _, data -> EntityNoId(data, data, data) }
    }

    private fun <T> generate(entityProcessor: FlowableProcessor<List<T>>, entity: (id: Int, data: String) -> T) {
        Completable.fromCallable {
            val entities = Flowable.range(0, Random().nextInt(10) + 1)
                    .map { entity(it, "Data$it") }
                    .toList()
                    .blockingGet()
            Collections.shuffle(entities)
            entityProcessor.onNext(entities)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}