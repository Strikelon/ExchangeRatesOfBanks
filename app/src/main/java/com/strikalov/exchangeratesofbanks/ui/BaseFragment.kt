package com.strikalov.exchangeratesofbanks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import toothpick.Scope
import toothpick.Toothpick

abstract class BaseFragment : MvpAppCompatFragment(){

    abstract val layoutRes: Int

    var fragmentScopeName = "${javaClass.simpleName}_${hashCode()}"

    protected open val parentScopeName: String by lazy {
        (parentFragment as? BaseFragment)?.fragmentScopeName
            ?: throw RuntimeException("Must be parent fragment!")
    }

    protected open val scopeModuleInstaller: (Scope) -> Unit = {}

    protected lateinit var scope: Scope
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        initScope()
        super.onCreate(savedInstanceState)
    }

    private fun initScope() {
        scope = Toothpick.openScopes(parentScopeName, fragmentScopeName)
            .apply { scopeModuleInstaller(this) }
        Toothpick.inject(this, scope)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layoutRes, container, false)

    

    open fun onBackPressed() {}

}