package no.hvl.dat109.springwscontroller.lobby_demo.message;

public class SpillerMessage extends Message {
	private String spiller;

	protected SpillerMessage(String spiller) {
		this.spiller = spiller;
	}

	protected SpillerMessage() {
	}

	public String getSpiller() {
		return spiller;
	}

	public void setSpiller(String spiller) {
		this.spiller = spiller;
	}

	@Override
	public String toString() {
		return "SpillerMessage{" +
				"spiller='" + spiller + '\'' +
				'}';
	}
}
