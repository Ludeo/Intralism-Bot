package commands;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParser;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class link {
	
	public static void link(String[] args, String link, MessageReceivedEvent event) {
		
		if(args.length != 2) {
			return;
		} else if(args[1] == null) {
			return;
		}
		
		String discordid = event.getAuthor().getId();
		String username = event.getAuthor().getName();
		String intralismid = args[1];
		
		JSONParser jsonParser = new JSONParser();
		
		Object oldUser = new Object();
		
		try {
			oldUser = jsonParser.parse(new FileReader(".\\user.json"));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			oldUser = "";
		}
		
		JSONArray user = new JSONArray();
		
		if(oldUser.toString().contains("\"DiscordID\":\"" + discordid + "\"")){
			if(oldUser.toString().contains("\"DiscordID\":\"" + discordid + "\",\"IntralismID\":\"" + intralismid)) {
				event.getMessage().getChannel().sendMessage("This ID is already linked to your Discord account").queue();
			} else {
				JSONArray oldUserArray= new JSONArray();
				oldUserArray = (JSONArray) oldUser;
				for(Object o : oldUserArray) {
					user.add(o);
				}
				
				for(int i = 0; i<user.size();i++) {
					Object currentUser = user.get(i);
					if(currentUser.toString().contains(discordid)) {
						user.remove(i);
					}
				}
				
				JSONObject userDetails = new JSONObject();
				userDetails.put("Username", username);
				userDetails.put("DiscordID", discordid);
				userDetails.put("IntralismID", intralismid);
				user.add(userDetails);
				
				try (FileWriter file = new FileWriter(".\\user.json")) {
				    file.write(user.toString());
				} catch (IOException e) {
				    e.printStackTrace();
				}
				
				event.getMessage().getChannel().sendMessage("Successfully changed your Intralism ID").queue();
				
			}
			
		} else {
			JSONObject userDetails = new JSONObject();
			userDetails.put("Username", username);
			userDetails.put("DiscordID", discordid);
			userDetails.put("IntralismID", intralismid);
			user.add(userDetails);
			
			if(oldUser == "") {
				
			} else {
				JSONArray oldUserArray= new JSONArray();
				oldUserArray = (JSONArray) oldUser;
				for(Object o : oldUserArray) {
					user.add(o);
				}
			}

			try (FileWriter file = new FileWriter(".\\user.json")) {
			    file.write(user.toString());
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			event.getMessage().getChannel().sendMessage("Successfully linked the IntralismID to your Discord account").queue();
			
		}
		
	}

}
