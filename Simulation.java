package cs2030.simulator;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.function.Supplier;

/**
 * Simulation class that controls the simulation. The 'Simulation' class
 * supports operators that includes: (i) Running the simulation. (ii) Getting
 * the average waiting time of the simulation. (iii) Handling the ArriveEvent
 * of the simulation. (iv) Handling the ServeEvent of the simulation. (v)
 * Handling the WaitEvent of the simulation. (vi) Handling the LeaveEvent of
 * the simulation. (vi) Handling the DoneEvent of the simulation. (vii)
 * Handling the ServerRestEvent of the simulation. (viii) Handling the
 * ServerBackEvent. (ix) Updating other ServeEvents of the same Server. (x)
 * Updating other ServeEvents of all same Self-Checkouts, with the earliest
 * Self-Checkout.
 * Simulation contains PriorityQueue events, Shop shop, RandomGenerator rng,
 * Supplier generator, double probRest, int served, int left, double
 * totalWaitTime.
 */
public class Simulation {
    private PriorityQueue<Event> events;
    private Shop shop;
    private final RandomGenerator rng;
    private final Supplier<RandomGenerator> generator;
    private final double probRest;
    private int served = 0;
    private int left = 0;
    private double totalWaitTime = 0;

    /**
     * Construct simulation based on command line arguments. index 0: base
     * seed of RandomGenerator. index 1: number of servers. index 2: number
     * of self-checkouts. index 3: maximum queue length. index 4: number of
     * customers. index 5: arrival rate of customers. index 6: service rate
     * of customers. index 7: resting rate of serverIs. index 8: probability
     * of rest for serverIs. index 9: probability of a customer being a
     * Greedy one.
     */
    public Simulation(String[] args) {
        int seed = Integer.parseInt(args[0]);
        int numServers = Integer.parseInt(args[1]);
        int numSelfCheckout = Integer.parseInt(args[2]);
        int maxQueueLength = Integer.parseInt(args[3]);
        int numCustomers = Integer.parseInt(args[4]);
        double arrivalRate = Double.parseDouble(args[5]);
        double serviceRate = Double.parseDouble(args[6]);
        double restingRate = Double.parseDouble(args[7]);
        double probRest = Double.parseDouble(args[8]);
        double probGreedy = Double.parseDouble(args[9]);
        rng = new RandomGenerator(seed, arrivalRate, serviceRate, restingRate);
        generator = () -> rng;
        this.probRest = probRest;
        shop = new Shop(numServers, numSelfCheckout, maxQueueLength);
        events = new PriorityQueue<Event>();
        double startTime = 0;
        for (int i = 1; i <= numCustomers; i++) {
            if (i != 1) {
                startTime += generator.get().genInterArrivalTime();
            }
            if (generator.get().genCustomerType() < probGreedy) {
                events.add(new ArriveEvent(new Customer(i, startTime,
                        CustomerType.GREEDY)));
            } else {
                events.add(new ArriveEvent(new Customer(i, startTime,
                        CustomerType.NORMAL)));
            }
        }
    }

    /**
     * Run the simulation. Execute the next event of the earliest start time
     * of the event, the lowest customer id and the sequencing of events. For
     * each event type, handle it differently based on its respective handle
     * methods, except for IdleEvent which is ignored. At the end, print the
     * statistics of the simulation: (i) average waiting time. (ii) number of
     * customers served. (iii) number of customers who left.
     */
    public void run() {
        while (!events.isEmpty()) {
            Event curr = events.poll();
            EventStatus status = curr.getStatus();
            switch (status) {
                case ARRIVE:
                    handleArrive(curr);
                    break;

                case SERVE:
                    handleServe(curr);
                    break;

                case WAIT:
                    handleWait(curr);
                    break;

                case LEAVE:
                    handleLeave(curr);
                    break;

                case DONE:
                    handleDone(curr);
                    break;

                case SERVERREST:
                    handleRest(curr);
                    break;

                case SERVERBACK:
                    handleBack(curr);
                    break;

                case IDLE:
                    break;

                default:
                    break;
            }
        }
        System.out.println(String.format("[%.3f %d %d]", averageWaitTime(),
                served, left));
    }

    /**
     * Return the average waiting time for the customers served in the
     * simulation. If there are no served customers, return 0.
     * @return average waiting time of the simulation.
     */
    public double averageWaitTime() {
        if (served == 0) {
            return 0;
        }
        return totalWaitTime / served;
    }

    /**
     * Prints the ArriveEvent input. Execute the input with the shop as an
     * argument, to check the shop for an available server to either serve or
     * have the customer wait in the queue. It returns a pair of the new
     * Event transitioned from ArriveEvent and the most updated shop. Add the
     * new Event into the priority queue of events.
     * @param event ArriveEvent.
     */
    private void handleArrive(Event event) {
        System.out.println(event);
        Pair<Shop, Event> pair = event.execute(shop);
        events.add(pair.second());
    }


