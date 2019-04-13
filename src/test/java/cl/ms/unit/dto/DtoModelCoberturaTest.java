package cl.ms.unit.dto;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.ms.dto.relacion.crear.CrearUsuarioRequestDTO;

public class DtoModelCoberturaTest {

	private static Logger logger = LoggerFactory.getLogger(DtoModelCoberturaTest.class);
	
	private static final List<Class<?>> DTO_MODEL_CLASSES = new ArrayList<>();

	private static final Map<String, Map<String, FieldBean>> FIELDS_MAP_CACHE = new HashMap<>();
	
	static {
		DTO_MODEL_CLASSES.add(CrearUsuarioRequestDTO.class);
	}
	
	@Test
	public void generalTest() {
		
		for (Class<?> dtoClass : DTO_MODEL_CLASSES) {

			logger.info("------------------------------------");
			
			Object dto = createInstance(dtoClass, true);

			//Para comparar con atributos iguales hasta un cierto punto, permitiendo pasar por todos los pasos
			Map<String, FieldBean> fieldsMap = getFieldsMap(dto.getClass());
			Object[] dtoClones = new Object[fieldsMap.size()];
			for (int i = 0; i < dtoClones.length; i++) {
				Object dtoClon = createInstance(dtoClass, false);
				clone(dto, dtoClon, fieldsMap, i);
				dtoClones[i] = dtoClon;
			}
			
			Object other = createInstance(dtoClass, true);
			
			Object otherEmpty = createInstance(dtoClass, false);

			logger.info("Comparing classes {}", dto.getClass());
			logger.info("dto: {}", dto);
			logger.info("other: {}", other);
			logger.info("otherEmpty: {}", otherEmpty);
			
			//Comparando para utilizar equals y hashCode en distintos caminos
			dto.equals(dto);
			dto.equals(new Object());
			for (int i = 0; i < dtoClones.length; i++) {
				dto.equals(dtoClones[i]);
				dtoClones[i].equals(dto);
			}
			dto.equals(other);
			dto.equals(otherEmpty);
			otherEmpty.equals(other);
			dto.hashCode();
			otherEmpty.hashCode();
			dto.toString();
			
		}
		
		
	}

	//Nota: esto s�lo sirve para los DTO. No colocar interfaces.

	private Object createInstance(Class<?> clz) {
		return createInstance(clz, null, true);
	}
	
	private Object createInstance(Class<?> clz, boolean fillFields) {
		return createInstance(clz, null, fillFields);
	}
	
