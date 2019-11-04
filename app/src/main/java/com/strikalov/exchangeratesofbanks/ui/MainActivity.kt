package com.strikalov.exchangeratesofbanks.ui

import android.os.Bundle
import android.util.Log
import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.di.DI
import com.strikalov.exchangeratesofbanks.model.BanksDataRepository
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import timber.log.Timber
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(DI.APP_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
