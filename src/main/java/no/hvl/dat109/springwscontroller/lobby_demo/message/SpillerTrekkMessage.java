package no.hvl.dat109.springwscontroller.lobby_demo.message;

import no.hvl.dat109.springwscontroller.lobby_demo.enums.Trekk;

public class SpillerTrekkMessage extends SpillerMessage {
	private Trekk trekk;
	private int mengde;
	public SpillerTrekkMessage(String spiller, Trekk trekk, int mengde) {
		super(spiller);
		this.mengde = mengde;
		this.trekk = trekk;
	}

	public SpillerTrekkMessage(String spiller, Trekk trekk) {
		super(spiller);
		this.mengde = 0;
		this.trekk = trekk;
	}
	public SpillerTrekkMessage() {
	}

	public Trekk getTrekk() {
		return trekk;
	}

	public void setTrekk(Trekk trekk) {
		this.trekk = trekk;
	}

	public int getMengde() {
		return mengde;
	}

	public void setMengde(int mengde) {
		this.mengde = mengde;
	}

	@Override
	public String toString() {
		return "SpillerTrekkMessage{" +
				"spiller='" + getSpiller() +
				", trekk=" + trekk +
				", mengde=" + mengde +
				'}';
	}
}
