package com.example.projectcontact.ui.admin.admin_dashboard

import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectcontact.databinding.FragmentAdminDashboardBinding
import com.example.projectcontact.ui.admin.admin_dashboard.adapter.ProgramAdapter
import com.example.projectcontact.ui.admin.admin_dashboard.adapter.ReportsAdapter
import com.example.projectcontact.util.ToastUtil.show
import com.example.projectcontact.util.chart.ChartMaker.simplePieChart

class AdminDashboardFragment : Fragment(), ReportsAdapter.OnItemClickListener, ProgramAdapter.OnItemClickListener {

    private var _binding: FragmentAdminDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[AdminDashboardViewModel::class.java]

        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        setupPieCharts()
        setupRecyclerReports()
        setupRecyclerPrograms()


        return root
    }

    private fun setupPieCharts(){
        simplePieChart(binding.pieExamined, parseColor("#4aa166"), parseColor("#4aa166"))
        simplePieChart(binding.pieMalnourished, parseColor("#c8a67e"), parseColor("#f3d18e"))
        simplePieChart(binding.pieSevere, parseColor("#be5559"), parseColor("#be5559"))

    }

    private fun setupRecyclerReports(){
        val recyclerReports = binding.recyclerReports
        recyclerReports.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val reportsAdapter = ReportsAdapter()
        reportsAdapter.listener = this
        recyclerReports.adapter = reportsAdapter
    }

    private fun setupRecyclerPrograms(){
        val recyclerProgram = binding.recyclerPrograms
        recyclerProgram.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val programAdapter = ProgramAdapter()
        programAdapter.listener = this
        recyclerProgram.adapter = programAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(str: String) {
        show(requireContext(), str)
    }

}