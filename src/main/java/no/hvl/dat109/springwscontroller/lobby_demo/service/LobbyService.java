package no.hvl.dat109.springwscontroller.lobby_demo.service;

import no.hvl.dat109.springwscontroller.lobby_demo.Lobby;
import no.hvl.dat109.springwscontroller.lobby_demo.LobbyAlreadyExistsException;
import no.hvl.dat109.springwscontroller.lobby_demo.Spiller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LobbyService {
	Logger logger = LoggerFactory.getLogger(LobbyService.class);

	private final ConcurrentHashMap<String, Lobby> lobbies;

	public LobbyService() {
		this.lobbies = new ConcurrentHashMap<>();
	}

	public boolean lobbyExists(String lobbyId) {
		return lobbies.containsKey(lobbyId);
	}

	public void createLobby(String lobbyId, String lobbyLeder) throws LobbyAlreadyExistsException {
		if (lobbyExists(lobbyId)) {
			logger.error("Lobby with id {} already exists", lobbyId);
			throw new LobbyAlreadyExistsException("Lobby with id %s already exists".formatted(lobbyId));
		}
		logger.info("Creating lobby with id {}", lobbyId);
		lobbies.put(lobbyId, new Lobby(lobbyId, lobbyLeder));
	}

	public Lobby getLobby(String lobbyId) {
		return lobbies.get(lobbyId);
	}

	public void removeLobby(String lobbyId) {
		logger.info("Removing lobby with id {}", lobbyId);
		lobbies.remove(lobbyId);
	}

	public List<String> getLobbies() {
		return Collections.list(lobbies.keys());
	}
}
