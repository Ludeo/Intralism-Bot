package commands;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import scores.allscores;

public class profile {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event) {
		String id = "";
		if(args.length == 1) {
			String discordid = event.getMessage().getAuthor().getId();
			
			JSONParser jsonParser = new JSONParser();
			Object user = new Object();
			
			try {
				user = jsonParser.parse(new FileReader("user.json"));
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
				event.getMessage().getChannel().sendMessage("You don't have a Intralism account linked to your Discord account").queue();
				return;
			}
			
			
		} else if(args.length != 2) {
			event.getMessage().getChannel().sendMessage("You are not using the right parameters. Check " + prefix + "commands for usage help").queue();
			return;
		} else if(args[1] == null) {
			event.getMessage().getChannel().sendMessage("You are not using the right parameters. Check " + prefix + "commands for usage help").queue();
			return;
		} else {
			if(args[1].contains("@")) {
				List<User> users = event.getMessage().getMentionedUsers();
				if(users.size() == 0) {
					event.getMessage().getChannel().sendMessage("There is no player with this ID").queue();
					return;
				}
				
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
			
		}
		
		try {
			Object[][] alldata = scores.allscores.getAllScores("https://intralism.khb-soft.ru/?player=" + id);
			
			double realpoints = 0;
			double rankedpoints = 0;
			double maximumpoints = 0;
			double totalacc = 0;
			int totalmiss = 0;
			double totaldifference = 0;
			int hundredcount = 0;
			int mapcount = alldata.length;
			int mapsplayed = alldata.length;
			for(int i = 0; i<alldata.length;i++) {
				if(alldata[i][0] != null) {
					double points = (double) alldata[i][4];
					double maxpoints = (double) alldata[i][5];
					double actpoints = points;
					realpoints += points;
					maximumpoints += maxpoints;
					
					String status = (String)alldata[i][7];
					
					if(status.contentEquals("Broken")) {
						if(points == maxpoints) {
							if(alldata[i][0] == "Indigo Child - Nostalgia") {
								alldata[i][4] = (double) 1.87;
								actpoints = points - 0.01;
							} else if(alldata[i][0] == "Mizutani Hiromi - Hidamari Michi to Ren-chon") {
								alldata[i][4] = (double) 2.11;
								actpoints = points - 0.01;
							} else if(alldata[i][0] == "Oskar Schuster - Wunder") {
								alldata[i][4] = (double) 2.86;
								actpoints = points - 0.01;
							} else {
								actpoints = points - 0.01;
								alldata[i][4] = (double) actpoints;
							}
						}
						rankedpoints +=  actpoints;
					} else {
						rankedpoints +=  points;
					}
					
					totalacc += (double) alldata[i][2];
					totalmiss += (int) alldata[i][3];
					
					double a = (double) alldata[i][5];
					double b = (double) alldata[i][4];
					alldata[i][6] = (double) a - b;
					totaldifference += (double) alldata[i][6];
					
					if((double)alldata[i][2] == (double) 100.00) {
						hundredcount++;
					}
					
					if((double)alldata[i][4] == 0) {
						mapsplayed--;
					}
					
				}
			}
			
			double avgaccexact = totalacc / mapsplayed;
			double avgacc = (double)Math.round(avgaccexact * 10000)/10000;
			
			double avgmiss = (double)totalmiss / mapsplayed;
			avgmiss = (double) Math.round(avgmiss * 100)/100;
			
			totaldifference = (double) Math.round(totaldifference * 100)/100;
			
			realpoints = (double)Math.round(realpoints * 100)/100;
			rankedpoints = (double)Math.round(rankedpoints * 100)/100;
			maximumpoints = (double)Math.round(maximumpoints * 100)/100;
			
			String user = "";
			String globalrank = "";
			String totalglobalrank = "";
			String countryrank = "";
			String totalcountryrank = "";
			String country = "";
			String pictureLink = "";
			try {
				String[] array = allscores.getUser_Rank("https://intralism.khb-soft.ru/?player=" + id);
				user = array[0];
				globalrank = array[1];
				totalglobalrank = array[2];
				countryrank = array[3];
				totalcountryrank = array[4];
				country = array[5];
				pictureLink = array[6];
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
			
			EmbedBuilder eb = new EmbedBuilder();
			eb.setDescription("");
			if(globalrank.contentEquals("?")) {
				eb.setAuthor(user + "#" + globalrank +"   BANNED","https://intralism.khb-soft.ru/?player=" + id,pictureLink);
			} else {
				eb.setAuthor(user + "#" + globalrank,"https://intralism.khb-soft.ru/?player=" + id,pictureLink);
			}
			eb.addField("Global Rank", globalrank + " / " + totalglobalrank, true);
			eb.addField("Country", country, true);
			eb.addField("Country Rank", countryrank + " / " + totalcountryrank, true);
			
			eb.addField("Points", rankedpoints+"", true);
			eb.addField("Real Points", realpoints+"", true);
			eb.addField("Maximum Points", maximumpoints+"", true);
			
			eb.addField("Difference", totaldifference+"", true);
			eb.addField("AVG Accuracy", avgacc+"%", true);
			eb.addField("AVG Misses", avgmiss+"", true);
			
			eb.addField("100% Plays", hundredcount+"", true);
			eb.addField("Total Maps", mapcount+"", true);
			
			int grank = 0;
			if(globalrank.contentEquals("?")) {
				Color black = new Color(0,0,0);
				eb.setColor(black);
			} else {
				grank = Integer.parseInt(globalrank);
				if(grank <= 10 ) {
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
			
			event.getMessage().getChannel().sendMessage(eb.build()).queue();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e) {
			
			event.getMessage().getChannel().sendMessage("There is no player with this ID").queue();
		}
	}

}
