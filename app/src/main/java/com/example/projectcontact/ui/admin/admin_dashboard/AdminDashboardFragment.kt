package com.example.projectcontact.ui.admin.admin_dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcontact.R
import com.example.projectcontact.databinding.FragmentAdminDashboardBinding
import com.example.projectcontact.ui.admin.admin_dashboard.adapter.ProgramAdapter
import com.example.projectcontact.ui.admin.admin_dashboard.adapter.ReportsAdapter
import com.example.projectcontact.util.ToastUtil.show
import com.example.projectcontact.util.chart.PieChartMaker.simplePieChart
import com.example.projectcontact.util.margin.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDashboardFragment : Fragment(), ReportsAdapter.OnItemClickListener, ProgramAdapter.OnItemClickListener {

    private var _binding: FragmentAdminDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AdminDashboardViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getChildren()

        observers()
        setupRecyclerReports()
        setupRecyclerPrograms()


        return root
    }

    private fun observers(){
        viewModel.childList.observe(viewLifecycleOwner){
            it.forEach { child -> Log.d("MyApp", "Child ${child.statusdb}")  }
        }

        viewModel.normalPercentage.observe(viewLifecycleOwner){
            simplePieChart(
                binding.pieExamined,  it,"#4aa166", "#4aa166"
            )
        }

        viewModel.malnourishedChildren.observe(viewLifecycleOwner){
            simplePieChart(
                binding.pieMalnourished,  it, "#f3d18e","#c8a67e"
            )
        }

        viewModel.severeCasesChildren.observe(viewLifecycleOwner){
            simplePieChart(binding.pieSevere,it, "#be5559", "#be5559"
            )
        }

    }

    @SuppressLint("WrongConstant")
    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        orientation: Int = LinearLayoutManager.HORIZONTAL
    ) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), orientation, false)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(MarginItemDecoration(requireContext(), R.dimen.margin))
    }

    private fun setupRecyclerReports() {
        val reportsAdapter = ReportsAdapter().apply { listener = this@AdminDashboardFragment }
        setupRecyclerView(binding.recyclerReports, reportsAdapter)
    }

    private fun setupRecyclerPrograms() {
        val programAdapter = ProgramAdapter().apply { listener = this@AdminDashboardFragment }
        setupRecyclerView(binding.recyclerPrograms, programAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(str: String) {
        show(requireContext(), str)
    }

}