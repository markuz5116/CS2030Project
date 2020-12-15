package cs2030.simulator;

/**
 * IdleEvent where the server is not serving and has an empty queue.
 * IdleEvent contains EventStatus status.
 */
public class IdleEvent extends Event {
    private static final EventStatus status = EventStatus.IDLE;

    /**
     * Constructs an IdleEvent containing a customer and an execute method
     * that returns an empty Pair.
     * @param customer Customer from DoneEvent.
     * @param server Server that is not serving and has an empty queue.
     */
    public IdleEvent(Customer customer, ServerI server) {
        super(customer, 0, server, x -> Pair.empty(), status);
    }

    /**
     * Retrieve String representation that should not be printed.
     * @return error message if this is printed.
     */
    @Override
    public String toString() {
        return "This should not be here";
    }
}
