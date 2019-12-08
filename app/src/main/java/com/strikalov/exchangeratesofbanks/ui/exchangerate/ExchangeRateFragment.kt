package com.strikalov.exchangeratesofbanks.ui.exchangerate

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.di.DI
import com.strikalov.exchangeratesofbanks.entity.ActionEnum
import com.strikalov.exchangeratesofbanks.entity.CurrencyEnum
import com.strikalov.exchangeratesofbanks.entity.ExchangeRates
import com.strikalov.exchangeratesofbanks.entity.ExtremumEnum
import com.strikalov.exchangeratesofbanks.presentation.exchangerate.ExchangeRatePresenter
import com.strikalov.exchangeratesofbanks.presentation.exchangerate.ExchangeRateView
import com.strikalov.exchangeratesofbanks.showSnackMessage
import com.strikalov.exchangeratesofbanks.ui.BaseFragment
import com.strikalov.exchangeratesofbanks.ui.exchangerate.epoxy.BankExchangeRateController
import kotlinx.android.synthetic.main.fragment_exchange_rate.*
import kotlinx.android.synthetic.main.layout_bank_sorting.*
import kotlinx.android.synthetic.main.layout_bank_sorting_controller.*
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
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSortingController()
        controller.addModelBuildListener {
            recycler_view.scrollToPosition(0)
        }
    }

    private fun setupRecyclerView() {
        scroll_up_fab_button.hide()
        recycler_view.setController(controller)
        val itemOffset: Int = context!!.resources.getDimensionPixelSize(R.dimen.item_padding)
        itemOffsetDecoration = EpoxyItemSpacingDecorator(itemOffset)
        recycler_view.addItemDecoration(itemOffsetDecoration)
        recycler_view.addOnScrollListener(object :  RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0 && scroll_up_fab_button.visibility == View.VISIBLE) {
                    scroll_up_fab_button.hide()
                } else if (dy < 0 && scroll_up_fab_button.visibility != View.VISIBLE) {
                    scroll_up_fab_button.show()
                }


                val isTheTop =
                    recyclerView.layoutManager?.isViewPartiallyVisible(recyclerView.getChildAt(0), true, true)
                        ?: false

                if (isTheTop) {
                    scroll_up_fab_button.hide()
                }
            }
        })
        scroll_up_fab_button.setOnClickListener {
            scroll_up_fab_button.hide()
            presenter.onScrollUpFabButtonClick()
        }
    }

    private fun setupSortingController() {
        sorting_open_close_button.setOnClickListener {
            presenter.onSortingControllerButtonClick()
        }

        sort_button.setOnClickListener {
            val currency = when (currency_radio_group.checkedRadioButtonId) {
                R.id.radio_button_dollar -> { CurrencyEnum.DOLLAR }
                R.id.radio_button_euro -> { CurrencyEnum.EURO }
                else -> { CurrencyEnum.NONE }
            }
            val action = when (purchase_sale_radio_group.checkedRadioButtonId) {
                R.id.radio_button_purchase -> { ActionEnum.PURCHASE }
                R.id.radio_button_sale -> { ActionEnum.SALE }
                else -> { ActionEnum.NONE }
            }
            val extremum = when (min_max_radio_group.checkedRadioButtonId) {
                R.id.radio_button_max -> { ExtremumEnum.MAX }
                R.id.radio_button_min -> { ExtremumEnum.MIN }
                else -> { ExtremumEnum.NONE }
            }
            presenter.onSortButtonClick(currency, action, extremum)
        }

        cancel_sort_button.setOnClickListener {
            presenter.onCancelSortButtonClick()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exchange_rate_menu, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.refresh_exchange_rate -> {
                presenter.onRefreshExchangeRate()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
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

    override fun showSnackBarMessage(messageId: Int) {
        showSnackMessage(getString(messageId))
    }

    override fun scrollRecyclerViewToPosition(position: Int) {
        recycler_view.scrollToPosition(position)
    }

    override fun openSortingController() {
        sorting_open_close_button.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
        bank_sorting_controller.visibility = View.VISIBLE
    }

    override fun closeSortingController() {
        sorting_open_close_button.setImageResource(R.drawable.ic_arrow_down_black_24dp)
        bank_sorting_controller.visibility = View.GONE
    }

    override fun resetSortRadioButtons() {
        currency_radio_group.check(R.id.radio_button_dollar)
        purchase_sale_radio_group.check(R.id.radio_button_purchase)
        min_max_radio_group.check(R.id.radio_button_max)
    }

    override fun showBankSorting() {
        bank_sorting.visibility = View.VISIBLE
    }

    override fun hideBankSorting() {
        bank_sorting.visibility = View.GONE
    }

    override fun setTitle(id: Int) {
        activity?.title = getString(id)
    }
}