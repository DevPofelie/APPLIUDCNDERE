package com.example.appliudcndere.ui.theme

import android.content.Context
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object LiturgicalThemeManager {
    private val PREFS_NAME = "liturgical_prefs"
    private val KEY_COLOR = "liturgical_color"

    // Flow réactif pour notifier instantanément l'interface lors d'une mise à jour de l'API
    private val _couleurActuelleFlow = MutableStateFlow("green")
    val couleurActuelleFlow: StateFlow<String> = _couleurActuelleFlow

    // Initialisation au démarrage de l'application
    fun init(context: Context) {
        _couleurActuelleFlow.value = getColor(context)
    }

    fun saveColor(context: Context, colorName: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_COLOR, colorName)
            .apply()
        // Met à jour instantanément le flux de données pour l'UI
        _couleurActuelleFlow.value = colorName
    }

    private fun getColor(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_COLOR, "green") ?: "green"
    }

    // Génère des harmonies douces et lisibles adaptées à l'identité visuelle de votre écran d'accueil
    fun getLiturgicalColorScheme(colorName: String): LiturgicalColors {
        return when (colorName.lowercase()) {
            "white", "blanc" -> LiturgicalColors(
                primary = Color(0xFF8D7736),       // Or / Bronze pour le texte des boutons
                container = Color(0xFFF4F0E6),     // Fond du bouton (Blanc cassé/crème pastel)
                background = Color(0xFFFDFDFB)     // Fond d'écran général
            )
            "red", "rouge" -> LiturgicalColors(
                primary = Color(0xFFB71C1C),       // Rouge profond liturgique
                container = Color(0xFFFFEBEE),     // Rouge pastel très doux pour le bouton
                background = Color(0xFFFFF8F8)
            )
            "violet", "pourpre" -> LiturgicalColors(
                primary = Color(0xFF5E35B1),       // Violet Avent/Carême
                container = Color(0xFFEDE7F6),     // Violet pastel
                background = Color(0xFFFAFAFE)
            )
            "rose" -> LiturgicalColors(
                primary = Color(0xFFD81B60),       // Rose liturgique
                container = Color(0xFFFCE4EC),     // Rose pastel
                background = Color(0xFFFFF8FA)
            )
            else -> LiturgicalColors(              // "green" / Vert (Temps ordinaire) par défaut
                primary = Color(0xFF1B5E20),       // Vert sapin
                container = Color(0xFFE8F5E9),     // Vert pastel (similaire à votre style actuel)
                background = Color(0xFFF9FBF9)
            )
        }
    }
}

data class LiturgicalColors(
    val primary: Color,
    val container: Color,
    val background: Color
)