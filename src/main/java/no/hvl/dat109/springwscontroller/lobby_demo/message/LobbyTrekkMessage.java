package no.hvl.dat109.springwscontroller.lobby_demo.message;

import java.util.List;

public class LobbyTrekkMessage extends LobbyStatusMessage {
	private String spillerSittTrekk;

	public LobbyTrekkMessage(String lobbyId, List<String> players, String spillerSittTrekk) {
		super(lobbyId, players);
		this.spillerSittTrekk = spillerSittTrekk;
	}

	public LobbyTrekkMessage() {
	}

	public String getSpillerSittTrekk() {
		return spillerSittTrekk;
	}

	public void setSpillerSittTrekk(String spillerSittTrekk) {
		this.spillerSittTrekk = spillerSittTrekk;
	}
}
