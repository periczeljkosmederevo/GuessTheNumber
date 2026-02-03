package com.periczeljkosmederevo.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowInsetsControllerCompat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private val gameManager = GameManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        val isDarkMode = resources.configuration.uiMode and
                android.content.res.Configuration.UI_MODE_NIGHT_MASK ==
                android.content.res.Configuration.UI_MODE_NIGHT_YES

        windowInsetsController.isAppearanceLightStatusBars = !isDarkMode
        window.statusBarColor = Color.TRANSPARENT

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.apply {
            touchscreenBlocksFocus = false
            isFocusable = false
        }

        textView = findViewById(R.id.textView)
        editText = findViewById(R.id.editTextText)

        findViewById<Button>(R.id.button).setOnClickListener {
            proveriPogodak()
        }

        startNewGame()

        editText.setOnEditorActionListener { v, _, _ ->
            UIHelper.hideKeyboard(this, v)
            proveriPogodak()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private val languageMap = mapOf(
        R.id.lang_srpski_cyrl to "sr-RS",
        R.id.lang_srpski_latin to "sr",
        R.id.lang_ru to "ru",
        R.id.lang_de to "de",
        R.id.lang_nl to "nl",
        R.id.lang_it to "it-IT",
        R.id.lang_en to "en",
        R.id.lang_fr to "fr",
        R.id.lang_es to "es",
        R.id.lang_el to "el",
        R.id.lang_pt to "pt",
        R.id.lang_tr to "tr",
        R.id.lang_pl to "pl",
        R.id.lang_zh to "zh",
        R.id.lang_ar to "ar",
        R.id.lang_hi to "hi",
        R.id.lang_ja to "ja",
        R.id.lang_ko to "ko",
        R.id.lang_in to "in",
        R.id.lang_vi to "vi",
        R.id.lang_bn to "bn",
        R.id.lang_sw to "sw"
    )

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val langCode = languageMap[item.itemId]
        if (langCode != null) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(langCode))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun startNewGame() {
        gameManager.novaIgra()
        textView.text = getString(R.string.status_pocni)
        editText.text.clear()
        findViewById<Button>(R.id.button).requestFocus()
    }

    private fun proveriPogodak() {
        val unos = editText.text.toString().trim()
        val korisnikovBroj = unos.toIntOrNull() ?: return
        val uzvici = resources.getStringArray(R.array.uzvici_lista)
        val rezultat = gameManager.proveriBroj(korisnikovBroj)

        when {
            rezultat == 0 -> prikaziKrajIgre(
                getString(
                    R.string.pobeda_poruka,
                    gameManager.zamisljeniBroj
                )
            )

            gameManager.isGameOver() -> prikaziKrajIgre(
                getString(
                    R.string.poraz_full,
                    gameManager.zamisljeniBroj
                )
            )

            rezultat == 1 -> {
                textView.text = getString(
                    R.string.poruka_veci_full,
                    uzvici.random(),
                    korisnikovBroj,
                    gameManager.preostaloPokusaja
                )
                editText.text.clear()
            }

            rezultat == -1 -> {
                textView.text = getString(
                    R.string.poruka_manji_full,
                    uzvici.random(),
                    korisnikovBroj,
                    gameManager.preostaloPokusaja
                )
                editText.text.clear()
            }
        }
    }

    private fun prikaziKrajIgre(poruka: String) {
        MessageDisplay.showGenericDialog(
            context = this,
            title = getString(R.string.kraj_igre),
            message = poruka,
            positiveButtonText = getString(android.R.string.ok),
            onPositiveClick = { dialog ->
                startNewGame()
                dialog.dismiss()
            }
        )
    }
}