package com.example.appliudcndere.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Structures de données pour nos listes de ressources
data class SiteWebItem(val nom: String, val description: String, val url: String)
data class ReseauSocialItem(val plateforme: String, val nomCompte: String, val iconeEteinte: String, val url: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NouvellesQuotidiennesEcran(
    onRetourClick: () -> Unit
) {
    val context = LocalContext.current

    // 1. Liste des sites officiels requis
    val listeSites = listOf(
        SiteWebItem("Le Saint-Siège (Vatican)", "Actualités du Pape François, documents officiels et magistère de l'Église.", "https://vatican.va"),
        SiteWebItem("OMI World", "Portail mondial de l'Administration Générale des Missionnaires Oblats.", "https://omiworld.org"),
        SiteWebItem("OMI Cameroun", "Actualités de la Province Oblate (Cameroun, Nigeria, Tchad).", "https://omicameroun.org")
    )

    // 2. Liens vers les réseaux sociaux catholiques et provinciaux majeurs
    val listeReseaux = listOf(
        ReseauSocialItem("Vatican News (Français)", "@vaticannews.fr", "📱 Facebook", "https://facebook.com"),
        ReseauSocialItem("OMI Cameroun Officiel", "Oblats Province Cameroun", "📺 YouTube", "https://youtube.com"),
        ReseauSocialItem("Diocèse de Ngaoundéré", "Média et communication diocésaine", "🌐 Web", "https://omicameroun.org") // Remplacer par l'URL propre du diocèse si existante
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Nouvelles & Liens Utiles",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onRetourClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("Retour à l'accueil", color = Color.White)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "🔎 Portails Institutionnels",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Boucle d'affichage pour les sites internet
            items(listeSites) { site ->
                CardSiteWeb(site = site, context = context)
            }

            item {
                Divider(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "✨ Réseaux Sociaux & Médias",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Boucle d'affichage pour les comptes sociaux
            items(listeReseaux) { reseau ->
                CardReseauSocial(reseau = reseau, context = context)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@Composable
fun CardSiteWeb(site: SiteWebItem, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { ouvrirLienUrl(context, site.url) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            // S'adapte à la couleur liturgique courante de manière subtile
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = site.nom,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = site.description,
                fontSize = 13.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Visiter le site →",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun CardReseauSocial(reseau: ReseauSocialItem, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { ouvrirLienUrl(context, reseau.url) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reseau.plateforme,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = reseau.nomCompte,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            Text(
                text = reseau.iconeEteinte,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

// Fonction utilitaire sécurisée pour le lancement des intentions de navigation (Intents)
fun ouvrirLienUrl(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Impossible d'ouvrir ce lien externe", Toast.LENGTH_SHORT).show()
    }
}