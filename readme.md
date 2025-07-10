# 🚀 ConnectorAPI

Bienvenue dans **ConnectorAPI** : la passerelle universelle pour connecter, automatiser et orchestrer vos flux de données !
Et si vous pensiez que les API étaient compliquées, détrompez-vous ! Avec ConnectorAPI, c’est comme jouer avec des Lego : simple, intuitif et puissant.

---
```java
Support Lib 23 | Future 24

Hook -----------------------|
 - Support Minecraft 1.8

 - Comming Soon.
```
---

Changelog : 

```java
 - [0.1.3.9-SNAPSHOT] : Ajout de la sérialisation des données pour une compatibilisation parfaite des données avec les HOOK.
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
    
    implementation("fr.sandro642.github:ConnectorAPI:0.1.1")
    
}

```

```java
import fr.sandro642.github.api.ApiClient;

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
- `build.gradle.kts` : configuration Gradle
- `scripts/` : scripts d’exécution

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

