package fr.sandro642.github.spring.controller;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.spring.dto.Request;
import fr.sandro642.github.spring.dto.RouteInfo;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * DataController is a REST controller that provides endpoints to manage requests and retrieve status and routes.
 */

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DataController {

    /**
     * Singleton instance of DataController.
     */
    private static final DataController INSTANCE = new DataController();

    /**
     * Private constructor to enforce singleton pattern.
     */
    private DataController() {}

    /**
     * Get the singleton instance of DataController.
     * @return the singleton instance
     */
    public static DataController getInstance() {
        return INSTANCE;
    }

    /**
     * ConnectLib instance for managing connections.
     */
    private final ConnectLib connectLib = new ConnectLib();

    /**
     * Atomic counter for generating unique request IDs.
     */
    private final AtomicLong requestIdCounter = new AtomicLong(0);

    /**
     * Concurrent map to store requests.
     */
    private final Map<Long, Request> requestsMap = new ConcurrentHashMap<>();

    /**
     * Endpoint to get the status of the ConnectLib.
     * @return a map containing the initialization status and resource type
     */
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("isInitialized", connectLib.isInitialized());
        status.put("resourceType", connectLib.HookManager().getResourceType());
        return status;
    }

    /**
     * Endpoint to get the available routes from ConnectLib.
     * @return a list of RouteInfo objects representing the routes
     */
    @GetMapping("/routes")
    public List<RouteInfo> getRoutes() {
        Map<String, String> map = connectLib.getRoutesMap();
        List<RouteInfo> result = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> e : map.entrySet()) {
                result.add(new RouteInfo(e.getKey(), e.getValue()));
            }
        }
        return result;
    }

    /**
     * Endpoint to get all requests.
     * @return a list of Request objects
     */
    @GetMapping("/requests")
    public List<Request> getRequests() {
        List<Request> result = new ArrayList<>(requestsMap.values());
        return result;
    }

    /**
     * Endpoint to create a new request.
     * @param route the route for the request
     * @param branch the branch for the request
     * @return the created Request object
     */
    public Request createRequest(String route, String branch) {
        if (route == null || branch == null) {
            throw new IllegalArgumentException("route and branch are required");
        }
        long id = requestIdCounter.incrementAndGet();

        Request req = new Request(id, route, branch, "pending");
        requestsMap.put(id, req);
        return req;
    }

    /**
     * Endpoint to update the status of an existing request.
     * @param id the ID of the request to update
     * @param status the new status for the request
     * @return the updated Request object, or null if not found
     */
    public Request updateRequestStatus(Long id, String status) {
        if (id == null || status == null) {
            throw new IllegalArgumentException("id and status are required");
        }
        if (!("pending".equals(status) || "success".equals(status) || "error".equals(status))) {
            throw new IllegalArgumentException("status must be 'pending', 'success' or 'error'");
        }
        Request existing = requestsMap.get(id);
        if (existing == null) {
            return null;
        }
        existing.setStatus(status);
        requestsMap.put(id, existing);
        return existing;
    }
}