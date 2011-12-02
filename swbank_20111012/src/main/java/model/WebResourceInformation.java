package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import util.RequestType;
import util.TargetType;

@NamedQueries({
	@NamedQuery(name=WebResourceInformation.FIND_BY_ID, query="select w from WebResourceInformation w where w.id=:id"),
	@NamedQuery(name=WebResourceInformation.FIND_BY_BANKCODE, query="select w from WebResourceInformation w where w.bankCode=:bankCode"),
	@NamedQuery(name=WebResourceInformation.FIND_BY_BANKCODE_AND_MEDIATYPE, query="select w from WebResourceInformation w where w.bankCode=:bankCode and w.mediaType=:mediaType"),
	@NamedQuery(name=WebResourceInformation.FIND_BY_BANKCODE_AND_MEDIATYPE_AND_REQUESTTYPE, query="select w from WebResourceInformation w where w.bankCode=:bankCode and w.mediaType=:mediaType and w.requestType=:requestType"),
	@NamedQuery(name=WebResourceInformation.FIND_BY_BANKCODE_AND_MEDIATYPE_AND_REQUESTTYPE_AND_TARGETTYPE, query="select w from WebResourceInformation w where w.bankCode=:bankCode and w.mediaType=:mediaType and w.requestType=:requestType and w.targetType=:targetType")
	})
@Entity
@Table(name="WebResourceInformationBean")
public class WebResourceInformation implements Serializable {
	
	private static final long serialVersionUID = 8043645406690444534L;

	public static final String FIND_BY_ID = "WebResourceInformation.FIND_BY_ID";
	public static final String FIND_BY_BANKCODE = "WebResourceInformation.FIND_BY_BANKCODE";
	public static final String FIND_BY_BANKCODE_AND_MEDIATYPE = "WebResourceInformation.FIND_BY_BANKCODE_AND_MEDIATYPE";
	public static final String FIND_BY_BANKCODE_AND_MEDIATYPE_AND_REQUESTTYPE = "WebResourceInformation.FIND_BY_BANKCODE_AND_MEDIATYPE_AND_REQUESTTYPE";
	public static final String FIND_BY_BANKCODE_AND_MEDIATYPE_AND_REQUESTTYPE_AND_TARGETTYPE = "WebResourceInformation.FIND_BY_BANKCODE_AND_MEDIATYPE_AND_REQUESTTYPE_AND_TARGETTYPE";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String bankCode;
	
	private String mediaType;
	
	private String path;
	
	private RequestType requestType;
	
	@Enumerated(EnumType.STRING)
	private TargetType targetType;

	private String info;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
