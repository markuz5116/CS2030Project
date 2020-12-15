package cs2030.simulator;

/**
 * EventStatus contains Event statuses, containing: (i) ARRIVE. (ii) SERVE.
 * (ii) DONE. (iii) SERVERREST. (iv) SERVERBACK. (v) IDLE. (vi) WAIT. (vii)
 * LEAVE.
 */
public enum EventStatus {
    IDLE(0), SERVERBACK(1), SERVERREST(2), LEAVE(3), WAIT(4), SERVE(5),
    ARRIVE(6), DONE(7);
    private final int label;

    private EventStatus(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }
}
