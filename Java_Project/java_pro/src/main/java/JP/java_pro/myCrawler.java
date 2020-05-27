package JP.java_pro;

import java.util.regex.Pattern;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.*;

public class myCrawler extends WebCrawler{
	public int Page = 2;
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
			while ( linkIterator.hasNext() ) {
				String nextLinkUrl = linkIterator.next().toString();
				if(FILTERS.matcher(nextLinkUrl).matches()) {
					seed.add(nextLinkUrl);
				}
				if (nextLinkUrl.contains("?page=" + Page) ) {
					
					String prelink = nextLinkUrl.split("&")[0];
					
					if (prelink.equals("https://www.ptt.cc/bbs/C_Chat/search?page=" + Page)) {
						System.out.println("*****************");
						System.out.println("Find previous Page : " + nextLinkUrl);
						Page += 1;
					}
					this.myController.addSeed(nextLinkUrl);
				}
			}
		}
	}
}
