package io.glide.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ResourceNotSavedException extends Exception{

	private static final long serialVersionUID = 1L;

	public ResourceNotSavedException(Class clazz, Object obj){
    	super(StringUtils.capitalize(clazz.getSimpleName()) + " was not saved for the object " + obj);
    }
}
