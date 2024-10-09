package org.mathieu.data.repositories

import org.mathieu.data.local.LocationLocal
import org.mathieu.data.local.objects.toModel
import org.mathieu.data.local.objects.toRealmObject
import org.mathieu.data.remote.LocationApi
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.repositories.LocationRepository
import java.lang.Exception

/**
 * Implementation of the `LocationRepository` interface.
 *
 * This class provides access to location data by fetching from both the local database (`LocationLocal`)
 * and a remote API (`LocationApi`). It follows a "local-first" approach: attempting to retrieve data
 * from the local database first, and only querying the API if the data is not available locally.
 *
 * @property locationApi The API interface used to fetch location data remotely.
 * @property locationLocal The local data source used to store and retrieve location data.
 */
internal class LocationRepositoryImpl(
    private val locationApi: LocationApi,
    private val locationLocal: LocationLocal
) : LocationRepository {

    /**
     * Retrieves a location by its ID, checking the local database first.
     *
     * This method first attempts to retrieve the location from the local database using `LocationLocal`.
     * If the location is not found locally, it queries the remote API via `LocationApi`. If the location
     * is successfully fetched from the API, it is inserted into the local database and returned as a model.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return The `Location` model.
     * @throws Exception If the location is not found in either the local or remote data sources.
     */
    override suspend fun getLocation(id: Int): Location =
        locationLocal.getLocation(id)?.toModel()
            ?: locationApi.getLocation(id = id)?.let { response ->
                val obj = response.toRealmObject()
                locationLocal.insert(obj)
                obj.toModel()
            }
            ?: throw Exception("Location not found.")

}