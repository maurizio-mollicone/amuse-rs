package it.mollik.amuse.amusers.model;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.StringJoiner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AmuseEntity implements IAmuseEntity {
    
    private static final Logger logger = LoggerFactory.getLogger(AmuseEntity.class);

    @Override
    public String toString(){
        StringJoiner sj = new StringJoiner(StringUtils.EMPTY).add(getClass().getName()).add(" [");
        try {
            for(PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(getClass()).getPropertyDescriptors()){
                if (propertyDescriptor != null && propertyDescriptor.getReadMethod() != null) {
                    Object o = propertyDescriptor.getReadMethod().invoke(this);
                    if (o != null) {
                        sj.add(propertyDescriptor.getReadMethod().getName()).add("=").add(o.toString()).add(", ");
                    }
                }
            }
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.debug("toString error", e);
        } 
        return sj.add("]").toString();
    }
    
    public String toJSONString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = StringUtils.EMPTY;
        try {
            jsonString = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.debug("toJSONString error", e);
        }
        return jsonString;
    } 

    /**
     * @return String return the name
     */
    public String getName() {
        return this.getClass().getName();
    }


}
