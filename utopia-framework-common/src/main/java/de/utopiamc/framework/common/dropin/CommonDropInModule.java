package de.utopiamc.framework.common.dropin;

import de.utopiamc.framework.api.dropin.DropInService;
import de.utopiamc.framework.api.module.AbstractDropInModule;
import de.utopiamc.framework.api.stereotype.*;
import de.utopiamc.framework.common.dropin.sterotype.*;

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
        addStereotype(Command.class, new CommandStereotype());
    }

}
