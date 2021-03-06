package com.lxl.support.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupportObjectFieldUtil {

	public final static String KEY_TYPE = "type";

	public final static String KEY_NAME = "name";

	public final static String KEY_VALUE = "value";

	/**
	 * 根据属性名设置属性值
	 * */
	public static void setFieldValueByName(String fieldName, Object value,Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "set" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, value.getClass());
			method.invoke(o, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 根据属性名获取属性值 
	 * */  
	   public static Object getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	    	   e.printStackTrace();
	           return null;    
	       }    
	   }   
	     
	   /** 
	    * 获取属性名数组 
	    * */  
	   public static String[] getFiledName(Object o){  
	    Field[] fields=o.getClass().getDeclaredFields();  
	        String[] fieldNames=new String[fields.length];  
	    for(int i=0;i<fields.length;i++){  
	        System.out.println(fields[i].getType());  
	        fieldNames[i]=fields[i].getName();  
	    }  
	    return fieldNames;  
	   }

	/**
	 * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 * */
	public static List getFiledsInfo(Object o){
		Field[] fields=o.getClass().getDeclaredFields();
		String[] fieldNames=new String[fields.length];
		List list = new ArrayList();
		Map infoMap=null;
		for(int i=0;i<fields.length;i++){
			infoMap = new HashMap();
			infoMap.put(KEY_TYPE, fields[i].getType().toString());
			infoMap.put(KEY_NAME, fields[i].getName());
			infoMap.put(KEY_VALUE, getFieldValueByName(fields[i].getName(), o));
			list.add(infoMap);
		}
		return list;
	}
	   /** 
	    * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list 
	    * */  
	   public static List getFiledsInfo(Object o,List<String> filterType){
	    Field[] fields=o.getClass().getFields();
	        String[] fieldNames=new String[fields.length];  
	        List list = new ArrayList();  
	        Map infoMap=null;  
	    for(int i=0;i<fields.length;i++){  
	        infoMap = new HashMap();
			if (!filterType.contains(fields[i].getType().toString())){
				continue;
			}
	        infoMap.put(KEY_TYPE, fields[i].getType().toString());
	        infoMap.put(KEY_NAME, fields[i].getName());
	        infoMap.put(KEY_VALUE, getFieldValueByName(fields[i].getName(), o));
	        list.add(infoMap);  
	    }  
	    return list;  
	   }

	     
	   /** 
	    * 获取对象的所有属性值，返回一个对象数组 
	    * */  
	   public static Object[] getFiledValues(Object o){  
	    String[] fieldNames=getFiledName(o);  
	    Object[] value=new Object[fieldNames.length];  
	    for(int i=0;i<fieldNames.length;i++){  
	        value[i]=getFieldValueByName(fieldNames[i], o);  
	    }  
	    return value;  
	   }      
}
