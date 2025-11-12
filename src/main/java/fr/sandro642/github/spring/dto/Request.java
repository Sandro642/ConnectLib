package fr.sandro642.github.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data Transfer Object representing a request.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {

    /**
     * Port information of the request.
     */
    private String infoPort;

    /**
     * Unique identifier for the request
     */
    private Long id;

    /**
     * Route associated with the request
     */
    private String route;

    /**
     * Branch associated with the request
     */
    private String branch;

    /**
     * Status of the request
     */
    private String status;

    /**
     * Default constructor
     */
    public Request() {}

    /**
     * Parameterized constructor
     * @param infoPort Port information of the request
     * @param id Unique identifier for the request
     * @param route Route associated with the request
     * @param branch Branch associated with the request
     * @param status Status of the request
     */
    public Request(String infoPort, Long id, String route, String branch, String status) {
        this.infoPort = infoPort;
        this.id = id;
        this.route = route;
        this.branch = branch;
        this.status = status;
    }

    /**
     * Get the unique identifier for the request.
     * @return the id
     */
    public Long getId() { return id; }

    /**
     * Set the unique identifier for the request.
     * @param id the id to set
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Get the route associated with the request.
     * @return the route
     */
    public String getRoute() { return route; }

    /**
     * Set the route associated with the request.
     * @param route the route to set
     */
    public void setRoute(String route) { this.route = route; }

    /**
     * Get the branch associated with the request.
     * @return the branch
     */
    public String getBranch() { return branch; }

    /**
     * Set the branch associated with the request.
     * @param branch the branch to set
     */
    public void setBranch(String branch) { this.branch = branch; }

    /**
     * Get the status of the request.
     * @return the status
     */
    public String getStatus() { return status; }

    /**
     * Set the status of the request.
     * @param status the status to set
     */
    public void setStatus(String status) { this.status = status; }
}
