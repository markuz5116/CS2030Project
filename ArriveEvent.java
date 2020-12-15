package cs2030.simulator;

import java.util.Optional;

/**
 * Event where customer arrives. The 'ArriveEvent' class supports operators
 * that includes: (i) Transition to ServeEvent, WaitEvent, and LeaveEvent
 * depending on criteria.
 * ArriveEvent contains EventStatus status.
 */
public class ArriveEvent extends Event {
    private static final EventStatus status = EventStatus.ARRIVE;

    /**
     * Constructs an ArriveEvent containing a customer and an execute method
     * to transition to the next events: (i) ServeEvent if there is an
     * available server. (ii) WaitEvent if there is a server without a full
     * queue. (iii) LeaveEvent if there are no available servers and all
     * servers have  full queue.
     * @param customer - customer that is arriving. Customers will either be
     *                 of type NORMAL or GREEDY. If customer is NORMAL, he
     *                 will join the first server who is available or does
     *                 not have a full queue. If customer is GREEDY, he will
     *                 join the server who is available, followed by the
     *                 server with the shortest queue, then the server with
     *                 the smallest id.
     */
    public ArriveEvent(Customer customer) {
        super(customer, customer.getArrivalTime(), new Server(-1, 0), shop -> {
            if (shop.hasAvailServer().isEmpty() && shop.hasFreeQueue().isEmpty()) {
                return Pair.of(shop, new LeaveEvent(customer));
            } else {
                CustomerType type = customer.getType();
                switch (type) {
                    case NORMAL:
                        Optional<ServerI> server = shop.hasAvailServer();
                        if (server.isPresent()) {
                            return Pair.of(shop, new ServeEvent(customer,
                                    server.get()));
                        } else {
                            server = shop.hasFreeQueue();
                            return Pair.of(shop, new WaitEvent(customer,
                                    server.get()));
                        }

                    case GREEDY:
                        ServerI fastServer = shop.getShortestQueue().get();
                        if (fastServer.isAvailable()) {
                            return Pair.of(shop, new ServeEvent(customer,
                                    fastServer));
                        } else {
                            return Pair.of(shop, new WaitEvent(customer,
                                    fastServer));
                        }

                    default:
                        return Pair.empty();
                }
            }
        }, status);
    }

    /**
     * Return String representation of ArriveEvent.
     * @return The arrival time and the id of the customer that arrives.
     */
    @Override
    public String toString() {
        return String.format("%s arrives", super.toString());
    }
}
