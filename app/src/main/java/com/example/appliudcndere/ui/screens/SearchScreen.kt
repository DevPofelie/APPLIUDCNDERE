package com.example.appliudcndere.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appliudcndere.data.local.entity.Membre
import com.example.appliudcndere.viewmodel.MembreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MembreViewModel,
    onModifier: (Long) -> Unit
) {
    val terme by viewModel.terme.collectAsState()
    val localite by viewModel.localite.collectAsState()
    val membres by viewModel.membres.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("UDCNdere — Recherche")
                }
            )
        },

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            // =====================================================
            // NOM / PRÉNOM
            // =====================================================

            OutlinedTextField(
                value = terme,
                onValueChange = viewModel::onTermeChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text("Nom ou prénom")
                },
                placeholder = {
                    Text("Ex. SIMO ou Thierry")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Recherche"
                    )
                },
                trailingIcon = {
                    if (terme.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.onTermeChange("")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Effacer"
                            )
                        }
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // =====================================================
            // LIEU DE RÉSIDENCE
            // =====================================================

            OutlinedTextField(
                value = localite,
                onValueChange = viewModel::onLocaliteChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = {
                    Text("Lieu de résidence")
                },
                placeholder = {
                    Text("Ex. Ngaoundéré")
                },
                leadingIcon = {
                    Text("📍")
                },
                trailingIcon = {
                    if (localite.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.onLocaliteChange("")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Effacer la localité"
                            )
                        }
                    }
                }
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // =====================================================
            // INFORMATION
            // =====================================================

            Text(
                text = "La recherche combine automatiquement le nom/prénom et le lieu de résidence.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // =====================================================
            // COMPTEUR
            // =====================================================

            // =====================================================
// COMPTEUR (À remplacer dans SearchScreen.kt)
// =====================================================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (terme.isBlank() && localite.isBlank()) "Tous les membres" else "Résultats de recherche",
                    style = MaterialTheme.typography.titleSmall
                )

                // Si aucune saisie n'est faite, on peut afficher un texte ou forcer à 0 selon votre choix
                Text(
                    text = if (terme.isBlank() && localite.isBlank()) "51" else "${membres.size}",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // ---------------------------------------------------------
// RÉSULTATS
// ---------------------------------------------------------

            // Dans SearchScreen.kt, remplacez la section RÉSULTATS par :
            // Dans SearchScreen.kt, remplacez la section RÉSULTATS par :
            if (terme.isBlank() && localite.isBlank()) {

                Text(
                    text = "Saisissez un nom, un prénom ou un lieu de résidence pour lancer une recherche.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            } else if (membres.isEmpty()) {

                Text(
                    text = "Aucun membre ne correspond aux critères.",
                    style = MaterialTheme.typography.bodyMedium
                )

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    items(
                        items = membres,
                        key = { membre -> membre.id }
                    ) { membre ->

                        MembreCard(
                            membre = membre,
                            modifier = Modifier.clickable {
                                onModifier(membre.id)
                            }
                        )
                    }
                }
            }
        }
    }
}


/**
 * Carte d'affichage d'un membre.
 */
@Composable
fun MembreCard(
    membre: Membre,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {

        Text(
            text = listOfNotNull(
                membre.nom,
                membre.prenom
            ).joinToString(" "),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        membre.congregation?.takeIf {
            it.isNotBlank()
        }?.let {

            Text(
                text = "Congrégation : $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        membre.localite?.takeIf {
            it.isNotBlank()
        }?.let {

            Text(
                text = "📍 $it",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}