package no.hvl.dat109.springwscontroller.lobby_demo;

import no.hvl.dat109.springwscontroller.lobby_demo.enums.SpillerStatus;

public class Spiller {
	private String navn; // navn på spiller (brukernavn) == spillerid
	private SpillerStatus status; // status på spiller

	public Spiller(String navn) {
		this.navn = navn;
		this.status = SpillerStatus.NOT_READY;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public SpillerStatus getStatus() {
		return status;
	}

	public void setStatus(SpillerStatus status) {
		this.status = status;
	}
}
