
# UtopiaFramework

The Bukkit API sucks, right? So we have (as many others) created a much nicer API. It supports Dependency Injection, automated commands and an entire new event system out of the box. If you need more features, the framework is extendable. You can install modules, our implementation or *your own*! 😁



## Features

Now you create something we call a 'DropIn'. It's just a piece of code you throw to eat to the framework. It's called drop in, because you can just drop it in the drop-ins folder, and we take care of the rest.


## Installation

### Dependencies

Just create a new Gradle *(recommended)* or Maven project. Authenticate with GitHub and add our repo.

Gradle *recommended*
```groovy
dependencies {
    compileOnly "de.utopiamc:utopia-framework-api:1.0-SNAPSHOT"
}
```

Maven
```xml
<dependencies>
    <dependency>
        <groupId>de.utopiamc</groupId>
        <artifactId>utopia-framework-api</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Some Code

Right before you start, you should know that we have lied to you. 😒
It's not *sooo* simple to start. First, you have to create a `manifest.json` file.

It should look like something like this:
```json
{
  "man-version": 1,
  "global": {
    "version": "1.0-SNAPSHOT"
  },

  "plugin": [
    {
      "id": "ANiceAndHandyId",
      "entrypoint": "de.utopiamc.demo.OhYeah"
    }
  ]
}
```

And then you could start:

```java
package de.utopiamc.demo;

import de.utopiamc.framework.api.event.FrameworkPlayerJoinEvent;
import de.utopiamc.framework.api.event.Subscribe;
import de.utopiamc.framework.api.event.qualifier.Player;
import de.utopiamc.framework.api.stereotype.Controller;
import de.utopiamc.framework.api.inject.components.DropIn;

@DropIn
@Controller
public final class OhYeah {

    @Inject
    private Logger logger;

    @OnEnable
    public void onEnable() {
        logger.log("Yes we did it!");
    }

    @OnDisable
    public void iCanNameThisWhatEverIWant() {
        logger.log("Good bye!");
    }

    @Subscribe(event = FrameworkPlayerJoinEvent.class)
    public void onJoin(@Player Player joined) {
        logget.log(String.format("OMG, '%s' just joined!", joined.getName()));
    }

}
```

