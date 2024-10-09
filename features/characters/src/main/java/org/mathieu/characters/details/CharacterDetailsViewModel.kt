package org.mathieu.characters.details

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.domain.repositories.LocationRepository
import org.mathieu.ui.Destination
import org.mathieu.ui.ViewModel

sealed interface CharacterDetailsAction {
    data class SelectedLocation(val locationId: Int):
        CharacterDetailsAction
}

class CharacterDetailsViewModel(application: Application) : ViewModel<CharacterDetailsState>(
    CharacterDetailsState(), application) {

    private val characterRepository: CharacterRepository by inject()
    private val locationRepository: LocationRepository by inject()

    fun init(characterId: Int) {
        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name, error = null) }

                fetchData(
                    source = { locationRepository.getLocation(id = it.location.second) }
                ) {
                    onSuccess {
                        updateState { copy(locationId = it.id,locationName = it.name, locationType = it.type, error = null) }
                    }

                    onFailure {
                        updateState { copy(error = it.toString()) }
                    }

                    updateState { copy(isLoading = false) }
                }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }

    fun handleAction(action: CharacterDetailsAction) {
        when(action) {
            is CharacterDetailsAction.SelectedLocation -> selectedLocation(action.locationId)
        }
    }

    private fun selectedLocation(locationId: Int) =
        sendEvent(Destination.LocationDetails(locationId.toString()))

}


data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val locationId: Int = 0,
    val locationName: String = "",
    val locationType: String = "",
    val error: String? = null
)