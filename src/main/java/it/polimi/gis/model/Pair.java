package it.polimi.gis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

@Entity
@Table(schema="public",name="pair")
public class Pair {
	
	@Id
	private Integer pairId;
	
	@Column(columnDefinition="Geometry",name="point_a")
	@Type(type="org.hibernate.spatial.GeometryType")
    private Point pointA;
	 
	@Column(columnDefinition="Geometry",name="point_b")
	@Type(type="org.hibernate.spatial.GeometryType")
    private Point pointB;

    public Point getPointA() {
        return pointA;
    }

    public void setPointA(Point pointA) {
        this.pointA = pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public void setPointB(Point pointB) {
        this.pointB = pointB;
    }

	public Integer getPairId() {
		return pairId;
	}

	public void setPairId(Integer pairId) {
		this.pairId = pairId;
	}
    
}
