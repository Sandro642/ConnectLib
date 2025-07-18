package fr.sandro642.github.enums;

/**
 * VersionType is an enumeration representing different versions of the ConnectLib library.
 *
 * @author Sandro642
 * @version 1.0
 */

public enum VersionType {
    V1_BRANCH("v1"),
    V2_BRANCH("v2"),
    V3_BRANCH("v3"),
    V4_BRANCH("v4"),
    V5_BRANCH("v5");

    private final String Version;

    VersionType(String version) {
        this.Version = version;
    }

    public String getVersion() {
        return Version;
    }
}
