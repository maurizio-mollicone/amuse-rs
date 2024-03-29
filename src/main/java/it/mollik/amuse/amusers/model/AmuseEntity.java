package it.mollik.amuse.amusers.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AmuseEntity implements IAmuseEntity {
    
    private static final Logger logger = LoggerFactory.getLogger(AmuseEntity.class);

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

}
