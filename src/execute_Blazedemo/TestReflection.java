package execute_Blazedemo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import wrapper.KeywordWrapper;

public class TestReflection {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {

		Class<KeywordWrapper> wrapper = KeywordWrapper.class;
	    Object wM = wrapper.newInstance();
	    
	    Method[] methods = wrapper.getDeclaredMethods();
	    for (Method method : methods) {
			System.out.println(method.getName());
		}
	    

	    for (Constructor<?> cons :  wrapper.getConstructors()) {
			System.out.println(cons.getName());
			System.out.println(cons.getParameterCount());
		}
	}

}
