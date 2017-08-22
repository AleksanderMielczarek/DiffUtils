package com.github.aleksandermielczarek.diffutils

import com.github.aleksandermielczarek.diffutils.domain.Generator
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider

/**
 * Created by Aleksander Mielczarek on 22.08.2017.
 */
val appModule = Kodein.Module {
    bind() from provider { Generator() }
}