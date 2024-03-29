package de.utopiamc.framework.api.event;

import de.utopiamc.framework.api.event.qualifier.Event;
import de.utopiamc.framework.api.exceptions.CandidateNotFoundException;
import de.utopiamc.framework.api.inject.CandidateQueue;
import de.utopiamc.framework.api.inject.OneToOneInjectableCandidate;
import de.utopiamc.framework.api.inject.annotations.Qualifier;
import de.utopiamc.framework.api.util.AnnotationUtil;
import de.utopiamc.framework.api.util.CandidateQueueUtil;

import java.lang.annotation.Annotation;
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

    public boolean isComparable(Class<?> cls) {
        return cls.isAssignableFrom(getClass());
    }

    public void addInjectableCandidates(CandidateQueue queue) {
        queue.addFirst(new OneToOneInjectableCandidate(Event.class, this));
    }

    public void callMethod(Method method, Object instance) throws Throwable {
        CandidateQueue tempQueue = queue.copy();
        addInjectableCandidates(tempQueue);

        makeMethodAccessible(method);

        List<Object> arguments;

        if (method.getParameters().length == 1 && !AnnotationUtil.isAnnotationPresent(method.getParameters()[0], Qualifier.class)) {
            arguments = List.of(this);
        }else {
            arguments = resolveParameters(method.getParameters(), tempQueue);
        }

        method.invoke(instance, arguments.toArray());
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
