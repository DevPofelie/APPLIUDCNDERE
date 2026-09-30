package com.example.appliudcndere.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "À propos",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Diocèse de Ngaoundéré",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Religieux et religieuses",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = """
Cette application est conçue pour faciliter la consultation des informations relatives aux religieux et religieuses présents dans le Diocèse de Ngaoundéré.

Elle permet notamment de rechercher les membres enregistrés et de consulter les informations disponibles les concernant.
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Objectif",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = """
L'application a pour objectif de mettre à disposition un annuaire pratique, simple et accessible des religieux et religieuses présents dans le diocèse.

Elle pourra progressivement accueillir de nouvelles fonctionnalités pastorales et informatives.
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Fonctionnalités",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = """
• Consultation des religieux et religieuses
• Recherche des membres
• Recherche par lieu de résidence
• Anniversaires
• Intentions de prière du Pape
• Nouvelles quotidiennes
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Version",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = "Version 1.1.0",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Développeur",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = """
Nom : DevPofelie
Email : devpofelie@gmail.com
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onBack
        ) {
            Text("Retour")
        }
    }
}