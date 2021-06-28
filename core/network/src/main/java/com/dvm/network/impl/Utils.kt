package com.dvm.network.impl

import com.dvm.utils.hasCode

internal suspend fun <T> getAllChunks(
    limit: Int = 30,
    call: suspend (offset: Int, limit: Int) -> List<T>
): List<T> {

    val elements = mutableListOf<T>()
    var offset = 0

    while (true) {
        val chunk = try {
            call(offset * limit, limit)
        } catch (exception: Exception) {
            if (exception.hasCode(304)) {
                break
            } else {
                throw exception
            }
        }

        elements.addAll(chunk)
        if (chunk.size < limit) break
        offset++
    }

    return elements
}