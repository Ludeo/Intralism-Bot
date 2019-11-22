package commands;

import java.awt.Color;
import java.io.IOException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class broken {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event) {
		Color pink = new Color(255,105,180);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("All broken maps 1");
		eb.setColor(pink);
		
		EmbedBuilder eb1 = new EmbedBuilder();
		eb1.setTitle("All broken maps 2");
		eb1.setColor(pink);
		
		try {
			Object[][] scoresObj = scores.allscores.getAllScores("https://intralism.khb-soft.ru/?player=76561198143629166");
			String allmaps = "";
			String allmaps2 = "";
			
			for(int i = 0; i<scoresObj.length;i++) {
				if(scoresObj[i][7] == "Broken") {
					if(allmaps.length() < 1950) {
						allmaps += scoresObj[i][0] +"\n";
					} else {
						allmaps2 += scoresObj[i][0] + "\n";
					}
					
				}
			}
			eb.setDescription(allmaps);
			eb1.setDescription(allmaps2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		event.getMessage().getChannel().sendMessage(eb.build()).queue();
		event.getMessage().getChannel().sendMessage(eb1.build()).queue();
	}

}
