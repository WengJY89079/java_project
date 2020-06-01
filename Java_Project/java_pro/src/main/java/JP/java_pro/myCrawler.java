package JP.java_pro;

import java.util.regex.Pattern;
import javax.sound.sampled.LineListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.*;

public class myCrawler extends WebCrawler{
	public static int Page = 2;
	public static int imgLimit = 50;
	public static int count = 0;
	private final static Pattern FILTERS = Pattern.compile(".*(.(gif|jpe?g|png))"); // imgur filters
	public static List<String> seed = new ArrayList<String>();
	@Override
	public boolean shouldVisit(Page referring, WebURL url) {
		String href = url.getURL().toLowerCase();
		return href.startsWith("https://www.ptt.cc/bbs/c_chat/m");
	}
	
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		//System.out.println("URL: " + url);
		if(page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			Set<WebURL> links = htmlParseData.getOutgoingUrls();
			Iterator<WebURL> linkIterator = links.iterator();
			while ( linkIterator.hasNext() && count < imgLimit) {
				String nextLinkUrl = linkIterator.next().toString();
				if(FILTERS.matcher(nextLinkUrl).matches() && !nextLinkUrl.contains("bbs/C_Chat/search?q=") ) {
					++count;
					seed.add(nextLinkUrl);
				}
				if (nextLinkUrl.contains("?page=" + Page) ) {
					
					String prelink = nextLinkUrl.split("&")[0];
					
					if (prelink.equals("https://www.ptt.cc/bbs/C_Chat/search?page=" + Page) ) {
						System.out.println("*****************");
						System.out.println("Find same topic previous Page : " + nextLinkUrl);
						Page += 1;
						this.myController.addSeed(nextLinkUrl);
					}
				}
			}
		}
	}
}
