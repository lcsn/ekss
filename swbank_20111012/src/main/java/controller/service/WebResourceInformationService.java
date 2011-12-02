package controller.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import model.WebResourceInformation;
import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import util.RequestType;
import util.TargetType;

@Stateless
public class WebResourceInformationService extends GenericService {
	
	@Inject
	@Category("webResourceInformationService")
	private Logger log;
	

	public WebResourceInformation findWebResourceInformationById(Long id) {
		log.trace("findWebResourceInformationById");
		Query q = em.createNamedQuery(WebResourceInformation.FIND_BY_ID);
		q.setParameter("id", id);
		return (WebResourceInformation) q.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<WebResourceInformation> findWebResourceInformationsByBankCode(String bankCode) {
		log.trace("findWebResourceInformationsByBankCode");
		Query q = em.createNamedQuery(WebResourceInformation.FIND_BY_BANKCODE);
		q.setParameter("bankCode", bankCode);
		return (List<WebResourceInformation>) q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WebResourceInformation> findWebResourceInformationsByBankCodeAndMediaType(String bankCode, String mediaType) {
		log.trace("findWebResourceInformationsByBankCode");
		Query q = em.createNamedQuery(WebResourceInformation.FIND_BY_BANKCODE_AND_MEDIATYPE);
		q.setParameter("bankCode", bankCode);
		q.setParameter("mediaType", mediaType);
		return (List<WebResourceInformation>) q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WebResourceInformation> findWebResourceInformationsByBankCodeAndMediaTypeAndRequestType(String bankCode, String mediaType, RequestType requestType) {
		log.trace("findWebResourceInformationsByBankCode");
		Query q = em.createNamedQuery(WebResourceInformation.FIND_BY_BANKCODE_AND_MEDIATYPE_AND_REQUESTTYPE);
		q.setParameter("bankCode", bankCode);
		q.setParameter("mediaType", mediaType);
		q.setParameter("requestType", requestType);
		return (List<WebResourceInformation>) q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public WebResourceInformation findWebResourceInformationsByBankCodeAndMediaTypeAndRequestType(String bankCode, String mediaType, RequestType requestType, TargetType targetType) {
		log.trace("findWebResourceInformationsByBankCode");
		Query q = em.createNamedQuery(WebResourceInformation.FIND_BY_BANKCODE_AND_MEDIATYPE_AND_REQUESTTYPE_AND_TARGETTYPE);
		q.setParameter("bankCode", bankCode);
		q.setParameter("mediaType", mediaType);
		q.setParameter("requestType", requestType);
		q.setParameter("targetType", targetType);
		return (WebResourceInformation) q.getResultList();
	}
	
}
