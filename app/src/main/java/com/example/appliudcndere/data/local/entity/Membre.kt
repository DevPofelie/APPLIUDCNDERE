package com.example.appliudcndere.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Représente un membre enregistré dans la table fullcensus.
 *
 * Correspond aux informations disponibles dans
 * udcndere_fullcensus_seed.csv
 */
@Entity(tableName = "fullcensus")
data class Membre(

    @PrimaryKey(autoGenerate = false)
    val id: Long,

    val nom: String = "",

    val prenom: String = "",

    val naissance: String = "",

    val nationalite: String = "",

    val congregation: String = "",

    val fete: String = "",

    val firstvows: String = "",

    val finalvows: String = "",

    val sacerdoce: String = "",

    val localite: String = ""
)