package com.github.aleksandermielczarek.diffutils.ui

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.github.aleksandermielczarek.diffutils.R
import com.github.aleksandermielczarek.diffutils.domain.EntityId
import com.github.aleksandermielczarek.diffutils.domain.EntityNoId
import com.github.aleksandermielczarek.diffutils.domain.Generator
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.view.RxMenuItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
abstract class DiffUtilFragment : Fragment() {

    private val kodein = LazyKodein(appKodein)
    private val generator: Generator by kodein.instance()
    protected val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        generator.idEntities.subscribeBy {
            Toast.makeText(activity, R.string.shuffle_id, Toast.LENGTH_SHORT).show()
            diffUtilId(it)
        }.addTo(disposables)
        generator.noIdEntities.subscribeBy {
            Toast.makeText(activity, R.string.shuffle_no_id, Toast.LENGTH_SHORT).show()
            diffUtilNoId(it)
        }.addTo(disposables)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_diff_util, menu)
        val shuffleIdItem = menu.findItem(R.id.action_shuffle_id)
        shuffleIdItem.tint(activity, R.color.colorWhite)
        RxMenuItem.clicks(shuffleIdItem)
                .subscribeBy { generator.generateId() }
                .addTo(disposables)
        val shuffleNoIdItem = menu.findItem(R.id.action_shuffle_no_id)
        shuffleNoIdItem.tint(activity, R.color.colorWhite)
        RxMenuItem.clicks(shuffleNoIdItem)
                .subscribeBy { generator.generateNoId() }
                .addTo(disposables)
    }

    protected abstract fun diffUtilId(entities: List<EntityId>)

    protected abstract fun diffUtilNoId(entities: List<EntityNoId>)

    private fun MenuItem.tint(context: Context, @ColorRes color: Int) {
        val drawable = DrawableCompat.wrap(icon)
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, color))
        icon = drawable
    }
}