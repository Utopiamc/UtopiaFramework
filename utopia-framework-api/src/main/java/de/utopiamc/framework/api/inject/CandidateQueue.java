package de.utopiamc.framework.api.inject;

import de.utopiamc.framework.api.exceptions.CandidateNotFoundException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.LinkedList;

public final class CandidateQueue {

    public static final String NO_CANDIDATE_WITH__QUALIFIERS_MSG = "There is no candidate for '%s', with '%s' qualifier!";
    final LinkedList<InjectableCandidate> injectableCandidates;

    public CandidateQueue() {
        injectableCandidates = new LinkedList<>();
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
