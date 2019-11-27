package com.strikalov.exchangeratesofbanks.ui.info

import android.os.Bundle
import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.di.DI
import com.strikalov.exchangeratesofbanks.presentation.info.InfoPresenter
import com.strikalov.exchangeratesofbanks.presentation.info.InfoView
import com.strikalov.exchangeratesofbanks.ui.BaseFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import toothpick.Toothpick

class InfoFragment : BaseFragment(), InfoView {

    override val layoutRes = R.layout.fragment_info

    override val parentScopeName = DI.APP_SCOPE

    @InjectPresenter
    lateinit var presenter : InfoPresenter

    @ProvidePresenter
    fun providePresenter() : InfoPresenter =
        scope.getInstance(InfoPresenter::class.java)

    companion object{

        private const val ID_BANK_KEY = "id_bank_key"

        fun newInstance(idBank: Int) = InfoFragment().apply {
            arguments = Bundle().apply {
                putInt(ID_BANK_KEY, idBank)
            }
        }

    }

    private var idBank: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
        arguments?.let {
            idBank = it.getInt(ID_BANK_KEY)
        }
    }


    override fun checkInit() {
        Timber.tag("InfoFragment").i("checkInit() idBank = $idBank")
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}