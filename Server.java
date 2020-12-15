package cs2030.simulator;

import java.util.LinkedList;
import java.util.List;

/**
 * Server which implements ServerI that requires rest and has its own queue.
 * The 'Server' class supports operators that includes: (i) Retrieve id. (ii)
 * Retrieve the queue. (iii) Updates the Server to serve the next customer in
 * queue or transition ot Idle if there is an empty queue. (iv) Retrieve
 * Boolean of whether the queue is full. (vi) Retrieve the next available
 * time of the SelfCheckOut. (vii) Updates the Server to the next available
 * time after serving the customer.  (viii) Enqueue a customer into the queue
 * . (ix) Allow the server to go for a rest. (x) Updates the server next
 * available time with the resting period when going for rest. (xi) Updates
 * the Server to come back from rest and either serve the next customer in
 * queue or transition to an idle state.
 * Server contains int identifier, boolean isAvailable, LinkedList of
 * Customer called queue, int maxQueueSize, double nextAvailableTime.
 */
public class Server implements ServerI {
    private final int id;
    private final boolean isAvailable;
    private final LinkedList<Customer> queue;
    private final int maxQueueSize;
    private final double nextAvailableTime;

    /**
     * Constructs a Server with specified id, isAvailable,
     * hasWaitingCustomer, and nextAvailableTime. maxQueueSize defaulted to 1
     * . If Server hasWaitingCustomer, add a customer into the queue to
     * simulate a customer waiting, else keep queue empty.
     * @param id identifier of Server.
     * @param isAvailable boolean to see if Server is available.
     * @param hasWaitingCustomer boolean to see if there is a customer
     *                           waiting for the server.
     * @param nextAvailableTime next time the server is able to serve a
     *                          customer.
     */
    public Server(int id, boolean isAvailable, boolean hasWaitingCustomer,
                  double nextAvailableTime) {
        this.id = id;
        this.isAvailable = isAvailable;
        queue = new LinkedList<>();
        if (hasWaitingCustomer) {
            queue.add(new Customer(1, 0.0));
        }
        maxQueueSize = 1;
        this.nextAvailableTime = nextAvailableTime;
    }

    /**
     * Constructs new Server with specified id, and maxQueueSize. isAvailable
     * is defaulted to true, initialised a queue for server and its
     * nextAvailableTime is also 0.
     * @param id Server's identifier.
     * @param maxQueueSize maximum size of queue.
     */
    public Server(int id, int maxQueueSize) {
        this.id = id;
        this.isAvailable = true;
        this.queue = new LinkedList<Customer>();
        this.maxQueueSize = maxQueueSize;
        this.nextAvailableTime = 0;
    }

