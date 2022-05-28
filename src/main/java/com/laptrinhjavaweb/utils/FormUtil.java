package com.laptrinhjavaweb.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class FormUtil {
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <T> T toModel(Class<T> tClass , HttpServletRequest request){
		T object = null;
		try {
			object = tClass.newInstance();
			try {
				BeanUtils.populate(object, request.getParameterMap());
			} catch (InvocationTargetException e) {
				Logger.getLogger(FormUtil.class.getName()).log(Level.SEVERE,null,e);
			}
		} catch (InstantiationException ex) {
			Logger.getLogger(FormUtil.class.getName()).log(Level.SEVERE,null,ex);
			
		} catch (IllegalAccessException ex) {
			Logger.getLogger(FormUtil.class.getName()).log(Level.SEVERE,null,ex);
		}
		return object;
	}
	
}
