package commands;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class credits {
	
	public static void credits(String[] args, String prefix, MessageReceivedEvent event) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription("The Intralism Bot was programmed by Ludeo#8554 and it is open source. When you click on \"Ludeo#8554\" you will get to the github page." 
		+ "\nSpecial Thanks to FlyingRabidUnicornPig™#5435 for helping me out with the code and Special Thanks to Kiri#1000 for checking"
		+ " all maps to see if they are broken or not.");
		String url = event.getGuild().getMemberById("311861142114926593").getUser().getAvatarUrl();
		eb.setAuthor("Ludeo#8554","https://github.com/Ludeo/Intralism-Bot",  url);
		Color pink = new Color(255,105,180);
		eb.setColor(pink);
		event.getMessage().getChannel().sendMessage(eb.build()).queue();
	}

}
