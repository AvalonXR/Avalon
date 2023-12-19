package xyz.avalonxr.data

class OptionStore(
    vararg options: Pair<String, Any>
) : Map<String, Any> by mapOf(*options) {

    inline fun <reified T> firstOfType(): T? = entries
        .firstOrNull { (key, value) -> value is T }
        ?.value as T

    @Suppress("UNCHECKED_CAST")
    fun <T> findByName(name: String): T? = get(name)
        ?.let { it as? T }
}

fun optionStoreOf(
    vararg options: Pair<String, Any>
): OptionStore =
    OptionStore(*options)
