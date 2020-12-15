package cs2030.simulator;

/**
 * DoneEvent where server is done serving a customer. The 'DoneEvent' class
 * supports operators that includes: (i) Transition to IdleEvent, ServeEvent,
 * or ServerRest depending on criteria.
 * DoneEvent contains EventStatus status and ServerI server.
 */
public class DoneEvent extends Event {
    private static final EventStatus status = EventStatus.DONE;
    private final ServerI server;

    /**
     * Constructs a DoneEvent containing a customer, a serverI, and a lambda
     * expression to transition to the next events: (i) IdleEvent if server has
     * an empty queue. (ii) ServeEvent if server has customers in the queue.
     * (iii) ServerRestEvent if server is going for a rest.
     * @param customer Customer that is done being served by server.
     * @param server ServerI that is done serving the customer.
     */
    public DoneEvent(Customer customer, ServerI server) {
        super(customer, server.getNextAvailableTime(), server, shop -> {
            ServerI s = shop.get(server).get();
            s = s.done();
            return Pair.of(shop.replace(s), new IdleEvent(customer, s));
        }, (shop, chance) -> {
            ServerI s = shop.get(server).get();
            s = s.goRest();
            return Pair.of(shop.replace(s), new ServerRestEvent(customer, s));
        }, status);
        this.server = server;
    }

    /**
     * Retrieve String representation of DoneEvent.
     * @return Time when server is done, the customer who has been served,
     *     and server who served the customer.
     */
    @Override
    public String toString() {
        if (server instanceof Server) {
            return String.format("%s done serving by server %d",
                    super.toString(), server.getId());
        } else {
            return String.format("%s done serving by %s", super.toString(),
                    server.toString());
        }
    }
}
