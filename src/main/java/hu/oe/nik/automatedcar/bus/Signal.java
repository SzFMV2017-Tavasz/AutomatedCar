package hu.oe.nik.automatedcar.bus;

/**
 * This class represent a single signal on the bus.
 * Signals can be handled by implementing the ISystemComponent interface.
 *
 * In a real environment signals are encapsulated by frames (terminology depends on the network)
 * and a frame can contain several signals. This way the throughput of the network is utilized
 * more efficiently.
 *
 * For the simulation environment let's assume that each frame contains a single message,
 * so we do not need to bother extracting signal data from frames.
 */

public class Signal {
	// Signal identifier, a component can decide based on this value
	// whether the content of the signal shall be processed or not.
	private int id;

	// Signal value, the meaning of the value is provided by the CommMatrix
	private Object data;

	// Constructor for Signal
	public Signal(int id, Object data) {
		this.id = id;
		this.data = data;
	}

	// Getter for Signal Id
	public int getId() {
		return id;
	}

	// Getter for Signal Value
	public Object getData() {
		return data;
	}
}
