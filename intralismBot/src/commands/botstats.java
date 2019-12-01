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
		int guildcountdbl=0;
		int usercountdbl=0;
		int mostuserdbl=0;
		for(int i=0; i<jda.getGuilds().size();i++) {
			guildcountdbl++;
			for(int j=0; j<jda.getGuilds().get(i).getMembers().size();j++) {
				usercountdbl++;
				if(mostuserdbl<jda.getGuilds().get(i).getMembers().size()) {
					mostuserdbl = jda.getGuilds().get(i).getMembers().size();
				}
			}
		}
		for(int i=0;i<jda.getGuilds().size();i++) {
			if(!jda.getGuilds().get(i).getId().contentEquals("264445053596991498")) {
				guildcount++;
				for(int j=0; j<jda.getGuilds().get(i).getMembers().size();j++) {
					usercount++;
					if(mostuser<jda.getGuilds().get(i).getMembers().size()) {
						mostuser = jda.getGuilds().get(i).getMembers().size();
					}
				}
			}
		}
		
		Color pink = new Color(255,105,180);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Intralism Bot Statistics");
		eb.addField("Average Ping",avgping+"",true);
		eb.addField("","Without Discord Bot List",false);
		eb.addField("Guildcount",guildcount+"",true);
		eb.addField("Usercount",usercount+"",true);
		eb.addField("Biggest Guild",mostuser+"",true);
		eb.addField("","With Discord Bot List",false);
		eb.addField("Guildcount",guildcountdbl+"",true);
		eb.addField("Usercount",usercountdbl+"",true);
		eb.addField("Biggest Guild",mostuserdbl+"",true);
		eb.setColor(pink);
		
		event.getMessage().getChannel().sendMessage(eb.build()).queue();
	}

}
