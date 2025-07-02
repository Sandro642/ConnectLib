package fr.sandro642.github.jobs.misc;

public enum VersionType {
    V1_BRANCH("v1"),
    V2_BRANCH("v2");

    private final String Version;

    VersionType(String version) {
        this.Version = version;
    }

    public String getVersion() {
        return Version;
    }
}
