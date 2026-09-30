package com.example.appliudcndere.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appliudcndere.data.repository.MembreRepository
import com.example.appliudcndere.ui.components.NouvellesQuotidiennesEcran
import com.example.appliudcndere.viewmodel.MembreViewModel
import com.example.appliudcndere.ui.screens.HomeScreen
import com.example.appliudcndere.ui.screens.AnniversairesScreen
import com.example.appliudcndere.ui.screens.ConstructionScreen
import com.example.appliudcndere.ui.screens.AboutScreen
import com.example.appliudcndere.ui.screens.SearchScreen
import com.example.appliudcndere.viewmodel.MembreViewModelFactory
import com.example.appliudcndere.ui.screens.IntentionsScreen

private const val ROUTE_HOME = "home"
private const val ROUTE_RECHERCHE = "recherche"

private const val ROUTE_ANNIVERSAIRES = "anniversaires"
private const val ROUTE_INTENTIONS = "intentions"
private const val ROUTE_NOUVELLES = "nouvelles"
private const val ROUTE_ABOUT = "about"

private const val ROUTE_FORMULAIRE = "formulaire/{membreId}"

@Composable
fun AppNavGraph(
    repository: MembreRepository,
    navController: NavHostController = rememberNavController()
) {

    /*
     * ViewModel principal utilisé pour la recherche des membres.
     *
     * Il est créé une seule fois au niveau du NavGraph
     * et partagé avec l'écran de recherche.
     */
    // Modifiez la ligne 40 dans AppNavGraph.kt :
    val MembreViewModel: MembreViewModel = viewModel<MembreViewModel>(
        factory = MembreViewModelFactory(repository)
    )


    NavHost(
        navController = navController,
        startDestination = ROUTE_HOME
    ) {

        // ============================================================
        // 1. ACCUEIL
        // ============================================================

        composable(
            route = ROUTE_HOME
        ) {

            HomeScreen(

                // Consultation des religieux/religieuses
                onConsultationClick = {
                    navController.navigate(ROUTE_RECHERCHE)
                },

                // Anniversaires
                onAnniversairesClick = {
                    navController.navigate(ROUTE_ANNIVERSAIRES)
                },

                // Intentions de prière du Pape
                onIntentionsClick = {
                    navController.navigate(ROUTE_INTENTIONS)
                },

                // Nouvelles quotidiennes
                onNouvellesClick = {
                    navController.navigate(ROUTE_NOUVELLES)
                },

                // À propos
                onAboutClick = {
                    navController.navigate(ROUTE_ABOUT)
                },

                // Retour
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // ============================================================
// 2. ANNIVERSAIRES
// ============================================================

        composable(
            route = ROUTE_ANNIVERSAIRES
        ) {

            AnniversairesScreen(
                viewModel = MembreViewModel,
                onRetour = {
                    navController.popBackStack()
                }
            )
        }

        // ============================================================
        // 3. INTENTIONS DE PRIÈRE DU PAPE — PAGE EN CONSTRUCTION
        // ============================================================

        composable(
            route = ROUTE_INTENTIONS
        ) {

            IntentionsScreen(

                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // ============================================================
        // 4. NOUVELLES QUOTIDIENNES — PAGE EN CONSTRUCTION
        // ============================================================

        composable(
            route = ROUTE_NOUVELLES
        ) {

            NouvellesQuotidiennesEcran(
                onRetourClick = {
                    navController.popBackStack()
                }
            )
        }

        // ============================================================
        // 5. À PROPOS
        // ============================================================

        composable(
            route = ROUTE_ABOUT
        ) {

            AboutScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ============================================================
        // 6. RECHERCHE DES MEMBRES
        // ============================================================

        composable(
            route = ROUTE_RECHERCHE
        ) {

            SearchScreen(
                viewModel = MembreViewModel,
                onModifier = { membreId ->
                    navController.navigate("formulaire/$membreId")
                }
            )

        }

        }
    }