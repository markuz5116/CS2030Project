package cs2030.simulator;

/**
 * WaitEvent where the customer joins a queue of a serverI and waits to be
 * served. The 'WaitEvent' class supports operators that includes: (i)
 * Transition to IdleEvent.
 * WaitEvent contains a EventStatus status and ServerI server.
 */
public class WaitEvent extends Event {
    private static final EventStatus status = EventStatus.WAIT;
    private final ServerI server;

    /**
     * Constructs a WaitEvent containing a customer, a serverI, and a lambda
     * expression to transition to the next IdleEvent.
     * @param customer customer that will be added into the queue.
     * @param server serverI whose queue the customer is added into.
     */
    public WaitEvent(Customer customer, ServerI server) {
        super(customer, customer.getArrivalTime(), server, shop -> {
            ServerI s = shop.get(server).get().wait(customer);
            return Pair.of(shop.replace(s), new ServeEvent(customer, s));
        },
        status);
        this.server = server;
    }

    /**
     * Return a String representation of the WaitEvent.
     * @return String representation of WaitEvent.
     */
    @Override
    public String toString() {
        if (server instanceof Server) {
            return String.format("%s waits to be served by server %d",
                    super.toString(), server.getId());
        } else {
            return String.format("%s waits to be served by %s", super.toString(),
                    server.toString());
        }
    }
}
