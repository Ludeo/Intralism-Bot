package commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class recent {
	
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
			Document doc = Jsoup.connect("https://intralism.khb-soft.ru/?player=" + id).get();
			String profileInfo = doc.toString();
			BufferedReader bufReader = new BufferedReader(new StringReader(profileInfo));
			String line=null;
			String map = "";
			String user = "";
			String globalrank = "";
			String totalglobalrank = "";
			String countryrank = "";
			String totalcountryrank = "";
			String country = "";
			String pictureLink = "";
			
			Object[][] alldata = scores.allscores.getAllScores("https://intralism.khb-soft.ru/?player=" + id);
			boolean notfound = true;
			
			while((line=bufReader.readLine()) != null) {
			
				if(line.contains("-0.2em") && line.contains("badge") && !line.contains("Random") && !line.contains("Hidden") && !line.contains("Endless") && notfound) {
					for(int i=0;i<alldata.length;i++) {
						map = line;
						int anum = map.indexOf("-0.2em");
						int bnum = map.indexOf("</a");
						map = map.substring(anum+10, bnum);
						
						if(map.contentEquals((String) alldata[i][0])) {
							notfound = false;
						}
					}
					
				} else if(line.contains("<title>")) {
					user = line;
			    	int anumber = user.indexOf(">");  
			    	int bnumber = user.toString().indexOf("<", anumber);
			    	user = user.substring(anumber+13, bnumber);
			    	
				} else if(line.contains("strong>Global Rank")) {
					line = bufReader.readLine();
					globalrank = line;
			    	int anumber = globalrank.indexOf("\">");  
			    	int bnumber = globalrank.toString().indexOf("</span", anumber);
			    	globalrank = globalrank.substring(anumber+2, bnumber);
			    	
			    	totalglobalrank = line;
			    	anumber = totalglobalrank.indexOf("</span>");
			    	totalglobalrank = totalglobalrank.substring(anumber+10, line.length());
			    	
				}  else if(line.contains("Country Rank")) {
					line = bufReader.readLine();
					countryrank = line;
					int anumber = countryrank.indexOf("\">");
					int bnumber = countryrank.toString().indexOf("</span", anumber);
					countryrank = countryrank.substring(anumber+2, bnumber);
					
					totalcountryrank = line;
					anumber = totalcountryrank.indexOf("</span");
					totalcountryrank = totalcountryrank.substring(anumber+10, line.length());
					
				} else if(line.contains(">#")) {
					country = bufReader.readLine();
					int anumber = country.indexOf("title=\"");
					int bnumber = country.toString().indexOf("><span", anumber);
					country = country.substring(anumber+7,bnumber-1);
					
				} else if(line.contains("og:image")) {
					pictureLink = bufReader.readLine();
					int anumber = pictureLink.indexOf("content");
					int bnumber = pictureLink.toString().indexOf(">");
					pictureLink = pictureLink.substring(anumber+9, bnumber-1);
					
				}
			}
			
			EmbedBuilder eb = new EmbedBuilder();
			
			for(int i=0;i<alldata.length;i++) {
				if(map.contentEquals((String) alldata[i][0])) {
					if(alldata[i][1] == null) {
						alldata[i][1] = (int)0;
						alldata[i][2] = (double)0;
						alldata[i][3] = (int)0;
						alldata[i][4] = (double)0;
						alldata[i][6] = (double)0;
						
					} else {
						if(alldata[i][7].equals("Broken")) {
							if((double)alldata[i][4]==(double)alldata[i][5]) {
								if(alldata[i][0] == "Indigo Child - Nostalgia") {
									alldata[i][4] = (double) 1.87;
								} else if(alldata[i][0] == "Mizutani Hiromi - Hidamari Michi to Ren-chon") {
									alldata[i][4] = (double) 2.11;
								} else if(alldata[i][0] == "Oskar Schuster - Wunder") {
									alldata[i][4] = (double) 2.86;
								} else {
									double actualpoints = (double)alldata[i][5]-0.01;
									alldata[i][4] = actualpoints;
								}
								
							}
						}
						double difference = (double)alldata[i][5] - (double)alldata[i][4];
						difference = (double)Math.round(difference * 100)/100;
						eb.addField(alldata[i][0]+"","Score: "+alldata[i][1]+"\nAccuracy: "+alldata[i][2]+"%\nMisses: "+alldata[i][3]
						+"\nPoints: "+alldata[i][4]+"\nMax Points: "+alldata[i][5]+"\nDifference: "+difference+"\nBroken?: "
						+alldata[i][7],true);

						if(globalrank.contentEquals("?")) {
							eb.setAuthor(user+"#"+globalrank+"   BANNED","https://intralism.khb-soft.ru/?player=" + id,pictureLink);
						} else {
							eb.setAuthor(user+"#"+globalrank,"https://intralism.khb-soft.ru/?player=" + id,pictureLink);
						}
						
						int grank = 0;
						if(globalrank.contentEquals("?")) {
							Color black = new Color(0,0,0);
							eb.setColor(black);
						} else {
							grank = Integer.parseInt(globalrank);
							if(grank == 1) {
								Color green = new Color(0,255,0);
								eb.setColor(green);
							}else if(grank <= 10 ) {
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
						
						eb.setTitle("Most Recent Score");
					}
				}
			}
			
			try {
				event.getMessage().getChannel().sendMessage(eb.build()).queue();
			} catch(IllegalStateException e) {
				event.getMessage().getChannel().sendMessage("There is no player with this ID or the user hasn't got a recent play").queue();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e) {
			event.getMessage().getChannel().sendMessage("There is no player with this ID or the user hasn't got a recent play").queue();
		}
	}

}
