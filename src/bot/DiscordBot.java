package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import listener.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordBot {
	
	public JDA shardMan;

	public static void main(String[] args) {
		try {
			new DiscordBot();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Make a new DiscordBot Object that reads the values from the config file to make a builder 
	 * @throws LoginException
	 * @throws IllegalArgumentException
	 */
	public DiscordBot() throws LoginException, IllegalArgumentException {
		// list of all config options
		List<String> config = new ArrayList<String>();
		
		// try reading the configuration file into the config list
		try {
			Files.lines(Paths.get(System.getProperty("user.dir"), "Configuration.txt"))
				.filter(str -> !str.startsWith("#"))
				.map(str -> str.replaceAll("\\s", ""))
				.map(str -> str.replaceAll("//", " "))
				.forEach(str -> config.add(str));
		} catch (IOException e) {
			System.out.println("Configuration file cannot be opened!");
		}
		
		// standard values
		String doingKind = "playing";
		String doing = "undefined";
		String token = "undefined";
		
		// load options from config list
		for (String str : config) {
			String[] option = str.split("=");
			switch (option[0]) {
				case "token":		token = option[1];
									break;
				case "playing":		doing = option[1];
									break;
				case "watching":	doing = option[1];
									doingKind = option[0];
									break;
				case "listening":	doing = option[1];
									doingKind = option[0];
									break;
				default:		System.out.println("Invalid option: " + option[0]);
								break;
			}
		}
		
		// check if token is set
		if (token.equals("undefined")) {
			System.out.println("Make sure to set the token in Configuration.txt");
			return;
		}
		
		// make new builder with token
		JDABuilder builder = JDABuilder.createDefault(token);
		
		//set status
		if (doing != "undefined") {
			switch(doingKind) {
				case "watching":	builder.setActivity(Activity.watching(doing));
									break;
				case "listening":	builder.setActivity(Activity.listening(doing));
									break;
				default:			builder.setActivity(Activity.playing(doing));
									break;
			}
		}
		builder.setStatus(OnlineStatus.ONLINE);
		
		// add our CommandListener
		builder.addEventListeners(new CommandListener());
		
		this.shardMan = builder.build();
		System.out.println("started");
		listenForShutdown();
	}
	
	
	/**
	 * start a new Thread that continually listens for the exit command
	 */
	public void listenForShutdown() {
		
		new Thread(() -> {
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while((line = reader.readLine()) != null) {
					if(line.equalsIgnoreCase("exit")) {
						if(shardMan != null) {
							shardMan.shutdown();
							System.out.println("shutdown");
						}
						reader.close();
						return;
					}
					else {
						System.out.println("Use exit to shutdown.");
					}
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
