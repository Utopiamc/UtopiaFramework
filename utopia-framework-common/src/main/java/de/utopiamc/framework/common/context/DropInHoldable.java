package de.utopiamc.framework.common.context;

import de.utopiamc.framework.common.dropin.DropIn;

import java.util.Set;

public interface DropInHoldable {

    Set<DropIn> getDropIns();

}
