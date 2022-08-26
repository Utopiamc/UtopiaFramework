package de.utopiamc.framework.proxy.service;

import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.api.event.ProxyEvent;
import de.utopiamc.framework.api.event.WrappedFrameworkEvent;
import de.utopiamc.framework.common.service.EventConverterService;
import net.md_5.bungee.api.plugin.Event;

public class ProxyEventConverterService implements EventConverterService {

    @Override
    public FrameworkEvent convertEvent(Object o) {
        Class<?> oClass = o.getClass();
        if (Event.class.isAssignableFrom(o.getClass())) {
            return convertProxyEvent((Event) o, oClass);
        }

        return new WrappedFrameworkEvent(o);
    }

    private ProxyEvent convertProxyEvent(Event o, Class<?> oClass) {
        return new ProxyEvent(o);
    }
}
