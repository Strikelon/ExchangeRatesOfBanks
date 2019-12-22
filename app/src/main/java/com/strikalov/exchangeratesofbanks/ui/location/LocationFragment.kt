package com.strikalov.exchangeratesofbanks.ui.location

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.strikalov.exchangeratesofbanks.R
import com.strikalov.exchangeratesofbanks.di.DI
import com.strikalov.exchangeratesofbanks.entity.BankOffices
import com.strikalov.exchangeratesofbanks.presentation.location.LocationPresenter
import com.strikalov.exchangeratesofbanks.presentation.location.LocationView
import com.strikalov.exchangeratesofbanks.showSnackMessage
import com.strikalov.exchangeratesofbanks.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_location.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import toothpick.Toothpick

class LocationFragment : BaseFragment(), LocationView, OnMapReadyCallback {

    override val layoutRes = R.layout.fragment_location

    override val parentScopeName = DI.APP_SCOPE

    @InjectPresenter
    lateinit var presenter : LocationPresenter

    @ProvidePresenter
    fun providePresenter() : LocationPresenter =
        scope.getInstance(LocationPresenter::class.java)

    private lateinit var mMap: GoogleMap

    companion object{

        private const val ID_BANK_KEY = "id_bank_key"
        private const val ID_BANK_NAME = "id_bank_name"

        fun newInstance(idBank: Int, bankName: String) = LocationFragment().apply {
            arguments = Bundle().apply {
                putInt(ID_BANK_KEY, idBank)
                putString(ID_BANK_NAME, bankName)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
        arguments?.let {
            val bankName = it.getString(ID_BANK_NAME)
            bankName?.let {
                presenter.onGetBankName(bankName)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Timber.tag("MyTag").i("[LocationFragment] onMapReady")
        mMap = googleMap

        arguments?.let {
            val idBank = it.getInt(ID_BANK_KEY)
            idBank?.let {
                presenter.onMapReady(idBank)
            }
        }
    }

    override fun showBankOffices(bankOfficesList: List<BankOffices.BankOffice>) {
        val listOfOffices = mutableListOf<LatLng>()
        bankOfficesList.forEach {bankOffice ->
            val latLng = LatLng(bankOffice.latitude, bankOffice.longitude)
            listOfOffices.add(latLng)
            val snippet = StringBuffer(bankOffice.officePhone)
            bankOffice.workingHoursList.forEach {
                snippet.append(" ")
                    .append(it.workingTime)
            }
            mMap.addMarker(MarkerOptions()
                .position(latLng)
                .title(bankOffice.officeAddress)
                .snippet(snippet.toString()))
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(listOfOffices[0],10f))
    }

    override fun showSnackBarMessage(messageId: Int) {
        showSnackMessage(getString(messageId))
    }

    override fun showProgressBar(show: Boolean) {
        val visible = if (show) View.VISIBLE else View.GONE
        progress_bar.visibility = visible
    }

    override fun setTitle(bankName: String) {
        activity?.title = bankName
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}