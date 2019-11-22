package commands;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class support {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event) {
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Join Support Server");
		eb.setDescription("With this link you can join the Intralism Bot support server. You can ask all questions related to the bot there "
		+"and I will answer you. You can also make suggestions there and report bugs.\n\nhttps://discord.gg/Br5mpec");
		Color pink = new Color(255,105,180);
		eb.setColor(pink);
		event.getMessage().getChannel().sendMessage(eb.build()).queue();
		
	}

}
