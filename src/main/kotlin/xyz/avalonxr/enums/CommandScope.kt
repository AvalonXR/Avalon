package xyz.avalonxr.enums

/**
 * @author Atri
 *
 * A limited type which describes the valid scopes for which a command can be defined in. According to Discord's
 * documentation, this falls under two types, [GLOBAL] and [GUILD] commands. This enum is used as part of our app's
 * command DSL, which will allow us to describe the preferred scope for which we will attempt to install our command
 * into.
 */
enum class CommandScope {

    /**
     * Refers to commands meant to be stored in the global namespace, accessible from all guilds, as well as in DMs. It
     * is recommended that during testing that commands are not saved in this scope as Discord enforces a 1-hour TLS on
     * all changes made to commands in this scope, whereas this update period is significantly shorter for the [GUILD]
     * scope.
     */
    GLOBAL,

    /**
     * Refers to commands meant to be stored in the guild namespace. These can differ between servers, and be updated
     * more frequently. This makes them far more ideal for testing and debugging, but they must be installed on a
     * per-guild basis, which may make them more annoying to manage internally.
     */
    GUILD,
    ;

    /**
     * Whether the given scope is global.
     *
     * @return True if the scope is global.
     */
    fun isGlobal(): Boolean = this == GLOBAL

    /**
     * Whether the given scope is guild.
     *
     * @return True if the scope is guild.
     */
    fun isGuild(): Boolean = this == GUILD
}
