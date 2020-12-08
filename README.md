# 6-digit-discord-bot

This is a simple Discord-Bot that will recognise everytime you send a 6 digit long code in any channel including private messages and respond to it with the fitting nhentai link.

To set this up yourself follow this description:
1. You need to go to https://discord.com/developers/applications and create a new application there.
2. Next go to bot and press add bot. This will irreversibally turn this application into a bot.
3. On that page you can give your bot a name and profile picture if you want.
4. Copy the Client ID of your application on the general page and get the number for the permissions you want to give to the bot on the bot page.
5. Insert these Values into a link, so that it looks like this: `https://discord.com/api/oauth2/authorize?client_id=157730590492196864&scope=bot&permissions=1`
6. Clone the repository to some place on the pc/server that will run the bot.
7. Copy the token from the bot page and insert it into the Configuration.txt, you can also change the activity of the bot in there.
8. Open a command line in the root folder of the cloned repository. Make sure you have maven properly installed and then run `mvn package` from the command line.
9. You can start the server by typing `java -cp target/six-digit-bot-0.1-STABLE.jar bot.DiscordBot` make sure to exchange `0.1-STABLE` for whatever version you are using.
10. Success! If you want to stop the execution properly, type `exit` into the command line where the Bot is running. If you change something in the Configuration.txt you need to restart the bot for the changes to take effect.
