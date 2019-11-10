package com.strikalov.exchangeratesofbanks.ui.exchangerate

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.di.DI
import com.strikalov.exchangeratesofbanks.entity.ExchangeRates
import com.strikalov.exchangeratesofbanks.presentation.exchangerate.ExchangeRatePresenter
import com.strikalov.exchangeratesofbanks.presentation.exchangerate.ExchangeRateView
import com.strikalov.exchangeratesofbanks.ui.BaseFragment
import com.strikalov.exchangeratesofbanks.ui.exchangerate.epoxy.BankExchangeRateController
import kotlinx.android.synthetic.main.fragment_exchange_rate.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class ExchangeRateFragment : BaseFragment(), ExchangeRateView {

    override val layoutRes = R.layout.fragment_exchange_rate

    override val parentScopeName = DI.APP_SCOPE

    @InjectPresenter
    lateinit var presenter: ExchangeRatePresenter

    @ProvidePresenter
    fun providePresenter() : ExchangeRatePresenter =
        scope.getInstance(ExchangeRatePresenter::class.java)

    private val controller: BankExchangeRateController by lazy {
        BankExchangeRateController(
            onCalculatorClickListener = presenter::onCalculatorClick,
            onInfoClickListener = presenter::onInfoClick,
            onLocationClickListener = presenter::onLocationClick
        )
    }

    private lateinit var itemOffsetDecoration: RecyclerView.ItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recycler_view.setController(controller)
        val itemOffset: Int = context!!.resources.getDimensionPixelSize(R.dimen.item_padding)
        itemOffsetDecoration = EpoxyItemSpacingDecorator(itemOffset)
        recycler_view.addItemDecoration(itemOffsetDecoration)

    }

    override fun updateExchangeRates(data: List<ExchangeRates.ExchangeRate>) {
        controller.setData(data)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    override fun showRecyclerView() {
        recycler_view.visibility = View.VISIBLE
    }

    override fun hideRecyclerView() {
        recycler_view.visibility = View.GONE
    }
}