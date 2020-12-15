package cs2030.simulator;

/**
 * ServerRestEvent where the server undergoes a resting period. The
 * 'ServerRestEvent' supports operators that includes: (i) Transition to
 * ServerBackEvent.
 * ServerRestEvent contains EventStatus status.
 */
public class ServerRestEvent extends Event {
    private static final EventStatus status = EventStatus.SERVERREST;

    /**
     * Constructs a ServerRestEvent containing a customer, a server, and a
     * lambda expression to the next event: (i) ServerBackEvent.
     * @param customer The latest customer that was served by the server.
     * @param server Server that is taking a rest.
     */
    public ServerRestEvent(Customer customer, ServerI server) {
        super(customer, 0, server, (shop, time) -> {
            ServerI tempServer = shop.get(server).get();
            tempServer = tempServer.rest(time);
            return Pair.of(shop.replace(tempServer),
                    new ServerBackEvent(customer, tempServer));
        }, status);
    }

    /**
     * Return error message as this should not printed.
     * @return error message.
     */
    @Override
    public String toString() {
        return "This should not be printed";
    }
}
