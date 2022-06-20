package de.utopiamc.framework.common.context;

import de.utopiamc.framework.common.dropin.DropIn;
import de.utopiamc.framework.common.dropin.DropInSource;

import java.util.Optional;
import java.util.Set;

public interface DropInHoldable {

    Optional<DropInSource> getById(String id);

    Set<DropIn> getDropIns();
    Set<DropInSource> getDropInSources();

}
