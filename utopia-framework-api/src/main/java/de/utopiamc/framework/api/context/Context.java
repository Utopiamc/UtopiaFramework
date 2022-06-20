package de.utopiamc.framework.api.context;

import com.google.inject.Injector;

public interface Context {

    Injector getGuiceInjector();

}
