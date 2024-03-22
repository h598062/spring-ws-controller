package no.hvl.dat109.springwscontroller.lobby_demo.message;

import no.hvl.dat109.springwscontroller.lobby_demo.enums.SpillerStatus;

public class SpillerStatusMessage extends SpillerMessage {
	private SpillerStatus status;
	public SpillerStatusMessage(String spiller, SpillerStatus status) {
		super(spiller);
		this.status = status;
	}

	public SpillerStatusMessage() {
	}

	public SpillerStatus getStatus() {
		return status;
	}

	public void setStatus(SpillerStatus status) {
		this.status = status;
	}
}
