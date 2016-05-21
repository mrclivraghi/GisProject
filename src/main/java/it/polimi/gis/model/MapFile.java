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
	
	private String name;
	
	private String workSpace;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkSpace() {
		return workSpace;
	}

	public void setWorkSpace(String workSpace) {
		this.workSpace = workSpace;
	}
}
