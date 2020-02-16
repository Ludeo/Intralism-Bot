package commands;

import java.awt.Color;
import java.io.IOException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class broken {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event, int page) {
		Color pink = new Color(255,105,180);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("All broken maps");
		eb.setColor(pink);
		
		try {
			Object[][] scoresObj = scores.allscores.getAllScores("https://intralism.khb-soft.ru/?player=76561198143629166");
			String allmaps = "";
			
			int broken = 0;
			
			for(int i = 0; i<scoresObj.length;i++) {
				if(scoresObj[i][7] == "Broken") {
					broken++;
					if(page == 0) {
						if(broken<26) {
							allmaps += scoresObj[i][0] +"\n";
							eb.setFooter("Page 1/4");
						}
					} else if(page == 1) {
						if(broken<26) {
							allmaps += scoresObj[i][0] +"\n";
							eb.setFooter("Page 1/4");
						}
					} else if(page == 2) {
						if(broken>25 && broken<51) {
							allmaps += scoresObj[i][0] +"\n";
							eb.setFooter("Page 2/4");
						}
					} else if(page == 3) {
						if(broken>50 && broken<76) {
							allmaps += scoresObj[i][0] +"\n";
							eb.setFooter("Page 3/4");
						}
					} else if(page == 4) {
						if(broken>75 && broken<101) {
							allmaps += scoresObj[i][0] +"\n";
							eb.setFooter("Page 4/4");
						}
					}
					
				}
			}
			eb.setDescription(allmaps);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(page == 0) {
			event.getMessage().getChannel().sendMessage(eb.build()).queue((message -> {
				message.addReaction("◀").queue();
				message.addReaction("▶").queue();
			}));
		} else {
			event.getMessage().editMessage(eb.build()).queue();
		}
		
		
	}

}
