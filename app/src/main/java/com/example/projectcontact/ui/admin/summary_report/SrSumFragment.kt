package com.example.projectcontact.ui.admin.summary_report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.projectcontact.databinding.FragmentSrSumBinding
import com.example.projectcontact.ui.admin.summary_report.Listener.listener
import com.example.projectcontact.util.TableUtil.generateTable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SrSumFragment : Fragment() {

    private var _binding : FragmentSrSumBinding? = null
    private val binding get() = _binding!!
    private val viewModel : SummaryReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSrSumBinding.inflate(inflater, container, false)
        val root : View = binding.root
        binding.scrollView.visibility = View.INVISIBLE
        listener(viewModel, activity as SummaryReport)
        observer()
        return root
    }
    private fun observer(){
        viewModel.summaryData.observe(viewLifecycleOwner){
            binding.scrollView.visibility = View.VISIBLE
            generateTable(requireContext(), binding.OPTTL, emptyArray(), it[0])
            generateTable(requireContext(), binding.MotherTL, emptyArray(), it[1])
            generateTable(requireContext(), binding.DataTL, emptyArray(), it[2])
        }
    }

}