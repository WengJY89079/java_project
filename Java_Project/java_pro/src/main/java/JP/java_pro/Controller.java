package JP.java_pro;

import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.*;
import javax.swing.text.html.HTMLDocument.Iterator;
import org.omg.CORBA.PUBLIC_MEMBER;
import java.util.*;

public class Controller {
	public static void main(String[] args) throws Exception{
		
		List<String> seedNext = new ArrayList<String>();
		
		Scanner input = new Scanner(System.in);
		String searchString;
		String crawlStorageFolder = "/data/crawl/root";
		int numberOfCrawlers = 1;
		
		searchString = input.nextLine();
		
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtconfig = new RobotstxtConfig();
		RobotstxtServer robotstxtserver = new RobotstxtServer(robotstxtconfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtserver);
		
		controller.addSeed("https://www.ptt.cc/bbs/C_Chat/search?q=" + searchString);
		
		controller.start(myCrawler.class, numberOfCrawlers);
		
		seedNext = myCrawler.seed;
		java.util.Iterator<String> iterator = seedNext.iterator();
		while (iterator.hasNext()) {
			String outputString = iterator.next();
			System.out.println(outputString);
		}
	}
}
