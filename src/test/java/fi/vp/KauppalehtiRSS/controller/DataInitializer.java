package fi.vp.KauppalehtiRSS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fi.vp.KauppalehtiRSS.model.FeedItem;

@Component
@Scope("prototype")
public class DataInitializer {

	public static final int FEEDITEM_COUNT = 3;

	@PersistenceContext
	private EntityManager entityManager;

	public List<Long> feedItems = new ArrayList<Long>();

	public void initData() {
		feedItems.clear();// clear out the previous list of people
		addFeedItem("Uutiset", "Suuri jymy juttu1", "Osakkeet pamahti nousuun", "12/12/12", "", "");
		addFeedItem("Uutiset", "Suuri jymy juttu2", "Osakkeet pamahti nousuun", "13/12/12", "", "");
		addFeedItem("Nokkoset", "Suuri jymy juttu3", "Osakkeet pamahti nousuun", "14/12/12", "", "");
		entityManager.flush();
		entityManager.clear();
	}

	public void addFeedItem(String category, String title, String description, String date, String imageURL, String link) {
		FeedItem feedItem = new FeedItem(category, title, description, date, imageURL, link);
		entityManager.persist( feedItem );
		feedItems.add(feedItem.getId());
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
