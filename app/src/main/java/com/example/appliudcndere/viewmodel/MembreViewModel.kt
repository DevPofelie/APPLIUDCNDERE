package com.example.appliudcndere.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appliudcndere.data.local.entity.Membre
import com.example.appliudcndere.data.repository.MembreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class MembreViewModel(private val repository: MembreRepository) : ViewModel() {

    private val _terme = MutableStateFlow("")
    val terme = _terme.asStateFlow()

    private val _localite = MutableStateFlow("")
    val localite = _localite.asStateFlow()

    fun onTermeChange(nouveauTerme: String) {
        _terme.value = nouveauTerme
    }

    fun onLocaliteChange(nouvelleLocalite: String) {
        _localite.value = nouvelleLocalite
    }

    // =========================================================================
    // RECHERCHE CROISÉE ET FILTRAGE EN TEMPS RÉEL
    // =========================================================================
    @OptIn(ExperimentalCoroutinesApi::class)
    val membres: StateFlow<List<Membre>> =
        combine(
            _terme.debounce(200),
            _localite.debounce(200)
        ) { termeRecherche, localiteRecherche ->
            Pair(termeRecherche.trim(), localiteRecherche.trim())
        }
            .flatMapLatest { (termeRecherche, localiteRecherche) ->
                when {
                    termeRecherche.isBlank() && localiteRecherche.isBlank() -> {
                        flowOf(emptyList())
                    }
                    termeRecherche.isBlank() -> {
                        repository.listerParLocalite(localiteRecherche)
                    }
                    localiteRecherche.isBlank() -> {
                        repository.rechercher(termeRecherche)
                    }
                    else -> {
                        repository.rechercherParNomEtLocalite(termeRecherche, localiteRecherche)
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
}
