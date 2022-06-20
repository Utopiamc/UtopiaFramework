package de.utopiamc.framework.common.bootstrap.inject;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import de.utopiamc.framework.api.util.CandidateQueueUtil;
import de.utopiamc.framework.common.context.ApplicationContext;

public class FrameworkBootstrapModule extends AbstractModule {

    @Override
    protected void configure() {

        requestStaticInjection(ApplicationContext.class);
    }
}
