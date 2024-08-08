package com.example.projectcontact.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectcontact.databinding.DialogDateIntervalBinding
import com.example.projectcontact.databinding.DialogDeleteChildBinding
import com.example.projectcontact.databinding.DialogPdfViewerBinding
import com.example.projectcontact.databinding.DialogSearchParentBinding
import com.example.projectcontact.databinding.DialogStatusBinding
import com.example.projectcontact.model.Child
import com.example.projectcontact.model.Parent
import com.example.projectcontact.ui.bns.bns_data.BNSDataViewModel
import com.example.projectcontact.ui.add_parent.SearchParentAdapter
import com.example.projectcontact.ui.bns.bns_dashboard.BNSDashboardViewModel
import com.example.projectcontact.util.pdf.DisplayPDF
import com.example.projectcontact.util.pdf.SavePDF
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


object DialogUtil {

    fun showDatePickerDialog(editText: TextInputEditText, context: Context) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                editText.setText(selectedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun showStatusDialog(context:Context,
                         child: Child)
    {
        val binding = DialogStatusBinding.inflate(LayoutInflater.from(context))
        binding.child = child
        Dialog(context).apply {
            setContentView(binding.root)
            window?.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            show()
        }
    }


    fun showDeleteDialog(context: Context, viewModel: BNSDataViewModel, child : Child){
        val binding = DialogDeleteChildBinding.inflate(LayoutInflater.from(context))
        binding.child = child
        val dialog = Dialog(context).apply {
            setContentView(binding.root)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            show()
        }

        binding.buttonYes.setOnClickListener{
            viewModel.deleteChild(child.id)
            dialog.dismiss()
        }

        binding.buttonNo.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun showPdfDialog(pdfBytes: ByteArray, context: Context, fileName: String) {
        val binding = DialogPdfViewerBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(context).apply{
            setContentView(binding.root)
            window?.setLayout(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            show()
        }
        DisplayPDF.fromBytes(context, pdfBytes, binding.pdfView, binding.progressBar)
        binding.btnSavePdf.setOnClickListener {
            val filename = fileName +
                    LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS")) + ".pdf"
            SavePDF.toStorage(filename, context.contentResolver, pdfBytes)
        }
        binding.btnCancel.setOnClickListener { dialog.dismiss() }
    }

    fun showSearchParentDialog(context:Context,
                               adapter: SearchParentAdapter,
                               parents: List<Parent>){
        val binding = DialogSearchParentBinding.inflate((LayoutInflater.from(context)))
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter
        adapter.submitList(parents)
        Dialog(context).apply{
            setContentView(binding.root)
            window?.setLayout(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
            show()
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filter(newText)
                }
                return true
            }
        })
    }

    fun showDateIntervalDialog(context: Context,
                               viewModel: BNSDashboardViewModel){
        val binding = DialogDateIntervalBinding.inflate(LayoutInflater.from(context))
        binding.viewModel = viewModel
        val dialog = Dialog(context).apply{
            setContentView(binding.root)
            window?.setLayout(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            show()
        }
        binding.dateFrom.setOnClickListener {
            showDatePickerDialog(binding.dateFrom, context)
        }

        binding.dateTo.setOnClickListener {
            showDatePickerDialog(binding.dateTo, context)
        }

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setOnDismissListener {
            viewModel.cancel()
        }
    }

}


