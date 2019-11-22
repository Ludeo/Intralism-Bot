package commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class top10 {
	
	public static void main(String[] args, String prefix, MessageReceivedEvent event) {
		try {
			Document doc = Jsoup.connect("https://intralism.khb-soft.ru/?page=ranks").get();
			String profileInfo = doc.toString();
			BufferedReader bufReader = new BufferedReader(new StringReader(profileInfo));
			String line=null;
			String onename="", twoname="", threename="", fourname="", fivename="", sixname="", sevenname="", eightname="", ninename="", tenname="";
			double onepoints=0, twopoints=0, threepoints=0, fourpoints=0, fivepoints=0, sixpoints=0, sevenpoints=0, eightpoints=0, ninepoints=0, tenpoints=0;
			double oneacc=0, twoacc=0, threeacc=0, fouracc=0, fiveacc=0, sixacc=0, sevenacc=0, eightacc=0, nineacc=0, tenacc=0;
			while((line=bufReader.readLine()) != null) {
				 
				if(line.contains("middle\">1<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					onename = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					onepoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					oneacc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				} else if(line.contains("middle\">2<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					twoname = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					twopoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					twoacc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				} else if(line.contains("middle\">3<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					threename = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					threepoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					threeacc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				} else if(line.contains("middle\">4<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					fourname = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					fourpoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					fouracc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				} else if(line.contains("middle\">5<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					fivename = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					fivepoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					fiveacc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				} else if(line.contains("middle\">6<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					sixname = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					sixpoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					sixacc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				} else if(line.contains("middle\">7<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					sevenname = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					sevenpoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					sevenacc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				} else if(line.contains("middle\">8<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					eightname = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					eightpoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					eightacc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				} else if(line.contains("middle\">9<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					ninename = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					ninepoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					nineacc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				} else if(line.contains("middle\">10<")) {
					line = bufReader.readLine();
					int anumber = line.indexOf("2em;\">");
					int bnumber = line.indexOf("</td>", anumber);
					tenname = line.substring(anumber+7,bnumber);
					bufReader.readLine();
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("</td>");
					tenpoints = Double.parseDouble(line.substring(anumber+2,bnumber).replaceAll(" ", ""));
					line = bufReader.readLine();
					anumber = line.indexOf("\">");
					bnumber = line.indexOf("%");
					tenacc = Double.parseDouble(line.substring(anumber+2,bnumber));
					
				}
			}
			
			EmbedBuilder eb = new EmbedBuilder();
			eb.addField(onename+"#1", onepoints + "\n" + oneacc + "%", true);
			eb.addField(twoname+"#2", twopoints + "\n" + twoacc + "%", true);
			eb.addField(threename+"#3", threepoints + "\n" + threeacc + "%", true);
			eb.addField(fourname+"#4", fourpoints + "\n" + fouracc + "%", true);
			eb.addField(fivename+"#5", fivepoints + "\n" + fiveacc + "%", true);
			eb.addField(sixname+"#6", sixpoints + "\n" + sixacc + "%", true);
			eb.addField(sevenname+"#7", sevenpoints + "\n" + sevenacc + "%", true);
			eb.addField(eightname+"#8", eightpoints + "\n" + eightacc + "%", true);
			eb.addField(ninename+"#9", ninepoints + "\n" + nineacc + "%", true);
			eb.addField(tenname+"#10", tenpoints + "\n" + tenacc + "%", true);
			eb.setAuthor("Intralism Top 10 Ranking", "https://intralism.khb-soft.ru/?page=ranks","https://intralism.khb-soft.ru/images/logo.png");
			Color yellow = new Color(255,215,0);
			eb.setColor(yellow);
			
			event.getMessage().getChannel().sendMessage(eb.build()).queue();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
