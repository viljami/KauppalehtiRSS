package fi.vp.KauppalehtiRSS.controller;

import java.io.IOException;
import java.util.List;
/*
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.stream.XMLInputFactory;
*/
//import java.util.Date;
//import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fi.vp.KauppalehtiRSS.dao.FeedItemDao;
import fi.vp.KauppalehtiRSS.model.FeedItem;

/**
 * Sample controller for going to the home page with a message
 * 
 * Archetype used ?
 * spring-mvc-jpa-archetype
 * OR
 * spring-mvc-webapp
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private FeedItemDao feedItemDao;
	
	private static String FEEDURL1 = "http://rss.kauppalehti.fi/rss/etusivun_uutiset.jsp";
	private static String FEEDURL2 = "http://rss.kauppalehti.fi/rss/etusivun_mobiili.jsp";
	private static String FEEDURL3 = "http://rss.kauppalehti.fi/rss/omaraha.jsp";
	private static String FEEDURL4 = "http://rss.kauppalehti.fi/rss/yritysuutiset.jsp";

	private static Boolean firstLoad = true;
	
	private class LoadContent implements Runnable {

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
	/**
	 * Selects the home page and populates the model with a message
	 * @throws IOException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Model model) throws IOException {
		/*
		logger.info("Welcome home, darling!");
		model.addAttribute("controllerMessage",
				"This is the message from the controller! Jiiihhaaaa! ");
		
		return "home";
		
		String[] rss = new Array();
		try {
	        URL oracle = new URL("http://rss.kauppalehti.fi/rss/etusivun_uutiset.jsp");
	        URLConnection yc = oracle.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	        yc.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) { 
	            rss += inputLine + "";
	        }
	        in.close();
		} catch( Exception e ) {
			feedItemDao.save(new FeedItem( "Error", "Feed fetch fail. ", "#nolink", "Sorry for that.", new Date()));
		}
		
		*/
		if( firstLoad ) {
			firstLoad = false;
			new ConcurrentTaskExecutor().execute( new LoadContent() );
		}
		//feedItemDao.save(new FeedItem("moi","hei","iloo","kiikku", "12/12/!2"));
		
		ModelAndView mav = new ModelAndView();
		List<FeedItem> feedItems = feedItemDao.getFeedItems();
		mav.addObject("feedItems", feedItems);
		mav.setViewName("feed");
		return mav;
	}

}
