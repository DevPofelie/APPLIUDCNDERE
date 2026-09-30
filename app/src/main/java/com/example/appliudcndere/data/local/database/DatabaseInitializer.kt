package com.example.appliudcndere.data.local.database

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.appliudcndere.data.local.entity.Membre

object DatabaseInitializer {

    private const val FILE_NAME =
        "udcndere_fullcensus_seed.csv"

    suspend fun importerSiNecessaire(
        context: Context,
        database: AppDatabase
    ) = withContext(Dispatchers.IO) {

        val dao = database.membreDao()

        // Si la base contient déjà des membres,
        // on ne réimporte pas le fichier.
        if (dao.compter() > 0) {
            return@withContext
        }

        val membres = mutableListOf<Membre>()

        context.assets.open(FILE_NAME)
            .bufferedReader(Charsets.UTF_8)
            .useLines { lignes ->

                lignes.drop(1).forEach { ligne ->

                    if (ligne.isBlank()) return@forEach

                    val colonnes = ligne.split(",")

                    if (colonnes.size >= 11) {

                        try {

                            val membre = Membre(

                                id = colonnes[0]
                                    .trim()
                                    .toLong(),

                                nom = colonnes[1]
                                    .trim(),

                                prenom = colonnes[2]
                                    .trim(),

                                naissance = colonnes[3]
                                    .trim(),

                                nationalite = colonnes[4]
                                    .trim(),

                                congregation = colonnes[5]
                                    .trim(),

                                fete = colonnes[6]
                                    .trim(),

                                firstvows = colonnes[7]
                                    .trim(),

                                finalvows = colonnes[8]
                                    .trim(),

                                sacerdoce = colonnes[9]
                                    .trim(),

                                localite = colonnes[10]
                                    .trim()
                            )

                            membres.add(membre)

                        } catch (_: Exception) {
                            // Ligne ignorée si elle est incorrecte
                        }
                    }
                }
            }

        if (membres.isNotEmpty()) {
            dao.insererTous(membres)
        }
    }
}