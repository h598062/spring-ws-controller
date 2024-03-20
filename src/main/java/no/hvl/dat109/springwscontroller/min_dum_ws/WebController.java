package no.hvl.dat109.springwscontroller.min_dum_ws;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/")
	public String getSomething() {
		return "page";
	}
}
