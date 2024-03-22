package no.hvl.dat109.springwscontroller.lobby_demo;

import no.hvl.dat109.springwscontroller.lobby_demo.service.LobbyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {
	Logger logger = LoggerFactory.getLogger(MainPageController.class);

	private final LobbyService lobbyService;

	@Autowired
	public MainPageController(LobbyService lobbyService) {
		this.lobbyService = lobbyService;
	}

	@GetMapping("/")
	public String getMainpage(Model model) {
		model.addAttribute("lobbies", lobbyService.getLobbies());
		return "mainpage";
	}

	@PostMapping("/createLobby")
	public String createLobby(@RequestParam("lobbyIdCreate") String lobbyId, @RequestParam String lobbyLeder, RedirectAttributes ra) {
		logger.info("Create lobby requested");
		List<String> errors = new ArrayList<>();
		if (lobbyId.isBlank()) {
			errors.add("Lobby id cannot be blank");
		}
		if (lobbyLeder.isBlank()) {
			errors.add("Lobby leader cannot be blank");
		}
		if (errors.isEmpty()) {
			try {
				lobbyService.createLobby(lobbyId, lobbyLeder);
			} catch (LobbyAlreadyExistsException e) {
				errors.add(e.getMessage());
			}
		}
		if (errors.isEmpty()) {
			ra.addFlashAttribute("lobbyId", lobbyId);
			ra.addFlashAttribute("spiller", lobbyLeder);
			return "redirect:/lobby";
		} else {
			ra.addFlashAttribute("errors", errors);
			return "redirect:/";
		}
	}

	@PostMapping("/joinLobby")
	public String joinLobby(@RequestParam("lobbySelect") String lobbyId, @RequestParam String spillerNavn, RedirectAttributes ra) {
		logger.info("Join lobby requested");
		List<String> errors = new ArrayList<>();
		if (lobbyId.isBlank()) {
			errors.add("Lobby id cannot be blank");
		}
		if (spillerNavn.isBlank()) {
			errors.add("Spiller name cannot be blank");
		}
		if (errors.isEmpty()) {
			Lobby lobby = lobbyService.getLobby(lobbyId);
			if (lobby == null) {
				errors.add("Lobby does not exist");
			} else {
				Spiller nySpiller = new Spiller(spillerNavn);
				lobby.leggTilSpiller(nySpiller);
			}
		}
		if (errors.isEmpty()) {
			ra.addFlashAttribute("lobbyId", lobbyId);
			ra.addFlashAttribute("spiller", spillerNavn);
			return "redirect:/lobby";
		} else {
			ra.addFlashAttribute("errors", errors);
			return "redirect:/";
		}
	}
}
