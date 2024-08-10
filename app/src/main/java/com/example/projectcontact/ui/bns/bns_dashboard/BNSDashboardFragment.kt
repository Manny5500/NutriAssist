package com.example.projectcontact.ui.bns.bns_dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.projectcontact.MarvaStructure
import com.example.projectcontact.databinding.FragmentBnsDashboardBinding
import com.example.projectcontact.model.Parent
import com.example.projectcontact.util.DialogUtil.showDateIntervalDialog
import com.example.projectcontact.util.chart.PieChartMaker.dualPieChart
import com.example.projectcontact.util.chart.DashboardPieChart.centerText
import com.example.projectcontact.util.chart.DashboardPieChart.colorList
import com.example.projectcontact.util.chart.DashboardPieChart.dataList
import com.example.projectcontact.util.chart.DashboardPieChart.labelList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BNSDashboardFragment : Fragment(), MarvaStructure{

    private var _binding: FragmentBnsDashboardBinding? = null

    private val binding get() = _binding!!

    private  val viewModel: BNSDashboardViewModel by viewModels()


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBnsDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        binding.namText.text = "Bucal"
        viewModel.init()
        observers()
        events()
        return root
    }

    @SuppressLint("SetTextI18n")
    override fun observers() {
        viewModel.childList.observe(viewLifecycleOwner) {
            dualPieChart(binding.rTBod, dataList(it), labelList, colorList, centerText)
        }
        viewModel.dateChange.observe(viewLifecycleOwner){
            binding.dateText.text  = viewModel.fromDate.get().toString() +
                    " - " + viewModel.toDate.get().toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun events() {
        binding.dateMenu.setOnClickListener {
            showDateIntervalDialog(requireContext(),
                viewModel
            )
        }
        with(binding){

            falView.setOnClickListener{
                goToFamilyList(true, "fal")
            }
            vulView.setOnClickListener{
                goToFamilyList(false, "vul")
            }
            gBView.setOnClickListener {
                goToFamilyList(true, "gB")
            }
            fPView.setOnClickListener {
                goToFeedingList()
            }
        }
    }

    private fun getListType(type: String):List<Parent>{
        return when(type){
            "fal" -> viewModel.parentList.value!!
            "vul" -> viewModel.vulnerableList.value!!
            "gB" -> viewModel.gulayanList.value!!
            else -> emptyList()
        }
    }

    private fun goToFamilyList(isFilterHidden:Boolean, type: String){
        val intent = Intent(requireContext(), FamilyList::class.java).apply{
            putExtra("parents", ArrayList(getListType(type)))
            putExtra("isFilterHidden", isFilterHidden)
        }
        startActivity(intent)
    }

    private fun goToFeedingList(){
        val childList = ArrayList(viewModel.feedingList.value!!)
        val intent = Intent(requireContext(), FeedingList::class.java).apply{
            putExtra("childList", childList)
        }
        startActivity(intent)
    }

}