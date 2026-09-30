package com.example.appliudcndere.data.repository

import com.example.appliudcndere.data.local.dao.MembreDao
import com.example.appliudcndere.data.local.entity.Membre
import kotlinx.coroutines.flow.Flow

class MembreRepository(private val dao: MembreDao) {

    fun listerTous(): Flow<List<Membre>> = dao.listerTous()

    suspend fun getParId(id: Long): Membre? = dao.getParId(id)

    fun rechercher(terme: String): Flow<List<Membre>> = dao.rechercherParNomPrenom(terme)

    fun listerParLocalite(localite: String): Flow<List<Membre>> = dao.listerParLocalite(localite)

    fun rechercherParNomEtLocalite(terme: String, localite: String): Flow<List<Membre>> =
        dao.rechercherParNomEtLocalite(terme, localite)

    // =========================================================================
    // PASSERELLES ANNIVERSAIRES SYNCHRONISÉES (Correction de la lettre "e")
    // =========================================================================

    suspend fun obtenirAnniversairesNaissance(jourMois: String): List<Membre> =
        dao.obtenirAnniversairesNaissance(jourMois)

    suspend fun obtenirAnniversairesPremiersVoeux(jourMois: String): List<Membre> =
        dao.obtenirAnniversairesPremiersVoeux(jourMois)

    suspend fun obtenerAnniversairesVoeuxPerpetuels(jourMois: String): List<Membre> =
        dao.obtenerAnniversairesVoeuxPerpetuels(jourMois)

    suspend fun obtenirAnniversairesSacerdoce(jourMois: String): List<Membre> =
        dao.obtenirAnniversairesSacerdoce(jourMois)

    suspend fun obtenirAnniversairesFete(jourMois: String): List<Membre> =
        dao.obtenirAnniversairesFete(jourMois)
}
