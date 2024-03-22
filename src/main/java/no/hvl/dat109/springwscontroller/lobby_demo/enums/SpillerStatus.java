package no.hvl.dat109.springwscontroller.lobby_demo.enums;

public enum SpillerStatus {
	JOINED, LEFT, AFK, READY, NOT_READY, DISCONNECTED
	// JOINED -> impliserer NOT_READY, spiller må manuelt trykke ready
	// AFK brukes i runder
	// NOT_READY brukes i lobby, er implisert når en er i en runde og er AFK
	// READY brukes i lobby, er implisert når en er i en runde og ikke AFK
	// LEFT brukes når en spiller forlater spillet i en runde eller i lobby. Denne brukes ikke hvis en spiller blir disconnected uventet, da blir spilleren satt til DISCONNECTED
	// DISCONNECTED brukes når en spiller blir disconnected uventet, funksjonelt sett det samme som AFK, bare at vi venter på en ny WS-tilkobling fra samme spiller

	// Hvis en spiller har status LEFT skal de fjernes fra lobbyen sin liste av spillere, evt WS koblinger skal avsluttes og de skal unsubscribes fra alle topics
}