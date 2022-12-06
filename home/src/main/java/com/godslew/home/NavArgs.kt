package com.godslew.home

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.navArgs
import java.lang.reflect.Method
import kotlin.reflect.KClass

@MainThread
inline fun <reified Args : NavArgs> Fragment.navArgsNoCache(): NavArgsLazyNoCache<Args> =
    NavArgsLazyNoCache(Args::class) {
        arguments ?: throw IllegalStateException("Fragment $this has null arguments")
    }

internal val methodSignature = arrayOf(Bundle::class.java)
internal val methodMap = ArrayMap<KClass<out NavArgs>, Method>()

/**
 * An implementation of [Lazy] used by [android.app.Activity.navArgs] and
 * [androidx.fragment.app.Fragment.navArgs].
 *
 * [argumentProducer] is a lambda that will be called during initialization to provide
 * arguments to construct an [Args] instance via reflection.
 */
class NavArgsLazyNoCache<Args : NavArgs>(
    private val navArgsClass: KClass<Args>,
    private val argumentProducer: () -> Bundle
) : Lazy<Args> {

    var isInitial: Boolean = true

    override val value: Args
        get() {
            val arguments = argumentProducer()
            val method: Method = methodMap[navArgsClass]
                ?: navArgsClass.java.getMethod("fromBundle", *methodSignature).also { method ->
                    // Save a reference to the method
                    methodMap[navArgsClass] = method
                }
            isInitial = false
            return method.invoke(null, arguments) as Args
        }

    override fun isInitialized(): Boolean = !isInitial
}