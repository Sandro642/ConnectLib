# 🚀 ConnectorAPI

Bienvenue dans **ConnectorAPI** : la passerelle universelle pour connecter, automatiser et orchestrer vos flux de données !
Et si vous pensiez que les API étaient compliquées, détrompez-vous ! Avec ConnectorAPI, c’est comme jouer avec des Lego : simple, intuitif et puissant. 
> Pour information, cette librairie n'est pas destinée à être utilisée pour Minecraft, elle est utilisée afin professionnel dans un projet Java.

---

```java
Stable Version : 0.2.1-STABLE
```

---

```java
Support Lib 23 | Future 24

Hook -----------------------|
 - Support Minecraft 1.8

 - Comming Soon.
```
---

Être tenu au courant des dernières features et mises à jour de ConnectorAPI.

- Système de débugging avancé. Disponible prochainement.

---

Changelog : 

```java
 - [0.1.3.9-SNAPSHOT] : Ajout de la sérialisation des données pour une compatibilisation parfaite des données avec les HOOK.
 - [0.1.3.17-SNAPSHOT] : Suppresion de la sérialisation des données... Toujours disponible depuis la branche /feature/serializer. Utile si les processus ne sont pas initialisé dans le même environnement.
 - [0.1.9.2-STABLE] : Patch de sécurité en utilisant la méthode getRoutes en utilisant une classe enumération pour la routeName : StackOverflowError... + Création des maps selon le type de variable souhaité.
 - [0.2.0-STABLE] : Woaw arrivé de la 0.2.0 en si peu de temps ? Il y en avait des choses à faire sur ce projet ;)
```

---

## 🌟 Pourquoi ConnectorAPI ?

Imaginez une API qui ne se contente pas de relier des services, mais qui devient le chef d’orchestre de vos intégrations. ConnectorAPI est conçu pour :

- **Centraliser** vos connexions API
- **Automatiser** vos tâches récurrentes
- **Sécuriser** vos échanges de données
- **Simplifier** l’ajout de nouveaux connecteurs

---

## 🛠️ Fonctionnalités principales

- 🔌 **Gestion centralisée des connecteurs**
- ⚡ **Exécution de jobs automatisés**
- 🧩 **Extensible et modulaire**
- 📊 **Logs détaillés et monitoring**
- 🔒 **Sécurité intégrée**

---

## 🚦 Démarrage rapide

1. **Cloner le projet**
   ```bash
   git clone https://github.com/votre-utilisateur/ConnectorAPI.git
   cd ConnectorAPI
   ```
2. **Compiler**
   ```bash
   ./gradlew build
   ```
3. **Exécuter un exemple**
   ```bash
   ./gradlew run
   ```

---

## 🧑‍💻 Exemple d’utilisation

Importation de la librairie
```java
repositories {
    
    maven {
        url = uri("https://sandro642.github.io/connectorapi/jar")
   }
   
}

dependencies {
    
    implementation("fr.sandro642.github:ConnectorAPI:0.1.5-STABLE")
    
}

```
Initialisation de la Librairie
```java
public class Example {
    
    // Initialisation avec une enumération
    public enum RouteList implements ConvertEnum.RouteImport {
       VERSION("/api/mcas/info/version"),
       INFO("/api/mcas/info/info");

       String route;

       TestRoutes(String route) {
          this.route = route;
       }

       @Override
       public String route() {
          return route;
       }
    }
    
    ConnectorAPI.initialize(ResourceType.MAIN_RESOURCES, RouteList.class);
    
    
///////////////////////////////////////////////////////////////////////////    
    ConnectorAPI.initialize(ResourceType.MAIN_RESOURCES);
    
    //Rajouter vos routes dans le fichier infos.yml
}

```

```java
public class Example {

    // 5. Appel POST avec version, body et paramètres
            System.out.println("5. Appel POST complet (version + body + paramètres) :");
            Map<String, Object> updateBody = new HashMap<>();
                updateBody.put("name", "John Doe");
                updateBody.put("email", "john.doe@example.com");
                updateBody.put("active", true);

            Map<String, Object> updateParams = new HashMap<>();
                updateParams.put("userId", "67890");
                updateParams.put("sessionId", "sess_abc123");

    ApiResponse<Void> completeResponse = ConnectorAPI.JobGetInfos()
            .getRoutes(VersionType.V2_BRANCH, MethodType.POST, "update_user", updateBody, updateParams)
            .getResponse();

    displayResponse(completeResponse);
    
}
```

Plus d'exemples dans ICI : [ExampleUsage.java](src/main/java/fr/sandro642/github/example/ExampleUsage.java)

---

## 📚 Structure du projet

- `src/main/java/fr/sandro642/github/` : code source principal
- `src/test/java/fr/sandro642/github/test/` : tests unitaires
- `build.gradle` : configuration Gradle

---

## 🤝 Contribuer

1. Forkez le repo
2. Créez une branche (`feature/ma-fonctionnalite`)
3. Poussez vos modifications et ouvrez une PR

---

## 🧠 Idées d’évolution

- Ajout de connecteurs pour de nouveaux services
- Interface web de gestion
- Système de plugins

---

## 📞 Contact

Pour toute question ou suggestion : [sandro33810@gmail.com](mailto:sandro33810@gmail.com)

---

> "ConnectorAPI, c’est la liberté de connecter l’impossible."

