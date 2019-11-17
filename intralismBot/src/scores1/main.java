package scores1;

import java.awt.Color;
import java.io.IOException;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class main extends ListenerAdapter {
	
	String prefix = "i"; //change the prefix here
	
	public static void main(String args[]) throws LoginException {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setToken(""); //add your bot token here
		builder.addEventListeners(new main());
		builder.setActivity(Activity.playing("Intralism"));
		builder.build();
	}
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)  {
		
		String[] args = event.getMessage().getContentDisplay().substring(prefix.length()).split(" ");
		if(event.getMessage().getContentDisplay().startsWith(prefix)) {
			if(args[0].toLowerCase().equals("score")){
				String id = args[1];
				String mapname = args[2];
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
					Object[][] scoresObj = scores1.allscores.getAllScores("https://intralism.khb-soft.ru/?player=" + id);
					String infos = "";
					
					for (int i = 0; i<scoresObj.length;i++) {
						if(scoresObj[i][0] == null) {
							
						}
						else if(scoresObj[i][0].toString().toLowerCase().contains(mapname)) {
							if(scoresObj[i][1] == null) {
								scoresObj[i][1] = (int)0;
								scoresObj[i][2] = (double)0;
								scoresObj[i][3] = (int)0;
								scoresObj[i][4] = (double)0;
								scoresObj[i][6] = (double)0;
							} else {
								
								System.out.println(mapname);
								double difference = (double)scoresObj[i][5] - (double)scoresObj[i][4];
								difference = (double)Math.round(difference * 100)/100;
								
								EmbedBuilder eb = new EmbedBuilder();
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
								event.getMessage().getChannel().sendMessage(eb.build()).queue();
							}
							
						} 
					}
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if(args[0].toLowerCase().equals("profile")) {
				String id = args[1];
				try {
					Object[][] alldata = scores1.allscores.getAllScores("https://intralism.khb-soft.ru/?player=" + id);
					
					double realpoints = 0;
					double rankedpoints = 0;
					double maximumpoints = 0;
					double totalacc = 0;
					int totalmiss = 0;
					double totaldifference = 0;
					int hundredcount = 0;
					int mapcount = alldata.length;
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
							
						}
					}
					
					double avgaccexact = totalacc / mapcount;
					double avgacc = (double)Math.round(avgaccexact * 10000)/10000;
					
					double avgmiss = (double)totalmiss / mapcount;
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
					eb.setAuthor(user + "#" + globalrank,"https://intralism.khb-soft.ru/?player=" + id,pictureLink);
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
					
					int grank = Integer.parseInt(globalrank);
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
					
					event.getMessage().getChannel().sendMessage(eb.build()).queue();
					
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if(args[0].toLowerCase().equals("credits")) {
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setDescription("The Intralism Bot was programmed by Ludeo#8554 and it is open source. When you click on \"Ludeo#8554\" you will get to the github page." 
				+ "\nSpecial Thanks to FlyingRabidUnicornPig™#5435 for helping me out with the code and Special Thanks to Kiri#1000 for checking"
				+ " all maps to see if they are broken or not.");
				String url = event.getGuild().getMemberById("311861142114926593").getUser().getAvatarUrl();
				System.out.println(url);
				eb.setAuthor("Ludeo#8554","https://github.com/Ludeo/Intralism-Bot",  url);
				Color pink = new Color(255,105,180);
				eb.setColor(pink);
				event.getMessage().getChannel().sendMessage(eb.build()).queue();
			} else if(args[0].toLowerCase().equals("commands")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setTitle("All commands");
				eb.addField(prefix+"profile <ID>", "Checking the profile of the user with the given ID", true);
				eb.addField(prefix+"score <ID> <map>","Checking the score on the given map from the user with the given ID", true);
				eb.addField(prefix+"credits","Showing the credits of the Intralism-Bot", true);
				eb.addField("ID Explanation", "When you are looking at a intralism profile you can see the ID in the URL", true);
				Color pink = new Color(255,105,180);
				eb.setColor(pink);
				event.getMessage().getChannel().sendMessage(eb.build()).queue();
			}
		} else {
			return;
		}
		
	}

}
