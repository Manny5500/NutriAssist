package com.example.projectcontact.ui.admin.prevalance_report

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectcontact.MarvaStructure
import com.example.projectcontact.databinding.ActivityPrevalanceReportBinding
import com.example.projectcontact.model.Barangay
import com.example.projectcontact.util.DateUtil.monthNow
import com.example.projectcontact.util.DialogUtil.showPdfDialog
import com.example.projectcontact.util.pdf.prevalance_report.PRPdf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrevalanceReport : AppCompatActivity(), MarvaStructure {

    private lateinit var binding : ActivityPrevalanceReportBinding
    private  var prevalanceReportValues = PrevalanceReportValues()
    private val viewModel: PrevalanceReportViewModel by viewModels()
    private val adapter by lazy{BarangayAdapter()}
    private var barangayList: List<Barangay> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrevalanceReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        events()
        preSelected()
        setupRecyclerView()
        observers()

    }

    private fun setupRecyclerView() {
        binding.barangayRecycler.apply{
            layoutManager = LinearLayoutManager(this@PrevalanceReport)
            adapter = this@PrevalanceReport.adapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun preSelected(){
        with(binding){
            textDate.setText(monthNow())
            textType.setText("Underweight")
            floatingActionButton.visibility = View.INVISIBLE
        }
        updateUI()
    }

    private fun updateUI(){
        val hexColor = prevalanceReportValues.themeColorString
        val colorStateList = ColorStateList.valueOf(parseColor(hexColor))
        val textTypeTitleText = prevalanceReportValues.statuses
        with(binding){
            upView.setBackgroundTintList(colorStateList)
            textThisMonth.setTextColor(colorStateList)
            textIncreased.setTextColor(colorStateList)
            floatingActionButton.backgroundTintList = colorStateList
            textTypeTitle.text = textTypeTitleText
            statusBarChanger(parseColor(hexColor))
        }
    }

    private fun statusBarChanger(color: Int) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    override fun events(){
        binding.textDate.setOnItemClickListener { _, _, _, _ ->
            val month = binding.textDate.text.toString()
            viewModel.setMonthAdded(month)
        }
        binding.textType.setOnItemClickListener {_, _, _, _ ->
            val status = binding.textType.text.toString()
            prevalanceReportValues.updateValues(status)
            viewModel.setStatus(prevalanceReportValues.statuses.split(" and "))
            updateUI()
        }
        binding.floatingActionButton.setOnClickListener{
            with(prevalanceReportValues){
                val pdfBytes = PRPdf(statuses, barangayList, methodType,
                    statuses.split(" and ")).pdfSetter()
                showPdfDialog(pdfBytes  , this@PrevalanceReport,
                    "Prevalance Report")
            }

        }
    }

    @SuppressLint("DefaultLocale")
    override fun observers(){
        viewModel.barangayList.observe(this){
            adapter.submitList(it)
            barangayList = it
            binding.floatingActionButton.visibility = View.VISIBLE
        }
        viewModel.townTotalCase.observe(this){
            binding.textDataThisMonth.text = it.toString()
        }
        viewModel.townPrevalance.observe(this){
            binding.textDataIncreased.text = String.format("%.2f%%", it)
        }
    }

}
