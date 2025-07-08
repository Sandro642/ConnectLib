package fr.sandro642.github.jobs.misc;

public enum MethodType {
    POST(true),
    GET(false);

    private final Boolean type;

    MethodType(Boolean typeMethod) {
        this.type = typeMethod;
    }

    public Boolean getMethod() {
        return type;
    }
}
