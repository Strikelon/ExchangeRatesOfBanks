package com.strikalov.exchangeratesofbanks.ui.exchangerate.conversion

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.strikalov.exchangeratesofbanks.R
import kotlinx.android.synthetic.main.bottom_sheet_conversion.view.*
import timber.log.Timber

class CurrencyConversionBottomSheetDialog : BottomSheetDialogFragment() {

    companion object{
        private const val BANK_NAME_KEY = "bank_name_key"
        private const val CURRENCY_KEY = "currency_key"
        private const val COEFFICIENT_PURCHASE_KEY = "coefficient_purchase_key"
        private const val COEFFICIENT_SALE_KEY = "coefficient_sale_key"

        fun newInstance(bankName: String, currency: String, coefficientPurchase: Double, coefficientSale: Double): CurrencyConversionBottomSheetDialog {
            return CurrencyConversionBottomSheetDialog().apply {
                val args = Bundle().apply {
                    this.putString(BANK_NAME_KEY, bankName)
                    this.putString(CURRENCY_KEY, currency)
                    this.putDouble(COEFFICIENT_PURCHASE_KEY, coefficientPurchase)
                    this.putDouble(COEFFICIENT_SALE_KEY, coefficientSale)
                }
                arguments = args
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = BottomSheetDialog(context!!, R.style.FilterBottomSheetDialogTheme)

        val view = View.inflate(context, R.layout.bottom_sheet_conversion, null)

        val size = Point()
        val windowManager = context!!.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getSize(size)

        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.setContentView(view, params)

        setupUi(view)

        return dialog
    }

    private fun setupUi(view: View) {

        if (arguments == null) {
            return
        }

        val bankName = arguments?.getString(BANK_NAME_KEY)

        val currency = arguments?.getString(CURRENCY_KEY)

        val coefficientPurchase = arguments?.getDouble(COEFFICIENT_PURCHASE_KEY)

        val coefficientSale = arguments?.getDouble(COEFFICIENT_SALE_KEY)

        Timber.tag("CurrencyConversion").i("bankName = $bankName currency = $currency")

        Timber.tag("CurrencyConversion").i("coefficientPurchase = $coefficientPurchase coefficientSale = $coefficientSale")

        view.main_title.text = "Название"

        view.currency_title.text = "Доллар"

        view.setOnClickListener {
            dismiss()
        }

    }

}