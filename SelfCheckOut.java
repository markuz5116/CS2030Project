package cs2030.simulator;

import java.util.LinkedList;
import java.util.List;

/**
 * SelfCheckOut which implements ServerI that does not require rest and share
 * a queue with other SelfCheckOut objects. The 'SelfCheckOut' class supports
 * operators that includes: (i) Retrieve id. (ii) Retrieve the queue. (iii)
 * Updates the SelfCheckOut to serve the next customer in the queue or
 * transition to idle if there is an empty queue. (iv) Retrieve Boolean of
 * whether the queue is full. (v) Retrieve Boolean of the SelfCheckOut is
 * available. (vi) Retrieve the next available time of the SelfCheckOut.
 * (vii) Updates the SelfCheckOut to the next available time after
 * serving the customer. (viii) Enqueue a customer into the queue.
 * SelfCheckOut contains LinkedList sharedQueue, int id, boolean isAvailable,
 * int maxQueueSize, double nextAvailableTime.
 */
public class SelfCheckOut implements ServerI {
    private static final LinkedList<Customer> sharedQueue = new LinkedList<>();
    private final int id;
    private final boolean isAvailable;
    private final int maxQueueSize;
    private final double nextAvailableTime;

    /**
     * Constructs SelfCheckout containing int identifier and int maxQueueSize.
     * @param id SelfCheckout's identifier
     * @param maxQueueSize Maximum size of the queue.
     */
    public SelfCheckOut(int id, int maxQueueSize) {
        this.id = id;
        this.isAvailable = true;
        this.maxQueueSize = maxQueueSize;
        this.nextAvailableTime = 0;
    }

    /**
     * Private constructor to update the SelfCheckOut to a new state.
     * @param id SelfCheckOut's identifier.
     * @param isAvailable Whether the SelfCheckOut is available.
     * @param maxQueueSize Maximum queue size of the SelfCheckOut.
     * @param nextAvailableTime SelfCheckOut's next available time.
     */
    private SelfCheckOut(int id, boolean isAvailable, int maxQueueSize,
                        double nextAvailableTime) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.maxQueueSize = maxQueueSize;
        this.nextAvailableTime = nextAvailableTime;
    }

    /**
     * Retrieve the SelfCheckOut identifier.
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieved the sharedQueue.
     * @return sharedQueue.
     */
    public List<Customer> getQueue() {
        return sharedQueue;
    }

    /**
     * Retrieve the boolean of whether the sharedQueue is full.
     * @return true if sharedQueue is full, else false.
     */
    public boolean fullQueue() {
        return sharedQueue.size() >= maxQueueSize;
    }

    /**
     * Retrieve the boolean of whether this SelfCheckOut is available.
     * @return true if this is available, else false.
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Retrieve this SelfCheckOut next available time.
     * @return nextAvailableTime.
     */
    public double getNextAvailableTime() {
        return nextAvailableTime;
    }

    /**
     * Return a updated SelfCheckout after being done serving a customer. If
     * there are still customers in the sharedQueue, SelfCheckout isAvailable
     * = false, else true.
     * @return updated SelfCheckout.
     */
    public ServerI done() {
        boolean avail;
        if (sharedQueue.isEmpty()) {
            avail = true;
        } else {
            avail = false;
        }
        return new SelfCheckOut(id, avail, maxQueueSize, nextAvailableTime);
    }

    /**
     * Updates this SelfCheckOut's nextAvailableTime with the arrivalTime and
     * servingTime of the customer to simulate serving the customer.
     * @param customer customer being served.
     * @param servingTime serving time for the customer.
     * @return The SelfCheckout updated with the new next available time
     *     after serving the customer.
     */
    public SelfCheckOut serve(Customer customer, double servingTime) {
        double startTime = Math.max(customer.getArrivalTime(),
                nextAvailableTime);
        sharedQueue.poll();
        return new SelfCheckOut(id, false, maxQueueSize,
                startTime + servingTime);
    }

    /**
     * Add customer into the sharedQueue of the SelfCheckout's counter.
     * @param customer customer that will be added into the sharedQueue.
     * @return the SelfCheckOut with the updated queue.
     */
    @Override
    public SelfCheckOut wait(Customer customer) {
        sharedQueue.add(customer);
        return this;
    }

    /**
     * Should not be used. If used, print the error.
     * @return error message.
     */
    public SelfCheckOut goRest() {
        System.out.println("error");
        return this;
    }

    /**
     * Return the same SelfCheckout and print an error.
     * @param restTime should not be used.
     * @return this.
     */
    public SelfCheckOut rest(double restTime) {
        System.out.println("error");
        return this;
    }

    /**
     * Return this and print an error.
     * @return this.
     */
    public ServerI doneRest() {
        System.out.println("error");
        return this;
    }

    /**
     * Compare this SelfCheckOut with the specified ServerI for order. Returns
     * a negative integer, zero, or a positive integer as this SelfCheckOut
     * is available, while the other is not, this and other are the same
     * ServerI, and if the other ServerI is available while this is not, or
     * other has a smaller id.
     * @param other the other ServerI being compared to
     * @return a negative integer, zero, or a positive integer based on the
     *     criteria.
     */
    @Override
    public int compareTo(ServerI other) {
        if (isAvailable && other.isAvailable()) {
            return id - other.getId();
        } else if (isAvailable) {
            return -1;
        } else if (other.isAvailable()) {
            return 1;
        } else if (sharedQueue.size() == other.getQueue().size()) {
            return this.id - other.getId();
        } else {
            return sharedQueue.size() - other.getQueue().size();
        }
    }

    /**
     * Compare the specified Object with this SelfCheckout for equality.
     * @param obj the reference object which will be compared to this.
     * @return true if Object is a SelfCheckout and if both have the same
     *     identifier.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SelfCheckOut) {
            SelfCheckOut other = (SelfCheckOut) obj;
            return id == other.id;
        } else {
            return false;
        }
    }

    /**
     * Retrieve String representation of the SelfCheckOut.
     * @return the identifier of the SelfCheckout and its representation.
     */
    @Override
    public String toString() {
        return String.format("self-check %d", id);
    }
}
