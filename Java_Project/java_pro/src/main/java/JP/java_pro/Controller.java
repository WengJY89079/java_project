package JP.java_pro;

import edu.uci.ics.crawler4j.crawler.*;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.*;
import java.util.*;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Controller {
	public static void saveImage ( String imageUrl ) throws IOException {
		URL url = new URL ( imageUrl );
		String fileName = url.getFile ( );

		String destName = "./figures" + fileName.substring ( fileName.lastIndexOf ( "/" ) );
		System.out.println ( destName );

		InputStream is = url.openStream ( );
		OutputStream os = new FileOutputStream ( destName );

		byte[] b = new byte[2048];
		int length;

		while ( ( length = is.read ( b ) ) != -1 ) {
			os.write ( b , 0 , length );
		}

		is.close ( );
		os.close ( );
	}

	public void searchPic ( String keyword ) throws Exception {

		List < String > seedNext = new ArrayList < String > ( );

		//Scanner input = new Scanner ( System.in );
		//System.out.println ( "input Keyword : " );
		//String searchString;
		String crawlStorageFolder = "./data/crawl/root";
		int numberOfCrawlers = 1;

		//searchString = input.nextLine ( );

		CrawlConfig config = new CrawlConfig ( );
		config.setCrawlStorageFolder ( crawlStorageFolder );

		PageFetcher pageFetcher = new PageFetcher ( config );
		RobotstxtConfig robotstxtconfig = new RobotstxtConfig ( );
		RobotstxtServer robotstxtserver = new RobotstxtServer ( robotstxtconfig, pageFetcher );
		CrawlController controller = new CrawlController ( config, pageFetcher, robotstxtserver );

		controller.addSeed ( "https://www.ptt.cc/bbs/C_Chat/search?q=" + keyword );

		controller.start ( myCrawler.class , numberOfCrawlers );

		seedNext = myCrawler.seed;
		java.util.Iterator < String > iterator = seedNext.iterator ( );
		// Is /figures exist?
		Path check_figures = Paths.get ( "./figures" );
		if ( !Files.exists ( check_figures ) ) {
			System.out.println ( "./figures create" );
			Files.createDirectory ( check_figures );
		}
		else {
			System.out.println ( "./figures exist" );
		}
		
		while ( iterator.hasNext ( ) ) {
			String outputString = iterator.next ( );
			try {	
				saveImage ( outputString );
			}
			catch ( IOException e ) {
				System.out.println ( e );
			}
			System.out.println ( outputString );
		}
	}
}
