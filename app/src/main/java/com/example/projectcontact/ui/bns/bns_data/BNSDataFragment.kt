package com.example.projectcontact.ui.bns.bns_data


import com.example.projectcontact.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectcontact.databinding.FragmentBnsDataBinding
import com.example.projectcontact.model.Child
import com.example.projectcontact.ui.add_parent.AddParent
import com.example.projectcontact.ui.update_child.EditChild
import com.example.projectcontact.ui.history.HistoryActivity
import com.example.projectcontact.util.DateUtil
import com.example.projectcontact.util.DialogUtil
import com.example.projectcontact.util.pdf.malnourished_list.MLPdf
import com.itextpdf.text.Rectangle
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class BNSDataFragment : Fragment(), ChildAdapter.OnButtonClickListener{

    private var _binding: FragmentBnsDataBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel:BNSDataViewModel by viewModels()

    private val adapter by lazy { ChildAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBnsDataBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        observeViewModel()
        event()

        return root
    }

    private fun setupRecyclerView(){
        binding.recycler.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@BNSDataFragment.adapter
        }
    }

    private fun observeViewModel(){
        viewModel.apply {
            fetchData()
            children.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteButtonClick(child: Child) {
        DialogUtil.showDeleteDialog(requireContext(), viewModel, child)
    }

    override fun onEditButtonClick(child: Child) {
        val intent = Intent(requireContext(), EditChild::class.java).apply{
            putExtra("child", child)
        }
        startActivity(intent)
    }
    override fun onProgressButtonClick(fullName: String, birthDate : String) {
        val intent = Intent(requireContext(), HistoryActivity::class.java).apply{
            putExtra("fullName", fullName)
            putExtra("birthDate", birthDate)
        }
        startActivity(intent)
    }

    private fun event(){
        binding.addData.setOnClickListener {
            val intent = Intent(requireContext(), AddParent::class.java)
            startActivity(intent)
        }
        binding.pdfMaker.setOnClickListener {
            val pdfBytes = MLPdf(
                ContextCompat.getDrawable(requireContext(), R.drawable.optlogojp)!!,
                "Bucal",
                viewModel.children.value!!
            ).pdfSetter()
            DialogUtil.showPdfDialog(pdfBytes, requireContext(), "Malnourished List")
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { adapter.filter(it) }
                return true
            }

        })
    }


}