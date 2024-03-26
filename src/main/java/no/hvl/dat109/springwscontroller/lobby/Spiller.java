package no.hvl.dat109.springwscontroller.lobby;

import no.hvl.dat109.springwscontroller.enums.Action;

import java.util.Objects;

public class Spiller {
	private String navn; // navn på spiller (brukernavn) == spillerid
	private Action status; // status på spiller

	public Spiller(String navn) {
		this.navn = navn;
		this.status = Action.UNREADY;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public Action getStatus() {
		return status;
	}

	public void setStatus(Action status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Spiller spiller = (Spiller) o;

		return Objects.equals(navn, spiller.navn);
	}

	@Override
	public int hashCode() {
		return navn != null ? navn.hashCode() : 0;
	}
}
