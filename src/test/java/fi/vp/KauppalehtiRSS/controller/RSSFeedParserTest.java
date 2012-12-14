package fi.vp.KauppalehtiRSS.controller;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import fi.vp.KauppalehtiRSS.model.FeedItem;

public class RSSFeedParserTest {

	private static String FEEDURL1 = "http://rss.kauppalehti.fi/rss/etusivun_uutiset.jsp";
	
	@Test
	public void testController() throws IOException {
		RSSFeedParser parser = new RSSFeedParser();
		List<FeedItem> items = parser.readFeed( FEEDURL1 );
		System.out.println("HEI!");
		Assert.assertNotNull( items );
		
				
	}
	
}
