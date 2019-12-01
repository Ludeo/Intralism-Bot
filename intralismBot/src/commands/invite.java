package commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class invite {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Invite");
		eb.setDescription("Add the Intralism Bot with this link: "
		+"https://discordapp.com/oauth2/authorize?client_id=340873693464887298&scope=bot&permissions=8");
		Color pink = new Color(255,105,180);
		eb.setColor(pink);
		
		event.getMessage().getChannel().sendMessage(eb.build()).queue();
	}

}