	private Object createInstance(Class<?> clz, ParameterizedType parameterizedType, boolean fillFields) {
		
		logger.info("Create instance of {}", clz.getName());
		
		Object res = null;
		
		//Creando objetos b�sicos
		if (String.class.isAssignableFrom(clz)) {
			res = UUID.randomUUID().toString();
		} else if (Boolean.class.isAssignableFrom(clz) || clz == boolean.class) {
			res = Math.random() > 0.5;
		} else if (Byte.class.isAssignableFrom(clz) || clz == byte.class) {
			res = (byte) new Random().nextInt(128);
		} else if (Character.class.isAssignableFrom(clz) || clz == char.class) {
			res = (char) new Random().nextInt(16384);
		} else if (Short.class.isAssignableFrom(clz) || clz == short.class) {
			res = (short) new Random().nextInt(16384);
		} else if (Integer.class.isAssignableFrom(clz) || clz == int.class) {
			res = new Random().nextInt(1000);
		} else if (Long.class.isAssignableFrom(clz) || clz == long.class) {
			res = Long.valueOf(new Random().nextInt(1000));
		} else if (Float.class.isAssignableFrom(clz) || clz == float.class) {
			res = (float) Math.random();
		} else if (Double.class.isAssignableFrom(clz) || clz == double.class) {
			res = Math.random();
		} else if (java.util.Date.class.isAssignableFrom(clz)) {
			res = new java.util.Date();
		} else if (Calendar.class.isAssignableFrom(clz)) {
			res = Calendar.getInstance();
		} else if (BigInteger.class.isAssignableFrom(clz)) {
			res = BigInteger.valueOf(new Random().nextInt(1000));
		} else if (BigDecimal.class.isAssignableFrom(clz)) {
			res = BigDecimal.valueOf(Math.random());
		} else if (clz.isEnum()) {
			Object[] values = clz.getEnumConstants();
			res = values[new Random().nextInt(values.length)];
		} else if (List.class.isAssignableFrom(clz)) {
			if (parameterizedType == null) {
				throw new RuntimeException("Se requiere parameterizedType para el tipo List");
			}
	    	Class<?> elemClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
			int len = 1 + new Random().nextInt(10);
			List<Object> list = new ArrayList<>();
			for (int i = 0; i < len; i++) {
				list.add(createInstance(elemClass));
			}
			res = list;
		} else {

			logger.info("Using constructor of {}", clz.getName());
			
			Constructor<?>[] cs = clz.getConstructors();
			
			boolean isLombok = false;
			Object builderObj = null;
			
			if (cs.length == 0) {
				//Si utiliza lombok, hay que crearlo a trav�s del builder. No sirve preguntar por @Builder porque tiene retention source,
				//con lo que no se detecta en runtime. Se debe preguntar directamente por el m�todo est�tico builder()
				Method builderMethod = null;
				try {
					builderMethod = clz.getMethod("builder");
				} catch (NoSuchMethodException e) {
					//Se continua con el constructor normal
				} catch (SecurityException e) {
					throw new RuntimeException(e);
				}
				
				if (builderMethod != null) {
					isLombok = true;
					try {
						builderObj = builderMethod.invoke(null);
						Method buildMethod = builderObj.getClass().getMethod("build");
						res = buildMethod.invoke(builderObj);
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				} else {
					try {
						res = clz.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
			} else {
			
				Object first = null;
				
				//Utilizando constructores
				for (int i = 0; i < cs.length; i++) {
					
					Class<?>[] paramTypes = cs[i].getParameterTypes();
					
					Object[] params = new Object[paramTypes.length];
					for (int j = 0; j < paramTypes.length; j++) {
						params[i] = createInstance(paramTypes[j]);
					}
					
					Object instance = null;
					
					try {
						instance = cs[i].newInstance(params);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						throw new RuntimeException(e);
					}
					
					//De preferencia utiliza la instancia de constructor vac�o
					if (paramTypes.length == 0) {
						res = instance;
					}
					
					if (first == null) {
						first = instance;
					}
				}
	
				//Si no hay instancia de constructor sin par�metros, utiliza la primera que haya construido.
				if (res == null) {
					res = first;
				}
			}
			
			logger.info("Created instance of {}. Value: {}", clz.getName(), res);
			
			if (fillFields) {
			
				//Creando atributos, asignandolos con el setter y ley�ndolos con el getter
				Map<String, FieldBean> fieldsMap = getFieldsMap(clz);
				for (Map.Entry<String, FieldBean> fieldEntry : fieldsMap.entrySet()) {
					FieldBean fieldBean = fieldEntry.getValue();
					Method getter = fieldBean.getGetter();
					Method setter = fieldBean.getSetter();
					Field field = fieldBean.getField();
					Object fieldObj;
					if (field.getGenericType() instanceof ParameterizedType) {
						fieldObj = createInstance(field.getType(), (ParameterizedType) field.getGenericType(), fillFields);
					} else {
						fieldObj = createInstance(field.getType(), fillFields);
					}
					logger.info("Class {}, using setter {} with value {}", clz.getName(), setter.getName(), fieldObj);
					try {
						setter.invoke(res, fieldObj);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new RuntimeException(e);
					}
					logger.info("Class {}, using getter {}", clz.getName(), getter.getName());
					try {
						getter.invoke(res);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new RuntimeException(e);
					}

					//Si es lombok, se utilizan los m�todos del builder, que se llaman igual que los atributos
					if (isLombok) {
						try {
							Method builderSetter = builderObj.getClass().getMethod(field.getName(), field.getType());
							builderObj = builderSetter.invoke(builderObj, fieldObj);
							Method toString = builderObj.getClass().getMethod("toString");
							toString.invoke(builderObj);
						} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							throw new RuntimeException(e);
						}
						
					}
					
				}

			}
			
		}
		
		return res;
	}
	
	private Map<String, FieldBean> getFieldsMap(Class<?> objClass) {
		Map<String, FieldBean> fieldsMap = FIELDS_MAP_CACHE.get(objClass.getName());
		if (fieldsMap == null) {	
			fieldsMap = new LinkedHashMap<>();
			Field[] fields = objClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				//Viendo si tiene getter
				String getterName = accessorName(fieldName, "get");
				Method getter = getGetter(objClass, getterName);
				if (getter == null) {
					if (field.getType() == boolean.class || field.getType() == Boolean.class) {
						getterName = accessorName(fieldName, "is");
						getter = getGetter(objClass, getterName);
					}
				}
				if (getter != null) {
					String setterName = accessorName(fieldName, "set");
					Method setter = getSetter(objClass, setterName , field.getType());
					FieldBean fieldBean = new FieldBean(field, getter, setter);
					fieldsMap.put(fieldName, fieldBean);
				}
			}
			
			//Agregando los atributos de la superclase
			if (objClass.getSuperclass() != null) {
				Map<String, FieldBean> superFieldsMap = getFieldsMap(objClass.getSuperclass());
				fieldsMap.putAll(superFieldsMap);
			}
			FIELDS_MAP_CACHE.put(objClass.getName(), fieldsMap);
		}
		
		return fieldsMap;
	}	
	
	private String accessorName(String fieldName, String prefix) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		
		sb.append(fieldName.substring(0,1).toUpperCase());
		
		if (fieldName.length() > 1) {
			sb.append(fieldName.substring(1));
		}
		
		return sb.toString();
	}
	
