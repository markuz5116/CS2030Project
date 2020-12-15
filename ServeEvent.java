package cs2030.simulator;

/**
 * ServeEvent where the server serves the customer. The 'ServeEvent' class
 * supports operators that includes: (i) Transition to DoneEvent.
 * ServeEvent contains the EventStatus and ServerI server.
 */
public class ServeEvent extends Event {
    private static final EventStatus status = EventStatus.SERVE;
    private final ServerI server;

    /**
     * Constructs a ServeEvent containing a customer and a server and an
     * execute method to transition to the next event: DoneEvent.
     * @param customer customer to be served.
     * @param server server that is serving a customer.
     */
    public ServeEvent(Customer customer, ServerI server) {
        super(customer, Math.max(customer.getArrivalTime(),
                server.getNextAvailableTime()), server, (shop, serviceTime) -> {
            ServerI s = shop.get(server).get();
            s = s.serve(customer, serviceTime);
            return Pair.of(shop.replace(s), new DoneEvent(customer, s));
        },
        status);
        this.server = server;
    }

    /**
     * Overloaded constructor to increment the start time of the ServeEvent
     * with the time provided. Time can either be serving time or resting time.
     * @param time time used to increment start time of ServeEvent.
     * @param serveE ServeEvent which startTime is increased.
     */
    public ServeEvent(double time, Event serveE) {
        super(time, serveE);
        this.server = serveE.getServer();
    }

    /**
     * Retrieve String representation of ServeEvent.
     * @return the String representation of the customer being served, the
     *     String representation of the Server and the time of the Event.
     */
    @Override
    public String toString() {
        if (server instanceof Server) {
            return String.format("%s served by server %d", super.toString(),
                    server.getId());
        } else {
            return String.format("%s served by %s", super.toString(),
                    server.toString());
        }
    }
}
