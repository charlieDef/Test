package com.materials.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "TEMPFILE")
public class TempFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TEMPFILE_ID")
	private long id;

	@Column(name = "RANDOM_ID")
	private String randomId;

	@Column(name = "CREATOR")
	private String creator;

	@Column(name = "DATA_TYPE")
	private String dataType;

	// ALTER TABLE 94_prebaaldb.tempfile MODIFY 94_prebaaldb.tempfile.TEMP_DATA
	// LONGBLOB
	@Column(name = "TEMP_DATA")
	@Lob
	private byte[] tempData;

	@Column(name = "BIG_DATA")
	private String bigData;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	public TempFile(String randomId) {
		this.randomId = randomId;
	}

	public TempFile() {
	}

	public long getId() {
		return id;
	}

	public String getRandomId() {
		return randomId;
	}

	public byte[] getTempData() {
		return tempData;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
	}

	public void setTempData(byte[] tempData) {
		this.tempData = tempData;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getBigData() {
		return bigData;
	}

	public void setBigData(String bigData) {
		this.bigData = bigData;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
