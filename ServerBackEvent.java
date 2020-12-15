package cs2030.simulator;

/**
 * ServerBackEvent where the server comes back from a break. The
 * 'ServerBackEvent' supports operators that includes: (i) Transition to
 * IdleEvent or ServeEvent depending on the state of the server.
 * ServerBackEvent contains EventStatus status.
 */
public class ServerBackEvent extends Event {
    private static final EventStatus status = EventStatus.SERVERBACK;

    /**
     * Constructs a ServerBackEvent containing a customer and a server, and an
     * execute method to transition to the next events: (i) IdleEvent if the
     * server has an empty queue. (ii) ServerEvent if server has customers in
     * the queue.
     * @param customer the latest customer that was served by this server.
     * @param server Server who was taking a rest.
     */
    public ServerBackEvent(Customer customer, ServerI server) {
        super(customer, server.getNextAvailableTime(), server, shop -> {
            ServerI s = shop.get(server).get();
            s = s.doneRest();
            return Pair.of(shop.replace(s), new IdleEvent(customer, s));
        }, status);
    }

    /**
     * This should not be accessed. Print an error message if it is.
     * @return Error message.
     */
    @Override
    public String toString() {
        return "This should not be printed";
    }
}
