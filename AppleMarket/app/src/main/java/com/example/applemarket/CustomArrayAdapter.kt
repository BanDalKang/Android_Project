package com.example.applemarket

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class CustomArrayAdapter(context: Context, private val items: Array<String>) :
    ArrayAdapter<String>(context, R.layout.custom_spinner, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(R.id.textView)

        if (position == (parent as Spinner).selectedItemPosition) {
            textView.setTypeface(null, Typeface.BOLD)
        } else {
            textView.setTypeface(null, Typeface.NORMAL)
        }

        return view
    }
}
