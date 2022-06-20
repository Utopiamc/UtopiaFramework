package de.utopiamc.framework.api.event;

import de.utopiamc.framework.api.event.qualifier.Event;
import de.utopiamc.framework.api.exceptions.CandidateNotFoundException;
import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.OneToOneInjectableCandidate;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.api.inject.annotations.Qualifier;
import de.utopiamc.framework.api.util.CandidateQueueUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public abstract class FrameworkEvent {

    private final CandidateQueue queue;

    protected FrameworkEvent(CandidateQueue queue) {
        this.queue = queue;
    }

    protected FrameworkEvent() {
        this.queue = CandidateQueueUtil.getBaseCandidateQueue();
    }

    public abstract Class<?> getCompareableClass();

    public void addInjectableCandidates(CandidateQueue queue) {
        queue.addFirst(new OneToOneInjectableCandidate(Event.class, this));
    }

    public void callMethod(Method method, Object instance) {
        CandidateQueue tempQueue = queue.copy();
        addInjectableCandidates(tempQueue);

        makeMethodAccessible(method);

        List<Object> arguments = resolveParameters(method.getParameters(), tempQueue);

        try {
            method.invoke(instance, arguments.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private List<Object> resolveParameters(Parameter[] parameters, CandidateQueue tempQueue) {
        return Arrays.stream(parameters).map(parameter -> resolveParameter(parameter, tempQueue)).toList();
    }

    private Object resolveParameter(Parameter parameter, CandidateQueue tempQueue) {
        List<Annotation> annotations = AnnotationUtil.getAnnotationsWithAnnotation(parameter, Qualifier.class).stream().toList();
        if (!annotations.isEmpty())
            return tempQueue.get(parameter, annotations.get(0));

        throw new CandidateNotFoundException(String.format("No '%s' annotation given!", Qualifier.class.getName()));
    }


    private void makeMethodAccessible(Method method) {
        method.setAccessible(true);
    }

}
