package fr.sandro642.github.spring.dto;

/**
 * Data Transfer Object representing route information.
 */

public class RouteInfo {

    /**
     * Port information of the route.
     */
    private String infoPort;

    /**
     * Name of the route.
     */
    private String name;

    /**
     * URL of the route.
     */
    private String url;

    /**
     * Default constructor.
     */
    public RouteInfo() {}

    /**
     * Parameterized constructor.
     * @param name Name of the route
     * @param url URL of the route
     */
    public RouteInfo(String infoPort, String name, String url) {
        this.infoPort = infoPort;
        this.name = name;
        this.url = url;
    }

    /**
     * Get the name of the route.
     * @return the name of the route
     */
    public String getName() { return name; }

    /**
     * Set the name of the route.
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Get the URL of the route.
     * @return the URL of the route
     */
    public String getUrl() { return url; }

    /**
     * Set the URL of the route.
     * @param url the URL to set
     */
    public void setUrl(String url) { this.url = url; }
}
