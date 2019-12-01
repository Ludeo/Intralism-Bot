package commands;

import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class commands {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event, int page) {
		EmbedBuilder eb = new EmbedBuilder();
		Color pink = new Color(255,105,180);
		if(page == 0) {
			eb.setTitle("All commands");
			eb.addField(prefix+"profile (<ID>|mention user)", "Checks the profile of the user with the given ID, of yourself when no ID is given "
			+"and you have your Intralism account linked or it shows the profile of the user you mentioned when he linked his Intralism account", true);
			eb.addField(prefix+"score (<ID>|mention user) <map>","Checks the score on the given map from the user with the given ID, of yourself "
			+ "when no ID is given and you have your Intralism account linked or it shows the profile of the user you mentioned when he linked "
			+"his Intralism account", true);
			eb.addField(prefix+"credits","Shows the credits of the Intralism-Bot", true);
			eb.addField(prefix+"commands | "+prefix+"help","Shows all the commands you can use", true);
			eb.addField(prefix+"top10", "Shows the current Top 10 players from Intralism", true);
			eb.addField(prefix+"link <ID>", "Links your discord account with the Intralism account that has the given ID", true);
			eb.addField("ID Explanation", "When you are looking at a intralism profile you can see the ID in the URL", false);
			eb.setColor(pink);
			eb.setFooter("Page 1/3");
			event.getMessage().getChannel().sendMessage(eb.build()).queue((message -> {
				message.addReaction("◀").queue();
				message.addReaction("▶").queue();
			}));
			
		} else if(page == 1) {
			eb.setTitle("All commands");
			eb.addField(prefix+"profile (<ID>|mention user)", "Checks the profile of the user with the given ID, of yourself when no ID is given "
			+"and you have your Intralism account linked or it shows the profile of the user you mentioned when he linked his Intralism account", true);
			eb.addField(prefix+"score (<ID>|mention user) <map>","Checks the score on the given map from the user with the given ID, of yourself "
			+ "when no ID is given and you have your Intralism account linked or it shows the profile of the user you mentioned when he linked "
			+"his Intralism account", true);
			eb.addField(prefix+"credits","Shows the credits of the Intralism-Bot", true);
			eb.addField(prefix+"commands | "+prefix+"help","Shows all the commands you can use", true);
			eb.addField(prefix+"top10", "Shows the current Top 10 players from Intralism", true);
			eb.addField(prefix+"link <ID>", "Links your discord account with the Intralism account that has the given ID", true);
			eb.addField("ID Explanation", "When you are looking at a intralism profile you can see the ID in the URL", false);
			eb.setColor(pink);
			eb.setFooter("Page 1/3");
			event.getMessage().editMessage(eb.build()).queue();
			
		} else if(page == 2) {
			eb.setTitle("All commands");
			eb.addField(prefix+"unlink","Unlinks your linked Intralism account", true);
			eb.addField(prefix+"support", "Shows you how to get support for the Intralism Bot", true);
			eb.addField(prefix+"broken", "Shows you every map thats broken", true);
			eb.addField(prefix+"botstats","Shows statistics about the Intralism Bot",true);
			eb.addField(prefix+"allscores (<ID>|mention user)","Shows every score of the player with the given ID or of the mentioned user "
			+" when he has his Intralism account linked or of yourself when you have your Intralism account linked", true);
			eb.addField(prefix+"invite","Gives you an invite link for the Intralism Bot",true);
			eb.addField("ID Explanation", "When you are looking at a intralism profile you can see the ID in the URL", false);
			eb.setColor(pink);
			eb.setFooter("Page 2/3");
			event.getMessage().editMessage(eb.build()).queue();
			
		} else if(page == 3) {
			eb.setTitle("All commands");
			eb.addField(prefix+"farm (<ID>|mention user)","Shows how much points the player, with the given ID, the player that you mentioned "
			+"when he has his Intralism account linked or yourself when you have your Intralism account linked, can still get on maps",true);
			eb.addField(prefix+"tryhard (<ID>|mention user)","Shows how much points the player, with the given ID, the player that you mentioned "
			+"when he has his Intralism account linked or yourself when you have your Intralism account linked, can still get on maps",true);
			eb.addField("ID Explanation", "When you are looking at a intralism profile you can see the ID in the URL", false);
			eb.setColor(pink);
			eb.setFooter("Page 3/3");
			event.getMessage().editMessage(eb.build()).queue();
		}
	}

}
