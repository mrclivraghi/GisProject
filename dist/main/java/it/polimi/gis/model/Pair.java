package it.polimi.gis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(schema="public",name="pair")
public class Pair {
	
	@Id
	@GeneratedValue
	private Integer pairId;
	
	@Column(columnDefinition="Geometry",name="point_a")
	@Type(type="org.hibernate.spatial.GeometryType")
    private MultiPoint pointA;
	 
	@Column(columnDefinition="Geometry",name="point_b")
	@Type(type="org.hibernate.spatial.GeometryType")
    private MultiPoint pointB;
	
	private String project;

    public MultiPoint getPointA() {
        return pointA;
    }

    public void setPointA(MultiPoint pointA) {
        this.pointA = pointA;
    }

    public MultiPoint getPointB() {
        return pointB;
    }

    public void setPointB(MultiPoint pointB) {
        this.pointB = pointB;
    }

	public Integer getPairId() {
		return pairId;
	}

	public void setPairId(Integer pairId) {
		this.pairId = pairId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
    
}
