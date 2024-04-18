package com.example.applemarket

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView


interface ButtonClickListener {
    fun onNegativeButtonClick()
    fun onPositiveButtonClick()
}

class CustomDialog(
    context: Context,
    title: String, message: String,
    private val buttonClickListener: ButtonClickListener): Dialog(context) {

    private val dialog: AlertDialog

    init {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)
        val tvDialogTitle = dialogView.findViewById<TextView>(R.id.tv_dialog_title)
        val tvDialogMessage = dialogView.findViewById<TextView>(R.id.tv_dialog_message)
        val btnNegative = dialogView.findViewById<Button>(R.id.btn_negative)
        val btnPositive = dialogView.findViewById<Button>(R.id.btn_positive)

        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(dialogView)
        dialog = dialogBuilder.create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)

        tvDialogTitle.text = title
        tvDialogMessage.text = message

        btnNegative.setOnClickListener {
            buttonClickListener.onNegativeButtonClick()
        }
        btnPositive.setOnClickListener {
            buttonClickListener.onPositiveButtonClick()
        }
    }

    fun showDialog() {
        // 화면 넓이의 70%로 다이얼로그 크기 설정
        val window = dialog.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        val width = (size.x * 0.7).toInt()
        window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }
}