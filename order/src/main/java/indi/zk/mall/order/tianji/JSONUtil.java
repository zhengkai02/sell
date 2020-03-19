package indi.zk.mall.order.tianji;

import com.google.gson.*;

import java.lang.reflect.Type;

public final class JSONUtil {

  public static String getJSONStringFromPOJO(Object pojo) {
    return getJSONStringFromPOJO(pojo, false);
  }

  public static String getJSONStringFromPOJO(Object pojo, boolean skipNull) {
    Gson gson = skipNull ? new Gson()
        : new GsonBuilder().serializeNulls().create();
    return gson.toJson(pojo);
  }

  public static String getJSONStringFromPOJO(Object pojo,
      ExclusionStrategy exclusion) {
    Gson gson = new GsonBuilder().setExclusionStrategies(
        exclusion).serializeNulls().create();
    return gson.toJson(pojo);
  }

  public static String getJSONStringFromPOJO(Object pojo, Type type) {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.toJson(pojo, type);
  }

  public static String getJSONStringFromArray(Object[] pojo) {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.toJson(pojo);
  }

  public static <T> T getPOJOFromJSONString(String json, Class<T> clazz) {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson(json, clazz);
  }

  public static <T> T getPOJOFromJSONString(Object json, Class<T> clazz) {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson((JsonElement) json, clazz);
  }

  public static <T> T getPOJOFromJSONString(Object json, Type type) {
    Gson gson = new GsonBuilder().serializeNulls().create();
    return gson.fromJson((JsonElement) json, type);
  }

  /*public static <T, F> T getPOJOFromJSONString(String json, Class<T> clazz,
      JSONFieldSetter<T> setter, boolean skip) throws JSONConvertException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    try {
      T t = gson.fromJson(json, clazz);
      JsonParser parser = new JsonParser();
      JsonObject object = parser.parse(json).getAsJsonObject();
      if (skip) {
        JsonElement skipField = object.get(setter.getFieldName());
        // JsonObject skipField = element.getAsJsonObject();
        setter.setValue(t, skipField);
        return t;
      }
      JsonElement setterElement = getNestingElement(
          object,
          setter.getFieldName());
      Object fieldValue = gson.fromJson(setterElement, setter.getFieldClass());
      setter.setValue(t, fieldValue);
      return t;
    } catch (JsonSyntaxException e) {
      throw new JSONConvertException(e);
    }
  }*/

  /*private static JsonElement getNestingElement(JsonObject object,
      String fieldName) {
    JsonElement nestingElement = object.get(fieldName);
    if (nestingElement == null) {
      for (Entry<String, JsonElement> entry : object.entrySet()) {
        JsonElement element = entry.getValue();
        if (element instanceof JsonObject) {
          JsonObject nestObject = (JsonObject) element;
          nestingElement = getNestingElement(nestObject, fieldName);
          if (nestingElement != null)
            break;
        }
      }
      return nestingElement;
    } else
      return nestingElement;
  }*/

  // public static <T> T getPOJOFromJSONString(String json, Class<T> clazz,
  // JSONFieldSetter<T> setter) throws JSONConvertException {
  // return getPOJOFromJSONString(json, clazz, setter, false);
  // }

  /*public static <T> T getPOJOFromJSONStringWithSkipField(String json,
      Class<T> clazz, JSONFieldSetter<T> setter) throws JSONConvertException {
    return getPOJOFromJSONString(json, clazz, setter, true);
  }

  public static <T> T getPOJOFromJSONStringWithFieldSetters(String json,
      Class<T> clazz, JSONFieldSetter<T> skipFieldSetter,
      List<JSONFieldSetter<T>> setters) throws JSONConvertException {
    Gson gson = new GsonBuilder().serializeNulls().create();
    try {
      T t = gson.fromJson(json, clazz);
      JsonParser parser = new JsonParser();
      JsonObject object = parser.parse(json).getAsJsonObject();
      JsonElement skipField = object.get(skipFieldSetter.getFieldName());
      skipFieldSetter.setValue(t, skipField);
      for (JSONFieldSetter<T> setter : setters) {
        JsonElement setterElement = getNestingElement(
            object,
            setter.getFieldName());
        if (setterElement == null)
          continue;
        Object fieldValue = gson.fromJson(
            setterElement,
            setter.getFieldClass());
        setter.setValue(t, fieldValue);
      }
      return t;
    } catch (JsonSyntaxException e) {
      throw new JSONConvertException(e);
    }
  }*/

