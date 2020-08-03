package com.github.ntngel1.linkedout.lib_extensions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    abstract val layoutId: Int

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        this.observe(viewLifecycleOwner, Observer {
            observer.invoke(it)
        })
    }
}