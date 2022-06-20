package de.utopiamc.framework.common.events;

import de.utopiamc.framework.api.event.FrameworkEvent;

public interface EventDispatchable {

    void dispatchEvent(FrameworkEvent event);

}
