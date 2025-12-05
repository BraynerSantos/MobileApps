package com.brayner.quotes.ui

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brayner.quotes.R
import com.brayner.quotes.widget.QuoteWidget

class SettingsActivity : AppCompatActivity() {

    private lateinit var spinnerFont: Spinner
    private lateinit var seekbarTransparency: SeekBar
    private lateinit var textTransparencyValue: TextView
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        spinnerFont = findViewById(R.id.spinner_font)
        seekbarTransparency = findViewById(R.id.seekbar_transparency)
        textTransparencyValue = findViewById(R.id.text_transparency_value)
        btnSave = findViewById(R.id.btn_save)

        setupSpinner()
        setupSeekbar()
        loadSettings()

        btnSave.setOnClickListener {
            saveSettings()
        }
    }

    private fun setupSpinner() {
        val fonts = arrayOf("Default", "Modern", "Elegant", "Fun")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fonts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFont.adapter = adapter
    }

    private fun setupSeekbar() {
        seekbarTransparency.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textTransparencyValue.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun loadSettings() {
        val prefs = getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        val fontStyle = prefs.getString("font_style", "Default")
        val transparency = prefs.getInt("transparency", 100)

        // Set spinner selection
        val adapter = spinnerFont.adapter as ArrayAdapter<String>
        val position = adapter.getPosition(fontStyle)
        if (position >= 0) {
            spinnerFont.setSelection(position)
        }

        // Set seekbar
        seekbarTransparency.progress = transparency
        textTransparencyValue.text = "$transparency%"
    }

    private fun saveSettings() {
        val selectedFont = spinnerFont.selectedItem.toString()
        val transparency = seekbarTransparency.progress

        val prefs = getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString("font_style", selectedFont)
            putInt("transparency", transparency)
            apply()
        }

        updateWidget()
        Toast.makeText(this, "Settings saved & widget updated!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun updateWidget() {
        val intent = Intent(this, QuoteWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(application).getAppWidgetIds(
            ComponentName(application, QuoteWidget::class.java)
        )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }
}
