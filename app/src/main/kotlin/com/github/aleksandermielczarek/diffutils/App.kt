package com.github.aleksandermielczarek.diffutils

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.github.salomonbrys.kodein.lazy

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        import(autoAndroidModule(this@App))
        import(appModule)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(androidActivityScope.lifecycleManager)
    }
}