package no.hvl.dat109.springwscontroller.stomp_demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWSConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// dette blir en slags subscribe endpoint
		// alle meldinger som sendes til /topic/* blir broadcastet til alle abonnenter på samme * topic
		config.enableSimpleBroker("/topic");

		// dette blir en slags publish endpoint
		// alle meldinger som sendes til /app/* blir sendt til serveren og håndtert deretter
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// dette er et endpoint for å koble til websocketen til STOMP
		registry.addEndpoint("/gs-guide-websocket");
	}
}
