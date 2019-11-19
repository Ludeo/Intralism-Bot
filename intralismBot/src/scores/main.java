package scores;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class main extends ListenerAdapter {
	
	String prefix = "i!"; //change prefix here
	
	public static void main(String args[]) throws LoginException {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setToken(""); //add the bot token here
		builder.addEventListeners(new main());
		builder.setActivity(Activity.playing("Intralism"));
		builder.build();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)  {
		if(!event.getMessage().getContentDisplay().startsWith(prefix)) {
			return;
		}
		
		System.out.println("User: " + event.getMessage().getAuthor().getName() + "\nGuild: " + event.getMessage().getGuild().getName()
				+ "\nMessage: " + event.getMessage().getContentDisplay() + "\n---------------------");
		
		String[] args = event.getMessage().getContentDisplay().substring(prefix.length()).split(" ");
		
		switch(args[0].toLowerCase()) {
			case "score":
				commands.score.score(args, prefix, event);
				break;
			case "profile":
				commands.profile.profile(args, prefix, event);
				break;
			case "credits":
				commands.credits.credits(args, prefix, event);
				break;
			case "commands":
				commands.commands.commands(args, prefix, event);
				break;
			case "top10":
				commands.top10.top10(args, prefix, event);
				break;
			case "link":
				commands.link.link(args, prefix, event);
				break;
			default:
				event.getMessage().getChannel().sendMessage("This command doesn't exit. Type " + prefix + "commands to get all available commands.").queue();
				break;
		};
		
	}
	
	@Override
	public void onReady(ReadyEvent ready) {
		System.out.println("TESTING BOT");
		System.out.println("Connected to " + ready.getGuildAvailableCount() + " out of " + ready.getGuildTotalCount() + " guilds");
	}

}
