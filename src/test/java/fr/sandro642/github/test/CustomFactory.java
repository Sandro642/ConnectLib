package fr.sandro642.github.test;

import fr.sandro642.github.provider.ApiAtomicReactor;


public class CustomFactory extends ApiAtomicReactor {

    public Object getToken() {
        return rawPhysx().get("token");
    }

}
