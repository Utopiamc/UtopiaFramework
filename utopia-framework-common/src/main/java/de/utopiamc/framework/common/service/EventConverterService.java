package de.utopiamc.framework.common.service;

import com.google.inject.ImplementedBy;
import de.utopiamc.framework.api.event.FrameworkEvent;
import de.utopiamc.framework.common.service.impl.CommonEventConverter;

@ImplementedBy(CommonEventConverter.class)
public interface EventConverterService {

    FrameworkEvent convertEvent(Object o);

}
