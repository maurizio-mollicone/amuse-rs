package it.mollik.amuse.amusers.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmuseObject implements IAmuseObject {
    
    private static final Logger logger = LoggerFactory.getLogger(AmuseObject.class);

    @Override
    public String toString(){
        
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = StringUtils.EMPTY;
        try {
            jsonString = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.debug("toJSONString error", e);
        }
        return jsonString;
    }
    
    public String toJSONString() {
        return toString();
    } 
    
}
