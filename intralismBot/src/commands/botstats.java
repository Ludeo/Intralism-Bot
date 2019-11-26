package commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class botstats {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event) {
		JDA jda = event.getJDA();
		long avgping = jda.getGatewayPing();
		int guildcount=0;
		int usercount=0;
		int mostuser=0;
		for(int i=0; i<jda.getGuilds().size();i++) {
			guildcount++;
			for(int j=0; j<jda.getGuilds().get(i).getMembers().size();j++) {
				usercount++;
				if(mostuser<jda.getGuilds().get(i).getMembers().size()) {
					mostuser = jda.getGuilds().get(i).getMembers().size();
				}
			}
		}
		
		Color pink = new Color(255,105,180);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Intralism Bot Statistics");
		eb.addField("Guildcount",guildcount+"",true);
		eb.addField("Usercount",usercount+"",true);
		eb.addField("Biggest Guild",mostuser+"",true);
		eb.addField("Average Ping",avgping+"",true);
		eb.setColor(pink);
		
		event.getMessage().getChannel().sendMessage(eb.build()).queue();
	}

}