    /**
     * Prints the ServeEvent. Increment the number of customers served.
     * Increment the wait time based on the ServeEvent start time and the
     * customer's arrival time. An execute function returns a pair of the
     * most updated shop and the DoneEvent of after serving the customer.
     * Update the priority queue of the events, looking for the ServeEvent of
     * the same ServerI and increase its start time based on the service time
     * of the input event. Add the next event in the PriorityQueue of events
     * and update the shop with the new shop.
     * @param event ServeEvent.
     */
    private void handleServe(Event event) {
        System.out.println(event);
        served++;
        totalWaitTime += event.getStartTime() - event.getCustomer().getArrivalTime();
        double serviceTime = generator.get().genServiceTime();
        Pair<Shop, Event> pair = event.execute(shop, serviceTime);
        updateTime(serviceTime, event);
        events.add(pair.second());
        shop = pair.first();
    }

    /**
     * Prints the WaitEvent. A execute function that returns a Pair of the
     * most updated shop, where the server of the event has its queue
     * incremented with the customer of the event, and a ServeEvent of the
     * customer and server. Updates shop with the shop returned in the pair.
     * Add the next event into the priority queue of the events.
     * @param event WaitEvent.
     */
    private void handleWait(Event event) {
        System.out.println(event);
        Pair<Shop, Event> pair = event.execute(shop);
        shop = pair.first();
        events.add(pair.second());
    }

    /**
     * Prints LeaveEvent. Increment the the number of customers who left.
     * @param event LeaveEvent.
     */
    private void handleLeave(Event event) {
        System.out.println(event);
        left++;
    }

    /**
     * Prints DoneEvent. If the serverI in the event is a SelfCheckOut, the
     * execute function will return a shop where the SelfCheckOut will be
     * available or unavailable depending on the sharedQueue being empty or
     * not, respectively, and the next IdleEvent. If the serverI in the event
     * is a Server, check whether the server will go for a break. If the
     * server is going for a break, the pair returned will be of the server
     * being unavailable to serve or wait a customer, and the next
     * ServerRestEvent If the server is not going on break, update the server
     * with similar criteria as the resting probability, and the next
     * IdleEvent. Update the shop with the new ServerI and if the next event
     * is a ServerRestEvent, add it into the priority queue of events.
     * @param event DoneEvent.
     */
    private void handleDone(Event event) {
        System.out.println(event);
        Pair<Shop, Event> pair;
        if (event.getServer() instanceof SelfCheckOut) {
            updateSelfCheckOut(event.getServer());
            pair = event.execute(shop);
        } else {
            if (generator.get().genRandomRest() < probRest) {
                pair = event.execute(shop, 0.0);
                events.add(pair.second());
                return;
            } else {
                pair = event.execute(shop);
            }
        }
        shop = pair.first();
    }

    /**
     * Used the RandomGenerator to get the resting period of the server in
     * the event. Update priority queue of events to increment all ServeEvent
     * with the same ServerI. The execute method returns a pair of the
     * updated shop to update the server going for a rest and increment its
     * nextAvailableTime with its resting time, and a ServerBackEvent. Add
     * the ServerBackEvent into the priority queue of the events, and update
     * the shop in the simulation with the resting server.
     * @param event RestEvent.
     */
    private void handleRest(Event event) {
        double restTime = generator.get().genRestPeriod();
        updateTime(restTime, event);
        Pair<Shop, Event> pair = event.execute(shop, restTime);
        events.add(pair.second());
        shop = pair.first();
    }

    /**
     * The execute method that returns an updated shop where the server
     * returning from rest is available to serve a customer or not based on
     * the queue of the server. If there are customers in the queue,
     * isAvailable = false, else true. Update the shop in the simulation with
     * its most updated version of the server.
     * @param event ServerBackEvent.
     */
    private void handleBack(Event event) {
        Pair<Shop, Event> pair = event.execute(shop);
        shop = pair.first();
    }

    /**
     * Update events in the priority queue if the event is a serve event, and
     * the server in the ServeEvent is the same as the Server in the event in
     * the input. Updated event will have its start time increased based on
     * the time param.
     * @param time time used to increment the start time of the ServeEvents
     *             that have the same ServerI.
     * @param event event that contains the ServerI used to check which
     *              events to update.
     */
    private void updateTime(double time, Event event) {
        ArrayList<Event> temp = new ArrayList<>();
        for (Event e : events) {
            if (e.getServer().equals(event.getServer()) &&
                    e.getStatus() == EventStatus.SERVE) {
                temp.add(new ServeEvent(time, e));
            } else {
                temp.add(e);
            }
        }
        events = new PriorityQueue<>(temp);
    }

    /**
     * Update all ServeEvents with a SelfCheckOut in the priority queue of
     * events to take in the earliest SelfCheckOut, which is the input.
     * @param s the earliest SelfCheckOut that can be used at the current
     *          instance.
     */
    private void updateSelfCheckOut(ServerI s) {
        ArrayList<Event> temp = new ArrayList<>();
        for (Event e : events) {
            if (e.getServer() instanceof SelfCheckOut &&
                    e.getStatus() == EventStatus.SERVE) {
                temp.add(new ServeEvent(e.getCustomer(), s));
            } else {
                temp.add(e);
            }
        }
        events = new PriorityQueue<>(temp);
    }
}
