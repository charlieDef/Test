package com.materials.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "APP_OBJECT")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class APPObject implements Serializable {

	private static final long serialVersionUID = 997634358418395795L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APP_OBJECT_ID")
	private long id;

	@Version
	@Column(name = "VERSION", columnDefinition = "integer DEFAULT 0", nullable = false)
	private Integer version = 0;

	@Column(name = "LOCKSTATE", nullable = false)
	private boolean lock = false;

	@Column(name = "TEXTINFO")
	private String textInfo;

	@Column(name = "LABEL")
	private String label;

	public long getId() {
		return id;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public Integer getVersion() {
		return version;
	}

	public String getTextInfo() {
		return textInfo;
	}

	public void setTextInfo(String textInfo) {
		this.textInfo = textInfo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
