package de.utopiamc.framework.api.context;

import de.utopiamc.framework.api.event.FrameworkEvent;

public interface EventDispatchable {

    void dispatchEvent(FrameworkEvent event);

}
