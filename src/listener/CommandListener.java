package listener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		String message = event.getMessage().getContentDisplay();
		// the resulting nhentai links
		List<String> links = new ArrayList<String>();
		
		
		if(event.isFromType(ChannelType.TEXT)) {
			// get the Textchannel of the message
			TextChannel channel = event.getTextChannel();
			
			// get any 6-digit numbers that are separated from the rest of the text by space
			Stream.of(message.split(" ")).forEach(str -> {
				if (str.matches("\\d\\d\\d\\d\\d\\d(/\\d*)?"))
					// add the appropriate url to links as a string
					links.add("https://nhentai.net/g/" + str);
			});
			// if there are any 6-digit numbers in there
			if(!links.isEmpty()) {
				for (String link: links) {
					//TODO
					channel.sendMessage(link).queue();
				}
			}
			
		}
	}
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		
		String message = event.getMessage().getContentDisplay();
		// the resulting nhentai links
		List<String> links = new ArrayList<String>();
		
		// the channel of the message
		PrivateChannel channel = event.getChannel();
			
		// get any 6-digit numbers that are separated from the rest of the text by space
		Stream.of(message.split(" ")).forEach(str -> {
			if (str.matches("\\d\\d\\d\\d\\d\\d(/\\d*)?"))
				// add the appropriate url to links as a string
				links.add("https://nhentai.net/g/" + str);
		});
		// if there are any 6-digit numbers in there
		if(!links.isEmpty()) {
			for (String link: links) {
				channel.sendMessage(link).queue();
			}
		}
	}
}
