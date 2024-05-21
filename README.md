# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```


(https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAEYAdACZT9qBACu2GADEaMBUljAASgCiAOIAkmQAKuEKCbEA8gBygQDuABZIYGKIqKQAtAB85JQ0UABcMADaAAppiQC6MAD0PgZQADpoAN4ARD2UwQC2KMO1wzDDADTzuOrZ0Bwzc4vzKBPASAib8wC+psI1MBWs7FyU9SNjUJPTs-NLwyuqa1Abr9vDu32hz+pzYnG4sCu51E9SgKHsSFUhSgAApwvDEcj0QBHHxqMAASkw0JUl0qsnkShU6nq9hQYAAqr0UY9nkSKYplGpVGSjDp6mRIgAZSJwBIwVnAKYwABm3gmEt6mA5VO5lyh1RhMDQPgQCGJmtJVxVXJpMBAcLkKCZlBZvWeS0+3w4S0BB3Z2lV6l5xnqsQygvC4slU0d+i+61dewOMH9CTSitoJMMxs9ptU9QtKCtCh8YFydvGUpQHsp6Z9-NjAciQcTT2LS2AedyCQgAGt0FX4zAm-nlWnqTzShqRCp6r2W+30AbRymrmDbnUIhikZR0apdVgFxD1ZVzncYA97cW5vUtu8J62O2hT-MTjOLsPKsVzPU3AAGd8DI9Fqa38-zJeU43jAZ73ugHCmJgXi+P4ATQOwdIwEKaRxFkAR5AURTIOYvL7kuzStAkHSdAY6hIBAaDfqMx5-n87xOusRzDKcD6ULy24Hj+9Z0QBHzhs6zGgjcO4jlQWoIBACJoCiQpSUgaA4niSJEsmZImoOtL0jaqIhiW-ZloOT58gKwqisGtEoAsAyMT8srynWHIGZyRliVqelhqs6wPqIFTJvUknSTphY8VZMC2RwRI+Ua5QadymaWoUub5iFbLOV6Q6VL6VaBhZv5hUB15dgmE5OXF3rGf5PbNle07Jk+nFLoFCnrpumCNU++H3MMhXoEJbGQlcL4YG+n7Ub1N4gmYnBQTBfiBF4KCdih0RpAy4oYfkhSYMNzBiQeDTSGZCSRCRZGqBRVHBDVwFnNU7HziJXE9Td179R15RVYFvhgCi315kp+KqYaKAVOVGYwP9jLMhNpYudyT7ZUdIondV+a1WgSx6fZEAKqV2gDRUu2je+02QdB3jzQEcIcMh8TitECgALKRGQORbdhJR7Xu90EdEkTEV09jFgME13dQD2VI13UTe9T1QH5IMBZif2YoDKnRaDsUDvFMB0mAyW5ByKKw+l6bGdlgoimKdYOjZAnrDjCqPE5ZuuZ9Sto5O15Y5ZmtgzrZrIEi0TFqoKJReDFSW2ZNvC1MsTSEsmGFB5MA6AgoBtjpdsOMWGTFk7MCh1MZWB6onWe8HzAQDKjTxygifJxzOcNunmcgNnlnWWgev58WbT+xU0uQ2raibhH7Xy5XEtLoejQjA3iczM4bgAMwACzvCn1qWZsiwDPMGdZ63vH-A3BdTLMLEwG0rGE+UxMwB+77z8MDeqCvAwNIvxbL-UTh15b3mDvU+LxhgH17sMY+ncwH73eBfYs19ji31OBBWalM4LYB8FAbA3B4BRBSJEYuzNWbsywjtHCZQeaz3qIRdoQsRZDBeujYCmwEH914veQmw95Yy1en1WYHCpiX3AfeD6VUsxWhRIgqYwNZwB0MrrfWhtjamyjuUGO1txQTV9vlIu+N5BuwRorWc9Q9L+21kos0UjCglxQDIzh+kNHZX9LlPuCck7al1AgJYOo9RLFkWFOAuRuT2JgBAHQAArFA4ABgKUgMXYsZdrEV1MeJMcS9pBD3KNLWxKBWoIF+kSDq6TnpZJXoAzerF8JEyoWAEmh535-2kJUoBaCZoU1goESwKA9QQGyDAAAUmkf0JCWZswCDAtslCuZ4V5nQpo60SIN1FgIq6gxdpwAgJJKAgSWk1N5ukrUUSIAKRRFsnZ0B9mePkRkrW4MtIG2bGo9ZcMMrR0rFbcyXsMZ6NCgYl5BMo7HLHBY+qVj4Y0lOQpexqU26XN2Tcxu0h3nm00V82O4oslFwbhyJYAw3E1jygChSMBQGWQibAaZYDjHqBnvc2oWScnSxhYpceRTJ61MfvUkmZMMHdICF4YAYQ4AigUOEGACgmhNHIdtXa8zaGNGRgLU6XRjDixqKClAmZuB4C5SDRRUKIYgD1aiNFRkMX1BVajOUuMvb4sctoZFTluVPxfvyoAA)
