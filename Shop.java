package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Shop contains the list of serverIs that will be serving the customer in the
 * simulation. The 'Shop' class supports operators that includes: (i) Finding
 * the serverI that fulfills the Predicate. (ii) Retrieving the serverI that
 * has the most updated specific server in the shop. (iii) Check whether the
 * shop has an available serverI. (iv) Check whether there is a serverI who
 * does not have a full queue in the shop. (v) Get the serverI with the
 * shortest queue from the shop. (vi) Updates the shop with the latest
 * serverI. (vii) Retrieve the list of serverIs.
 * Shop contains List serverList.
 */
public class Shop {
    private final List<ServerI> serverList;

    /**
     * Constructs a shop with all Servers with the number, specified by the
     * input.
     * @param numServers number of servers in the shop being constructed.
     */
    public Shop(int numServers) {
        serverList = Stream
                .iterate(1, i -> i <= numServers, i -> i + 1)
                .map(index -> new Server(index, true, false, 0))
                .collect(Collectors.toList());
    }

    /**
     * Constructs a new Shop with the specified number of servers and
     * self-checkouts and their maximum queue size.
     * @param numServers Number of servers in the shop.
     * @param numSelfCheckout Number of self-checkouts in the shop.
     * @param maxQ Maximum queue size.
     */
    public Shop(int numServers, int numSelfCheckout, int maxQ) {
        int total = numServers + numSelfCheckout;
        List<ServerI> lst1 = Stream
                .iterate(1, i -> i <= numServers, i -> i + 1)
                .map(id -> new Server(id, maxQ))
                .collect(Collectors.toList());
        List<ServerI> lst2 = Stream
                .iterate(numServers + 1, i -> i <= total, i -> i + 1)
                .map(id -> new SelfCheckOut(id, maxQ))
                .collect(Collectors.toList());
        lst1.addAll(lst2);
        serverList = lst1;
    }

    /**
     * Private constructor to update the shop.
     * @param interServers Updated list of servers in the shop.
     */
    public Shop(List<ServerI> interServers) {
        this.serverList = interServers;
    }

    /**
     * Return the list of serverI in the shop.
     * @return List of serverIs.
     */
    public List<ServerI> getServers() {
        return serverList;
    }

    /**
     * Return the first serverI that fulfills the predicate.
     * @param pred The predicate.
     * @return true if the input argument matches the predicate, else false.
     */
    public Optional<ServerI> find(Predicate<? super ServerI> pred) {
        ArrayList<ServerI> temp = new ArrayList<>(serverList);
        return temp.stream().filter(pred).findFirst();
    }

    /**
     * Return the most updated serverI in the shop that is equal to the
     * argument serverI.
     * @param s serverI that is used to look for the updated serverI in the
     *          shop.
     * @return the updated serverI.
     */
    public Optional<ServerI> get(ServerI s) {
        return find(x -> x.equals(s));
    }

    /**
     * Return the boolean of whether there is a serverI available.
     * @return true if there is a serverI available in the shop, else false.
     */
    public Optional<ServerI> hasAvailServer() {
        return find(ServerI::isAvailable);
    }

    /**
     * Return the boolean of whether there is a serverI with a non-full queue.
     * @return true if there is a serverI with a non-full queue, else false.
     */
    public Optional<ServerI> hasFreeQueue() {
        return find(x -> !x.fullQueue());
    }

    /**
     * Return the serverI with the shortest queue in the shop.
     * @return the serverI with the shortest queue in the job. If there are
     *     more than one serverI with the same queue size, return the one with a
     *     smaller id.
     */
    public Optional<ServerI> getShortestQueue() {
        PriorityQueue<ServerI> temp = new PriorityQueue<>(serverList);
        ServerI serverInter = temp.poll();
        return Optional.ofNullable(serverInter);
    }

    /**
     * Return the updated Shop with a serverI replaced.
     * @param server the updated serverI which will replace the one in the shop.
     * @return the updated shop with the updated serverI.
     */
    public Shop replace(ServerI server) {
        return new Shop(serverList.stream().map(x -> {
            if (x.equals(server)) {
                return server;
            } else {
                return x;
            }
        }).collect(Collectors.toList()));
    }

    /**
     * Return String representation of the shop.
     * @return List of the servers in the shop.
     */
    @Override
    public String toString() {
        return serverList.toString();
    }
}
