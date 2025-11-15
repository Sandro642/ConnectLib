package fr.sandro642.github.provider;

import fr.sandro642.github.api.ApiFactory;

import java.util.Map;

public abstract class AtomicFactory {

    private ApiFactory apiFactory;

    protected void getPhysx(ApiFactory apiFactory) {
        this.apiFactory = apiFactory;
    }

    protected Map<?,?> rawPhysx() {
        return apiFactory.getRawData();
    }
}
