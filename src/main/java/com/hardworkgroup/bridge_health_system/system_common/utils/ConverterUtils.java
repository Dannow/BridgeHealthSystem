package com.hardworkgroup.bridge_health_system.system_common.utils;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConverterUtils {
    public static Number getAsNumber(final Object obj) {
        if (obj != null) {
            if (obj instanceof Number) {
                return (Number) obj;
            } else if (obj instanceof Boolean) {
                return ((Boolean) obj) ? 1 : 0;
            } else if (obj instanceof String) {
                try {
                    return NumberFormat.getInstance().parse((String) obj);
                } catch (final ParseException e) {
                    throw new NumberFormatException("For input string: \"" + obj + "\"");
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        return null;
    }

    public static Integer getAsInteger(final Object obj) {
        final Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return Integer.valueOf(answer.intValue());
    }

    /**
     * Gets a Integer from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Integer is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj           the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of Object as a Integer, <code>defaultValue</code> if null object input
     */
    public static Integer getAsInteger(final Object obj, final Integer defaultValue) {
        Integer answer = getAsInteger(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a int from a Object in a null-safe manner.
     * <p>
     * The int is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @return the value of the Object as a int, <code>0</code> if null object input
     */
    public static int getAsIntValue(final Object obj) {
        final Integer integerObject = getAsInteger(obj);
        if (integerObject == null) {
            return 0;
        }
        return integerObject.intValue();
    }

    /**
     * Gets a int from a Object in a null-safe manner.
     * <p>
     * The int is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj   the object to use
     * @param defaultValue  return if the value is null or if the conversion fails
     * @return the value of the Object as a int, <code>defaultValue</code> if null object input
     */
    public static int getAsIntValue(final Object obj, final int defaultValue) {
        final Integer integerObject = getAsInteger(obj);
        if (integerObject == null) {
            return defaultValue;
        }
        return integerObject.intValue();
    }

}
