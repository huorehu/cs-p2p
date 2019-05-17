package com.shpp.p2p.cs.sushakov.assignment11.advancedcalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Contains methods that forming from a stream of strings a map as
 * key - value.
 */
public class ArgumentParser {

    /**
     * Parses stream of the string and forming map of the arguments as name
     * variable and it value.
     * @param args the stream of the strings as arguments
     * @return map of the arguments as name(key) and value(value).
     */
    public Map<String, Double> parseArguments(Stream<String> args) {
        if (args == null) {
            throw new IllegalArgumentException("'args' can not be 'null'");
        }
        Map<String, Double> argsMap = new HashMap<>();
        args.forEach(arg -> argsMap.put(getName(arg), getValue(arg)));
        return argsMap;
    }

    /**
     * Parse {@link String} and returns int value constructed from substring started from '=' and to
     * and line. If this substring is not int value then return '0'.
     * @param arg parsed string
     * @return int value after '=' and to end line. '0' - if line is not a integer.
     */
    private double getValue(String arg) {
        if (!checkArgument(arg)) {
            throw new IllegalArgumentException(getExceptionMessage(arg));
        }

        return Double.parseDouble(arg.substring(arg.indexOf('=') + 1));
    }

    /**
     * Parsed name of the particular variable.
     * @param arg parsed string
     * @return name of variable
     */
    private String getName(String arg) {
        if (!checkArgument(arg)) {
            throw new IllegalArgumentException(getExceptionMessage(arg));
        }
        return arg.substring(0, arg.indexOf('='));
    }

    /** Validate expression argument. */
    private boolean checkArgument(String arg) {
        return arg != null && arg.matches("[A-Za-z]+=-?\\d+(\\.\\d+)?");
    }

    /** Forming exception message from not valid arguments line. */
    private String getExceptionMessage(String arg) {
        return "Argument '" + arg + "' is not valid";
    }

}
