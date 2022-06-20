package de.utopiamc.framework.common.inject;

import com.google.inject.AbstractModule;
import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.api.util.CandidateQueueUtil;
import de.utopiamc.framework.common.context.ApplicationContext;

public class FrameworkModule extends AbstractModule {

    private final ApplicationContext context;

    public FrameworkModule(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected void configure() {
        bind(Context.class).toInstance(context);

        requestStaticInjection(CandidateQueueUtil.class);
    }
}
