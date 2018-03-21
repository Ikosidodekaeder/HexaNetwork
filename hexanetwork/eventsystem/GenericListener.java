package hexanetwork.eventsystem;

import hexanetwork.eventsystem.annotations.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Stores ONE EventHandler of an EventListener
 *
 * @param <E>
 * @param <L>
 */
public class GenericListener<E,L>  implements Comparable<GenericListener> {

    /**
     * The actual EvenHandler which shall be invoked upon occurence of the event
     */
    private final Method          invokable;
    /**
     * The Instance which contains the actual implementation of the invokable or
     * No Method without its Object!!!!
     */
    private final L               listener;

    /**
     * The Instance of the annotation associate with the implementation of the
     * actual event handler which is stored within the invokable
     */
    private final EventHandler Meta;

    public GenericListener(L listener, Method handler,EventHandler annotation){
        this.listener = listener;
        this.invokable = handler;
        this.Meta = annotation;
    }

    public void fire(E event){
        try {
            invokable.invoke(listener, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @return Method reference to an EventHandler from an EventListener
     */
    public Method getMethod(){
        return invokable;
    }

    public Class< ? extends E> getEventType(){
        return (Class< ? extends E>) invokable.getParameterTypes()[0];
    }

    /**
     *
     * @return Priority of the EventHandler within the Listener
     */
    public EventPriority getPriority(){
        return Meta.priority();
    }

    /**
     *
     * @return Instance of the EventListener
     */
    public L getListener(){
        return listener;
    }

    @Override
    public int compareTo(GenericListener o)
    {
        return o.getMethod().equals(this.getMethod()) && o.getListener() == this.getListener() ? 0 : Integer.compare(
                        this.getPriority().ordinal(),
                        o.getPriority().ordinal()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this || obj == null) return false;
        if (obj instanceof GenericListener) {
            GenericListener other = (GenericListener) obj;
            return other.getListener() == this.getListener() && other.getMethod().equals(this.getMethod()); // Reference equality for listeners
        }
        return false;
    }
}
