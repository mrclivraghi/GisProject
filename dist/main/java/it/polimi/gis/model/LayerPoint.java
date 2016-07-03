package it.polimi.gis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

@Entity
public class LayerPoint {

	@Id
	@GeneratedValue
	private Integer layerPointId;
	
	private String layerName;
	
	
	@Column(columnDefinition="Geometry",name="point")
	@Type(type="org.hibernate.spatial.GeometryType")
	private Point point;



	public Integer getLayerPointId() {
		return layerPointId;
	}



	public void setLayerPointId(Integer layerPointId) {
		this.layerPointId = layerPointId;
	}



	public String getLayerName() {
		return layerName;
	}



	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}



	public Point getPoint() {
		return point;
	}



	public void setPoint(Point point) {
		this.point = point;
	}
	
}
