package com.example.appliudcndere.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appliudcndere.data.local.entity.Membre
import com.example.appliudcndere.viewmodel.MembreViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Représente un anniversaire à afficher.
 */
data class Anniversaire(
    val membre: Membre,
    val type: TypeAnniversaire,
    val date: String
)

/**
 * Les cinq catégories d'anniversaires retenues
 * pour l'application UDCNdéré.
 */
enum class TypeAnniversaire(
    val libelle: String
) {
    NAISSANCE("Anniversaire de naissance"),
    PREMIERS_VŒUX("Anniversaire des premiers vœux"),
    VŒUX_PERPETUELS("Anniversaire des vœux perpétuels"),
    SACERDOCE("Anniversaire de sacerdoce"),
    FÊTE_PATRONALE("Fête patronale")
}

/**
 * Écran des anniversaires du jour.
 *
 * L'année de la date enregistrée n'est pas prise en compte.
 * Seuls le jour et le mois sont comparés avec la date actuelle.
 */
@Composable
fun AnniversairesScreen(
    viewModel: MembreViewModel,
    onRetour: () -> Unit
) {
    val membres by viewModel.membres.collectAsState()

    val aujourdHui = LocalDate.now()

    val anniversaires = construireAnniversairesDuJour(
        membres = membres,
        jour = aujourdHui.dayOfMonth,
        mois = aujourdHui.monthValue
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Anniversaires",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = formaterDateDuJour(aujourdHui),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (anniversaires.isEmpty()) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Celebration,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Aucun anniversaire",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Aucun anniversaire à célébrer aujourd'hui.",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(
                    onClick = onRetour
                ) {
                    Text("Retour")
                }
            }

        } else {

            Text(
                text = "${anniversaires.size} célébration(s) aujourd'hui",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = anniversaires,
                    key = { anniversaire ->
                        "${anniversaire.membre.id}_${anniversaire.type.name}"
                    }
                ) { anniversaire ->

                    AnniversaireCard(
                        anniversaire = anniversaire
                    )
                }
            }
        }
    }
}

/**
 * Carte d'un anniversaire.
 */
@Composable
private fun AnniversaireCard(
    anniversaire: Anniversaire
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = iconePourType(anniversaire.type),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "${anniversaire.membre.nom} ${anniversaire.membre.prenom}"
                        .trim(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = anniversaire.type.libelle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = anniversaire.date,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

/**
 * Retourne l'icône correspondant au type d'anniversaire.
 */
private fun iconePourType(
    type: TypeAnniversaire
) = when (type) {

    TypeAnniversaire.NAISSANCE ->
        Icons.Default.Cake

    TypeAnniversaire.PREMIERS_VŒUX ->
        Icons.Default.Favorite

    TypeAnniversaire.VŒUX_PERPETUELS ->
        Icons.Default.Star

    TypeAnniversaire.SACERDOCE ->
        Icons.Default.Church

    TypeAnniversaire.FÊTE_PATRONALE ->
        Icons.Default.Celebration
}

/**
 * Construit la liste des anniversaires correspondant
 * au jour et au mois demandés.
 *
 * Les dates acceptées sont :
 *
 * JJ/MM
 * JJ/MM/AAAA
 *
 * Les valeurs invalides sont automatiquement ignorées.
 */
private fun construireAnniversairesDuJour(
    membres: List<Membre>,
    jour: Int,
    mois: Int
): List<Anniversaire> {

    val resultat = mutableListOf<Anniversaire>()

    membres.forEach { membre ->

        verifierDate(
            membre = membre,
            date = membre.naissance,
            type = TypeAnniversaire.NAISSANCE,
            jour = jour,
            mois = mois,
            resultat = resultat
        )

        verifierDate(
            membre = membre,
            date = membre.firstvows,
            type = TypeAnniversaire.PREMIERS_VŒUX,
            jour = jour,
            mois = mois,
            resultat = resultat
        )

        verifierDate(
            membre = membre,
            date = membre.finalvows,
            type = TypeAnniversaire.VŒUX_PERPETUELS,
            jour = jour,
            mois = mois,
            resultat = resultat
        )

        verifierDate(
            membre = membre,
            date = membre.sacerdoce,
            type = TypeAnniversaire.SACERDOCE,
            jour = jour,
            mois = mois,
            resultat = resultat
        )

        verifierDate(
            membre = membre,
            date = membre.fete,
            type = TypeAnniversaire.FÊTE_PATRONALE,
            jour = jour,
            mois = mois,
            resultat = resultat
        )
    }

    return resultat
}

/**
 * Vérifie une date.
 *
 * Exemples acceptés :
 *
 * 20/06
 * 08/09/2016
 * 01/01/2020
 *
 * Exemples ignorés :
 *
 * --------
 * ------
 * --/--
 * ??????
 * vide
 */
private fun verifierDate(
    membre: Membre,
    date: String,
    type: TypeAnniversaire,
    jour: Int,
    mois: Int,
    resultat: MutableList<Anniversaire>
) {

    val dateNettoyee = date.trim()

    if (dateNettoyee.isEmpty()) return

    val morceaux = dateNettoyee.split("/")

    if (morceaux.size < 2) return

    val jourDate = morceaux[0].toIntOrNull()
    val moisDate = morceaux[1].toIntOrNull()

    if (jourDate == null || moisDate == null) return

    if (jourDate !in 1..31) return
    if (moisDate !in 1..12) return

    if (jourDate == jour && moisDate == mois) {

        resultat.add(
            Anniversaire(
                membre = membre,
                type = type,
                date = dateNettoyee
            )
        )
    }
}

/**
 * Exemple :
 *
 * 7 septembre 2026
 */
private fun formaterDateDuJour(
    date: LocalDate
): String {

    val formatter = DateTimeFormatter.ofPattern(
        "d MMMM yyyy",
        Locale.FRENCH
    )

    return date.format(formatter)
}