package edu.axiacore.freedm.controller.uploadFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.EntityManager;

import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import edu.axiacore.freedm.controller.documentcommand.DocumentCommand;
import edu.axiacore.freedm.controller.documentcommand.DocumentCommandImpl;

@Name("uploadFile")
public class UploadFileBean implements UploadFile {

	private static final Integer maxSize = 10 * 1024 * 1024;

	private InputStream file;

	private String contentType;

	private String fileName;

	private Integer fileSize;
	
	private DocumentCommand documentCommand;
	
	@Logger
	private Log log;

	@In
	FacesMessages facesMessages;
	
	@In 
	EntityManager entityManager;
	
	@In
	Identity identity;

	@Override
	public void upload() {
		log.info("Preparing to uploadFile...");
		if (fileSize > 0 && fileSize < maxSize) {
			log.info("Nombre: \t#0", fileName);
			log.info("Tamanno: \t#0", fileSize);
			log.info("Tipo: \t#0", contentType);

			documentCommand = DocumentCommandImpl.getInstance(contentType);
			
			if(documentCommand == null) {
				facesMessages.add("Tipo de archivo '#0' no soportado", contentType);
				log.info("Archivo no soportado");
				return;
			}

			byte[] fileBytes = new byte[fileSize];
			try {
				file.read(fileBytes);
				documentCommand.register(entityManager, fileName, fileBytes, identity.getUsername());
			} catch (IOException e) {
				e.printStackTrace();
			}

			facesMessages.add("Archivo: #0 cargado correctamente", fileName);
			log.info("Upload complete");
		} else {
			facesMessages.add("Archivo Incorrecto, verifique e intente de nuevo!");
			log.info("Upload failed");
		}
	}
	
	/**
	 * Escribe en disco el archivo, por el momento estamos guardando en la BD
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private void saveFile() {
	    try {
			FileOutputStream fileOutputStream = new FileOutputStream("/tmp/freeDM/"+fileName);
			byte[] buffer = new byte[8192];
			int bytesLeidos = 0;
			while ((bytesLeidos = file.read(buffer, 0, 8192)) != -1) {
				fileOutputStream.write(buffer, 0, bytesLeidos );
			}
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			log.error("Hay que crear la carpeta!!!\n#0", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Error en lectura de archivo:\n#0", e.getMessage());
			e.printStackTrace();
		}
	}

	@NotNull
	@Override
	public void setFile(InputStream file) {
		this.file = file;
	}

	@Override
	public InputStream getFile() {
		return file;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Integer getFileSize() {
		return fileSize;
	}

	@Override
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

}
