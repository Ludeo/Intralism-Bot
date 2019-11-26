package commands;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class credits {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event) {
		JDA jda = event.getJDA();
		String url = jda.getUserById("311861142114926593").getAvatarUrl();
		Color pink = new Color(255,105,180);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setDescription("The Intralism Bot was programmed by Ludeo#8554 and it is open source. When you click on \"Ludeo#8554\" you will get to the github page." 
		+ "\nSpecial Thanks to FlyingRabidUnicornPig™#5435 for helping me out with the code and Special Thanks to Kiri#1000 for checking"
		+ " all maps to see if they are broken or not.");
		eb.setAuthor("Ludeo#8554","https://github.com/Ludeo/Intralism-Bot",  url);
		eb.setColor(pink);
		event.getMessage().getChannel().sendMessage(eb.build()).queue();
	}

}
