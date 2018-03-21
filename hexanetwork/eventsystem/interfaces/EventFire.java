package hexanetwork.eventsystem.interfaces;

public interface EventFire<E,L> {

    void fire(E event, L listener);

}
