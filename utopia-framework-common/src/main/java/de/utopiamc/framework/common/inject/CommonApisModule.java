package de.utopiamc.framework.common.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.service.ColorService;
import de.utopiamc.framework.api.service.RequestService;
import de.utopiamc.framework.api.service.TempEventService;
import de.utopiamc.framework.common.service.impl.CommonColorService;
import de.utopiamc.framework.common.service.impl.CommonRequestService;
import de.utopiamc.framework.common.service.impl.CommonTempEventService;

public class CommonApisModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ColorService.class).to(CommonColorService.class);
        bind(TempEventService.class).to(CommonTempEventService.class);
        bind(RequestService.class).to(CommonRequestService.class);
    }
}
