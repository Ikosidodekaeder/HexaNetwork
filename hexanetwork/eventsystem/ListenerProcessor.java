package hexanetwork.eventsystem;

import hexanetwork.eventsystem.annotations.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class ListenerProcessor<E,L> {

    /**
     * Stores Listeners
     */
    final Set<GenericListener<E,L>>     concreteHandler = Collections.synchronizedSet(new TreeSet<>());

    public ListenerProcessor(@NotNull E event,@NotNull L listener){

        Arrays.stream(listener.getClass().getDeclaredMethods())
                .filter(handler -> handler.isAnnotationPresent(EventHandler.class))
                .forEach(
                        handler -> {
                            concreteHandler.add(new GenericListener<E,L>(
                                    listener,
                                    handler,
                                    handler.getAnnotation(EventHandler.class)
                            ));
                        }
                );
    }


    void remove(GenericListener<E,L> listener){
        concreteHandler.remove(listener);
    }

    void register(GenericListener<E,L> listener){
        concreteHandler.add(listener);
    }

    public void invoke(E event){
        if(!concreteHandler.isEmpty())
            concreteHandler.stream()
                    //.filter(handler -> handler.getEventType() .equals (event))
                    .forEach( handler -> handler.fire(event) );
    }
}
