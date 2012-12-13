package fi.vp.KauppalehtiRSS.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fi.vp.KauppalehtiRSS.dao.FeedItemDao;
import fi.vp.KauppalehtiRSS.model.FeedItem;

/**
 * Sample controller for going to the home page with a message
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	private FeedItemDao feedItemDao;
	
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
		*/
		String rss = "";
		try {
	        URL oracle = new URL("http://rss.kauppalehti.fi/rss/etusivun_uutiset.jsp");
	        URLConnection yc = oracle.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	        yc.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) { 
	            rss += inputLine + ",";
	        }
	        in.close();
		} catch( Exception e ) {
			feedItemDao.save(new FeedItem( "Feed fetch fail. ", "Sorry for that.", new Date()));
		}
		
		feedItemDao.save(new FeedItem( rss, "topic1", new Date()));
		feedItemDao.save(new FeedItem( "header2", "topic2", new Date()));
		
		ModelAndView mav = new ModelAndView();
		List<FeedItem> feedItems = feedItemDao.getFeedItems();
		mav.addObject("feedItems", feedItems);
		mav.setViewName("feed");
		return mav;
	}
	
}

/*
<?xml version="1.0" encoding="ISO-8859-1"?>
<rss xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:atom="http://www.w3.org/2005/Atom" version="2.0">   
<channel>
	<title>Etusivun uutiset | Kauppalehti.fi</title>
		<image>
			<url>http://kuvat.kauppalehti.fi/5/i/img/logo_kl_igoogle.gif</url>
				<title>Etusivun uutiset | Kauppalehti.fi</title>,         
				<link>http://www.kauppalehti.fi/</link>,      
		</image>
		<description>Kauppalehti.fi:n etusivulta n?et nopeasti yhdell? silm?yksell? p?iv?n t?rkeimm?t talousuutiset ja -tapahtumat.</description>
		<link>http://www.kauppalehti.fi</link>,      <language>fi</language>
			<atom:link href="http://rss.kauppalehti.fi/rss/etusivun_uutiset.jsp" rel="self" type="application/rss+xml"/>
		<item>,         <title>UM: Kehitysavusta vahinkoja 415 000 euroa</title>
			<link>http://www.kauppalehti.fi/etusivu/um+kehitysavusta+vahinkoja+415+000+euroa/201212321013&amp;ext=rss</link>
			<guid>http://www.kauppalehti.fi/etusivu/um+kehitysavusta+vahinkoja+415+000+euroa/201212321013&amp;ext=rss</guid>
			<description>Helsingin Sanomat uutisoi eilen, ett? v??rink?yt?ksi? tulee esiin ministeri?n omassa valvonnassa vuosittain noin 10 miljoonan euron verran.</description>
			<pubDate>Wed, 12 Dec 2012 17:20:47 +0200</pubDate>
			<dc:date>2012-12-12T17:20:47+02:00</dc:date>
		</item>
		<item>,         <title>Kilometrikorvausten leikkaus raivostuttaa</title>,         <link>http://www.kauppalehti.fi/auto/uutiset/kilometrikorvausten+leikkaus+raivostuttaa/201212321009&amp;ext=rss</link>,         <guid>http://www.kauppalehti.fi/auto/uutiset/kilometrikorvausten+leikkaus+raivostuttaa/201212321009&amp;ext=rss</guid>,         <description>Autoilijoita raivostuttaa eduskunnan p??t?s pienent?? ty?ajoista maksettavia verottomia kilometrikorvauksia.</description>,         <pubDate>Wed, 12 Dec 2012 17:20:45 +0200</pubDate>,         <dc:date>2012-12-12T17:20:45+02:00</dc:date>,      </item>,      <item>,         <title>Lumiat vied??n Ruotsissa k?sist?</title>,         <link>http://www.kauppalehti.fi/etusivu/lumiat+vieda
*/