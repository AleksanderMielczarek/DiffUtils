package com.github.aleksandermielczarek.diffutils.ui

import android.app.Fragment
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.aleksandermielczarek.diffutils.R
import com.github.aleksandermielczarek.diffutils.ui.binding.BindingFragment
import com.github.aleksandermielczarek.diffutils.ui.fastadapter.FastAdapterFragment
import com.github.aleksandermielczarek.diffutils.ui.sdk.SdkFragment
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG_FAST_ADAPTER_FRAGMENT = "FastAdapterFragment"
        const val TAG_BINDING_FRAGMENT = "BindingFragment"
        const val TAG_SDK_FRAGMENT = "SdkFragment"
    }

    private val navigationClicks: Observable<MenuItem> by lazy {
        RxBottomNavigationView.itemSelections(bottomNavigation).cache()
    }
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        navigationClick(R.id.action_fast_adapter, TAG_FAST_ADAPTER_FRAGMENT) { FastAdapterFragment() }
        navigationClick(R.id.action_binding, TAG_BINDING_FRAGMENT) { BindingFragment() }
        navigationClick(R.id.action_sdk, TAG_SDK_FRAGMENT) { SdkFragment() }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun navigationClick(@IdRes item: Int, tag: String, fragment: () -> Fragment) {
        navigationClicks.filter { it.itemId == item }
                .subscribeBy { replaceFragment(tag, fragment) }
                .addTo(disposables)
    }

    private fun replaceFragment(tag: String, fragment: () -> Fragment) {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment(), tag)
                    .commit()
        }
    }
}