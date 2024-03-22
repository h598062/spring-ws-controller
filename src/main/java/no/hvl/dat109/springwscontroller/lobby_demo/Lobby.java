package no.hvl.dat109.springwscontroller.lobby_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Lobby implements Runnable {
	Logger logger = LoggerFactory.getLogger(Lobby.class);

	private final String lobbyId;
	private final ConcurrentHashMap<String, Spiller> spillere;
	private boolean startet;
	private final Spiller lobbyLeder;

	public Lobby(String lobbyId, String lobbyLeder) {
		this.lobbyId = lobbyId;
		this.spillere = new ConcurrentHashMap<>();
		this.startet = false;
		this.lobbyLeder = new Spiller(lobbyLeder);
		spillere.put(lobbyLeder, this.lobbyLeder);
	}

	@Override
	public void run() {

	}

	public String getLobbyId() {
		return lobbyId;
	}

	public synchronized void leggTilSpiller(Spiller spiller) {
		spillere.put(spiller.getNavn(), spiller);
	}

	public synchronized void fjernSpiller(Spiller spiller) {
		spillere.remove(spiller.getNavn());
	}

	public synchronized boolean erSpillerMed(String navn) {
		return spillere.containsKey(navn);
	}

	public synchronized Spiller getSpiller(String navn) {
		return spillere.get(navn);
	}

	public synchronized boolean isStartet() {
		return startet;
	}

	public synchronized void setStartet(boolean startet) {
		this.startet = startet;
	}

	public synchronized List<String> getSpillerNavn() {
		return Collections.list(spillere.keys());
	}
}
