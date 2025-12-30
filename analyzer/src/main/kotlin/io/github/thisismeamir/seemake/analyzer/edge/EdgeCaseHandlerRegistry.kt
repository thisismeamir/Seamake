package io.github.thisismeamir.seemake.analyzer.edge

/**
 * Registry for edge case handlers
 */
class EdgeCaseHandlerRegistry {
    private val handlers = mutableListOf<EdgeCaseHandler>()

    fun register(handler: EdgeCaseHandler) {
        handlers.add(handler)
        handlers.sortByDescending { it.priority() }
    }

    fun unregister(handler: EdgeCaseHandler) {
        handlers.remove(handler)
    }

    fun getHandlers(): List<EdgeCaseHandler> = handlers.toList()

    fun clear() {
        handlers.clear()
    }
}