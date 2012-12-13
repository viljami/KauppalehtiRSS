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
@RequestMapping("/feed/")
public class FeedController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

	@Autowired
	private FeedItemDao feedItemDao;
	
	@RequestMapping(method=RequestMethod.GET,value="edit-list-item")
	public ModelAndView editFeedItem(@RequestParam(value="id",required=false) Long id) {		
		ModelAndView mav = new ModelAndView();		
 		mav.setViewName("edit-list-item");
 		FeedItem feedItem = null;
 		if (id == null) {
 			feedItem = new FeedItem();
 		} else {
 			feedItem = feedItemDao.find(id);
 		}
 		
 		mav.addObject("feedItem", feedItem);
		return mav;
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="edit-list-item") 
	public String saveFeedItem(@ModelAttribute FeedItem feedItem) {
		feedItemDao.save(feedItem);
		return "redirect:feed";
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/")
	public ModelAndView getFeed() {
		ModelAndView mav = new ModelAndView();
		List<FeedItem> feedItems = feedItemDao.getFeedItems();
		mav.addObject("feedItems", feedItems);
		mav.setViewName("feed");
		return mav;
	}

}