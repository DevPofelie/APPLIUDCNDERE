package com.example.appliudcndere.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onConsultationClick: () -> Unit,
    onAnniversairesClick: () -> Unit,
    onIntentionsClick: () -> Unit,
    onNouvellesClick: () -> Unit,
    onAboutClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Diocèse de Ngaoundéré",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Religieux et religieuses",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = "Bienvenue dans l'application des religieux et religieuses présents dans le Diocèse de Ngaoundéré.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Menu principal",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onConsultationClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultation des religieux/religieuses")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                onAnniversairesClick()
            }
        ) {
            Text("Anniversaires")
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Autres",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onIntentionsClick()
            }
        ) {
            Text("Intentions de prière du Pape")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onNouvellesClick()
            }
        ) {
            Text("Nouvelles quotidiennes")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onAboutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("À propos")
        }
    }
}