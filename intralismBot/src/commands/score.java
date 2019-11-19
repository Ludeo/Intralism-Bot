package commands;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message.MentionType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import scores.allscores;

public class score {
	
	public static void score(String[] args, String prefix, MessageReceivedEvent event) {
		String id = "";
		String mapname = "";
		if(args.length == 2) {
			String discordid = event.getMessage().getAuthor().getId();
			
			JSONParser jsonParser = new JSONParser();
			Object user = new Object();
			
			try {
				user = jsonParser.parse(new FileReader(".\\user.json"));
			} catch (IOException | ParseException e) {
				e.printStackTrace();
				user = "";
			}
			
			JSONArray userArray= new JSONArray();
			userArray = (JSONArray) user;
			for(int i = 0;i<userArray.size();i++) {
				if(userArray.get(i).toString().contains(discordid)) {
					String userx = userArray.get(i).toString();
					int anumber = userx.indexOf("Intralism");
					int bnumber = userx.indexOf(",", anumber);
					id = userx.substring(anumber+14,bnumber-1);
				}
			}
			if(id=="") {
				event.getMessage().getChannel().sendMessage("You don't have a Intralism account linked to your Discord account or you didn't" 
			+" use the right parameters. Check " + prefix + "commands for usage help").queue();
				return;
			}
			
			if(args[1].length() < 3) {
				event.getMessage().getChannel().sendMessage("The mapname length must at least be 3 characters long").queue();
				return;
			}
			
			mapname = args[1];
			
		} else if(args.length != 3) {
			event.getMessage().getChannel().sendMessage("You are not using the right parameters. Check " + prefix + "commands for usage help").queue();
			return;
		} else if(args[1] == null) {
			event.getMessage().getChannel().sendMessage("You are not using the right parameters. Check " + prefix + "commands for usage help").queue();
			return;
		} else if(args[2] == null) {
			event.getMessage().getChannel().sendMessage("You are not using the right parameters. Check " + prefix + "commands for usage help").queue();
			return;
		} else {
			if(args[1].contains("@")) {
				User user = event.getMessage().getMentionedUsers().get(0);
				String userid = user.getId();
				
				String discordid = userid;
				
				JSONParser jsonParser = new JSONParser();
				Object userO = new Object();
				
				try {
					userO = jsonParser.parse(new FileReader(".\\user.json"));
				} catch (IOException | ParseException e) {
					e.printStackTrace();
					userO = "";
				}
				
				JSONArray userArray= new JSONArray();
				userArray = (JSONArray) userO;
				for(int i = 0;i<userArray.size();i++) {
					if(userArray.get(i).toString().contains(discordid)) {
						String userx = userArray.get(i).toString();
						int anumber = userx.indexOf("Intralism");
						int bnumber = userx.indexOf(",", anumber);
						id = userx.substring(anumber+14,bnumber-1);
					}
				}
				if(id=="") {
					event.getMessage().getChannel().sendMessage("The mentioned user hasn't linked his Intralism account to his Discord account").queue();
					return;
				}
			} else {
				id = args[1];
			}
			
			if(args[2].length() < 3) {
				event.getMessage().getChannel().sendMessage("The mapname length must at least be 3 characters long").queue();
				return;
			}
			mapname = args[2];
		}
		
		String user = "";
		String rank = "";
		try {
			String[] array = allscores.getUser_Rank("https://intralism.khb-soft.ru/?player=" + id);
			user = array[0];
			rank = array[1];
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		try {
			Object[][] scoresObj = scores.allscores.getAllScores("https://intralism.khb-soft.ru/?player=" + id);
			String infos = "";
			boolean bool = false;
			EmbedBuilder eb = new EmbedBuilder();
			for (int i = 0; i<scoresObj.length;i++) {
				if(scoresObj[i][0].toString().toLowerCase().contains(mapname)) {
					if(scoresObj[i][1] == null) {
						scoresObj[i][1] = (int)0;
						scoresObj[i][2] = (double)0;
						scoresObj[i][3] = (int)0;
						scoresObj[i][4] = (double)0;
						scoresObj[i][6] = (double)0;
						
					} else {
						double difference = (double)scoresObj[i][5] - (double)scoresObj[i][4];
						difference = (double)Math.round(difference * 100)/100;
						eb.setTitle(user + "#" + rank);
						eb.addField("Map", scoresObj[i][0]+"", true);
						eb.addField("Score", scoresObj[i][1]+"", true);
						eb.addField("Accuracy", scoresObj[i][2]+"", true);
						eb.addField("Misses", scoresObj[i][3]+"", true);
						eb.addField("Points",scoresObj[i][4]+"",true);
						eb.addField("Max Points", scoresObj[i][5]+"", true);
						eb.addField("Difference", difference+"", true);
						eb.addField("Broken?", scoresObj[i][7]+"", true);
						
						int grank = Integer.parseInt(rank);
						if(grank == 1) {
							Color green = new Color(0,255,0);
							eb.setColor(green);
						}else if(grank <=10 ) {
							Color yellow = new Color(255,215,0);
							eb.setColor(yellow);
						} else if(grank > 10 && grank <=25) {
							Color purple = new Color(148,0,211);
							eb.setColor(purple);
						} else if(grank > 25 && grank <= 100) {
							Color red = new Color(255,0,0);
							eb.setColor(red);
						} else if(grank > 100 && grank <= 500) {
							Color blue = new Color(0,191,255);
							eb.setColor(blue);
						} else {
							Color black = new Color(0,0,0);
							eb.setColor(black);
						}

					}
					bool = true;
				} 
			}
			
			if(bool) event.getMessage().getChannel().sendMessage(eb.build()).queue();
			else event.getMessage().getChannel().sendMessage("I couldn't find the map or the user you were looking for or you are not using " 
			+ " the right parameters. Check " + prefix + "commands for usage help").queue();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e) {
			event.getMessage().getChannel().sendMessage("There is no player with this ID").queue();
		}
		
		return;
	}

}
