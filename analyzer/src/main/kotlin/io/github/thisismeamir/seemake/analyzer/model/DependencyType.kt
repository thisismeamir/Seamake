package io.github.thisismeamir.seemake.analyzer.model

enum class DependencyType {
    FIND_PACKAGE,
    FETCH_CONTENT,
    SUBDIRECTORY,
    EXTERNAL_PROJECT,
    PKG_CONFIG,
    UNKNOWN
}