    /**
     * Private constructor to update the Server to a new state.
     * @param id Server identifier.
     * @param isAvailable Whether the Server is available.
     * @param queue The waiting queue of the waiter.
     * @param maxQueueSize The maximum queue size of the Server.
     * @param nextAvailableTime Server's next available time.
     */
    private Server(int id, boolean isAvailable, LinkedList<Customer> queue,
                  int maxQueueSize, double nextAvailableTime) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.queue = queue;
        this.maxQueueSize = maxQueueSize;
        this.nextAvailableTime = nextAvailableTime;
    }

    /**
     * Return the boolean of whether this queue is full of customers.
     * @return true if queue is full, else false.
     */
    public boolean fullQueue() {
        return queue.size() >= maxQueueSize;
    }

    /**
     * Return the boolean of whether the Server is available to serve a
     * customer.
     * @return true if server is available, else false.
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Return double of the server's next available time to serve a customer.
     * @return double next available time of server.
     */
    public double getNextAvailableTime() {
        return nextAvailableTime;
    }

    /**
     * Return the server's identifier.
     * @return this identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Return this queue of customer.
     * @return this queue of customer.
     */
    public List<Customer> getQueue() {
        return queue;
    }

    /**
     * Updates this Server's next available time with the arrival time and
     * serving time of the customer to simulate serving the customer. If the
     * queue is not empty, poll the queue to simulate serving the first
     * customer in the queue.
     * @param customer customer being served.
     * @param servingTime serving time for the customer.
     * @return The Server updated with the new available time after serving
     *      the customer.
     */
    public Server serve(Customer customer, double servingTime) {
        double startTime = Math.max(customer.getArrivalTime(),
                nextAvailableTime);
        LinkedList<Customer> temp = new LinkedList<>(queue);
        if (!temp.isEmpty()) {
            temp.poll();
        }
        return new Server(id, false, temp, maxQueueSize,
                startTime + servingTime);
    }

    /**
     * Add customer into the queue for the server.
     * @param customer customer that is added into the queue.
     * @return Updated server with the new customer in its queue.
     */
    public Server wait(Customer customer) {
        LinkedList<Customer> temp = new LinkedList<Customer>(queue);
        temp.offer(customer);
        return new Server(id, isAvailable, temp, maxQueueSize,
                nextAvailableTime);
    }

    /**
     * Return the updated Server where if there is no customer in the queue,
     * its isAvailable = true, else false.
     * @return updated server on whether isAvailable = true if customer queue
     *     is empty, else false.
     */
    public Server done() {
        boolean avail;
        if (queue.isEmpty()) {
            avail = true;
        } else {
            avail = false;
        }
        return new Server(id, avail, queue, maxQueueSize, nextAvailableTime);
    }

    /**
     * Return the updated Server that went for a rest, meaning it will not be
     * available to take a customer or have a customer join the queue.
     * @return updated Server who is on rest.
     */
    public Server goRest() {
        return new Server(id, false, queue, maxQueueSize, nextAvailableTime);
    }

    /**
     * Return the updated Server who is resting, updating its next available
     * time to after the rest period.
     * @param restTime Double duration of rest for Server.
     * @return Updated Server with the updated next available time after the
     *     resting period.
     */
    public Server rest(double restTime) {
        return new Server(id, false, queue, maxQueueSize,
                nextAvailableTime + restTime);
    }

    /**
     * Return the Server after resting where isAvailable = true, if there is
     * no customer in its queue, else false.
     * @return Updated Server where isAvailable = true if customer queue is
     *     empty, else false.
     */
    public Server doneRest() {
        boolean avail;
        Customer customer = null;
        LinkedList<Customer> temp = new LinkedList<Customer>(queue);
        if (temp.isEmpty()) {
            avail = true;
        } else {
            avail = false;
        }
        return new Server(id, avail, temp, maxQueueSize, nextAvailableTime);
    }

    /**
     * Compare the specified Object with this Server for equality.
     * @param o the reference object which will be compared to this.
     * @return true if Object is a Server and if both have the same
     *     identifier.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Server) {
            Server otherServer = (Server) o;
            return id == otherServer.id;
        } else {
            return false;
        }
    }

    /**
     * Compare this Server with the specified ServerI for order. Returns
     * a negative integer, zero, or a positive integer as this Server
     * is available, while the other is not, this and other are the same
     * ServerI, and if the other ServerI is available while this is not, or
     * other has a smaller id.
     * @param o the other ServerI being compared to
     * @return a negative integer, zero, or a positive integer based on the
     *     criteria.
     */
    @Override
    public int compareTo(ServerI o) {
        if (isAvailable && o.isAvailable()) {
            return id - o.getId();
        } else if (isAvailable) {
            return -1;
        } else if (o.isAvailable()) {
            return 1;
        } else if (queue.size() == o.getQueue().size()) {
            return this.id - o.getId();
        } else {
            return queue.size() - o.getQueue().size();
        }
    }

    /**
     * Return String representation of the Server being available to take a
     * customer, is busy and has a waitingCustomer, or is busy without any
     * customers waiting, and when the server will be available.
     * @return The Server state, whether there are customers waiting and its
     *     next available time.
     */
    @Override
    public String toString() {
        if (isAvailable) {
            return String.format("%d is available", id);
        } else if (queue.size() != 0) {
            return String.format("%d is busy; waiting customer to be served at %.3f",
                        id, nextAvailableTime);
        } else {
            return String.format("%d is busy; available at %.3f",
                        id, nextAvailableTime);
        }
    }
}

