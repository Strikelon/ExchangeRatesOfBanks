package com.strikalov.exchangeratesofbanks.ui.exchangerate.conversion

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.strikalov.exchangeratesofbanks.R
import kotlinx.android.synthetic.main.bottom_sheet_conversion.view.*

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

    private var isPurchaseEditTextChanged : Boolean = false
    private var isSaleEditTextChanged : Boolean = false

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

        val coefficientPurchase : Double = arguments?.getDouble(COEFFICIENT_PURCHASE_KEY) ?: 1.0

        val coefficientSale : Double = arguments?.getDouble(COEFFICIENT_SALE_KEY) ?: 1.0

        view.main_title.text = bankName

        view.currency_title.text = currency

        view.ruble_purchase_et.setText(coefficientPurchase.toString())

        view.currency_purchase_et.setText("1")

        view.ruble_sale_et.setText(coefficientSale.toString())

        view.currency_sale_et.setText("1")

        view.ruble_purchase_et.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(editable: Editable?) {
                val editableText = editable.toString()
                if (editableText.isEmpty()) {
                    if (view.currency_purchase_et.text.isNotEmpty()) {
                        view.currency_purchase_et.setText("")
                    }
                    return
                }
                if (isPurchaseEditTextChanged) {
                    isPurchaseEditTextChanged = false
                    return
                }
                val value : Double = editableText.toDouble()
                val currencyValue : Double = value.div(coefficientPurchase)
                isPurchaseEditTextChanged = true
                view.currency_purchase_et.setText(currencyValue.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        view.currency_purchase_et.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(editable: Editable?) {
                val editableText = editable.toString()
                if (editableText.isEmpty()) {
                    if (view.ruble_purchase_et.text.isNotEmpty()) {
                        view.ruble_purchase_et.setText("")
                    }
                    return
                }
                if (isPurchaseEditTextChanged) {
                    isPurchaseEditTextChanged = false
                    return
                }
                val value : Double = editableText.toDouble()
                val rubleValue : Double = value * coefficientPurchase
                isPurchaseEditTextChanged = true
                view.ruble_purchase_et.setText(rubleValue.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        view.ruble_sale_et.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(editable: Editable?) {
                val editableText = editable.toString()
                if (editableText.isEmpty()) {
                    if (view.currency_sale_et.text.isNotEmpty()) {
                        view.currency_sale_et.setText("")
                    }
                    return
                }
                if (isSaleEditTextChanged) {
                    isSaleEditTextChanged = false
                    return
                }
                val value : Double = editableText.toDouble()
                val currencyValue : Double = value.div(coefficientSale)
                isSaleEditTextChanged = true
                view.currency_sale_et.setText(currencyValue.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        view.currency_sale_et.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(editable: Editable?) {
                val editableText = editable.toString()
                if (editableText.isEmpty()) {
                    if (view.ruble_sale_et.text.isNotEmpty()) {
                        view.ruble_sale_et.setText("")
                    }
                    return
                }
                if (isSaleEditTextChanged) {
                    isSaleEditTextChanged = false
                    return
                }
                val value : Double = editableText.toDouble()
                val rubleValue : Double = value * coefficientSale
                isSaleEditTextChanged = true
                view.ruble_sale_et.setText(rubleValue.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        view.setOnClickListener {
            dismiss()
        }

    }

}