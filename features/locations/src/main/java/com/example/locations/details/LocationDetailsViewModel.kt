package com.example.locations.details

import android.app.Application
import org.mathieu.ui.ViewModel
import org.koin.core.component.inject
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.repositories.LocationRepository

class LocationDetailsViewModel(application: Application) : ViewModel<LocationDetailsState>(
    LocationDetailsState(), application
) {
    private val locationRepository: LocationRepository by inject()


    /**
     * Initializes the ViewModel by fetching location details from the repository
     * using the given locationId.
     *
     * This function performs the following:
     * - Fetches data from the repository using the provided location ID.
     * - On success, updates the state with the fetched location name, type, and resident characters.
     * - On failure, updates the state with the encountered error.
     * - Finally, sets the loading state to false once the data has been processed.
     *
     * @param locationId The unique identifier for the location to fetch.
     */
    fun init(locationId: Int) {
        fetchData(
            source = { locationRepository.getLocation(id = locationId) }
        ) {
            onSuccess {
                updateState { copy(name = it.name, type = it.type, characters = it.residents , error = null) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }
}

/**
 * Data class representing the UI state for the location details screen.
 *
 * This state is used to display the location's information (name, type),
 * its list of resident characters, loading status, and any potential errors.
 *
 * @property isLoading Boolean indicating whether data is currently being loaded.
 * @property name The name of the location.
 * @property type The type of the location (e.g., planet, city, etc.).
 * @property characters A list of resident [Character] objects that belong to this location.
 * @property error Optional error message in case something went wrong while fetching the data.
 */
data class LocationDetailsState(
    val isLoading: Boolean = true,
    val name: String = "",
    val type: String = "",
    val characters: List<Character> = emptyList(),
    val error: String? = null
)