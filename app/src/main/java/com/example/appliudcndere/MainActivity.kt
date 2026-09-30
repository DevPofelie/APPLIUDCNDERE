package com.example.appliudcndere

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.appliudcndere.data.local.database.AppDatabase
import com.example.appliudcndere.data.repository.MembreRepository
import com.example.appliudcndere.model.AnniversaireWorker
import com.example.appliudcndere.ui.navigation.AppNavGraph
import com.example.appliudcndere.ui.theme.AppliUDCNdereTheme
import java.util.Calendar
import java.util.concurrent.TimeUnit
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import com.example.appliudcndere.model.LiturgyWorker
import com.example.appliudcndere.ui.theme.LiturgicalThemeManager
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.appliudcndere.ui.navigation.AppNavGraph


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. DEMANDE DE PERMISSION INTERACTIVE (Pour Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
        }

        // 2. DÉCLENCHEMENT DE LA PLANIFICATION À 03H00 DU MATIN
        planifierNotificationAnniversaires()

        // 3. VÉRIFICATION IMMÉDIATE AU CAS OÙ L'UTILISATEUR OUVRE L'APP APRÈS 03H00
        //    (garantit la notification même si le téléphone était éteint à 03h00)
        val requeteImmediate = OneTimeWorkRequestBuilder<AnniversaireWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(requeteImmediate)

        // 4. CONSTRUCTION DE LA BASE DE DONNÉES ET DU REPOSITORY
        val database = AppDatabase.getInstance(applicationContext)
        val repository = MembreRepository(database.membreDao())

        // 5. ENTRÉE DE L'INTERFACE GRAPHIQUE
        setContent {
            AppliUDCNdereTheme {
                AppNavGraph(repository = repository)
            }
        }

        LiturgicalThemeManager.init(applicationContext)

        // Configuration des contraintes : l'application attend d'avoir internet pour interroger le site
        val contraintesReseau = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Planification automatique : s'exécute en arrière-plan toutes les 12 heures
        val requeteLiturgique = PeriodicWorkRequestBuilder<LiturgyWorker>(12, TimeUnit.HOURS)
            .setConstraints(contraintesReseau)
            .build()

        // Lance la synchronisation en arrière-plan de manière unique
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "SynchroCouleurLiturgique",
            ExistingPeriodicWorkPolicy.KEEP, // Conserve la tâche sans la dupliquer au redémarrage
            requeteLiturgique
        )

        setContent {
            AppliUDCNdereTheme {
                AppNavGraph(repository = repository)
            }
        }
    }

    // =========================================================================
    // FONCTION DE PLANIFICATION AUTOMATIQUE EN ARRIÈRE-PLAN (03h00)
    // =========================================================================
    private fun planifierNotificationAnniversaires() {
        val calendrierActuel = Calendar.getInstance()

        val calendrierCible = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 3)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (calendrierActuel.after(calendrierCible)) {
            calendrierCible.add(Calendar.DAY_OF_YEAR, 1)
        }

        val delaiInitial = calendrierCible.timeInMillis - calendrierActuel.timeInMillis

        val requeteAnniversaire = PeriodicWorkRequestBuilder<AnniversaireWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(delaiInitial, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "WorkAnniversairesUnique",
            ExistingPeriodicWorkPolicy.KEEP,
            requeteAnniversaire
        )
    }
}
