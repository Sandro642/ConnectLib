# 🚀 ConnectLib

Welcome to **ConnectLib**: the universal gateway to connect, automate and orchestrate your data flows!
And if you thought APIs were complicated, think again! With ConnectLib, it's like playing with Lego: simple, intuitive and powerful.
> For information, this library is not intended to be used for Minecraft, it is used professionally in a Java project.

---

```java
Stable Version: 0.2.5-STABLE
```

---

```java
Support Lib : SDK 8

Hook -----------------------|
 - Support Minecraft 1.8

 - Coming Soon.
```
---

Stay informed about the latest features and updates of ConnectLib.

- Advanced debugging system. Available soon.

---

Changelog:

```java
 - [0.1.3.9-SNAPSHOT]: Added data serialization for perfect data compatibility with HOOK.
 - [0.1.3.17-SNAPSHOT]: Removal of data serialization... Still available from the /feature/serializer branch. Useful if processes are not initialized in the same environment.
 - [0.1.9.2-STABLE]: Security patch using the getRoutes method using an enumeration class for routeName: StackOverflowError... + Creation of maps according to the desired variable type.
 - [0.2.0-STABLE]: Wow, arrival of 0.2.0 in such a short time? There were things to do on this project ;)
 - [0.2.2-STABLE]: Added log creation.
```

---

## 🌟 Why ConnectLib?

Imagine an API that doesn't just connect services, but becomes the conductor of your integrations. ConnectLib is designed to:

- **Centralize** your API connections
- **Automate** your recurring tasks
- **Secure** your data exchanges
- **Simplify** adding new connectors

---

## 🛠️ Main Features

- 🔌 **Centralized connector management**
- ⚡ **Automated job execution**
- 🧩 **Extensible and modular**
- 📊 **Detailed logs and monitoring**
- 🔒 **Built-in security**

---

## 🚦 Quick Start

1. **Clone the project**
   ```bash
   git clone https://github.com/your-username/ConnectLib.git
   cd ConnectLib
   ```
2. **Compile**
   ```bash
   ./gradlew build
   ```
3. **Run an example**
   ```bash
   ./gradlew run
   ```

---

## 🧑‍💻 Usage Example

Library Import
```java
repositories {
    
    maven {
        url = uri("https://sandro642.github.io/connectlib/jar")
   }
   
}

dependencies {
    
    implementation("fr.sandro642.github:ConnectLib:0.2.5-STABLE")
    
}

```
Library Initialization
```java
public class Example {
    
    // Initialization with an enumeration
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
    
    ConnectLib.initialize(ResourceType.MAIN_RESOURCES, RouteList.class);
    
    
///////////////////////////////////////////////////////////////////////////    
    ConnectLib.initialize(ResourceType.MAIN_RESOURCES);
    
    //Add your routes in the infos.yml file
}
```

More examples HERE: [ExampleUsage.java](src/main/java/fr/sandro642/github/example/ExampleUsage.java) Not available at the moment due to new features.

---

## 📚 Project Structure

- `src/main/java/fr/sandro642/github/` : main source code
- `src/test/java/fr/sandro642/github/test/` : unit tests
- `build.gradle` : Gradle configuration

---

## 🤝 Contributing

1. Fork the repo
2. Create a branch (`feature/my-feature`)
3. Push your changes and open a PR

---

## 🧠 Evolution Ideas

- Adding connectors for new services
- Web management interface
- Plugin system

---

## 📞 Contact

For any questions or suggestions: [sandro33810@gmail.com](mailto:sandro33810@gmail.com)

---

> "ConnectLib is the freedom to connect the impossible."
