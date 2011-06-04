package edu.axiacore.freedm.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "Document", catalog = "freeDM")
public class Document implements Serializable {

	private Long id;
	private Long version;
	private String contentType;
	private String fileName;
	private String userId;
	private Date lastModification;
	private byte[] documentData;

	public Document() {
	}

	public Document(Long version, String contentType, String fileName,
			String userId, Date lastModification, byte[] documentData) {
		this.version = version;
		this.contentType = contentType;
		this.fileName = fileName;
		this.userId = userId;
		this.lastModification = lastModification;
		this.documentData = documentData;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Length(max = 100)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLastModification() {
		return lastModification;
	}

	public void setLastModification(Date lastModification) {
		this.lastModification = lastModification;
	}

	@NotNull
	public byte[] getDocumentData() {
		return documentData;
	}
	
	public void setDocumentData(byte[] documentData) {
		this.documentData = documentData;
	}

}
