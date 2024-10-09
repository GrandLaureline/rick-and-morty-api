package org.mathieu.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.data.remote.responses.LocationResponse
import org.mathieu.data.repositories.tryOrNull
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.character.CharacterGender
import org.mathieu.domain.models.character.CharacterStatus
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.models.locationPreview.LocationPreview

/**
 * Represents a location entity stored in the SQLite database. This object provides fields
 * necessary to represent all the attributes of a location from the data source.
 * The object is specifically tailored for SQLite storage using Realm.
 *
 * @property id Unique identifier of the location.
 * @property name Name of the location.
 * @property type The type of the location.
 * @property dimension The dimension of the location.
 * @property characterId Unique identifier of the character.
 * @property characterName The name of the character.
 * @property characterStatus The status of the character.
 * @property characterSpecies The dimension of the character.
 * @property characterType The type or subspecies of the character.
 * @property characterGender Gender of the character (e.g. 'Female', 'Male').
 * @property characterOriginName The origin location name.
 * @property characterOriginId The origin location id.
 * @property characterImage The image of the character.
 * @property created Timestamp indicating when the location entity was created in the database.
 */
internal class LocationObject: RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var type: String = ""
    var dimension: String = ""
    var characterId: Int = -1
    var characterName: String = ""
    var characterStatus: String = ""
    var characterSpecies: String = ""
    var characterType: String = ""
    var characterGender: String = ""
    var characterOriginName: String = ""
    var characterOriginId: Int = -1
    var characterImage: String = ""
    var created: String = ""
}

internal fun LocationResponse.toRealmObject() = LocationObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.type = type
    obj.dimension = dimension
    obj.created = created
}

internal fun LocationObject.toModel() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = listOf(
        Character(
            id = characterId,
            name = characterName,
            status = tryOrNull { CharacterStatus.valueOf(characterStatus) } ?: CharacterStatus.Unknown,
            species = characterSpecies,
            type = characterType,
            gender = tryOrNull { CharacterGender.valueOf(characterGender) } ?: CharacterGender.Unknown,
            avatarUrl = characterImage,
            origin = characterOriginName to characterOriginId,
            location = name to id,
            locationPreview = LocationPreview(
                id = id,
                name = name,
                type = type,
                dimension = dimension
            )
        )
    )
)