package edu.axiacore.freedm.controller.documentcommand;

public class DocumentCommandImpl {
	
	/**
	 * Retorna null si la instancia aun no es manejada por nadie.
	 * Aqui se deben agregar los otros tipos de archivos que debe manejar el programa
	 * @param contentType
	 * @return
	 */
	public static DocumentCommand getInstance(String contentType) {
		DocumentCommand instance = null;
		if(contentType.equals(TextDocument.contentType))
			instance = new TextDocument();
		
		return instance;
	}

}
