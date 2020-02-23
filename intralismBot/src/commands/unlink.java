package commands;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class unlink {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event) {
		String discordid = event.getAuthor().getId();
		
		JSONParser jsonParser = new JSONParser();
		
		Object oldUser = new Object();
		
		try {
			oldUser = jsonParser.parse(new FileReader("user.json"));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			oldUser = "";
		}
		
		JSONArray user = new JSONArray();
		
		if(oldUser.toString().contains("\"DiscordID\":\"" + discordid + "\"")){
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
			
			try (FileWriter file = new FileWriter("user.json")) {
			    file.write(user.toString());
			} catch (IOException e) {
			    e.printStackTrace();
			}
			event.getMessage().getChannel().sendMessage("Successfully unlinked your linked Intralism account").queue();
		} else {
			event.getMessage().getChannel().sendMessage("You don't have a Intralism account linked").queue();
		}
		
	}

}
