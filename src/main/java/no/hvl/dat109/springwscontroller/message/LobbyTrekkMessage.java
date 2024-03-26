package no.hvl.dat109.springwscontroller.message;

import no.hvl.dat109.springwscontroller.enums.Trekk;

import java.util.List;

/**
 * Melding som sendes til alle spillere i en lobby når en spiller har gjort et trekk
 */
public class LobbyTrekkMessage extends LobbyMessage {
	/**
	 * Spilleren (navn) som har spilt et trekk
	 */
	private String spillerNavn;
	private Trekk trekk;

	public LobbyTrekkMessage(String lobbyId, String spillerNavn, Trekk trekk) {
		super(lobbyId);
		this.spillerNavn = spillerNavn;
		this.trekk = trekk;
	}

	/**
	 * Tom konstruktør for serialisering<br>
	 * Lager du meldingen selv, bruk konstruktøren med parametre.
	 */
	public LobbyTrekkMessage() {
	}

	public String getSpillerNavn() {
		return spillerNavn;
	}

	public void setSpillerNavn(String spillerNavn) {
		this.spillerNavn = spillerNavn;
	}

	public Trekk getTrekk() {
		return trekk;
	}

	public void setTrekk(Trekk trekk) {
		this.trekk = trekk;
	}

	@Override
	public String toString() {
		return "LobbyTrekkMessage{" +
		       "lobbyId='" + getLobbyId() + '\'' +
		       ", spillerNavn='" + getSpillerNavn() + '\'' +
		       ", trekk=" + getTrekk() +
		       '}';
	}
}
