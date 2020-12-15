package cs2030.simulator;

/**
 * Customer contains int id, double arrivalTime and CustomerType type. The
 * Customer class supports operators that includes: (i) Retrieve id. (ii)
 * Retrieve arrivalTime. (iii) Retrieve type.
 * Customer contains int id, double arrivalTime, and CustomerType type.
 */
public class Customer {
    private final int id;
    private final double arrivalTime;
    private final CustomerType type;

    /**
     * Constructs a Normal Customer containing an id, and arrival time.
     * @param id customer identifier.
     * @param arrivalTime customer arrival time.
     */
    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.type = CustomerType.NORMAL;
    }

    /**
     * Constructs Customer containing an id, arrival time, and type.
     * @param id customer identifier.
     * @param arrivalTime customer arrival time.
     * @param type customer type.
     */
    public Customer(int id, double arrivalTime, CustomerType type) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.type = type;
    }

    /**
     * Retrieve customer identifier.
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieve customer arrival time.
     * @return arrivalTime.
     */
    public double getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Retrieve customer type (i) NORMAL, or (ii) GREEDY.
     * @return type.
     */
    public CustomerType getType() {
        return type;
    }

    /**
     * Retrieve string representation of customer.
     * @return customer identifier, and if customer's type is GREEDY, include
     *     a "(greedy)".
     */
    @Override
    public String toString() {
        if (type == CustomerType.GREEDY) {
            return String.format("%d(greedy)", id);
        } else {
            return String.format("%d", id);
        }
    }
}
