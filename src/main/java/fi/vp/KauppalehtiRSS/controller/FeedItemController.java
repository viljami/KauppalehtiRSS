package fi.vp.KauppalehtiRSS.controller;

import java.util.List;

import fi.vp.KauppalehtiRSS.dao.FeedItemDao;
import fi.vp.KauppalehtiRSS.model.FeedItem;

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

	@Autowired
	private FeedItemDao feedItemDao;
	
	@RequestMapping(method=RequestMethod.GET,value="editFeedItem")
	public ModelAndView editFeedItem(@RequestParam(value="id",required=false) Long id) {		
		ModelAndView mav = new ModelAndView();		
 		mav.setViewName("editFeedItem");
 		FeedItem feedItem = null;
 		if (id == null) {
 			feedItem = new FeedItem();
 		} else {
 			feedItem = feedItemDao.find(id);
 		}
 		
 		mav.addObject("feedItem", feedItem);
		return mav;
		
	}
	
	@RequestMapping(method=RequestMethod.POST,value="editFeedItem") 
	public String saveFeedItem(@ModelAttribute FeedItem feedItem) {
		feedItemDao.save(feedItem);
		return "redirect:feed";
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="feed")
	public ModelAndView listFeedItems() {
		ModelAndView mav = new ModelAndView();
		List<FeedItem> feedItems = feedItemDao.getFeedItems();
		mav.addObject("feedItems", feedItems);
		mav.setViewName("feed");
		return mav;
		
	}

}
