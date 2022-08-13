package de.utopiamc.framework.common.service.impl;

import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.event.WrappedFrameworkEvent;
import de.utopiamc.framework.common.service.EventConverterService;

public class CommonEventConverter implements EventConverterService {

    @Override
    public FrameworkEvent convertEvent(Object o) {
        return new WrappedFrameworkEvent(o);
    }

}
