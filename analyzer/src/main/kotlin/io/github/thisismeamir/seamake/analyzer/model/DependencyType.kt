package io.github.thisismeamir.seamake.analyzer.model

enum class DependencyType {
    FIND_PACKAGE,
    FETCH_CONTENT,
    SUBDIRECTORY,
    EXTERNAL_PROJECT,
    PKG_CONFIG,
    UNKNOWN
}