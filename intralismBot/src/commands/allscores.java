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

public class allscores {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event, int page) {
		String id = "";
		String pages = "32";
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
			
			eb.setTitle("All Scores");
			
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
			
			for(int i = 0; i<alldata.length;i++) {
				if(page == 0) {
					if(i<6) {
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
							eb.setFooter("Page 1/"+pages);
						}
					}
				} else if(page == 1) {
					if(i<6) {
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
							eb.setFooter("Page 1/"+pages);
						}
					}
					
				} else if(page == 2) {
					if(i>5 && i<12) {
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
							eb.setFooter("Page 2/"+pages);
						}
					}
				} else if(page == 3) {
					if(i>11 && i<18) {
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
							eb.setFooter("Page 3/"+pages);
						}
					}
				} else if(page == 4) {
					if(i>17 && i<24) {
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
							eb.setFooter("Page 4/"+pages);
						}
					}
				} else if(page == 5) {
					if(i>23 && i<30) {
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
							eb.setFooter("Page 5/"+pages);
						}
					}
				} else if(page == 6) {
					if(i>29 && i<36) {
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
							eb.setFooter("Page 6/"+pages);
						}
					}
				} else if(page == 7) {
					if(i>35 && i<42) {
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
							eb.setFooter("Page 7/"+pages);
						}
					}
				} else if(page == 8) {
					if(i>41 && i<48) {
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
							eb.setFooter("Page 8/"+pages);
						}
					}
				} else if(page == 9) {
					if(i>47 && i<54) {
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
							eb.setFooter("Page 9/"+pages);
						}
					}
				} else if(page == 10) {
					if(i>53 && i<60) {
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
							eb.setFooter("Page 10/"+pages);
						}
					}
				} else if(page == 11) {
					if(i>59 && i<66) {
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
							eb.setFooter("Page 11/"+pages);
						}
					}
				} else if(page == 12) {
					if(i>65 && i<72) {
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
							eb.setFooter("Page 12/"+pages);
						}
					}
				} else if(page == 13) {
					if(i>71 && i<78) {
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
							eb.setFooter("Page 13/"+pages);
						}
					}
				} else if(page == 14) {
					if(i>77 && i<84) {
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
							eb.setFooter("Page 14/"+pages);
						}
					}
				} else if(page == 15) {
					if(i>83 && i<90) {
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
							eb.setFooter("Page 15/"+pages);
						}
					}
				} else if(page == 16) {
					if(i>89 && i<96) {
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
							eb.setFooter("Page 16/"+pages);
						}
					}
				} else if(page == 17) {
					if(i>95 && i<102) {
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
							eb.setFooter("Page 17/"+pages);
						}
					}
				} else if(page == 18) {
					if(i>101 && i<108) {
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
							eb.setFooter("Page 18/"+pages);
						}
					}
				} else if(page == 19) {
					if(i>107 && i<114) {
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
							eb.setFooter("Page 19/"+pages);
						}
					}
				} else if(page == 20) {
					if(i>113 && i<120) {
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
							eb.setFooter("Page 20/"+pages);
						}
					}
				} else if(page == 21) {
					if(i>119 && i<126) {
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
							eb.setFooter("Page 21/"+pages);
						}
					}
				} else if(page == 22) {
					if(i>125 && i<132) { 
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
							eb.setFooter("Page 22/"+pages);
						}
					}
				} else if(page == 23) {
					if(i>131 && i<138) { 
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
							eb.setFooter("Page 23/"+pages);
						}
					}
				} else if(page == 24) {
					if(i>137 && i<144) { 
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
							eb.setFooter("Page 24/"+pages);
						}
					}
				} else if(page == 25) {
					if(i>143 && i<150) { 
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
							eb.setFooter("Page 25/"+pages);
						}
					}
				} else if(page == 26) {
					if(i>149 && i<156) {
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
							eb.setFooter("Page 26/"+pages);
						}
					}
				} else if(page == 27) {
					if(i>155 && i<162) {
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
							eb.setFooter("Page 27/"+pages);
						}
					}
				} else if(page == 28) {
					if(i>161 && i<168) {
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
							eb.setFooter("Page 28/"+pages);
						}
					}
				} else if(page == 29) {
					if(i>167 && i<174) {
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
							eb.setFooter("Page 29/"+pages);
						}
					}
				} else if(page == 30) {
					if(i>173 && i<180) {
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
							eb.setFooter("Page 30/"+pages);
						}
					}
				} else if(page == 31) {
					if(i>179 && i<186) {
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
							eb.setFooter("Page 31/"+pages);
						}
					}
				} else if(page == 32) {
					if(i>185 && i<192) {
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
							eb.setFooter("Page 32/"+pages);
						}
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
