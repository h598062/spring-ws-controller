package no.hvl.dat109.springwscontroller.lobby_demo;

import no.hvl.dat109.springwscontroller.lobby_demo.message.LobbyStatusMessage;
import no.hvl.dat109.springwscontroller.lobby_demo.message.SpillerMessage;
import no.hvl.dat109.springwscontroller.lobby_demo.message.SpillerStatusMessage;
import no.hvl.dat109.springwscontroller.lobby_demo.message.SpillerTrekkMessage;
import no.hvl.dat109.springwscontroller.lobby_demo.service.LobbyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LobbyController {
	private static final Logger logger = LoggerFactory.getLogger(LobbyController.class);

	private final LobbyService lobbyService;

	@Autowired
	public LobbyController(LobbyService lobbyService) {
		this.lobbyService = lobbyService;
	}

	@GetMapping("/lobby")
	public String getLobby() {

		return "lobby";
	}

	@MessageMapping("/trekk/{lobbyId}")
	@SendTo("/lobby/status/{lobbyId}")
	public LobbyStatusMessage lobbyStatus(@DestinationVariable String lobbyId, @Payload SpillerTrekkMessage message) throws
			Exception {
		logger.info("Received SpillerTrekkMessage: {}", message);
		switch (message.getTrekk()) {
			case RAISE -> {
				logger.info("Spiller {} har raiset med {} i lobbyen {}", message.getSpiller(), message.getMengde(),
				            lobbyId);
			}
			case CALL -> {
				logger.info("Spiller {} har callet i lobbyen {}", message.getSpiller(), lobbyId);
			}
			case FOLD -> {
				logger.info("Spiller {} har foldet i lobbyen {}", message.getSpiller(), lobbyId);
			}
		}
		Lobby lobby = lobbyService.getLobby(lobbyId);
		return new LobbyStatusMessage(lobbyId, lobby.getSpillerNavn());
	}

	// lobbyId hentes dynamisk fra path i request
	@MessageMapping("/spillerstatus/{lobbyId}")
	@SendTo("/lobby/status/{lobbyId}")
	public LobbyStatusMessage lobbyStatus(@DestinationVariable String lobbyId, @Payload SpillerStatusMessage message) throws
			Exception {
		logger.info("Received SpillerStatusMessage: {}", message);

		switch (message.getStatus()) {
			case JOINED -> {
				logger.info("Spiller {} har joinet lobbyen {}", message.getSpiller(), lobbyId);
			}
			case LEFT -> {
				logger.info("Spiller {} har forlatt lobbyen {} ", message.getSpiller(), lobbyId);
			}
			case AFK -> {
				logger.info("Spiller {} er AFK i lobbyen {} ", message.getSpiller(), lobbyId);
			}
			case READY -> {
				logger.info("Spiller {} er klar i lobbyen {} ", message.getSpiller(), lobbyId);
			}
			case NOT_READY -> {
				logger.info("Spiller {} er ikke klar i lobbyen {} ", message.getSpiller(), lobbyId);
			}
			case DISCONNECTED -> {
				logger.info("Spiller {} har blitt disconnected i lobbyen {} ", message.getSpiller(), lobbyId);
			}
		}

		Lobby lobby = lobbyService.getLobby(lobbyId);
		return new LobbyStatusMessage(lobbyId, lobby.getSpillerNavn());
	}
}
