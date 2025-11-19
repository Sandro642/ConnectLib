package fr.sandro642.github.spring.controller;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.spring.dto.Request;
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
        status.put("portInfo", connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().DYNAMIC_PORT));
        String nameFromStore = connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().NAME_DASHBOARD).toString();

        status.put("nameComponent", nameFromStore);
        status.put("isInitialized", connectLib.isInitialized());
        status.put("resourceType", connectLib.HookManager().getResourceType());
        return status;
    }

    @GetMapping("/routes")
    public Map<String, Object> getRoutes() {
        Map<String, String> map = connectLib.getRoutesMap();
        Map<String, Object> result = new HashMap<>();

        // portInfo
        result.put("portInfo", connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().DYNAMIC_PORT).toString());
        result.put("nameComponent", connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().NAME_DASHBOARD));

        List<Map<String, String>> routes = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, String> e : map.entrySet()) {
                Map<String, String> route = new HashMap<>();
                route.put("name", e.getKey());
                route.put("url", e.getValue());
                routes.add(route);
            }
        }
        result.put("routes", routes);
        return result;
    }


    /**
     * Endpoint to get all requests.
     * @return a list of Request objects
     */
    @GetMapping("/requests")
    public Map<String, Object> getRequests() {
        Map<String, Object> result = new HashMap<>();

        result.put("portInfo", connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().DYNAMIC_PORT).toString());
        result.put("nameComponent", connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().NAME_DASHBOARD));

        List<Request> requests = new ArrayList<>(requestsMap.values());
        result.put("requests", requests);

        return result;
    }

    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("nameDashboard", connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().NAME_DASHBOARD));
        return config;
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

        Request req = new Request(connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().DYNAMIC_PORT).toString(), id, route, branch, "pending");
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