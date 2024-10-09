package org.mathieu.data.local

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import org.mathieu.data.local.objects.LocationObject

/**
 * This class provides local data operations for the `LocationObject` using a `RealmDatabase`.
 *
 * The `LocationLocal` class handles retrieving and inserting location data into the local Realm database.
 *
 * @property database The Realm database instance used for local storage operations.
 */
internal class LocationLocal(private val database: RealmDatabase) {

    /**
     * Retrieves a location from the local database based on its ID.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return The `LocationObject` if found, or null if the location does not exist in the database.
     */
    suspend fun getLocation(id: Int): LocationObject? = database.use {
        query<LocationObject>("id == $id").first().find()
    }

    /**
     * Inserts or updates a location in the local database.
     *
     * The method writes the `LocationObject` into the Realm database, updating it if it already exists.
     *
     * @param location The `LocationObject` to be inserted or updated.
     */
    suspend fun insert(location: LocationObject) {
        database.write {
            copyToRealm(location, UpdatePolicy.ALL)
        }
    }
}