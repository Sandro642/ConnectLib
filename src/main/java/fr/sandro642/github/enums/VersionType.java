package fr.sandro642.github.enums;

import fr.sandro642.github.provider.VersionProvider;

/**
 * VersionType is an enumeration representing different versions of the ConnectLib library.
 *
 * @author Sandro642
 * @version 1.0
 */

public enum VersionType implements VersionProvider {
    V1_BRANCH("v1"),
    V2_BRANCH("v2"),
    V3_BRANCH("v3"),
    V4_BRANCH("v4"),
    V5_BRANCH("v5");

    private final String version;

    VersionType(String version) {
        this.version = version;
    }

    @Override
    public String getVersion() {
        return version;
    }
}
