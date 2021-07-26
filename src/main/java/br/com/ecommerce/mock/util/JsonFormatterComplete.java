package br.com.ecommerce.mock.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author frank.oliveira
 * @version 1.7
 */
public class JsonFormatterComplete {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonFormatterComplete.class);

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private static final String REGEX_JSON_FORMAT = "\\{[^\\+]+\\}"	;
	private static final String REGEX_ARRAY_JSON_FORMAT = "\\[(\\n+|\\s+|)\\{[^\\+]+\\}(\\n+|\\s+|)\\]";
	
	private static final String REGEX_SELECT_ARRAY_PROPERTY = "\\[[0-9]\\]";
	private static final String REGEX_SELECT_FIRST_ARRAY_PROPERTY = "^\\[[0-9]\\]" + "\\.";
	private static final String REGEX_SELECTALL_AROUND_ARRAY_PROPERTY = "([^\\+]*)\\[|\\]|";
	
	private JsonFormatterComplete() {}

	public static boolean isEqual(String expected, String result) {
		return isEqualByJsonNode(convert(expected), convert(result));
	}
	
	public static boolean isEqualByJsonNode(JsonNode expected, JsonNode result) {
		if (expected != null && result != null) 
			return expected.equals(result);
		return false;
	}
	
	public static JsonNode convert(String json) {
		try {
			return MAPPER.readTree(json);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> objectToMap(Object obj) {
		return jsonToObject(objectToJson(obj, false), Map.class);
	}

	public static String objectToJson(Object obj) {
		return objectToJson(obj, false);
	}

	public static String objectToJson(Object obj, boolean prettyPrinter) {
		return objectToJson(obj, prettyPrinter, null);
	}
	
	public static String objectToJson(Object obj, boolean prettyPrinter, String timezone)
	{
		try {
			if (timezone != null && !timezone.isEmpty())
				MAPPER.setTimeZone(TimeZone.getTimeZone(timezone));
			else
				MAPPER.setTimeZone(TimeZone.getTimeZone("UTC"));
				
			if (prettyPrinter)
				return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			
			return MAPPER.writeValueAsString(obj);
			
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	public static <T> T jsonToObject(String json, Class<T> type)
	{
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, type);
			
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> jsonToArray(String json, Class<T> type) {
		List<Map<String, Object>> jsonItems = jsonToObject(json, List.class);
		
		if (jsonItems != null) {
			List<T> items = new ArrayList<>();
			
			for (Map<String, Object> jsonItem : jsonItems)
				items.add(jsonToObject(objectToJson(jsonItem), type));
			return items;
		}
		return null;
	}
	
	public static <T> String stringToJson(String string, Class<T> type)
	{
		T obj = jsonToObject(string, type);
		return objectToJson(obj);
	}
	
	public static boolean isJSONFormat(String json) 
	{
		if (json.trim().isEmpty())
			return false;
		return json.replaceAll(REGEX_JSON_FORMAT, "").trim().isEmpty();
	}
	
	public static boolean isArrayJSONFormat(String json) 
	{
		if (json.trim().isEmpty())
			return false;
		return json.replaceAll(REGEX_ARRAY_JSON_FORMAT, "").trim().isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public static Object getJsonPropertyValue(Object[] arrayJsonProperties, String propertiesName)
	{
		String[] properties = propertiesName.split("\\.");
		return getJsonPropertyValue((
				Map<String, Object>) arrayJsonProperties[extractArrayPosition(properties[0])], 
				propertiesName.replaceAll(REGEX_SELECT_FIRST_ARRAY_PROPERTY, ""));
	}
	
	public static Map<String, Object> mergeJson(Map<String, Object> oldJson, Map<String, Object> newJson, String... notOverrideArrayProperties) {
		return mergeJson("", oldJson, newJson, notOverrideArrayProperties);
	}

	@SuppressWarnings({"unchecked", "rawtypes" })
	private static Map<String, Object> mergeJson(String propertyPath, Map<String, Object> oldJson, Map<String, Object> newJson, String[] notOverrideArrayProperties) {
		String currentPropertyPath = "";
		
		for (Map.Entry<String, Object> entryNewJson : newJson.entrySet()) 
		{
			if (oldJson != null) {
				if (oldJson.containsKey(entryNewJson.getKey())) {
					currentPropertyPath = generatePropertyPath(propertyPath, entryNewJson.getKey());
					
					if (oldJson.get(entryNewJson.getKey()) instanceof Map && entryNewJson.getValue() instanceof Map)
						mergeJson(currentPropertyPath, (Map) oldJson.get(entryNewJson.getKey()), (Map) entryNewJson.getValue(), notOverrideArrayProperties);
					else if (oldJson.get(entryNewJson.getKey()) instanceof ArrayList && entryNewJson.getValue() instanceof ArrayList)
						oldJson.put(entryNewJson.getKey(), mergeJsonByArray(currentPropertyPath, (ArrayList) oldJson.get(entryNewJson.getKey()), (ArrayList) entryNewJson.getValue(), notOverrideArrayProperties));
					else			
						oldJson.put(entryNewJson.getKey(), entryNewJson.getValue());
					
				} else
					oldJson.put(entryNewJson.getKey(), entryNewJson.getValue());
			}
		}
		return oldJson;
	}

	private static String generatePropertyPath(String propertyName, String propertyKey) {
		if (propertyName == null || propertyName.isEmpty())
			return propertyKey;
		else
			return MessageFormat.format("{0}.{1}", propertyName, propertyKey);
	}
	
	/**
	*	 MELHORIAS:
	*     -  Percorrer profundamente cada item do array,
	 */
	private static List<Object> mergeJsonByArray(String propertyName, List<Object> oldArray, List<Object> newArray, String[] notOverrideArrayProperties) {
		if (isNotOverrideProperty(propertyName, notOverrideArrayProperties)) {
			for (Object newItem : newArray)
				if (!oldArray.contains(newItem)) {
					oldArray.add(newItem);
				}
			return oldArray; 
		} else {
			return newArray;
		}
	}
	
	private static boolean isNotOverrideProperty(String propertyName, String[] overrideArrayProperties) {
		for (String prop : overrideArrayProperties) {
			if (propertyName.equals(prop))
				return true;
		}
		return false;
	}

	private static boolean isArrayProperty(String property) {
		return Pattern.compile(REGEX_SELECT_ARRAY_PROPERTY).matcher(property).find();
	}
	
	private static Integer extractArrayPosition(String propertyName) {
		if (propertyName != null && !propertyName.trim().isEmpty())
			return Integer.parseInt(propertyName.replaceAll(REGEX_SELECTALL_AROUND_ARRAY_PROPERTY, ""));
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getJsonPropertyValue(Map<String, Object> jsonProperties, String propertiesName, Class<T> returnType)
	{
		LOGGER.debug(returnType.getName());
		
		Object jsonPropertyResult = getJsonProperty(jsonProperties, propertiesName);
		if (jsonPropertyResult != null) {
			if (ArrayJsonProperty.class.isInstance(jsonPropertyResult))
				return (T) ((ArrayJsonProperty) jsonPropertyResult).toValue();
			return (T) ((Entry<String, Object>) jsonPropertyResult).getValue();
		}
		return null;			
	}
	
	@SuppressWarnings({ "unchecked"})
	public static Object getJsonPropertyValue(Map<String, Object> jsonProperties, String propertiesName)
	{
		Object jsonPropertyResult = getJsonProperty(jsonProperties, propertiesName);
		if (jsonPropertyResult != null) {
			if (ArrayJsonProperty.class.isInstance(jsonPropertyResult))
				return ((ArrayJsonProperty) jsonPropertyResult).toValue();
			return ((Entry<String, Object>) jsonPropertyResult).getValue();
		}
		return null;			
	}
	
	// # Necessita refactoring
	@SuppressWarnings({ "unchecked"})
	public static Object getJsonProperty(Map<String, Object> jsonProperties, String propertiesName)
	{
		String[] properties = propertiesName.split("\\.");
		for (int i = 0; i < properties.length; i++) 
		{
			String propertyName = properties[i].replaceAll(REGEX_SELECT_ARRAY_PROPERTY, "");
			
			for (Map.Entry<String, Object> jsonProperty : jsonProperties.entrySet()) {
				if (jsonProperty.getKey() != null && jsonProperty.getKey().equals(propertyName))
				{
					if (isArrayProperty(properties[i])) {
						List<Map<String, Object>> subJsonProperties = (List<Map<String, Object>>) jsonProperty.getValue();
						Integer position = extractArrayPosition(properties[i]);
						
						if (!subJsonProperties.isEmpty() && i < subJsonProperties.size()  && Map.class.isInstance(subJsonProperties.get(position)))
							jsonProperties = subJsonProperties.get(position);
						else
							return ArrayJsonProperty.of()
									.setPosition(position)
									.setJsonProperty(jsonProperty);
						
					} else if (Map.class.isInstance(jsonProperty.getValue())) {
						jsonProperties = (Map<String, Object>) jsonProperty.getValue();
					} else {
						return jsonProperty;
					}
					
					if (properties.length > 1 && i <= properties.length) {
						return getJsonProperty(jsonProperties, propertiesName.replace(properties[i] + ".", ""));
					} else {
						return jsonProperty;
					}
				}
			}
		}
		return null;
	}
	
	static class ArrayJsonProperty {
		private int position;
		private Entry<String, Object> jsonProperty;
		
		private ArrayJsonProperty() {}
		
		public static ArrayJsonProperty of() {
			return new ArrayJsonProperty();
		}

		public int getPosition() {
			return position;
		}

		public ArrayJsonProperty setPosition(int position) {
			this.position = position;
			return this;
		}

		public Entry<String, Object> getJsonProperty() {
			return jsonProperty;
		}

		public ArrayJsonProperty setJsonProperty(Entry<String, Object> jsonProperty) {
			this.jsonProperty = jsonProperty;
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public Object toValue() {
			if (jsonProperty != null && !((ArrayList<Object>) jsonProperty.getValue()).isEmpty() && position < ((ArrayList<Object>) jsonProperty.getValue()).size())
				return ((ArrayList<Object>) jsonProperty.getValue()).get(position);
			return null;
		}
		
	}
	
}
