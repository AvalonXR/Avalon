package xyz.avalonxr.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp

/**
 * @author Atri
 *
 * Describes the entity used for storing welcome messages related to user join events. This will later be used to send a
 * pre-configured message to the user when they join a guild.
 */
@Entity
@Table(name = "welcome_message")
class WelcomeMessage(

    /**
     * Maps to a Discord guild's ID.
     */
    @Column(name = "guild_id", insertable = true, updatable = true, unique = true, nullable = false)
    var guildId: Long,

    /**
     * Maps to a channel ID for a channel within a Discord guild.
     */
    @Column(name = "channel_id", insertable = true, updatable = true, nullable = false)
    var channelId: Long,

    /**
     * The message we want to send upon user join. This field may contain un-formatted text that will need to be parsed
     * at runtime before sending to the user.
     */
    @Column(name = "message", insertable = true, updatable = true, nullable = false)
    var message: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "welcome_message_id", insertable = false, updatable = false, unique = true)
    val welcomeMessageId: Long? = null

    @CreationTimestamp
    val createdTimestamp: Timestamp? = null

    @UpdateTimestamp
    val updateTimestamp: Timestamp? = null
}
