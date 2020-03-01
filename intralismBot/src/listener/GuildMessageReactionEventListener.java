package listener;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessageReactionEventListener extends ListenerAdapter {
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
    	
    	if(event.getUser().isBot()) {
    		return;
    	}

    	new MessageHistory(event.getChannel()).retrievePast(1).queue(messages -> {
    		if(!messages.get(0).getAuthor().getId().contentEquals("340873693464887298")) {
    			return;
    		}
    		
    		if(messages.get(0).getId().contentEquals(event.getMessageId())) {
    			try {
	    			MessageEmbed eb = messages.get(0).getEmbeds().get(0);
	        		String msg = eb.toData().toString();
	        		
	        		if(eb.getTitle() == null) {
	     
	        		} else if(eb.getTitle().contentEquals("All commands")) {
	        			String reaction = event.getReactionEmote().getEmoji();
	        			int anum = msg.indexOf("footer");
	            		int bnum = msg.indexOf("/",anum);
	            		String page = msg.substring(anum+22,bnum);
	            		if(page.contentEquals("1")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.commands.main(args,"i!",event1,2);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.commands.main(args,"i!",event1,3);
	                		}
	                		
	            		} else if(page.contentEquals("2")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.commands.main(args,"i!",event1,3);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.commands.main(args,"i!",event1,1);
	                		}
	                		
	            		}  else if(page.contentEquals("3")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.commands.main(args,"i!",event1,1);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.commands.main(args,"i!",event1,2);
	                		}
	                		
	            		}
	            		
	        		} else if(eb.getTitle().contentEquals("All Scores")) {
	        			String reaction = event.getReactionEmote().getEmoji();
	        			int anum = msg.indexOf("footer");
	            		int bnum = msg.indexOf("/",anum);
	            		String page = msg.substring(anum+22,bnum);
	            		
	            		if(page.contentEquals("1")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,2);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,29);
	                		}
	                		
	            		} else if(page.contentEquals("2")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,3);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,1);
	                		}
	                		
	            		} else if(page.contentEquals("3")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,4);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,2);
	                		}
	                		
	            		} else if(page.contentEquals("4")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,5);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,3);
	                		}
	                		
	            		} else if(page.contentEquals("5")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,6);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,4);
	                		}
	                		
	            		} else if(page.contentEquals("6")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,7);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,5);
	                		}
	                		
	            		} else if(page.contentEquals("7")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,8);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,6);
	                		}
	                		
	            		} else if(page.contentEquals("8")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,9);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,7);
	                		}
	                		
	            		} else if(page.contentEquals("9")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,10);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,8);
	                		}
	                		
	            		} else if(page.contentEquals("10")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,11);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,9);
	                		}
	                		
	            		} else if(page.contentEquals("11")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,12);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,10);
	                		}
	                		
	            		} else if(page.contentEquals("12")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,13);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,11);
	                		}
	                		
	            		} else if(page.contentEquals("13")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,14);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,12);
	                		}
	                		
	            		} else if(page.contentEquals("14")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,15);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,13);
	                		}
	                		
	            		} else if(page.contentEquals("15")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,16);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,14);
	                		}
	                		
	            		} else if(page.contentEquals("16")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,17);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,15);
	                		}
	                		
	            		} else if(page.contentEquals("17")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,18);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,16);
	                		}
	                		
	            		} else if(page.contentEquals("18")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,19);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,17);
	                		}
	                		
	            		} else if(page.contentEquals("19")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,20);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,18);
	                		}
	                		
	            		} else if(page.contentEquals("20")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,21);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,19);
	                		}
	                		
	            		} else if(page.contentEquals("21")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,22);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,20);
	                		}
	                		
	            		} else if(page.contentEquals("22")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,23);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,21);
	                		}
	                		
	            		} else if(page.contentEquals("23")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,24);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,22);
	                		}
	                		
	            		} else if(page.contentEquals("24")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,25);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,23);
	                		}
	                		
	            		} else if(page.contentEquals("25")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,26);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,24);
	                		}
	                		
	            		} else if(page.contentEquals("26")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,27);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,25);
	                		}
	                		
	            		} else if(page.contentEquals("27")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,28);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,26);
	                		}
	                		
	            		} else if(page.contentEquals("28")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,29);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,27);
	                		}
	                		
	            		} else if(page.contentEquals("29")) {
	            			MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.allscores.main(args,"i!",event1,1);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.allscores.main(args,"i!",event1,28);
	                		}
	                		
	            		}
	            		
	        		} else if(eb.getTitle().contentEquals("All broken maps")) {
	        			String reaction = event.getReactionEmote().getEmoji();
	        			int anum = msg.indexOf("footer");
	            		int bnum = msg.indexOf("/",anum);
	            		String page = msg.substring(anum+22,bnum);
	            		
	        			if(page.contentEquals("0")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.broken.main(args,"i!",event1,2);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.broken.main(args,"i!",event1,4);
	                		}
	        			} else if(page.contentEquals("1")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.broken.main(args,"i!",event1,2);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.broken.main(args,"i!",event1,4);
	                		}
	        			} else if(page.contentEquals("2")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.broken.main(args,"i!",event1,3);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.broken.main(args,"i!",event1,1);
	                		}
	        			} else if(page.contentEquals("3")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.broken.main(args,"i!",event1,4);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.broken.main(args,"i!",event1,2);
	                		}
	        			} else if(page.contentEquals("4")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.broken.main(args,"i!",event1,1);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.broken.main(args,"i!",event1,3);
	                		}
	        			}
	        			
	        		} else if (eb.getTitle().contentEquals("All Available Points")) {
	        			String reaction = event.getReactionEmote().getEmoji();
	        			int anum = msg.indexOf("footer");
	            		int bnum = msg.indexOf("/",anum);
	            		String page = msg.substring(anum+22,bnum);
	            		
	        			if(page.contentEquals("0")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,2);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,14);
	                		}
	                		
	        			} else if(page.contentEquals("1")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,2);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,14);
	                		}
	                		
	        			} else if(page.contentEquals("2")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,3);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,1);
	                		}
	                		
	        			} else if(page.contentEquals("3")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,4);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,2);
	                		}
	                		
	        			} else if(page.contentEquals("4")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,5);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,3);
	                		}
	                		
	        			} else if(page.contentEquals("5")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,6);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,4);
	                		}
	                		
	        			} else if(page.contentEquals("6")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,7);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,5);
	                		}
	                		
	        			} else if(page.contentEquals("7")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,8);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,6);
	                		}
	                		
	        			} else if(page.contentEquals("8")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,9);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,7);
	                		}
	                		
	        			} else if(page.contentEquals("9")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,10);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,8);
	                		}
	                		
	        			} else if(page.contentEquals("10")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,11);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,9);
	                		}
	                		
	        			} else if(page.contentEquals("11")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,12);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,10);
	                		}
	                		
	        			} else if(page.contentEquals("12")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,13);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,11);
	                		}
	                		
	        			} else if(page.contentEquals("13")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,14);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,12);
	                		}
	                		
	        			} else if(page.contentEquals("14")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,15);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,13);
	                		}
	                		
	        			} else if(page.contentEquals("15")) {
	        				MessageReceivedEvent event1 = new MessageReceivedEvent(event.getJDA(), 0, messages.get(0));
	                		String[] args = {""};
	                		if(reaction.contentEquals("▶")) {
	                			commands.farm.main(args,"i!",event1,1);
	                		} else if(reaction.contentEquals("◀")) {
	                			commands.farm.main(args,"i!",event1,14);
	                		}
	                		
	        			}
	        			
	        		} else {
	        			return;
	        		}
    			} catch (IndexOutOfBoundsException e) {
    				
    			}
    		}
        });
    	
    }
}