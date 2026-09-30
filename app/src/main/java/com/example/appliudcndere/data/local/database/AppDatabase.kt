package com.example.appliudcndere.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appliudcndere.data.local.dao.MembreDao
import com.example.appliudcndere.data.local.entity.Membre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

@Database(
    entities = [Membre::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun membreDao(): MembreDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "udcndere.db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .addCallback(AppDatabaseCallback(context.applicationContext))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    // =========================================================================
    // CLASSE INTERNE : CALLBACK POUR REMPLIR LA BASE AU PREMIER DÉMARRAGE
    // =========================================================================
    private class AppDatabaseCallback(
        private val context: Context
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    peuplerDepuisCSV(context, database.membreDao())
                }
            }
        }

        // =========================================================================
        // LECTURE DU CSV — COLONNES FIXES ET CONNUES :
        // 0=id, 1=nom, 2=prénom, 3=naissance, 4=nationalité, 5=congrégation,
        // 6=fête, 7=firstvows, 8=finalvows, 9=sacerdoce, 10=localité, 11=email, 12=tél
        // =========================================================================
        private suspend fun peuplerDepuisCSV(context: Context, dao: MembreDao) {
            try {
                val inputStream = context.assets.open("udcndere_fullcensus_seed.csv")
                val reader = BufferedReader(InputStreamReader(inputStream))

                // Ignorer la ligne d'en-tête (la toute première ligne)
                reader.readLine()

                val listeMembres = mutableListOf<Membre>()
                var ligne: String? = reader.readLine()

                while (ligne != null) {
                    val ligneNettoyee = ligne.trim()
                    if (ligneNettoyee.isNotEmpty()) {
                        val tokens = ligneNettoyee.split(",")

                        if (tokens.size >= 11) {

                            val nomMembre = tokens[1].trim()
                            val prenomMembre = tokens[2].trim()
                            val naissanceMembre = tokens[3].trim()
                            val congregationMembre = tokens[5].trim()
                            val feteMembre = tokens[6].trim()
                            val firstvowsMembre = tokens[7].trim()
                            val finalvowsMembre = tokens[8].trim()
                            val sacerdoceMembre = tokens[9].trim()
                            var localiteMembre = tokens[10].trim()

                            if (localiteMembre.isEmpty()) {
                                localiteMembre = "Inconnue"
                            }

                            val membre = Membre(
                                id = tokens[0].toLongOrNull() ?: 0L,
                                nom = nomMembre,
                                prenom = prenomMembre,
                                naissance = naissanceMembre,
                                congregation = congregationMembre,
                                fete = feteMembre,
                                firstvows = firstvowsMembre,
                                finalvows = finalvowsMembre,
                                sacerdoce = sacerdoceMembre,
                                localite = localiteMembre
                            )
                            listeMembres.add(membre)
                        }
                    }
                    ligne = reader.readLine()
                }
                reader.close()

                if (listeMembres.isNotEmpty()) {
                    dao.insererTous(listeMembres)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}