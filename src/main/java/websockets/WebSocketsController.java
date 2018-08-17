/**
 * 
 */
package websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mumbaisurfer
 *
 */
@RestController
public class WebSocketsController {
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@RequestMapping("greet")
	public String greet(@RequestParam("message") String message) {
		messagingTemplate.convertAndSend("/queue/reply", "From server: " + message);
		return "Sent";
	}
	
	@MessageMapping("/send/message")
    @SendTo("/queue/reply")
	public Greeting sendGreetings(@Payload Greeting greeting) {
		System.out.println("From client: " + greeting.getMessage());
		//messagingTemplate.convertAndSend("/queue/reply", "From server: " + greeting.getMessage());
		return new Greeting("Hello from server");
	}

}
