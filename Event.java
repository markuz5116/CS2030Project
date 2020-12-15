package cs2030.simulator;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Abstract Event containing ArriveEvent, DoneEvent, LeaveEvent, WaitEvent,
 * ServeEvent, IdleEvent, and ServeRestEvent. The 'Event' class supports
 * operators that includes: (i) Retrieve customer. (ii) Retrieve startTime of
 * event. (iii) Retrieve server of event. (iv) Retrieve EventStatus of event.
 * (v) Applying Function to transition to next Event with an input of a Shop.
 * (vi) Applying BiFunction to transition to next Event with input of a Shop
 * and Supplier of RandomGenerator.
 * Event contains Customer customer, double startTime, ServerI server,
 * Function and BiFunction that both return a Pair of shop and Event, and
 * EventStatus status.
 */
public abstract class Event implements Comparable<Event> {
    private final Customer customer;
    private final double startTime;
    private final ServerI server;
    private final Function<Shop, Pair<Shop, Event>> func;
    private final BiFunction<Shop, Double, Pair<Shop, Event>> biFunc;
    private final EventStatus status;

    /**
     * Construct an Event containing a customer, start time of event, server
     * of the event, Function to transition to the next event, and status of
     * the event.
     * @param customer Customer of the event.
     * @param startTime Time the event started.
     * @param server Server of the event.
     * @param func Function to lead to the next event.
     * @param status Status of the Event.
     */
    public Event(Customer customer, double startTime, ServerI server,
             Function<Shop, Pair<Shop, Event>> func, EventStatus status) {
        this.customer = customer;
        this.startTime = startTime;
        this.server = server;
        this.func = func;
        this.status = status;
        this.biFunc = null;
    }

    /**
     * Overloaded constructor of Event containing a customer, start time of
     * event, server of the event, BiFunction to transition to the next
     * event, and status of the event.
     * @param customer Customer of the event.
     * @param startTime Time the event started.
     * @param server Server of the event.
     * @param biFunc BiFunction to lead to the next event with the input from
     *              the Supplier of RandomGenerator.
     * @param status Status of the Event.
     */
    public Event(Customer customer, double startTime, ServerI server,
                 BiFunction<Shop, Double, Pair<Shop,
                         Event>> biFunc, EventStatus status) {
        this.customer = customer;
        this.startTime = startTime;
        this.server = server;
        this.biFunc = biFunc;
        this.func = null;
        this.status = status;
    }

    /**
     * Overloaded constructor of Event containing a customer, start time of
     * event, server of the event, function to transition to next event,
     * BiFunction to transition to next event, and status of the event.
     * @param customer Customer of the event.
     * @param startTime Time the event started.
     * @param server Server of the event.
     * @param func Function to lead to the next event.
     * @param biFunc BiFunction ot lead to the next event with the input from
     *              the Supplier of RandomGenerator.
     * @param status Status of the Event.
     */
    public Event(Customer customer, double startTime, ServerI server,
                 Function<Shop, Pair<Shop, Event>> func, BiFunction<Shop,
            Double, Pair<Shop, Event>> biFunc, EventStatus status) {
        this.customer = customer;
        this.startTime = startTime;
        this.server = server;
        this.biFunc = biFunc;
        this.func = func;
        this.status = status;
    }

    /**
     * Overloaded constructor to update the startTime of the event, increased
     * by the addTime provided.
     * @param addTime time will be added to the event's start time.
     * @param event event whose time will increase based on the addTime.
     */
    public Event(double addTime, Event event) {
        this.customer = event.customer;
        this.startTime = event.startTime + addTime;
        this.server = event.server;
        this.biFunc = event.biFunc;
        this.func = event.func;
        this.status = event.status;
    }

    /**
     * Retrieve customer engaging in the event.
     * @return customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Retrieve time event started.
     * @return startTime.
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * Retrieve server of the event.
     * @return server.
     */
    public ServerI getServer() {
        return server;
    }

    /**
     * Retrieve status of event.
     * @return event.
     */
    public EventStatus getStatus() {
        return status;
    }

    /**
     * Return the next event and updates the Shop s based on the event.
     * @param s Shop s of the latest state.
     * @return A pair of the latest Shop and the next Event.
     */
    public final Pair<Shop, Event> execute(Shop s) {
        return func.apply(s);
    }

    /**
     * Return the next event and updates the shop based on the event and the
     * time, either resting time or serving time.
     * @param s Shop s of the latest state.
     * @param time time used to update the Shop.
     * @return A pair of the latest Shop and the next Event.
     */
    public Pair<Shop, Event> execute(Shop s, Double time) {
        return biFunc.apply(s, time);
    }

    /**
     * Compares this event with the specified event for order. Returns a
     * negative integer, zero, or a positive integer as this event has a
     * smaller than, equal to, or greater than the specified event.
     * @param e2 - the event to be compared.
     * @return a negative integer, zero, or a positive integer as this event
     *     is earlier, of the same time, or later than the specified event.
     */
    @Override
    public int compareTo(Event e2) {
        if (this.startTime == e2.startTime) {
            if (customer.getId() == e2.customer.getId()) {
                return this.status.getLabel() - e2.status.getLabel();
            }
            return customer.getId() - e2.customer.getId();
        } else {
            return Double.compare(this.startTime, e2.startTime);
        }
    }

    /**
     * Retrieve String representation of the start time of the event and the
     * customer.
     * @return startTime of event and String representation of customer.
     */
    @Override
    public String toString() {
        return String.format("%.3f %s", startTime, customer.toString());
    }
}
