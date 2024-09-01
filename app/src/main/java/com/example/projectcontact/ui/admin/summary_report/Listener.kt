package com.example.projectcontact.ui.admin.summary_report

object Listener {

    fun listener(viewModel: SummaryReportViewModel, parent: SummaryReport){
        with(parent){
            viewModel.setMonthAddedandBarangay(monthAdded, barangay)
            setListener(object : SummaryReport.SpinnerListener{
                override fun onBarangayItemClicked(string: String) {
                    viewModel.setBarangay(string)
                }
                override fun onMonthItemClicked(string: String) {
                   viewModel.setMonthAdded(string)
                }
            })

        }
    }

}