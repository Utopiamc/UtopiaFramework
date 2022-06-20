package de.utopiamc.test;

import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.event.Subscribe;
import de.utopiamc.framework.api.event.qualifier.Event;
import de.utopiamc.framework.api.inject.components.Controller;
import de.utopiamc.framework.api.inject.components.Plugin;

@Plugin
@Controller
public class Test {

    @Subscribe(event = FrameworkEvent.class)
    public void test(@Event FrameworkEvent event) {
        System.out.println(event);
    }

}
