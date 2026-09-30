package com.example.appliudcndere.model

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.appliudcndere.ui.theme.LiturgicalThemeManager
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LiturgyWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val client = OkHttpClient()

        // 1. Génération automatique de la date du jour (ex: 2026-09-09) pour interroger l'API
        val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateDuJour = formatDate.format(Date())

        // URL ciblée sur l'API publique du calendrier liturgique romain pour le jour actuel
        val urlApi = "http://inadiutorium.cz"

        val request = Request.Builder()
            .url(urlApi)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful && response.body != null) {
                    val responseData = response.body!!.string()

                    // 2. Analyse de la réponse JSON du site liturgique
                    val jsonObject = JSONObject(responseData)
                    val celebrations = jsonObject.getJSONArray("celebrations")

                    if (celebrations.length() > 0) {
                        val premiereCelebration = celebrations.getJSONObject(0)

                        // Récupération de la couleur retournée par le site ("green", "white", "red", "violet")
                        val colorName = premiereCelebration.getString("colour")

                        // 3. Sauvegarde ET déclenchement instantané du changement de couleur global
                        LiturgicalThemeManager.saveColor(applicationContext, colorName)

                        Log.d("LiturgyWorker", "Mise à jour automatique réussie pour $dateDuJour : $colorName")
                        Result.success()
                    } else {
                        Result.failure()
                    }
                } else {
                    Log.e("LiturgyWorker", "Erreur de réponse du site liturgique : ${response.code}")
                    Result.retry() // Réessaye automatiquement plus tard si le serveur est surchargé
                }
            }
        } catch (e: Exception) {
            Log.e("LiturgyWorker", "Impossible de joindre le site liturgique (Pas de réseau)", e)
            Result.retry() // Réessaye automatiquement dès que le smartphone retrouve internet
        }
    }
}