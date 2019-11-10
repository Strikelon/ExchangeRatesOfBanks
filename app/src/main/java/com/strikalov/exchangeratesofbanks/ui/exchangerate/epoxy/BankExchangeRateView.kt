package com.strikalov.exchangeratesofbanks.ui.exchangerate.epoxy

import android.content.Context
import android.view.View
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.bumptech.glide.Glide
import com.strikalov.exchangeratesofbanks.R
import kotlinx.android.synthetic.main.item_bank_exchange_rate.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class BankExchangeRateView(context: Context): CardView(context) {

    init {
        View.inflate(context, R.layout.item_bank_exchange_rate, this)
    }

    @TextProp
    fun bankName(name: CharSequence) {
        bank_name.text = name
    }

    @TextProp
    fun saleUsdVal(value: CharSequence) {
        sale_usd_val.text = value
    }

    @TextProp
    fun saleEurVal(value: CharSequence) {
        sale_eur_val.text = value
    }

    @TextProp
    fun purchaseUsdVal(value: CharSequence) {
        purchase_usd_val.text = value
    }

    @TextProp
    fun purchaseEurVal(value: CharSequence) {
        purchase_eur_val.text = value
    }

    @ModelProp
    fun logoUrl(url: String) {
        Glide.with(context)
            .load(url)
            .into(bank_logo)
    }

    @CallbackProp
    fun onCalculatorButtonClick(clickListener : (() -> Unit)?) {
        calculator_button.setOnClickListener {
            clickListener?.invoke()
        }
    }

    @CallbackProp
    fun onInfoButtonClick(clickListener : (() -> Unit)?) {
        info_button.setOnClickListener{
            clickListener?.invoke()
        }

    }

    @CallbackProp
    fun onLocationButtonClick(clickListener : (() -> Unit)?) {
        location_button.setOnClickListener {
            clickListener?.invoke()
        }
    }

}