  public static <T> T getValueFromJSONString(String json, String fieldName,
      Class<T> fieldClass) {
    Gson gson = new GsonBuilder().serializeNulls().create();
    JsonParser parser = new JsonParser();
    JsonObject object = parser.parse(json).getAsJsonObject();
    return gson.fromJson(object.get(fieldName), fieldClass);
  }

  /*@SuppressWarnings("unchecked")
  public static <T> T getValueFromJSONObject(JsonObject jsonObject,
      String fieldName, Type fieldClass) throws JSONConvertException {
    try {
      // JsonObject jsonObject = (JsonObject) object;
      GsonBuilder gsonBuilder = new GsonBuilder();
      gsonBuilder.registerTypeAdapter(CvdImageType.class, new EnumSerializer());
      Gson gson = gsonBuilder.serializeNulls().create();
      return (T) gson.fromJson(jsonObject.get(fieldName), fieldClass);
    } catch (JsonSyntaxException e) {
      throw new JSONConvertException(e);
    }
  }

  @Deprecated
  public static boolean hasFieldInJSONString(String json, String fieldName)
      throws JSONConvertException {
    JsonParser parser = new JsonParser();
    try {
      JsonObject object = parser.parse(json).getAsJsonObject();
      return object.has(fieldName);
    } catch (JsonSyntaxException e) {
      throw new JSONConvertException(e);
    }
  }*/

  /**
   * This method parse the string of JSON message to JSON object.
   * 
   * @param json
   *          the string representation of JSON message
   * @return the object representation of JSON message
   * @throws JsonSyntaxException
   */
  public final static JsonObject toJsonObject(String json)
      throws JsonSyntaxException {
    JsonParser parser = new JsonParser();
    JsonObject object = parser.parse(json).getAsJsonObject();
    return object;
  }

  /**
   * Append time stamp to the end of message
   * 
   * <pre>
   *  for example: before method calling, the raw message is
   *  	{"jsonrpc":"2.0", "method":"methodName","params":paramObject} 
   *   
   * after method called, the modified message will be:
   *     {"jsonrpc":"2.0", "method":"methodName","params":paramObject,"time":time}
   * </pre>
   * 
   * @param rawJsonMessage
   *          the message to be appended time
   * @param time
   *          the time to be append to the end of message
   * 
   * @return the JSON message with appended time.
   */
  /*public static byte[] appendTime(byte[] rawJsonMessage, long time) {

    // Arrays.copyOf(original, newLength)
    final byte brace = '}';
    int index = rawJsonMessage.length - 1;
    for (; index >= 0; index--) {

      if (rawJsonMessage[index] == brace) {
        break;
      }
    }
    if (index > 4) {
      String timeString = ",\"time\":" + time;
      byte[] timeBytes = OutputFormatter.stringToChars(timeString);
      byte[] result = new byte[index + timeBytes.length + 1];
      byte[] lengthBytes = OutputFormatter.intToBytes(result.length - 4);
      // change to new length;
      System.arraycopy(lengthBytes, 0, result, 0, 4);
      System.arraycopy(rawJsonMessage, 4, result, 4, index - 4);
      System.arraycopy(timeBytes, 0, result, index, timeBytes.length);
      result[result.length - 1] = brace;
      return result;
    } else {
      // It's not an valid json message.
      return rawJsonMessage;
    }

  }*/
}
