package de.utopiamc.framework.api.util;

import com.google.inject.Inject;
import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.api.inject.CandidateQueue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CandidateQueueUtil {

    @Inject
    private static Context context;

    public static CandidateQueue getBaseCandidateQueue() {
        return new CandidateQueue(context);
    }

}
