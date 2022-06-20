package de.utopiamc.framework.common.bootstrap.inject;

import com.google.inject.Injector;
import de.utopiamc.framework.common.context.ApplicationContext;

public interface Bootstrapper {

    Injector getBootstrapInjector();

    ApplicationContext createApplicationContext();
}
