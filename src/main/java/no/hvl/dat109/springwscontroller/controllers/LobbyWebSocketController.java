package no.hvl.dat109.springwscontroller.controllers;

import no.hvl.dat109.springwscontroller.enums.Action;
import no.hvl.dat109.springwscontroller.lobby.Lobby;
import no.hvl.dat109.springwscontroller.lobby.Spiller;
import no.hvl.dat109.springwscontroller.message.*;
import no.hvl.dat109.springwscontroller.service.LobbyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Controller
public class LobbyWebSocketController {
	private static final Logger logger = LoggerFactory.getLogger(LobbyWebSocketController.class);

	private final LobbyService lobbyService;

	private final SimpMessagingTemplate messagingTemplate;

	@Autowired
	public LobbyWebSocketController(LobbyService lobbyService, SimpMessagingTemplate messagingTemplate) {
		this.lobbyService = lobbyService;
		this.messagingTemplate = messagingTemplate;
	}

	/**
	 * Sjekker om meldingen er ugyldig
	 *
	 * @param message meldingen som skal sjekkes
	 *
	 * @return true hvis meldingen er ugyldig, false ellers
	 */
	private boolean erUgyldigMelding(SpillerMessage message) {
		if (message == null) {
			logger.warn("Message is null");
			return true;
		}
		if (message.getSpillerNavn() == null || message.getSpillerNavn().isBlank()) {
			logger.warn("SpillerNavn is missing or blank: {}", message);
			return true;
		}
		return false;
	}

	/**
	 * Sender en melding til en spiller sin egen topic.<br>
	 * Metoden formatterer navnet til en gyldig URI før den sender meldingen.<br>
	 * Antar at navnet er gyldig, sjekk først med f.eks. {@link #erUgyldigMelding(SpillerMessage)}
	 *
	 * @param spillerNavn navnet på spilleren, må være sjekket først
	 * @param message     meldingen som skal sendes
	 */
	private void sendUserError(String spillerNavn, String message) {
		messagingTemplate.convertAndSend("/spiller/" + UriUtils.encode(spillerNavn, StandardCharsets.UTF_8), message);
	}

	/**
	 * Sjekker om lobbyId er gyldig og om lobbyen eksisterer<br>
	 * Antar at meldingen er gyldig, sjekk først med {@link #erUgyldigMelding(SpillerMessage)}
	 *
	 * @param spillerNavn meldingen som skal sjekkes
	 * @param lobbyId     lobbyId som skal sjekkes
	 *
	 * @return lobbyen som meldingen refererer til
	 */
	private Lobby sjekkLobby(String spillerNavn, String lobbyId) {
		if (lobbyId == null || lobbyId.isBlank()) {
			logger.warn("LobbyId is missing or blank in message from: {}", spillerNavn);
			sendUserError(spillerNavn, "Melding mangler lobbyId");
			throw new IllegalArgumentException("LobbyId is missing or blank");
		}
		Lobby lobby = lobbyService.getLobby(lobbyId);
		if (lobby == null) {
			logger.warn("Lobby {} does not exist", lobbyId);
			sendUserError(spillerNavn, "Lobby %s finnes ikke".formatted(lobbyId));
			throw new IllegalArgumentException("Lobby does not exist");
		}
		return lobby;
	}

	/**
	 * Sjekker om spilleren eksisterer i lobbyen<br>
	 * Antar at meldingen er gyldig, sjekk først med {@link #erUgyldigMelding(SpillerMessage)}
	 *
	 * @param spillerNavn meldingen som skal sjekkes
	 * @param lobby       lobbyen som meldingen refererer til
	 *
	 * @return spilleren som meldingen refererer til
	 */
	private Spiller sjekkSpiller(String spillerNavn, Lobby lobby) {
		Spiller spiller = lobby.getSpiller(spillerNavn);
		if (spiller == null) {
			logger.warn("Spiller {} does not exist in lobby {}", spillerNavn, lobby.getLobbyId());
			sendUserError(spillerNavn, "Spiller %s finnes ikke i lobby %s".formatted(spillerNavn, lobby.getLobbyId()));
			throw new IllegalArgumentException("Spiller does not exist in lobby");
		}
		return spiller;
	}

