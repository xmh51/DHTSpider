package me.hncn.util.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**JsonUtil
 * Created by XMH on 2016/5/21.
 */
public class JsonUtil {
    private static Logger logger = LogManager.getLogger(JsonUtil.class);
    public static String getJsonStr(Object obj){
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            logger.error("JsonGenerationException :",e);
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException :",e);
        } catch (IOException e) {
            logger.error("IOException :",e);
        }
        return json;
    }

    public static Map<String,Object> getMap(String jsonStr){
        return getObj(jsonStr, new TypeReference<Map<String, Object>>() {});
    }

    public static JsonNode getJsonNode(String jsonStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
      return mapper.readTree(jsonStr);
    }

    public static List<Map<String, String>> getList4Map(String jsonStr){
        return getObj(jsonStr, new TypeReference<List<Map<String, String>>>() {});
    }

    public static <T> T  getObj(String jsonStr,Class<T> clazz){
        return getObj(jsonStr, new TypeReference<T>() {});
    }

    public static <T> T  getObj(String jsonStr, TypeReference valueTypeRef){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStr,valueTypeRef);
        } catch (JsonParseException e) {
            logger.error("JsonParseException :",e);
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException :",e);
        } catch (IOException e) {
            logger.error("IOException :",e);
        }
        return null;
    }
}
