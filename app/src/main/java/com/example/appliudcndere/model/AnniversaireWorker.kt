package com.example.appliudcndere.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appliudcndere.data.local.database.AppDatabase
import com.example.appliudcndere.data.local.entity.Membre
import com.example.appliudcndere.data.repository.MembreRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AnniversaireWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val CHANNEL_ID = "anniversaires_channel"
    private var notificationId = 100

    override suspend fun doWork(): Result {
        val appContext = applicationContext
        val repository = MembreRepository(AppDatabase.getInstance(appContext).membreDao())

        // 1. Récupérer le jour et le mois actuels au format "JJ/MM" (ex: "11/04")
        val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
        val aujourdHui = sdf.format(Calendar.getInstance().time)

        // 2. Interroger les 5 catégories (Synchronisées avec les bons noms de fonctions)
        verifierEtNotifier(repository.obtenirAnniversairesNaissance(aujourdHui), "Anniversaire de naissance")
        verifierEtNotifier(repository.obtenirAnniversairesPremiersVoeux(aujourdHui), "Anniversaire de premiers vœux")
        verifierEtNotifier(repository.obtenerAnniversairesVoeuxPerpetuels(aujourdHui), "Anniversaire de vœux perpétuels")
        verifierEtNotifier(repository.obtenirAnniversairesSacerdoce(aujourdHui), "Anniversaire de sacerdoce")
        verifierEtNotifier(repository.obtenirAnniversairesFete(aujourdHui), "Fête patronale / Anniversaire de fête")

        return Result.success()
    }

    private fun verifierEtNotifier(membres: List<Membre>, categorie: String) {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Création du canal de notification pour Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notifications d'Anniversaires",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }

        // Créer une notification distincte pour chaque personne concernée
        membres.forEach { membre ->
            val nomComplet = listOfNotNull(membre.nom, membre.prenom).joinToString(" ")

            val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(nomComplet)
                .setContentText(categorie)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()

            manager.notify(notificationId++, notification)
        }
    }
}
