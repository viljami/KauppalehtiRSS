package fi.vp.KauppalehtiRSS.controller;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import fi.vp.KauppalehtiRSS.model.FeedItem;

public class RSSFeedParserTest {

	private static String FEEDURL1 = "http://rss.kauppalehti.fi/rss/etusivun_uutiset.jsp";
	
	@Test
	public void testController() throws IOException {
		RSSFeedParser parser = new RSSFeedParser();
		List<FeedItem> items = parser.readFeed( FEEDURL1 );
		Assert.assertNotNull( items );
	}
	
}
