package cs2030.simulator;

/**
 * LeaveEvent where customer leaves the shop.
 * LeaveEvent contains EventStatus status.
 */
public class LeaveEvent extends Event {
    private static final EventStatus status = EventStatus.LEAVE;

    /**
     * Constructs a LeaveEvent containing a customer and an execute method
     * that returns an empty Pair.
     * @param customer Customer that is leaving the shop.
     */
    public LeaveEvent(Customer customer) {
        super(customer, customer.getArrivalTime(), null, x -> Pair.empty(), status);
    }

    /**
     * Retrieve String representation of LeaveEvent.
     * @return Customer that is leaving the shop.
     */
    @Override
    public String toString() {
        return String.format("%s leaves", super.toString());
    }
}
