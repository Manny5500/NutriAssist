package com.example.projectcontact.ui.admin.admin_analytics

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projectcontact.MarvaStructure
import com.example.projectcontact.databinding.FragmentAdminAnalyticsBinding
import com.example.projectcontact.util.DialogUtil
import com.example.projectcontact.util.DialogUtil.showDateIntervalDialogGeneric
import com.example.projectcontact.util.chart.LineChartMaker.simpleLineChart
import com.example.projectcontact.R
import com.example.projectcontact.util.Notation.statsNotation
import com.example.projectcontact.util.TableUtil.generateTable
import com.example.projectcontact.util.chart.BarChartMaker.simpleBarChart
import com.example.projectcontact.util.chart.PieChartMaker.dualPieChart
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminAnalyticsFragment : Fragment(), MarvaStructure, DialogUtil.OnSetButtonClickListener {

    private var _binding: FragmentAdminAnalyticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : AdminAnalyticsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminAnalyticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        observers()
        events()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun observers() {

        viewModel.historicalDataDemographics.observe(viewLifecycleOwner){
            simpleLineChart(binding.lineChart, it.lineChartData.first, it.lineChartData.second)
            binding.labelTotalCase.text = it.currentTotalCase.toString()
            binding.labelObservation.text = it.observation
            binding.labelObservation.setTextColor(parseColor(it.observationColorString))
        }

        viewModel.genderDemographics.observe(viewLifecycleOwner){
            dualPieChart(binding.pieChart, listOf(it.pieChartData.first, it.pieChartData.second),
                listOf("M", "F"), listOf("#3498db", "#2980b9"), "Gender Distribution")
            generateTable(requireContext(), binding.sextableLayout, arrayOf("Gender", "Total", "Percentage"), it.tableData)
        }

        viewModel.statusDemographics.observe(viewLifecycleOwner){
            simpleBarChart(binding.barChart, it.barChartData.first, it.barChartData.second.map{s-> statsNotation(s)})
            generateTable(requireContext(), binding.tableLayout, arrayOf("Category", "Total"), it.tableData)
        }

        viewModel.ageDemographics.observe(viewLifecycleOwner){
            simpleBarChart(binding.barChart2, it.barChartData.first, it.barChartData.second)
            generateTable(requireContext(), binding.agesTable, arrayOf("Age Group", "Total"), it.tableData)
        }

        viewModel.barangayDemographics.observe(viewLifecycleOwner){
            generateTable(requireContext(), binding.tableLayout3, arrayOf("Barangay", "Total"), it.tableData)
        }

        viewModel.periodType.observe(viewLifecycleOwner){
            when(it){
                "week" -> updateDateTabUI(7, binding.dateWeeks)
                "month" -> updateDateTabUI(30, binding.dateMonth)
                "year" -> updateDateTabUI(365, binding.dateYear)
                "custom" -> showDateRangeTab()
            }
        }
        viewModel.dateRangeVal.observe(viewLifecycleOwner){
            binding.dateRange.text  = it
        }
        viewModel.hideDateRangeTab.observe(viewLifecycleOwner){
            if(it) hideDateRangeTab()
        }
    }
    override fun events() {
        binding.dateOptions.setOnClickListener {
            showDateIntervalDialogGeneric(requireContext(), this)
        }
        binding.dateRangeOptions.setOnClickListener {
            showPopupMenu(it)
        }
        binding.dateWeeks.setOnClickListener{
            viewModel.setPeriodType("week")
        }
        binding.dateMonth.setOnClickListener {
            viewModel.setPeriodType("month")
        }
        binding.dateYear.setOnClickListener {
            viewModel.setPeriodType("year")
        }
    }

    private fun showPopupMenu(v: View){
        val popupMenu = PopupMenu(requireContext(), v)
        popupMenu.menuInflater.inflate(R.menu.date_tab_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_item_1 -> {
                    viewModel.setPeriodType("week", true)
                    true
                }
                R.id.menu_item_2 -> {
                    viewModel.setPeriodType("month", true)
                    true
                }
                R.id.menu_item_3 ->{
                    viewModel.setPeriodType("year", true)
                    true
                }
                R.id.menu_item_4 -> {
                    showDateIntervalDialogGeneric(requireContext(), this)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(dateFrom: String, dateTo: String) {
        viewModel.setDataByCostumedDate(dateFrom, dateTo)
        viewModel.setDateRangeVal("$dateFrom - $dateTo")
        viewModel.setPeriodType("custom", false)
    }

    private fun updateDateTabUI(days: Long, textView: TextView){
        resetButtonColor()
        setButtonColor(textView)
        //this will update the data
        viewModel.setDataByPeriodDate(days)
    }

    private fun resetButtonColor(){
        with(binding){
            val btnArray = listOf(dateWeeks, dateMonth, dateYear)
            btnArray.forEach {
                it.setTextColor(Color.BLACK)
                it.setBackgroundColor(Color.WHITE)
            }
        }
    }

    private fun setButtonColor(textView: TextView){
        textView.setTextColor(Color.WHITE)
        textView.setBackgroundColor(Color.parseColor("#51ADE5"))
    }

    private fun showDateRangeTab(){
        with(binding){
            dateRangeTab.visibility = View.VISIBLE
            dateTab.visibility = View.GONE
        }
    }

    private fun hideDateRangeTab(){
        with(binding){
            dateRangeTab.visibility = View.GONE
            dateTab.visibility = View.VISIBLE
            dateRange.text = ""
        }
    }

}