package de.utopiamc.framework.common.inject;

import com.google.inject.*;
import de.dytanic.cloudnet.wrapper.Wrapper;
import de.utopiamc.framework.api.info.ServerName;
import de.utopiamc.framework.api.info.TaskName;

public class ConstantsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(ServerName.class)
                .toProvider(() -> Wrapper.getInstance().getServiceId().getName())
                .in(Scopes.NO_SCOPE);

        bind(String.class)
                .annotatedWith(TaskName.class)
                .toProvider(() -> Wrapper.getInstance().getServiceId().getTaskName())
                .in(Scopes.NO_SCOPE);;
    }
}
