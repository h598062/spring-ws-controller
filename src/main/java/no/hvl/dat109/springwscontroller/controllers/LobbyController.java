package no.hvl.dat109.springwscontroller.controllers;

import no.hvl.dat109.springwscontroller.service.LobbyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

}
