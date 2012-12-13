package fi.vp.KauppalehtiRSS.controller;

import java.util.List;

import fi.vp.KauppalehtiRSS.dao.FeedItemDao;
import fi.vp.KauppalehtiRSS.model.FeedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/feedItem/")
public class FeedItemController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedItemController.class);

	@Autowired
	private FeedItemDao feedItemDao;
	
	@RequestMapping(method=RequestMethod.GET,value="edit")
	public ModelAndView editFeedItem(@RequestParam(value="id",required=false) Long id) {		
		ModelAndView mav = new ModelAndView();		
 		mav.setViewName("edit");
 		FeedItem feedItem = null;
 		if (id == null) {
 			feedItem = new FeedItem();
 		} else {
 			feedItem = feedItemDao.find(id);
 		}
 		
 		mav.addObject("feedItem", feedItem);
		return mav;
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="edit") 
	public String saveFeedItem(@ModelAttribute FeedItem feedItem) {
		feedItemDao.save(feedItem);
		return "redirect:list";
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="list")
	public ModelAndView listFeedItems() {
		ModelAndView mav = new ModelAndView();
		List<FeedItem> feedItems = feedItemDao.getFeedItems();
		mav.addObject("feedItems", feedItems);
		mav.setViewName("feed");
		return mav;
		
	}

}
