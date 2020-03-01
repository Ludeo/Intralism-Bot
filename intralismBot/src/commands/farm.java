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

public class farm {
	
	public static void main(String args[], String prefix, MessageReceivedEvent event, int page) {
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
			if(page == 0) {
				if(id=="") {
					event.getMessage().getChannel().sendMessage("You don't have a Intralism account linked to your Discord account").queue();
					return;
				}
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
					userO = jsonParser.parse(new FileReader("user.json"));
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
			
			if(id == "") {
				String author = event.getMessage().getEmbeds().get(0).getAuthor().getName();
				int anum = author.lastIndexOf("(");
				int bnum = author.indexOf(")",anum);
				id = author.substring(anum+1,bnum);
			}
			Object[][] alldata = scores.allscores.getAllScores("https://intralism.khb-soft.ru/?player=" + id);
			EmbedBuilder eb = new EmbedBuilder();
			
			eb.setTitle("All Available Points");
			
			String[] array = scores.allscores.getUser_Rank("https://intralism.khb-soft.ru/?player=" + id);
			String user = array[0];
			String globalrank = array[1];
			String pictureLink = array[6];
			if(globalrank.contentEquals("?")) {
				eb.setAuthor(user+"#"+globalrank+"   BANNED ("+id+")","https://intralism.khb-soft.ru/?player=" + id,pictureLink);
			} else {
				eb.setAuthor(user+"#"+globalrank+" ("+id+")","https://intralism.khb-soft.ru/?player=" + id,pictureLink);
			}
			
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
			
			double avcount = 0;
			for(int i = 0; i<alldata.length;i++) {
				double avpoints = 0;

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
					
					double points = (double)alldata[i][4];
					double maxpoints = (double)alldata[i][5];
					points = (double)Math.round(points*100)/100;
					maxpoints = (double)Math.round(maxpoints*100)/100;
					avpoints = maxpoints-points;
					avpoints = (double)Math.round(avpoints*100)/100;
					
					if(avpoints == 0.01) {
						avpoints = 0;
					} else {
						avcount++;
					}
				} else {
					double points = (double)alldata[i][4];
					double maxpoints = (double)alldata[i][5];
					points = (double)Math.round(points*100)/100;
					maxpoints = (double)Math.round(maxpoints*100)/100;
					avpoints = maxpoints-points;
					avpoints = (double)Math.round(avpoints*100)/100;
					
					if(avpoints != 0) {
						avcount++;
					}
					
				}
				if(avpoints != 0) {
					if(page == 0) {
						if(avcount<13) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 1/15");
					} else if(page == 1) {
						if(avcount<13) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 1/15");
					} else if(page == 2) {
						if(avcount>12 && avcount<25) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 2/15");
					} else if(page == 3) {
						if(avcount>24 && avcount<37) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 3/15");
					} else if(page == 4) {
						if(avcount>36 && avcount<49) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 4/15");
					} else if(page == 5) {
						if(avcount>48 && avcount<61) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 5/15");
					} else if(page == 6) {
						if(avcount>60 && avcount<73) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 6/15");
					} else if(page == 7) {
						if(avcount>72 && avcount<85) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 7/15");
					} else if(page == 8) {
						if(avcount>84 && avcount<97) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 8/15");
					} else if(page == 9) {
						if(avcount>96 && avcount<109) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 9/15");
					} else if(page == 10) {
						if(avcount>108 && avcount<121) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 10/15");
					} else if(page == 11) {
						if(avcount>120 && avcount<133) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 11/15");
					} else if(page == 12) {
						if(avcount>132 && avcount<145) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 12/15");
					} else if(page == 13) {
						if(avcount>144 && avcount<157) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 13/15");
					} else if(page == 14) {
						if(avcount>156 && avcount<169) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 14/15");
					} else if(page == 14) {
						if(avcount>168 && avcount<181) {
							eb.addField(""+alldata[i][0],""+avpoints,true);
						}
						eb.setFooter("Page 15/15");
					}
					
				}
			}
			if(page == 0) {
				event.getMessage().getChannel().sendMessage(eb.build()).queue((message -> {
					message.addReaction("◀").queue();
					message.addReaction("▶").queue();
				}));
			} else {
				event.getMessage().editMessage(eb.build()).queue();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			event.getMessage().getChannel().sendMessage("There is no player with this ID").queue();
		}
		
	}

}
