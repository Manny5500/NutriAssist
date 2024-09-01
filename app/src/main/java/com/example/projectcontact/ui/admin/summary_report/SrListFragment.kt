package com.example.projectcontact.ui.admin.summary_report

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectcontact.databinding.FragmentSrListBinding
import com.example.projectcontact.ui.admin.summary_report.Listener.listener
import com.example.projectcontact.util.TableUtil.generateTable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SrListFragment : Fragment() {
    private val viewModel: SummaryReportViewModel by viewModels()
    private var _binding : FragmentSrListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSrListBinding.inflate(inflater, container, false)
        val root : View = binding.root

        binding.scrollView.visibility = View.INVISIBLE
        listener(viewModel, activity as SummaryReport)
        observer()
        return root
    }


    private fun observer(){
        viewModel.listData.observe(viewLifecycleOwner){
            binding.scrollView.visibility= View.VISIBLE
            generateTable(requireContext(), binding.WFATL, emptyArray(), it[0])
            generateTable(requireContext(), binding.HFATL, emptyArray(), it[1])
            generateTable(requireContext(), binding.WFHTL, emptyArray(), it[2])
        }
    }


}