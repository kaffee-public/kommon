package kaffee.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * .
 * @author ahorvath
 */
public final class Enums {

	private static final Map<Class<?>, Map<String, Enum<?>>> names = new HashMap<>();
	private static final Map<Class<?>, Map<String, Enum<?>>> codesToEnum = new HashMap<>();

	public static <T extends Enum<?> & StringEnum> T getByCode(Class<T> clazz, String code) {
		if (!codesToEnum.containsKey(clazz)) {
			gatherCodes(clazz);
		}
		return (T) codesToEnum.get(clazz).get(code);
	}

	public static <T extends Enum<?> & StringEnum> Set<String> codes(Class<T> clazz) {
		if (!codesToEnum.containsKey(clazz)) {
			gatherCodes(clazz);
		}
		return codesToEnum.get(clazz).keySet();
	}

	public static <T extends Enum<?>> T getByName(Class<T> clazz, String name) {
		if (!names.containsKey(clazz)) {
			gatherNames(clazz);
		}
		return (T) codesToEnum.get(clazz).get(name);
	}

	public static <T extends Enum<?>> boolean hasName(Class<T> clazz, String name) {
		if (!names.containsKey(clazz)) {
			gatherNames(clazz);
		}
		return names.get(clazz).keySet().contains(name);
	}

	public static <T extends Enum<?> & StringEnum> boolean hasCode(Class<T> clazz, String code) {
		return codes(clazz).contains(code);
	}

	public static <T extends Enum<?>> Set<String> names(Class<T> clazz) {
		if (!names.containsKey(clazz)) {
			gatherNames(clazz);
		}
		return names.get(clazz).keySet();
	}

	private static <T extends Enum<?>> void gatherNames(Class<T> forType) {
		Object[] possibleValues = forType.getEnumConstants();
		Map<String, Enum<?>> enumNames = new TreeMap<>();
		for (Object obj : possibleValues) {
			T enumValue = (T) obj;
			enumNames.put(enumValue.name(), enumValue);
		}
		names.put(forType, enumNames);
	}

	private static <T extends Enum<?> & StringEnum> void gatherCodes(Class<T> forType) {
		Object[] possibleValues = forType.getEnumConstants();
		Map<String, Enum<?>> codes = new TreeMap<>();
		for (Object obj : possibleValues) {
			T enumValue = (T) obj;
			codes.put(enumValue.toString(), enumValue);
		}
		codesToEnum.put(forType, codes);
	}
}
