package kaffee.util;

/**
 * [public]
 *
 * Methods in this class throw Exceptions; each in a cross-language, unified format so that
 * developers will recognize quickly the severity of a problem by reading the log.
 *
 * @author ahorvath
 */
public class ExceptionThrower {

	/**
	 * Use this in switch's default block. IDE may warn you about the same problem.
	 *
	 * @param value What's unhandled.
	 */
	public static void unhandledCase(Object value) {
		throw new RuntimeException("Unhandled switch-case of type " + value.getClass().getSimpleName() + ':' + value.toString());
	}

	/**
	 * When an Enum's value could not be resolved: it was deleted or it wasn't introduced to all
	 * systems.
	 *
	 * @param clazz Enum's class.
	 * @param name .
	 */
	public static void invalidEnumName(Class<? extends Enum> clazz, String name) {
		throw new IllegalArgumentException("Could not resolve " + clazz.getSimpleName() + " value by the name: " + name);
	}
}
