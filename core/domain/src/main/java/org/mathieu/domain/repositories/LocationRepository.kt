package org.mathieu.domain.repositories

import org.mathieu.domain.models.location.Location

interface LocationRepository {
    /**
     * Fetches the details of a specific location based on the provided ID.
     *
     * @param id The unique identifier of the location to be fetched.
     * @return Details of the specified location.
     */
    suspend fun getLocation(id: Int): Location
}