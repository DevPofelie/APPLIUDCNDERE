package com.example.appliudcndere.ui.screens

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun IntentionsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val url = "https://popesprayer.va"

    // Lance l'onglet du Vatican automatiquement au chargement de l'écran
    LaunchedEffect(Unit) {
        val customTabsIntent = CustomTabsIntent.Builder().apply {
            setShowTitle(true)
            setShareState(CustomTabsIntent.SHARE_STATE_ON)
        }.build()

        customTabsIntent.launchUrl(context, Uri.parse(url))
    }

    // Interface d'attente/arrière-plan professionnelle
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Chargement des intentions du Pape...")
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()

            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onNavigateBack) {
                Text("Retour à l'accueil")
            }
        }
    }
}