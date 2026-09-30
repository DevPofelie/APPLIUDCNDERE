package com.example.appliudcndere.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConstructionScreen(
    title: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.Construction,
            contentDescription = null,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Page en construction",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Cette fonctionnalité sera disponible prochainement.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 32.dp
            )
        )

        Button(
            onClick = onBack
        ) {
            Text("Retour")
        }
    }
}