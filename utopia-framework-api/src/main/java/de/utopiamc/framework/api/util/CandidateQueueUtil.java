package de.utopiamc.framework.api.util;

import com.google.inject.Inject;
import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.InjectableGuiceCandidate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CandidateQueueUtil {

    @Inject
    private static Context context;

    public static CandidateQueue getBaseCandidateQueue() {
        CandidateQueue candidateQueue = new CandidateQueue();
        candidateQueue.addLast(new InjectableGuiceCandidate(context.getGuiceInjector()));
        return candidateQueue;
    }

}
