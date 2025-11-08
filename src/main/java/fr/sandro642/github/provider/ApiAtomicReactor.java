package fr.sandro642.github.provider;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.api.ApiFactory;
import fr.sandro642.github.enums.lang.CategoriesType;

import java.util.Map;

public abstract class ApiAtomicReactor {

    private final ApiFactory apiFactory;

    private static final ConnectLib connectLib = new ConnectLib();

    protected ApiAtomicReactor() {
        this.apiFactory = new ApiFactory();
    }

    protected Map<?, ?> rawPhysx() {
        return apiFactory.getRawData();
    }
}
