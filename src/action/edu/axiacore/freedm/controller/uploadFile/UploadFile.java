package edu.axiacore.freedm.controller.uploadFile;

import java.io.InputStream;

import javax.ejb.Local;

@Local
public interface UploadFile {

	/**
	 * Este metodo es llamado para cargar el archivo desde un control richfaces
	 */
	public void upload();

	public void setFile(InputStream file);

	public InputStream getFile();

	public String getContentType();

	public void setContentType(String contentType);

	public String getFileName();

	public void setFileName(String fileName);

	public Integer getFileSize();

	public void setFileSize(Integer fileSize);

}