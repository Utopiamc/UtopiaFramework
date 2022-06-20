package de.utopiamc.framework.api.inject;

import de.utopiamc.framework.api.context.Context;
import de.utopiamc.framework.api.exceptions.CandidateNotFoundException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class CandidateQueue {

    public static final String NO_CANDIDATE_WITH__QUALIFIERS_MSG = "There is no candidate for '%s', with '%s' qualifier!";
    private final LinkedList<InjectableCandidate> injectableCandidates;

    public CandidateQueue(Context context) {
        injectableCandidates = new LinkedList<>();

        injectableCandidates.addLast(new InjectableGuiceCandidate(context.getGuiceInjector()));
    }

    private CandidateQueue(LinkedList<InjectableCandidate> injectableCandidates) {
        this.injectableCandidates = injectableCandidates;
    }

    public void addFirst(InjectableCandidate candidate) {
        injectableCandidates.addFirst(candidate);
    }

    public void addLast(InjectableCandidate candidate) {
        injectableCandidates.addLast(candidate);
    }

    public Object get(Parameter type, Annotation annotations) {
        for (InjectableCandidate injectableCandidate : injectableCandidates) {
            Object t = injectableCandidate.get(type, annotations);
            if (t != null)
                return t;
        }

        throw new CandidateNotFoundException(String.format(NO_CANDIDATE_WITH__QUALIFIERS_MSG, type, annotations));
    }

    public CandidateQueue copy() {
        return new CandidateQueue(injectableCandidates);
    }

}
