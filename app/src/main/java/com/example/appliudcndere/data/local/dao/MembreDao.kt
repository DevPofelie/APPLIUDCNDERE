package com.example.appliudcndere.data.local.dao

import com.example.appliudcndere.data.local.entity.Membre
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MembreDao {

    // =========================================================
    // INSERTION
    // =========================================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserer(membre: Membre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insererTous(membres: List<Membre>)

    // =========================================================
    // MODIFICATION
    // =========================================================

    @Update
    suspend fun modifier(membre: Membre)

    // =========================================================
    // SUPPRESSION
    // =========================================================

    @Delete
    suspend fun supprimer(membre: Membre)

    // =========================================================
    // TOUS LES MEMBRES
    // =========================================================

    @Query("""
        SELECT * FROM fullcensus
        ORDER BY nom COLLATE NOCASE, prenom COLLATE NOCASE
    """)
    fun listerTous(): Flow<List<Membre>>

    // =========================================================
    // MEMBRE PAR ID
    // =========================================================

    @Query("""
        SELECT * FROM fullcensus
        WHERE id = :id
    """)
    suspend fun getParId(id: Long): Membre?

    // =========================================================
    // RECHERCHE NOM / PRÉNOM
    // =========================================================

    @Query("""
        SELECT * FROM fullcensus
        WHERE nom LIKE '%' || :terme || '%'
           OR prenom LIKE '%' || :terme || '%'
        ORDER BY nom COLLATE NOCASE, prenom COLLATE NOCASE
    """)
    fun rechercherParNomPrenom(
        terme: String
    ): Flow<List<Membre>>

    // =========================================================
    // RECHERCHE PAR LOCALITÉ
    // =========================================================

    @Query("""
        SELECT * FROM fullcensus
        WHERE localite LIKE '%' || :localite || '%'
        ORDER BY nom COLLATE NOCASE, prenom COLLATE NOCASE
    """)
    fun listerParLocalite(
        localite: String
    ): Flow<List<Membre>>

    // =========================================================
    // RECHERCHE CROISÉE
    //
    // NOM/PRÉNOM + LOCALITÉ
    // =========================================================

    @Query("""
        SELECT * FROM fullcensus
        WHERE
        (
            nom LIKE '%' || :terme || '%'
            OR prenom LIKE '%' || :terme || '%'
        )
        AND localite LIKE '%' || :localite || '%'
        ORDER BY nom COLLATE NOCASE, prenom COLLATE NOCASE
    """)
    fun rechercherParNomEtLocalite(
        terme: String,
        localite: String
    ): Flow<List<Membre>>

    // =========================================================
    // CONGRÉGATION + LOCALITÉ
    // =========================================================

    @Query("""
        SELECT * FROM fullcensus
        WHERE congregation LIKE '%' || :congregation || '%'
          AND localite LIKE '%' || :localite || '%'
        ORDER BY nom COLLATE NOCASE, prenom COLLATE NOCASE
    """)
    fun listerParCongregationEtLocalite(
        congregation: String,
        localite: String
    ): Flow<List<Membre>>

    // =========================================================
    // NOMBRE DE MEMBRES
    // =========================================================

    @Query("SELECT COUNT(*) FROM fullcensus")
    suspend fun compter(): Int

    // =========================================================================
    // REQUÊTES POUR LES ANNIVERSAIRES (CORRIGÉES SUR LES VARIABLES DU MEMBRE.KT)
    // =========================================================================

    @Query("SELECT * FROM fullcensus WHERE naissance LIKE :jourMois || '%'")
    suspend fun obtenirAnniversairesNaissance(jourMois: String): List<Membre>

    @Query("SELECT * FROM fullcensus WHERE firstvows LIKE :jourMois || '%'")
    suspend fun obtenirAnniversairesPremiersVoeux(jourMois: String): List<Membre>

    @Query("SELECT * FROM fullcensus WHERE finalvows LIKE :jourMois || '%'")
    suspend fun obtenerAnniversairesVoeuxPerpetuels(jourMois: String): List<Membre>

    @Query("SELECT * FROM fullcensus WHERE sacerdoce LIKE :jourMois || '%'")
    suspend fun obtenirAnniversairesSacerdoce(jourMois: String): List<Membre>

    @Query("SELECT * FROM fullcensus WHERE fete LIKE :jourMois || '%'")
    suspend fun obtenirAnniversairesFete(jourMois: String): List<Membre>
}
