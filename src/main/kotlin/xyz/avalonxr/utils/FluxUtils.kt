package xyz.avalonxr.utils

import reactor.core.publisher.Flux

/**
 * @author Atri
 *
 * Retrieves a list from a given [Flux] instance. This is blocking by nature, care should be exercised to ensure this is
 * not called more than necessary if you're attempting to make sure your code executes as asynchronously as possible.
 *
 * @return A [List] containing all values present of expected type [T].
 */
fun <T : Any> Flux<T>.blockList(): List<T> = collectList()
    .block()
    ?: emptyList()
