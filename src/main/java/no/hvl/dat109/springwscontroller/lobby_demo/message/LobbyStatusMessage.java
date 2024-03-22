package no.hvl.dat109.springwscontroller.lobby_demo.message;

import java.util.List;

public class LobbyStatusMessage extends Message {
	private String lobbyId;
	private List<String> players;

	public LobbyStatusMessage(String lobbyId, List<String> players) {
		this.lobbyId = lobbyId;
		this.players = players;
	}

	public LobbyStatusMessage() {
	}

	public String getLobbyId() {
		return lobbyId;
	}

	public void setLobbyId(String lobbyId) {
		this.lobbyId = lobbyId;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}
}
