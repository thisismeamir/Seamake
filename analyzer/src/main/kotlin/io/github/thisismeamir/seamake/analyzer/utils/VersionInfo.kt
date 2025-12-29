package io.github.thisismeamir.seamake.analyzer.utils

/**
 * Version information
 */
data class VersionInfo(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val suffix: String = ""
) : Comparable<VersionInfo> {

    override fun compareTo(other: VersionInfo): Int {
        if (major != other.major) return major.compareTo(other.major)
        if (minor != other.minor) return minor.compareTo(other.minor)
        if (patch != other.patch) return patch.compareTo(other.patch)
        return suffix.compareTo(other.suffix)
    }

    override fun toString(): String {
        return if (suffix.isEmpty()) {
            "$major.$minor.$patch"
        } else {
            "$major.$minor.$patch-$suffix"
        }
    }
}