package edu.axiacore.freedm.controller.documentcommand;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.axiacore.freedm.entity.Document;

public class TextDocument implements DocumentCommand {

	/**
	 * Identificador de la implementacion del documento
	 */
	public static final String contentType = "text/plain";
	
	protected TextDocument() {
	}
	
	@Override
	public Document getCurrentVersion(EntityManager entityManager, String fileName) {
		Query query = entityManager.createQuery("from Document where fileName = :filename");
		query.setParameter("filename", fileName);
		
		List<Document> resultList = query.getResultList();
		if(resultList != null && resultList.size() > 0) {
			return resultList.get(resultList.size()-1);
		}
		return null;
	}
	
	@Override
	public Document getDocument(EntityManager entityManager, Long id) {
		Query query = entityManager.createQuery("from Document where id = :id");
		query.setParameter("id", id);
		
		return (Document) query.getSingleResult();
	}

	@Override
	public Document getEspecificVersion(EntityManager entityManager, String fileName, Long version) {
		Query query = entityManager.createQuery("from Document where fileName = :filename and version = :version ");
		query.setParameter("filename", fileName);
		query.setParameter("version", version);
		
		List<Document> resultList = query.getResultList();
		if(resultList != null && resultList.size() > 0) {
			return resultList.get(resultList.size()-1);
		}
		return null;
	}

	@Override
	public Document register(EntityManager entityManager, String fileName, byte[] fileBytes, String userName) {
		Document document = null;
		Date today = new Date(System.currentTimeMillis());
		String userId = userName;
		Long version = 1L;

		//Verificamos si existe previamente
		document = getCurrentVersion(entityManager, fileName);
		if(document != null) {
			//Actualizar la version anterior
			version += document.getVersion();
		}
		
		document = new Document(version, contentType, fileName, userId, today, fileBytes);
		entityManager.persist(document);
		
		return document;
	}

	@Override
	public void delete(EntityManager entityManager, Document document) {
    	entityManager.remove(entityManager.merge(document));
    	document = null;
	}

}
