package de.utopiamc.framework.common.inject;

import com.google.inject.AbstractModule;
import de.dytanic.cloudnet.wrapper.Wrapper;
import de.utopiamc.framework.api.info.ServerName;
import de.utopiamc.framework.api.info.TaskName;

public class ConstantsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(ServerName.class)
                .toInstance(Wrapper.getInstance().getServiceId().getName());

        bind(String.class)
                .annotatedWith(TaskName.class)
                .toInstance(Wrapper.getInstance().getServiceId().getTaskName());
    }
}
