package edu.axiacore.freedm.controller.filemanager;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import edu.axiacore.freedm.entity.Document;

@Name("fileHistory")
public class FileHistory extends EntityQuery {

	private Document document = new Document();
	
	private String fileName;

	@Override
	public String getEjbql() {
		return "from Document where fileName = '"+fileName+"' ";
	}

	@Override
	public Integer getMaxResults() {
		return 25;
	}

	public Document getDocument() {
		return document;
	}
	
	@NotNull
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	//Mr Machete presents:
    public void downloadFile(Document selectedDocument) {
		try {
			System.out.println("Estamos bajando esto "+selectedDocument.getFileName()+" v."+selectedDocument.getVersion());
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			response.setContentType(selectedDocument.getContentType());
			response.setContentLength(selectedDocument.getDocumentData().length);
			response.setHeader("Content-disposition","attachment; filename=\""
							+ selectedDocument.getFileName() + "\"");
			response.getOutputStream().write(selectedDocument.getDocumentData());

			context.responseComplete();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
