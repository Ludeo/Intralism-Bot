package scores;

import javax.security.auth.login.LoginException;
import listener.GuildMessageReactionEventListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class main extends ListenerAdapter {
	
	String prefix = "i!"; //change the prefix here
	
	public static void main(String args[]) throws LoginException {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setToken(""); //add your bot token here //change ID in ReactionEvent
		builder.addEventListeners(new main());
		builder.addEventListeners(new GuildMessageReactionEventListener());
		builder.setActivity(Activity.playing("Intralism"));
		builder.build();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)  {
		if(!event.getMessage().getContentDisplay().startsWith(prefix)) {
			return;
		}
		
		if(event.getMessage().getGuild().getId().contentEquals("264445053596991498")) {
			if(!event.getJDA().getGuildById("264445053596991498").getMemberById("340873693464887298").hasPermission(Permission.MESSAGE_WRITE)) {
				return;
			}
		}
		
		System.out.println("User: " + event.getMessage().getAuthor().getName() + "\nGuild: " + event.getMessage().getGuild().getName()
				+ "\nMessage: " + event.getMessage().getContentDisplay() + "\n---------------------");
		
		String[] args = event.getMessage().getContentDisplay().substring(prefix.length()).split(" ");
		
		switch(args[0].toLowerCase()) {
			case "score":
				commands.score.main(args, prefix, event);
				break;
			case "profile":
				commands.profile.main(args, prefix, event);
				break;
			case "credits":
				commands.credits.main(args, prefix, event);
				break;
			case "commands":
				commands.commands.main(args, prefix, event, 0);
				break;
			case "help":
				commands.commands.main(args, prefix, event,0);
				break;
			case "top10":
				commands.top10.main(args, prefix, event);
				break;
			case "link":
				commands.link.main(args, prefix, event);
				break;
			case "unlink":
				commands.unlink.main(args, prefix, event);
				break;
			case "support":
				commands.support.main(args, prefix, event);
				break;
			case "broken":
				commands.broken.main(args, prefix, event, 0);
				break;
			case "allscores":
				commands.allscores.main(args, prefix, event, 0);
				break;
			case "botstats":
				commands.botstats.main(args, prefix, event);
				break;
			case "invite":
				commands.invite.main(args, prefix, event);
				break;
			case "farm":
				commands.farm.main(args,prefix,event,0);
				break;
			case "tryhard":
				commands.farm.main(args, prefix, event, 0);
				break;
			case "recent":
				commands.recent.main(args, prefix, event);
				break;
			default:
				event.getMessage().getChannel().sendMessage("This command doesn't exit. Type " + prefix + "commands to get all available commands.").queue();
				break;
		};
		
	}
	
	@Override
	public void onReady(ReadyEvent ready) {
		System.out.println("---------------------");
		System.out.println("Connected to " + ready.getGuildAvailableCount() + " out of " + ready.getGuildTotalCount() + " guilds\n---------------------");
		for(int i=0;i<ready.getGuildAvailableCount();i++) {
			String name = ready.getJDA().getGuilds().get(i).getName();
			String user = ready.getJDA().getGuilds().get(i).getMembers().size() +"";
			System.out.println("Guild: "+name+"\nUsercount: "+user+"\n-");
		}
		System.out.println("---------------------");
	}
	
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		System.out.println("Joined the Guild: "+event.getGuild().getName()+"\nUsercount: "+event.getGuild().getMembers().size()+"\n---------------------");
	}
	
	@Override
	public void onGuildLeave(GuildLeaveEvent event) {
		System.out.println("Left the Guild: "+event.getGuild().getName()+"\nUsercount: "+event.getGuild().getMembers().size()+"\n---------------------");
	}
	
}
