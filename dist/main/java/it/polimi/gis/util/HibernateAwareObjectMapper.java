package it.polimi.gis.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
