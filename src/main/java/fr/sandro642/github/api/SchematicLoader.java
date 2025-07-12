package fr.sandro642.github.api;

import fr.sandro642.github.ConnectorAPI;

public class SchematicLoader {

  public void getTypeVariable() {

    try {
      String yamlFilePath = ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().FILE_LOCATION_KEY)
          + "/infos.yml";

    } catch (Exception e) {
      ConnectorAPI.Logger().ERROR("Erreur lors de la récupération du type de variable : " + e.getMessage());
      ConnectorAPI.MCSupport().getPluginPath();
    }
  }

}
