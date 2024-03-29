package fi.vp.KauppalehtiRSS.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import fi.vp.KauppalehtiRSS.dao.FeedItemDao;
import fi.vp.KauppalehtiRSS.model.FeedItem;

@Controller
public class HomeController {

	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private static String FEEDURL1 = "http://rss.kauppalehti.fi/rss/yritysuutiset.jsp";
	private static String FEEDURL2 = "http://rss.kauppalehti.fi/rss/auto.jsp";
	private static String FEEDURL3 = "http://rss.kauppalehti.fi/rss/omaraha.jsp";
	private static String FEEDURL4 = "http://rss.kauppalehti.fi/rss/etusivun_uutiset.jsp";

	private static Boolean firstLoad = true;
	private static ConcurrentTaskExecutor taskExecutor = null;

	@Autowired
	private FeedItemDao feedItemDao;
	
	private ContentLoader contentLoader = null;
	
	/**
	 * Loads the RSS feeds and updates the results into database feed by feed.
	 * 
	 * @author viljami
	 *
	 */
	private class ContentLoader implements Runnable {
		public void run() {
			RSSFeedParser feedParser = new RSSFeedParser();
			List<FeedItem> feedItems = feedParser.readFeed( FEEDURL1 );
			for(int i = 0; i < feedItems.size(); i++) {
				feedItemDao.save(feedItems.get( i ));
			}
			feedItems = feedParser.readFeed( FEEDURL2 );
			for(int i = 0; i < feedItems.size(); i++) {
				feedItemDao.save(feedItems.get( i ));
			}
			feedItems = feedParser.readFeed( FEEDURL3 );
			for(int i = 0; i < feedItems.size(); i++) {
				feedItemDao.save(feedItems.get( i ));
			}
			feedItems = feedParser.readFeed( FEEDURL4 );
			for(int i = 0; i < feedItems.size(); i++) {
				feedItemDao.save(feedItems.get( i ));
			}
		}
	}
	
	@RequestMapping( value = "/", method = RequestMethod.GET )
	public ModelAndView home(Model model) throws IOException {
		if ( firstLoad ) {
			firstLoad = false;
			taskExecutor = new ConcurrentTaskExecutor();
			contentLoader = new ContentLoader();
			//Timer timer = new Timer();
			//timer.scheduleAtFixedRate( loadContent, 300000, 10000);
			taskExecutor.execute( contentLoader );
		}
		
		ModelAndView mav = new ModelAndView();
		List<FeedItem> feedItems = feedItemDao.getFeedItems();
		mav.addObject( "feedItems", feedItems );
		mav.setViewName( "feed" );
		return mav;
	}
	
	@RequestMapping( value = "/feedItems", method = RequestMethod.GET )
	public ModelAndView getFeedItems ( Model model ) throws IOException {
		ModelAndView mav = new ModelAndView();
		List<FeedItem> feedItems = feedItemDao.getFeedItems();
		
		Gson gson = new Gson();
		String feedItemsJSON = gson.toJson( feedItems );
		
		mav.addObject( "json", feedItemsJSON );
		
		mav.setViewName( "json" );
		return mav;
	}
}

