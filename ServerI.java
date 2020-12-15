package cs2030.simulator;

import java.util.List;

/**
 * ServerI is an interface implemented by Server and SelfCheckOut.
 */
public interface ServerI extends Comparable<ServerI> {
    ServerI serve(Customer customer, double servingTime);

    ServerI wait(Customer customer);

    ServerI done();

    ServerI rest(double restTime);

    ServerI doneRest();

    boolean fullQueue();

    boolean isAvailable();

    double getNextAvailableTime();

    int getId();

    List<Customer> getQueue();

    ServerI goRest();
}
