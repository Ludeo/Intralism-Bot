package commands;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class commands {
	
	public static void commands(String[] args, String prefix, MessageReceivedEvent event) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("All commands");
		eb.addField(prefix+"profile (<ID>|mention user)", "Checks the profile of the user with the given ID, of yourself when no ID is given "
		+"and you have your Intralism account linked or it shows the profile of the user you mentioned when he linked his Intralism account", true);
		eb.addField(prefix+"score (<ID>|mention user) <map>","Checks the score on the given map from the user with the given ID of yourself "
		+ "when no ID is given and you have your Intralism account linked or it shows the profile of the user you mentioned when he linked "
		+"his Intralism account", true);
		eb.addField(prefix+"credits","Shows the credits of the Intralism-Bot", true);
		eb.addField(prefix+"commands","Shows all the commands you can use", true);
		eb.addField(prefix+"top10", "Shows the current Top 10 players from Intralism", true);
		eb.addField(prefix+"link <ID>", "Links your discord account with the Intralism account that has the given ID", true);
		eb.addField(prefix+"unlink","Unlinks your linked Intralism account", true);
		eb.addField("ID Explanation", "When you are looking at a intralism profile you can see the ID in the URL", false);
		Color pink = new Color(255,105,180);
		eb.setColor(pink);
		event.getMessage().getChannel().sendMessage(eb.build()).queue();
	}

}