	private Method getGetter(Class<?> clz, String methodName) {
		Method method = null;
		try {
			method = clz.getMethod(methodName);
		} catch (NoSuchMethodException e) {
			method = null;
		} catch (SecurityException e) {
			// Ignored
		}
		return method;
	}

	private Method getSetter(Class<?> clz, String methodName, Class<?> type) {
		Method method = null;
		try {
			method = clz.getMethod(methodName, type);
		} catch (NoSuchMethodException e) {
			method = null;
		} catch (SecurityException e) {
			// Ignored
		}
		return method;
	}	
	
	private void clone(Object fromObj, Object toObj, Map<String, FieldBean> fieldsMap, int idx) {
		
		Class<?> clz = fromObj.getClass();
		
		int i = 0;
		//Creando atributos, asignandolos con el setter y ley�ndolos con el getter, hasta un cierto campo
		for (Map.Entry<String, FieldBean> fieldEntry : fieldsMap.entrySet()) {
			if (i == idx) {
				break;
			}
			FieldBean fieldBean = fieldEntry.getValue();
			Method getter = fieldBean.getGetter();
			Method setter = fieldBean.getSetter();
			Field field = fieldBean.getField();
			Object fieldObj;				
			logger.info("Class {}, cloning field {}", clz.getName(), field.getName());
			try {
				fieldObj = getter.invoke(fromObj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			try {
				setter.invoke(toObj, fieldObj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			i++;
		}
	}
	
	/**
	 * Contiene informaci�n de atributo y sus accessors
	 * @author fmontoya
	 *
	 */
	private class FieldBean {
		
		private final Field field;
		
		private final Method getter;
		
		private final Method setter;
		
		public FieldBean(Field field, Method getter, Method setter) {
			this.field = field;
			this.getter = getter;
			this.setter = setter;
		}

		public Field getField() {
			return field;
		}

		public Method getGetter() {
			return getter;
		}

		public Method getSetter() {
			return setter;
		}

	}	
}
