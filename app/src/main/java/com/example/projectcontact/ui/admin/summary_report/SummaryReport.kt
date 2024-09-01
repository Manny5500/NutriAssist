package com.example.projectcontact.ui.admin.summary_report

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projectcontact.R
import com.example.projectcontact.databinding.ActivitySummaryReportBinding
import com.example.projectcontact.model.Child
import com.example.projectcontact.util.DateUtil.monthNow
import com.example.projectcontact.util.DialogUtil.showPdfDialog
import com.example.projectcontact.util.pdf.summary_report.SRPdf
import com.example.projectcontact.util.pdf.summary_report.SRPdfTableMaker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SummaryReport : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryReportBinding
    private lateinit var spinnerListener: SpinnerListener
    private val viewModel:SummaryReportViewModel by viewModels()
    var monthAdded = monthNow()
    var barangay = "Alipit"
    private lateinit var childList:List<Child>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preSelected()
        replaceFragment(SrListFragment())
        events()
        observers()
    }

    private fun observers(){
        viewModel.childrenList.observe(this){
            childList = it
        }
    }

    @SuppressLint("SetTextI18n")
    private fun preSelected(){
        binding.textDate.setText(monthAdded)
        binding.textBarangay.setText(barangay)
        setButtonColor(binding.naviData)
    }

    private fun events(){
        binding.naviData.setOnClickListener {
            buttonUIChange(SrListFragment(), binding.naviData)
        }
        binding.naviConso.setOnClickListener {
            buttonUIChange(SrConsoFragment(), binding.naviConso)
        }
        binding.naviSum.setOnClickListener {
            buttonUIChange(SrSumFragment(), binding.naviSum)
        }
        binding.naviPdf.setOnClickListener {
            val pdfBytes = SRPdf(
                ContextCompat.getDrawable(this, R.drawable.optlogojp)!!,
                barangay,
                10, 10, 10,
                SRPdfTableMaker(childList)
            ).pdfSetter()
            showPdfDialog(pdfBytes,this, "SummaryReport" )
        }

        binding.textDate.setOnItemClickListener { _, _, _, _ ->
            if(::spinnerListener.isInitialized){
                spinnerListener.onMonthItemClicked(binding.textDate.text.toString())
                monthAdded = binding.textDate.text.toString()

                viewModel.setMonthAddedandBarangay(monthAdded, barangay)
            }
        }

        binding.textBarangay.setOnItemClickListener {_, _, _, _ ->
            if(::spinnerListener.isInitialized){
                spinnerListener.onBarangayItemClicked(binding.textBarangay.text.toString())
                barangay = binding.textBarangay.text.toString()


                viewModel.setMonthAddedandBarangay(monthAdded, barangay)
            }
        }
    }

    private fun buttonUIChange(fragment: Fragment, view: View){
        replaceFragment(fragment)
        resetButtonColor()
        setButtonColor(view)
    }
    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutSR, fragment, "fragment_sr_list")
        fragmentTransaction.commit()
    }
    fun setListener(spinnerListener: SpinnerListener){
        this.spinnerListener = spinnerListener
    }

    interface SpinnerListener{
        fun onBarangayItemClicked(string: String)
        fun onMonthItemClicked(string : String)
    }
    private fun resetButtonColor(){
        with(binding){
            val btnArray = listOf(naviData, naviConso, naviSum)
            btnArray.forEach {
                it.setBackgroundColor(parseColor("#51ADE5"))
            }
        }
    }
    private fun setButtonColor(view: View){
        view.setBackgroundColor(parseColor("#1888c8"))
    }
    
}
