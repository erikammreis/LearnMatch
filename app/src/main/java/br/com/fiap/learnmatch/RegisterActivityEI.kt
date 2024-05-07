package br.com.fiap.learnmatch

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RegisterActivityEI : AppCompatActivity() {

    private lateinit var chipGroup: ChipGroup
    private var chipTexts = mutableListOf<String>()
    private lateinit var allInterests: Array<String>
    private var searchText: String? = null
    private lateinit var registerEINext: Button

    private fun setSearchText(searchText: String) {
        this.searchText = searchText
    }

    private fun getSearchText(): String? {
        return searchText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_e_i_mentor)

        registerEINext = findViewById(R.id.registerEiNext)

        registerEINext.setOnClickListener {
            val intent = Intent(this@RegisterActivityEI, RegisterActivityMC::class.java)
            startActivity(intent)
        }

        val searchEditText = findViewById<EditText>(R.id.edit_text_search)
        val addChipButton = findViewById<Button>(R.id.button_add_chip)
        chipGroup = findViewById(R.id.chip_group)

        chipTexts = mutableListOf()

        allInterests = resources.getStringArray(R.array.interest)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setSearchText(s.toString())
                showSuggestions(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        addChipButton.setOnClickListener {
            if (shouldAddChip()) {
                addChip(getSearchText())
            }
            clearSearchText()
        }
    }

    private fun shouldAddChip(): Boolean {
        val searchText = getSearchText()
        if (searchText != null && searchText.length >= 3) {
            var alreadyExists = false
            for (chipText in chipTexts) {
                if (chipText.equals(searchText, ignoreCase = true)) {
                    alreadyExists = true
                    break
                }
            }
            if (!alreadyExists) {
                return true
            } else {
                showMessage("Esse interesse já está adicionado.")
                return false
            }
        }
        return false
    }

    private fun clearSearchText() {
        val searchEditText = findViewById<EditText>(R.id.edit_text_search)
        searchEditText.setText("")
    }

    private fun showSuggestions(searchText: String) {
        if (searchText.length >= 4) {
            val suggestedInterests = mutableListOf<String>()
            for (interest in allInterests) {
                if (interest.toLowerCase().contains(searchText.toLowerCase()) && !chipTexts.contains(interest)) {
                    suggestedInterests.add(interest)
                }
            }
            if (suggestedInterests.isNotEmpty()) {
                showSelectionDialog(suggestedInterests.toTypedArray())
            }
        }
    }

    private fun showSelectionDialog(suggestions: Array<String>) {
        val builder = AlertDialog.Builder(this)
        if (suggestions.isNotEmpty()) {
            builder.setItems(suggestions) { dialog, which ->
                val selectedInterest = suggestions[which]
                if (!chipTexts.contains(selectedInterest)) {
                    addChip(selectedInterest)
                    clearSearchText()
                } else {
                    showMessage("Esse interesse já está adicionado.")
                }
            }

            builder.setNegativeButton("Fechar", null)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setGravity(Gravity.BOTTOM)
        }
    }

    private fun addChip(text: String?) {
        text?.let {
            val chip = Chip(this)
            chip.text = text
            chip.isCloseIconVisible = true
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(it)
                chipTexts.remove((it as Chip).text.toString())
            }
            if (!chipTexts.contains(text)) {
                chipGroup.addView(chip)
                chipTexts.add(text)
            }
        }
    }

    private fun showMessage(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
