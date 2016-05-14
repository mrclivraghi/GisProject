package it.polimi.gis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MapFile {
	
	@Id
	@GeneratedValue
	private Integer mapFileId;
	
	private String filePath;

	public Integer getMapFileId() {
		return mapFileId;
	}

	public void setMapFileId(Integer mapFileId) {
		this.mapFileId = mapFileId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
