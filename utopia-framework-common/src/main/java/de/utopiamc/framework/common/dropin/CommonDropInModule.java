package de.utopiamc.framework.common.dropin;

import de.utopiamc.framework.api.dropin.DropInService;
import de.utopiamc.framework.api.module.AbstractDropInModule;
import de.utopiamc.framework.api.stereotype.Configuration;
import de.utopiamc.framework.api.stereotype.Controller;
import de.utopiamc.framework.api.stereotype.Plugin;
import de.utopiamc.framework.api.stereotype.Service;
import de.utopiamc.framework.common.dropin.sterotype.ConfigurationStereotype;
import de.utopiamc.framework.common.dropin.sterotype.ControllerStereotype;
import de.utopiamc.framework.common.dropin.sterotype.PluginStereotype;
import de.utopiamc.framework.common.dropin.sterotype.ServiceStereotype;

public class CommonDropInModule extends AbstractDropInModule {

    public CommonDropInModule(DropInService dropInService) {
        super.dropInService = dropInService;
    }

    @Override
    public void configure() {
        addStereotype(Service.class, new ServiceStereotype());
        addStereotype(Configuration.class, new ConfigurationStereotype());
        addStereotype(Controller.class, new ControllerStereotype());
        addStereotype(Plugin.class, new PluginStereotype());
    }

}
