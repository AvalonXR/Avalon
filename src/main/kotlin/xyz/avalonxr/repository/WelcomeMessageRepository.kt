package xyz.avalonxr.repository

import org.springframework.data.repository.CrudRepository
import xyz.avalonxr.data.entity.WelcomeMessage
import java.util.Optional

/**
 * @author Atri
 *
 * A repository for managing [WelcomeMessage] entities in our database. This provides functionality for retrieving any
 */
interface WelcomeMessageRepository : CrudRepository<WelcomeMessage, Long> {

    /**
     * Retrieves entities by guild id.
     *
     * @param guildId The guild id we want to retrieve.
     *
     * @return An entry matching the given [guildId] or empty if not present in the database.
     */
    fun findByGuildId(guildId: Long): Optional<WelcomeMessage>

    /**
     * Deletes entities by guild id.
     *
     * @param guildId The guild id for the welcome message we want to delete.
     */
    fun deleteByGuildId(guildId: Long)
}
