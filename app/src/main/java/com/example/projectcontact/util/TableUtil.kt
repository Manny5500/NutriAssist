package com.example.projectcontact.util

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView


object TableUtil {

    fun generateTable(
        context: Context?,
        tableLayout: TableLayout,
        headers: Array<String?>,
        data: Array<Array<String>>
    ) {
        clearTable(tableLayout)
        val headerRow = TableRow(context)

        for (i in headers.indices) {
            val headerTextView = TextView(context)
            headerTextView.text = headers[i]
            Costumize(headerRow, headerTextView)
            if (i == 0) {
                headerTextView.gravity = Gravity.START
            } else {
                headerTextView.gravity = Gravity.END
            }
            setTextSizeAndPaddingForTextViews(18f, 16, headerTextView)
            headerRow.addView(headerTextView)
        }

        tableLayout.addView(headerRow)


        // Populate table rows with data
        for (rowData in data) {
            val tableRow = TableRow(context)

            // Create cells for each column in the row
            for (i in rowData.indices) {
                val cellTextView = TextView(context)
                cellTextView.text = rowData[i]
                Costumize(tableRow, cellTextView)
                if (i == 0) {
                    cellTextView.gravity = Gravity.START
                } else {
                    cellTextView.gravity = Gravity.END
                }
                setTextSizeAndPaddingForTextViews(18f, 16, cellTextView)
                tableRow.addView(cellTextView)
            }
            tableLayout.addView(tableRow)
        }
    }

    fun setTextSizeAndPaddingForTextViews(
        textSize: Float,
        padding: Int,
        vararg textViews: TextView
    ) {
        for (textView in textViews) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
            textView.setPadding(padding, padding, padding, padding)
        }
    }

    fun Costumize(tableRow: TableRow, vararg textViews: TextView) {
        tableRow.layoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        for (textView in textViews) {
            textView.layoutParams = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT, 1f
            )
        }
    }

    fun clearTable(tableLayout: TableLayout) {
        // Get the number of rows in the table
        val rowCount = tableLayout.childCount

        // Remove all rows starting from index 1 (index 0 is the header row)
        for (i in 0 until rowCount) {
            val child: View = tableLayout.getChildAt(0)
            if (child is TableRow) {
                // Remove the TableRow from the TableLayout
                tableLayout.removeViewAt(0)
            }
        }
    }
}