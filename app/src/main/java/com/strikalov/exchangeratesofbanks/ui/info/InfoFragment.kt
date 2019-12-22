package com.strikalov.exchangeratesofbanks.ui.info

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.di.DI
import com.strikalov.exchangeratesofbanks.presentation.info.InfoPresenter
import com.strikalov.exchangeratesofbanks.presentation.info.InfoView
import com.strikalov.exchangeratesofbanks.showSnackMessage
import com.strikalov.exchangeratesofbanks.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_info.*
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
        private const val BANK_NAME_KEY = "bank_name_key"
        private const val BANK_LOGO_LINK_KEY = "bank_logo_link_key"

        fun newInstance(idBank: Int, bankName: String, bankLogoLink: String) = InfoFragment().apply {
            arguments = Bundle().apply {
                putInt(ID_BANK_KEY, idBank)
                putString(BANK_NAME_KEY, bankName)
                putString(BANK_LOGO_LINK_KEY, bankLogoLink)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
        arguments?.let {

            val idBank = it.getInt(ID_BANK_KEY)
            idBank?.let {
                presenter.onGetIdBank(idBank)
            }

            val bankName = it.getString(BANK_NAME_KEY)
            bankName?.let {
                presenter.onGetBankName(bankName)
            }

            val bankLogoLink = it.getString(BANK_LOGO_LINK_KEY)
            bankLogoLink?.let {
                presenter.onGetBankLogoLink(bankLogoLink)
            }
        }
    }

    override fun showBankName(bankName: String) {
        bank_name.text = bankName
    }

    override fun showBankLogo(bankLogoLink: String) {
        Glide.with(requireContext())
            .load(bankLogoLink)
            .into(bank_logo)
    }

    override fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progress_bar.visibility = View.GONE
    }

    override fun showInfoPanel(site: String, phoneNumber: String, address: String) {
        site_title.visibility = View.VISIBLE
        phone_number_title.visibility = View.VISIBLE
        address_title.visibility = View.VISIBLE
        site_name.visibility = View.VISIBLE
        site_name.text = site
        phone_value.visibility = View.VISIBLE
        phone_value.text = phoneNumber
        address_value.visibility = View.VISIBLE
        address_value.text = address
    }

    override fun hideInfoPanel() {
        site_title.visibility = View.GONE
        phone_number_title.visibility = View.GONE
        address_title.visibility = View.GONE
        site_name.visibility = View.GONE
        phone_value.visibility = View.GONE
        address_value.visibility = View.GONE
    }

    override fun showSnackBarMessage(messageId: Int) {
        showSnackMessage(getString(messageId))
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}