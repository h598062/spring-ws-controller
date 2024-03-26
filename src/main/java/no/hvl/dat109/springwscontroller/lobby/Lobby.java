package no.hvl.dat109.springwscontroller.lobby;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Lobby {
	Logger logger = LoggerFactory.getLogger(Lobby.class);

	private final String lobbyId;
	private final ConcurrentHashMap<String, Spiller> spillere;
	private boolean startet;
	private final Spiller lobbyLeder;

	public Lobby(String lobbyId, String lobbyLederNavn) {
		this.lobbyId = lobbyId;
		this.spillere = new ConcurrentHashMap<>();
		this.startet = false;
		this.lobbyLeder = new Spiller(lobbyLederNavn);
		spillere.put(lobbyLederNavn, this.lobbyLeder);
	}

	public String getLobbyId() {
		return lobbyId;
	}

	public synchronized void leggTilSpiller(Spiller spiller) {
		spillere.put(spiller.getNavn(), spiller);
	}

	public synchronized void fjernSpiller(Spiller spiller) {
		if (spiller.equals(lobbyLeder)) {
			logger.warn("Lobbyleder kan ikke fjernes fra lobbyen");
			return;
		}
		spillere.remove(spiller.getNavn());
	}

	public synchronized boolean erSpillerMed(String navn) {
		return spillere.containsKey(navn);
	}

	/**
	 * Henter en spiller fra lobbyen
	 * @param navn navnet på spilleren
	 * @return spilleren hvis den finnes, null ellers
	 */
	public synchronized Spiller getSpiller(String navn) {
		return spillere.get(navn);
	}

	public synchronized boolean isStartet() {
		return startet;
	}

	public Spiller getLobbyLeder() {
		return lobbyLeder;
	}

	public synchronized List<String> getSpillernesNavn() {
		return Collections.list(spillere.keys());
	}

	public synchronized void start() {
		this.startet = true;
		// start selve spillet
	}

	public synchronized void stopp() {
		this.startet = false;
		// stopp spillet
	}
}
