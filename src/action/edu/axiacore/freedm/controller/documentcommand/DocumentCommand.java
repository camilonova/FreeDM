package edu.axiacore.freedm.controller.documentcommand;

import javax.persistence.EntityManager;

import edu.axiacore.freedm.entity.Document;

public interface DocumentCommand {
	
	/**
	 * Este metodo ingresa un documento al repositorio. Verifica si el archivo
	 * ya se encuentra bajo control de version, en caso de ser cierto aumenta a
	 * la version siguiente, si no, lo crea en el repositorio
	 */
	public Document register(EntityManager entityManager, String fileName, byte[] fileBytes, String userName);

	/**
	 * Retorna el documento identificado con el parametro
	 * @param id
	 * @return
	 */
	public Document getDocument(EntityManager entityManager, Long id);

	/**
	 * Elimina la ultima version del archivo del repositorio
	 */
	public void delete(EntityManager entityManager, Document document);

	/**
	 * Retorna la ultima version del documento en el repositorio
	 */
	public Document getCurrentVersion(EntityManager entityManager, String fileName);

	/**
	 * Retorna la version especificada del documento. Notese que no debe ser
	 * mayor que la version actual
	 */
	public Document getEspecificVersion(EntityManager entityManager, String fileName, Long version);

}
