package com.example.projectcontact.ui.admin.summary_report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.projectcontact.databinding.FragmentSrConsoBinding
import com.example.projectcontact.ui.admin.summary_report.Listener.listener
import com.example.projectcontact.util.TableUtil.generateTable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SrConsoFragment : Fragment() {

    private var _binding : FragmentSrConsoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SummaryReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSrConsoBinding.inflate(inflater, container, false)
        val root : View = binding.root

        binding.scrollView.visibility = View.INVISIBLE
        listener(viewModel, activity as SummaryReport)
        observer()

        return root
    }

    private fun observer(){
        viewModel.consoData.observe(viewLifecycleOwner){
            binding.scrollView.visibility = View.VISIBLE
            generateTable(requireContext(), binding.allAgeTL, emptyArray(), it[0])
            generateTable(requireContext(), binding.halfAgeTL, emptyArray(), it[1])
        }
    }


}