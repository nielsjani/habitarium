package niels.jani.habitarium.infrastructure.messaging;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.inject.Named;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Stream;

import static com.google.common.base.Throwables.getRootCause;
import static com.google.common.base.Throwables.propagate;
import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.isTrue;

@Named
public class HabitariumEventBus implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabitariumEventBus.class);

    private Multimap<Class<? extends Event<?>>, EventHandlerMethode> eventHandlerRegistry = null;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private Multimap<Class<? extends Event<?>>, EventHandlerMethode> getEventHandlerRegistry() {
        if (eventHandlerRegistry == null) {
            eventHandlerRegistry = createEventHandlerRegistry();
        }
        return eventHandlerRegistry;
    }

    private Multimap<Class<? extends Event<?>>, EventHandlerMethode> createEventHandlerRegistry() {
        eventHandlerRegistry = ArrayListMultimap.create();
        getEventHandlersFromApplicationContext().stream()
            .map(this::unwrapProxies)
            .flatMap(this::combineerEventHandlerMetMethodes)
            .filter(pair -> pair.getRight().isAnnotationPresent(Handle.class))
            .forEach(pair -> registerEventHandler(eventHandlerRegistry, pair.getLeft(), pair.getRight()));
        return eventHandlerRegistry;
    }

    private Collection<EventHandler> getEventHandlersFromApplicationContext() {
        return applicationContext.getBeansOfType(EventHandler.class).values();
    }

    private EventHandler unwrapProxies(EventHandler eventHandler) {
        if (AopUtils.isJdkDynamicProxy(eventHandler) || AopUtils.isCglibProxy(eventHandler)) {
            try {
                return (EventHandler) ((Advised) eventHandler).getTargetSource().getTarget();
            } catch (Exception e) {
                throw propagate(e);
            }
        }
        return eventHandler;
    }

    private Stream<Pair<EventHandler, Method>> combineerEventHandlerMetMethodes(EventHandler eventHandler) {
        return Stream.of(eventHandler.getClass().getDeclaredMethods()).map(method -> Pair.of(eventHandler, method));
    }

    private void registerEventHandler(Multimap<Class<? extends Event<?>>, EventHandlerMethode> registry, EventHandler eventHandler, Method method) {
        LOGGER.info(format("Registering event handler: %s::%s(%s)", eventHandler.getClass().getName(), method.getName(), getEventType(method).getName()));
        validateMessageHandlerMethodParameters(method.getParameterTypes());
        registry.put(
            getEventType(method),
            new EventHandlerMethode(eventHandler, method));
    }

    private void validateMessageHandlerMethodParameters(Class<?>[] parameters) {
        isTrue(
            parameters.length == 1 && Event.class.isAssignableFrom(parameters[0]),
            "The @Handle annotation requires a single parameter of type Event, the event to be handled");
    }

    private Class<? extends Event<?>> getEventType(Method method) {
        return (Class<? extends Event<?>>) method.getParameterTypes()[0];
    }

    public void stuurEvent(Event<?> event) {
        LOGGER.debug(format("Received event %s", event));
        getEventHandlerRegistry().get((Class<? extends Event<?>>) event.getClass()).stream()
            .forEach(eventHandlerMethode -> eventHandlerMethode.handleEvent(event));
    }

    private static class EventHandlerMethode {

        private final EventHandler target;
        private final Method method;

        public EventHandlerMethode(EventHandler target, Method method) {
            this.target = target;
            this.method = method;
        }

        public void handleEvent(Event<?> event) {
            try {
                LOGGER.debug(format("Sending event: %s::%s(%s)", target.getClass().getName(), method.getName(), event.getClass().getName()));
                method.invoke(target, event);
            } catch (Exception e) {
                propagate(getRootCause(e));
            }
        }
    }
}
