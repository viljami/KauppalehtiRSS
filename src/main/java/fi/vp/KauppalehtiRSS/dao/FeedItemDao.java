package fi.vp.KauppalehtiRSS.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fi.vp.KauppalehtiRSS.model.FeedItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FeedItemDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public FeedItem find(Long id) {
		return entityManager.find(FeedItem.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<FeedItem> getFeedItems() {
		return entityManager.createQuery("select p from FeedItem p").getResultList();
	}
	
	@Transactional
	public FeedItem save(FeedItem feedItem) {
		if (feedItem.getId() == null) {
			entityManager.persist(feedItem);
			return feedItem;
		} else {
			return entityManager.merge(feedItem);
		}		
	}	
	
}
