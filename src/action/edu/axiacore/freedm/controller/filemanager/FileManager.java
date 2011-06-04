package edu.axiacore.freedm.controller.filemanager;

import java.io.IOException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.FacesMessages;
import org.jboss.seam.log.Log;
import org.richfaces.component.ListRowKey;
import org.richfaces.component.TreeNode;
import org.richfaces.component.TreeNodeImpl;
import org.richfaces.component.UITree;
import org.richfaces.component.events.NodeSelectedEvent;

import edu.axiacore.freedm.controller.documentcommand.DocumentCommand;
import edu.axiacore.freedm.controller.documentcommand.DocumentCommandImpl;
import edu.axiacore.freedm.controller.documentcommand.TextDocument;
import edu.axiacore.freedm.entity.Document;

@Name("fileManager")
@Scope(ScopeType.SESSION)
public class FileManager {
	
	@Logger
	Log log;

	@In
	FacesMessages facesMessages;
	
	@In
	EntityManager entityManager; 
	
    private TreeNode rootNode;
    private String folderName;
    private Document selectedDocument;
    
    public void processSelection(NodeSelectedEvent event) {
    	UITree tree = (UITree) event.getComponent();
        log.info("Elemento seleccionado: #0 - ID: #1", tree.getRowData(), tree.getRowKey());
        
        ListRowKey rowKey = (ListRowKey) tree.getRowKey();
        //Mr machete presents:
        DocumentCommand command = DocumentCommandImpl.getInstance(TextDocument.contentType);
        selectedDocument = command.getDocument(entityManager, Long.valueOf(rowKey.toString()));
    }
    
    public TreeNode getTreeNode() {
    	updateFileTree();
        return rootNode;
    }

    /**
     * Crea una carpeta en el directorio de archivos
     */
    public void addFolder() {
		facesMessages.add("Aun no implementado...");
    }
    
    /**
     * Este metodo actualiza el contenido del arbol de archivos directamente
     * del repositorio
     */
    public void updateFileTree() {
    	rootNode = new TreeNodeImpl();
    	
		Query query = entityManager.createQuery("from Document order by fileName asc, version asc ");
		List<Document> resultList = query.getResultList();
		for (int i = 0; i < resultList.size(); i++) {
	    	TreeNode treeNode = new TreeNodeImpl();
	    	
			Document actual = resultList.get(i);
			if(i > 0) {
				Document anterior = resultList.get(i-1);
				if(actual.getFileName().equals(anterior.getFileName())) {
					//Quitar el anterior
					rootNode.removeChild(anterior.getId());
				}
			}
	    	treeNode.setData(actual.getFileName());
	    	rootNode.addChild(actual.getId(), treeNode);
		}
		entityManager.clear();
    }
    
    /**
     * Descarga el archivo actualmente seleccionado en el arbol
     */
    public void downloadFile() {
        if (selectedDocument != null) {
        	log.info("Iniciando descarga para el documento #0 v.#1", selectedDocument.getFileName(), selectedDocument.getVersion());
			try {
				FacesContext context = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) context
						.getExternalContext().getResponse();

				response.setContentType(selectedDocument.getContentType());
				response.setContentLength(selectedDocument.getDocumentData().length);
				response.setHeader("Content-disposition","attachment; filename=\""
								+ selectedDocument.getFileName() + "\"");
				response.getOutputStream().write(selectedDocument.getDocumentData());

				context.responseComplete();
				log.info("Descarga Completa");
			} catch (IOException e) {
				log.error("Error al crear archivo: #0", e.getMessage());
				e.printStackTrace();
			}
		} else
			facesMessages.add("Debe seleccionar un documento!");
    }
    
    /**
     * Elimina la ultima version del documento seleccionado.
     * FIXME Problema, se debe recargar la pagina para ver los cambios, parece que hibernate se demora un poco en procesar, algo me debe hacer falta por hacer
     */
    public void deleteFile() {
        if (selectedDocument != null) {
        	log.info("Eliminando documento #0 v.#1", selectedDocument.getFileName(), selectedDocument.getVersion());
            
        	//Mr machete presents:
            DocumentCommand command = DocumentCommandImpl.getInstance(TextDocument.contentType);
            command.delete(entityManager, selectedDocument);
        	
			facesMessages.add("Documento eliminado correctamente");
		} else
			facesMessages.add("Debe seleccionar un documento!");
    }
    
    public String getFolderName() {
		return folderName;
	}
    
    public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
    
    public Document getSelectedDocument() {
		return selectedDocument;
	}
    
    public void setSelectedDocument(Document selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

}