	@MessageMapping("/trekk/{lobbyId}")
	@SendTo("/lobby/status/{lobbyId}")
	public LobbyTrekkMessage lobbyTrekkHandler(@DestinationVariable String lobbyId,
	                                           @Payload SpillerTrekkMessage message) {
		logger.info("Received SpillerTrekkMessage: {}", message);
		if (lobbyId == null || lobbyId.isBlank()) {
			logger.warn("LobbyId is missing or blank in message: {}", message);
			return null;
		}
		if (erUgyldigMelding(message)) {
			logger.warn("Invalid message: {}", message);
			return null;
		}
		Lobby lobby;
		Spiller spiller;
		try {
			lobby = sjekkLobby(message.getSpillerNavn(), lobbyId);
			spiller = sjekkSpiller(message.getSpillerNavn(), lobby);
		} catch (IllegalArgumentException e) {
			return null;
		}
		if (!lobby.isStartet()) {
			logger.warn("Spillet er ikke startet i lobbyen {}", lobbyId);
			sendUserError(spiller.getNavn(), "Spillet er ikke startet i lobby %s".formatted(lobbyId));
			return null;
		}
		switch (message.getTrekk()) {
			case RAISE -> {
				logger.info("Spiller {} har raiset med {} i lobbyen {}", spiller.getNavn(), message.getMengde(),
				            lobbyId);
				logger.error("RAISE er ikke implementert");
			}
			case CALL -> {
				logger.info("Spiller {} har callet i lobbyen {}", spiller.getNavn(), lobbyId);
				logger.error("CALL er ikke implementert");
			}
			case FOLD -> {
				logger.info("Spiller {} har foldet i lobbyen {}", spiller.getNavn(), lobbyId);
				logger.error("FOLD er ikke implementert");
			}
		}
		return null;
	}

	// lobbyId hentes dynamisk fra path i request
	@MessageMapping("/action/{lobbyId}")
	@SendTo("/lobby/status/{lobbyId}")
	public LobbyActionMessage lobbyActionHandler(@DestinationVariable String lobbyId,
	                                             @Payload SpillerActionMessage message) {
		logger.info("Received SpillerActionMessage: {}", message);
		if (lobbyId == null || lobbyId.isBlank()) {
			logger.warn("LobbyId is missing or blank in message: {}", message);
			return null;
		}
		if (erUgyldigMelding(message)) {
			logger.warn("Invalid message: {}", message);
			return null;
		}
		Lobby lobby;
		Spiller spiller;
		try {
			lobby = sjekkLobby(message.getSpillerNavn(), lobbyId);
			spiller = sjekkSpiller(message.getSpillerNavn(), lobby);
		} catch (IllegalArgumentException e) {
			return null;
		}
		switch (message.getAction()) {
			case JOIN -> {
				logger.info("Spiller {} har joinet lobbyen {}", spiller.getNavn(), lobbyId);
				logger.error("JOIN er ikke ferdig implementert");
				lobby.leggTilSpiller(spiller);
				return new LobbyActionMessage(lobbyId, lobby.getSpillernesNavn(), spiller.getNavn(), Action.JOIN);
			}
			case LEAVE -> {
				logger.info("Spiller {} har forlatt lobbyen {} ", spiller.getNavn(), lobbyId);
				logger.error("LEAVE er ikke implementert");
			}
			case AFK -> {
				logger.info("Spiller {} er AFK i lobbyen {} ", spiller.getNavn(), lobbyId);
				logger.error("AFK er ikke implementert");
			}
			case READY -> {
				logger.info("Spiller {} er klar i lobbyen {} ", spiller.getNavn(), lobbyId);
				logger.error("READY er ikke implementert");
			}
			case UNREADY -> {
				logger.info("Spiller {} er ikke klar i lobbyen {} ", spiller.getNavn(), lobbyId);
				logger.error("UNREADY er ikke implementert");
			}
			case DISCONNECT -> {
				logger.info("Spiller {} har blitt disconnected i lobbyen {} ", spiller.getNavn(), lobbyId);
				logger.error("DISCONNECT er ikke implementert");
			}
			case START -> {
				logger.info("Spiller {} prøver å starte spillet i lobbyen {} ", spiller.getNavn(), lobbyId);
				if (!lobby.isStartet()) {
					if (lobby.getLobbyLeder().equals(spiller)) {
						lobby.start();
						return new LobbyActionMessage(lobbyId, lobby.getSpillernesNavn(), spiller.getNavn(), Action.START);
					} else {
						logger.warn("Spiller {} er ikke lobbyleder i lobbyen {}", spiller.getNavn(), lobbyId);
					}
				} else {
					logger.warn("Spillet er allerede startet i lobbyen {}", lobbyId);
					sendUserError(spiller.getNavn(), "Spillet er allerede startet i lobby %s".formatted(lobbyId));
				}
			}
			case END -> {
				logger.info("Spiller {} prøver å stoppe spillet i lobbyen {} ", spiller.getNavn(), lobbyId);
				logger.error("END er ikke implementert");
			}
		}

		return null;
	}
}
