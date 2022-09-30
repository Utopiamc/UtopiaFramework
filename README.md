
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
    compileOnly "de.utopiamc:utopia-framework-api:1.2-RELEASE"
}
```

Maven
```xml
<dependencies>
    <dependency>
        <groupId>de.utopiamc</groupId>
        <artifactId>utopia-framework-api</artifactId>
        <version>1.2-RELEASE</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Some Code

And then you could start:

```java
package de.utopiamc.demo;

import de.utopiamc.framework.api.event.FrameworkPlayerJoinEvent;
import de.utopiamc.framework.api.event.Subscribe;
import de.utopiamc.framework.api.event.qualifier.Player;
import de.utopiamc.framework.api.stereotype.Controller;
import de.utopiamc.framework.api.inject.components.DropIn;

@Plugin
@Controller
public final class OhYeah {

    @Inject
    private Logger logger;

    @OnEnable
    public void onEnable() {
        logger.info("Yes we did it!");
    }

    @OnDisable
    public void iCanNameThisWhatEverIWant() {
        logger.info("Good bye!");
    }

    @Subscribe(event = FrameworkPlayerJoinEvent.class)
    public void onJoin(@Player Player joined) {
        logget.info(String.format("OMG, '%s' just joined!", joined.getName()));
    }

}
```

### Config

You need custom Config to change the color scheme?
There it is: 

```java
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfig;
import de.utopiamc.framework.api.config.meta.UtopiaMetaConfiguration;
import de.utopiamc.framework.api.stereotype.Configuration;

@Configuration
public class VeryCoolConfig implements UtopiaMetaConfiguration {

    @Override
    public void configure(UtopiaMetaConfig config) {
        config.primaryColor('c').secondaryColor('b'); //Use the normal Minecraft color codes!
    }
}
```

## Cool Addons
### Scoreboard
Have you always wanted to create a scoreboard quickly and easily? <br>
![Demo](https://media.discordapp.net/attachments/748504230738001961/1025148491426709504/unknown.png)
```java
import com.google.inject.Inject;
import de.utopiamc.framework.api.info.ServerName;
import de.utopiamc.framework.api.service.ScoreboardFactory;
import de.utopiamc.framework.api.ui.scoreboard.Subscribeables;

public class ScoreboardCreator {
    
    @Inject
    public void setupScoreboard(ScoreboardFactory factory, @ServerName String serverName) {
        factory
                .createScoreboard()
                
                .addLine()
                .setTitle("Hello")
                .setContent("What's up?")
                .and()
                
                .addDynamicLine()
                .setTitle("Server")
                .setContent(serverName)
                .and()
                
                .addDynamicLine()
                .setTitle("Spieler")
                .setContent(Subscribeables.playerCount())
                
                .build()
                .autoBind();
    }
}
```
### Command Creator
Do you keep forgetting to register your commands? Then just leave it alone! 🤟🏻
```java
import de.utopiamc.framework.api.commands.CommandConfig;
import de.utopiamc.framework.api.commands.descriptors.Configure;
import de.utopiamc.framework.api.commands.descriptors.MapRoute;
import de.utopiamc.framework.api.commands.descriptors.Variable;
import de.utopiamc.framework.api.service.MessageService;
import de.utopiamc.framework.api.stereotype.Command;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Command(value = "niceCommand", aliases = {"nc", "niceC"})
public class CommandCreator {

    @Configure
    public void configure(CommandConfig descriptor) {
        descriptor
                .description("This is a nice command")
                .neededPermission("nice.command")
                .primaryColor('a'); // This is the color of the command Prefix
    }
    
    @MapRoute("")
    public void Command(MessageService messageService) {
        messageService.sendMessage("$p Command outout");
    }

    @MapRoute("<NameOfVar>") // @MapRoute("JiggleJiggle <NameOfVar>")
    public void RequiresAtLeastOneInput(MessageService messageService, Player player, @Variable("NameOfVar") Sound varName) {
        messageService.sendMessage("$pHello World!"); // Use $p to get the primaryColor
        player.playSound(player.getLocation(), varName, 1, 1);
    }

}
```
