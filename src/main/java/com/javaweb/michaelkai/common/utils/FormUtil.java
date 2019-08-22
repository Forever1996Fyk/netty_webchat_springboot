package com.javaweb.michaelkai.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormUtil {
	public static final char UNDERLINE = '_';

	private static final Logger logger = LoggerFactory.getLogger(FormUtil.class);

	/**
	 * 数组去重复
	 * @description 
	 * @date 2018年3月21日
	 * @param 
	 * @return
	 */
	public static String[] arrayUnique(String[] a) {
		// array_unique
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < a.length; i++) {
			if (!list.contains(a[i])) {
				list.add(a[i]);
			}
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
    /**
     * 驼峰转下划线
     * @description 
     * @date 2018年3月21日
     * @param 
     * @return
     */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	/**
	 * 下划线转驼峰
	 * @description 
	 * @date 2018年3月21日
	 * @param 
	 * @return
	 */
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
    /**
     * 下划线转驼峰
     * @description 
     * @date 2018年3月21日
     * @param 
     * @return
     */
	public static String underlineToCamel2(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		StringBuilder sb = new StringBuilder(param);
		Matcher mc = Pattern.compile("_").matcher(param);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将一个Bean属性赋值给另一个Bean
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> copyProperties(List srcBeans, Class targetClass) {
		List<T> targetBeans = new ArrayList<T>();
		try {
			for (int i = 0; i < srcBeans.size(); i++) {
				T target = (T) targetClass.newInstance();
				BeanUtils.copyProperties(srcBeans.get(i), target);
				targetBeans.add(target);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return targetBeans;
	}

	/**
	 * 将一个Bean转换为Map
	 *
	 * @param
	 * @return
	 */
	public static <T> Map<String, Object> populate(T bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		return populate(bean, map);
	}
    /**
     * 将一个Bean属性追加到已存在的Map中
     *
     * @param
     * @return
     */
	public static <T> Map<String, Object> populate(T bean, Map<String, Object> map) {
		/*if (bean == null) {
			return null;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(bean);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}*/
		return map;
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 *
	 * @param clazz
	 * @param map
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	public static <T> T populate(Class<T> clazz, Map<String, Object> map, boolean delUnderLine){
		/*try{
            T obj = clazz.newInstance(); // 创建 JavaBean 对象
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
			setPropertyValue(obj, beanInfo, map, delUnderLine);
			return obj;
		}catch(Exception e){
			e.printStackTrace();
		}*/
		return null;
	}
	/**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param
     * @param map
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    public static <T> T populate(T obj, Map<String, Object> map, boolean delUnderLine){
       /* try{
            Class<?> clazz = obj.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
            setPropertyValue(obj, beanInfo, map, delUnderLine);
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }*/
        return null;
    }
    private  static <T> void setPropertyValue(T obj,BeanInfo beanInfo,Map<String, Object> map, boolean delUnderLine) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
      /*// 给 JavaBean 对象的属性赋值
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      for (int i = 0; i < propertyDescriptors.length; i++) {
          PropertyDescriptor descriptor = propertyDescriptors[i];
          descriptor.getPropertyType();
          String propertyName = delUnderLine ? camelToUnderline(descriptor.getName()) : descriptor.getName();
          if (!map.containsKey(propertyName) || null==map.get(propertyName)) {
            continue;
          }
          // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
          try {
			  Object value = map.get(propertyName);
			  Object[] args = new Object[1];
			  args = setValue(descriptor, args, value);
			  descriptor.getWriteMethod().invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
      }*/
    }
	private static Object[] setValue(PropertyDescriptor descriptor, Object[] args, Object mapValue)
			throws ParseException {
		/*if (descriptor.getPropertyType() == Integer.class || descriptor.getPropertyType() == int.class) {
			if (mapValue instanceof Integer) {
				args[0] = mapValue;
			} else {
				if(StringUtils.isNotBlank(mapValue.toString())){
					args[0] = Integer.parseInt(mapValue.toString());
				}
			}
			// 当set方法中的参数为Date
		}else if (descriptor.getPropertyType() == Byte.class || descriptor.getPropertyType() == byte.class) {
			if (mapValue instanceof Byte) {
				args[0] = mapValue;
			} else {
				if(StringUtils.isNotBlank(mapValue.toString())){
					args[0] = Byte.parseByte(mapValue.toString());
				}
			}
			// 当set方法中的参数为Float
		}else if (descriptor.getPropertyType() == BigDecimal.class) {
          if (mapValue instanceof BigDecimal) {
            args[0] = mapValue;
          } else {
        	  if(StringUtils.isNotBlank(mapValue.toString())){
        		  args[0] = new BigDecimal(mapValue.toString());
			  }
          }
        // 当set方法中的参数为Float
        } else if (descriptor.getPropertyType() == Date.class || descriptor.getPropertyType() == java.sql.Date.class) {
			if (mapValue instanceof Date) {
				args[0] = mapValue;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				args[0] = sdf.parse((String) mapValue);
			}
			// 当set方法中的参数为Float
		}else if (descriptor.getPropertyType() == double.class || descriptor.getPropertyType() == Double.class) {
			if (mapValue instanceof Double) {
				args[0] = mapValue;
			} else {
				if(StringUtils.isNotBlank(mapValue.toString())){
					args[0] = Double.parseDouble((String) mapValue);
				}
			}
			// 当set方法中的参数为其他
		} else if (descriptor.getPropertyType() == String.class) {

			if (mapValue instanceof String[]) {

				String[] tempArray = (String[]) mapValue;
				String result = "";
				for (int m = 0; m < tempArray.length; m++) {
					result = result + tempArray[m] + ",";
				}
				result = result.substring(0, result.length() - 1);
				args[0] = result;

			} else {
				args[0] = (String) mapValue;
			}
		}*/
		return args;
	}

	/**
	 * 根据key获取map中对应的值
	 * @description 
	 * @date 2018年1月31日
	 * @param 
	 * @return
	 */
    public static String getMapValue(Map<String,Object> map,String key){
      if(CollectionUtils.isEmpty(map)){
        return null;
      }
      if(null==map.get(key) || "".equals(map.get(key).toString().trim())){
        return null;
      }
      return map.get(key).toString();
    }
    
    /**
     * 求和：list中map的key对应的value数字之和
     * @description 
     * @date 2018年1月31日
     * @param 
     * @return
     */
    public static BigDecimal sumListMapValue(List<Map<String,Object>> list,String key){
      BigDecimal sum = new BigDecimal(0);
      if(CollectionUtils.isEmpty(list)){
        return sum;
      }
      for(Map<String,Object> map:list){
        if(null==map.get(key) || "".equals(map.get(key).toString().trim())){
          continue;
        }
        String value = map.get(key).toString();
        sum = sum.add(new BigDecimal(value));
      }
      return sum;
    }
    /**
     * 根据key获取list的map中对应的值 
     * @description 唯一性判断用，只有一条记录
     * @date 2018年1月31日
     * @param 
     * @return
     */
    public static Object getListMapUniqueValue(List<Map<String,Object>> list,String key){
      Object o = null;
      if(CollectionUtils.isEmpty(list)){
        return null;
      }
      for(Map<String,Object> map:list){
        if(!CollectionUtils.isEmpty(map) && null!=map.get(key)){
          o=map.get(key);
          break;
        }
      }
      return o;
    }
    /**
     * 验证key是否在List的map中存在
     * @description 唯一性判断用，只有一条记录
     * @date 2018年1月31日
     * @param 
     * @return
     */
    public static void uniqueValue(List<Map<String,Object>> list,String key,String tipName,String tipMsg){
      if(null!=getListMapUniqueValue(list,key)){
        //throw new UniqueException(String.format("%s%s", tipName,tipMsg));
      }
    }
}
