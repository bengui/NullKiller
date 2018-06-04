package com.bengui.nullkiller;

import java.util.List;

/**
 * A humble attempt to simplify the code for the common null check scenarios.
 *
 * @author benjamin.massello.
 */
public class NullKiller {

    /**
     * Callback triggered by {@link NullKiller#ifNotNull(Object, NotNullCallback)} or {@link PossibleNotNull}
     * when the object validated is not null.
     *
     * @param <T> element type.
     */
    public interface NotNullCallback<T> {
        /**
         * Action to be executed using the variable {@param element}.
         *
         * @param element non null instance of the {@param T} class.
         */
        void action(T element);
    }

    /**
     * Callback triggered by {@link NullKiller#ifNull(Object, NullCallback)} or {@link PossibleNull}
     * when the object validated is null.
     */
    public interface NullCallback {
        /**
         * Action to be executed when the validated object is null.
         */
        void action();
    }

    /**
     * Useless method that always retrieves null. The only purpose is to avoid typing the reserved word null.
     *
     * @param <T> element type.
     * @return null
     */
    public static <T> T getNull() {
        return null;
    }

    /**
     * Validates if the given object is null.
     *
     * @param o object to be validated.
     * @return true if the given object is null, false otherwise.
     */
    public static boolean isNull(Object o) {
        return o == null;
    }

    /**
     * Validates if the given object is not null.
     *
     * @param o object to be validated.
     * @return true if the given object is not null, false otherwise.
     */
    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }

    /**
     * Validates if the given {@link List} is not null nor empty.
     *
     * @param list list to be validated.
     * @return true if the given list is not null nor empty, false otherwise.
     */
    public static boolean isNotNullNorEmpty(List list) {
        return isNotNull(list) && !list.isEmpty();
    }

    /**
     * hrows {@link NullPointerException} if the given object {@param o} is null.
     *
     * @param o       object to be validated.
     * @param message to be displayed in the exception.
     */
    private static void checkNotNull(Object o, String message) {
        if (isNull(o)) {
            throw new NullPointerException(message);
        }
    }

    /**
     * Returns the {@param input} if is not null or the {@param defaultValue} if the {@param input} is null.
     *
     * @param input        given object to be validated.
     * @param defaultValue default value to be returned. Must not be null.
     * @param <T>          element type.
     * @return The given {@param input} or {@param defaultValue} if the previous one is null.
     */
    public static <T> T or(T input, T defaultValue) {
        checkNotNull(defaultValue, "The default value can not be null");

        if (isNull(input)) {
            return defaultValue;
        }

        return input;
    }

    /**
     * Validates that the {@param instance} is not null and executes the {@param callback} with the first one
     * as a parameter. Returns a {@link PossibleNull} instance in order to specify an action
     * {@link PossibleNull#ifNull(NullCallback)} that will be executed when the {@param instance} is null.
     *
     * @param instance object to be validated.
     * @param callback will be executed with the value {@param instance} when it is not null.
     * @param <T>      element type.
     * @return A {@link PossibleNull} instance with the given object {@param instance}.
     */
    public static <T> PossibleNull<T> ifNotNull(T instance, NotNullCallback<T> callback) {
        checkNotNull(callback, "The callback can not be null");

        if (isNotNull(instance)) {
            callback.action(instance);
        }

        return new PossibleNull<>(instance);
    }

    /**
     * Validates that the {@param instance} is null and executes the {@param callback}.
     * Returns a {@link PossibleNotNull} instance in order to specify an action
     * {@link PossibleNotNull#ifNotNull(NotNullCallback)} that will be executed when
     * the {@param instance} is null.
     *
     * @param instance object to be validated.
     * @param callback will be executed with the value {@param instance} when it is not null.
     * @param <T>      element type.
     * @return A {@link PossibleNotNull} instance with the given object {@param instance}.
     */
    public static <T> PossibleNotNull<T> ifNull(T instance, NullCallback callback) {
        checkNotNull(callback, "The callback can not be null");

        if (isNull(instance)) {
            callback.action();
        }

        return new PossibleNotNull<>(instance);
    }

    /**
     * Validates that a list is not null nor empty, retrieves the first element and calls to the {@param notNullCallback}
     * with the given element.
     *
     * @param list            {@link List} to be validated.
     * @param notNullCallback callback to be executed if all the validations are successful.
     * @param <T>             element type.
     * @return {@link PossibleNull} instance to specify an action that will be called when the list is null or the
     * list is empty or the first element is null.
     */
    public static <T> PossibleNull<T> ifFirstNotNull(List<T> list, NotNullCallback<T> notNullCallback) {
        checkNotNull(notNullCallback, "The notNullCallback can not be null");
        T o = getNull();

        if (isNotNullNorEmpty(list)) {
            o = list.get(0);
            if (isNotNull(o)) {
                notNullCallback.action(o);
            }
        }

        return new PossibleNull<>(o);
    }

    /**
     * Wraps an object from the class {@param T} that will be potentially null and allows to specify an action for
     * this scenario.
     *
     * @param <T> element type.
     */
    public static class PossibleNull<T> {
        private final T element;

        public PossibleNull(T element) {
            this.element = element;
        }

        /**
         * Executes the callback if the {@link PossibleNull#element} is null.
         *
         * @param callback action to be executed when the {@link PossibleNull#element} is null.
         */
        public void ifNull(NullCallback callback) {
            checkNotNull(callback, "The callback can not be null");

            if (isNull(element)) {
                callback.action();
            }
        }
    }

    /**
     * Wraps an object from the class {@param T} that will be potentially not null and allows to specify an action for
     * this scenario.
     *
     * @param <T> element type.
     */
    public static class PossibleNotNull<T> {
        private final T element;

        public PossibleNotNull(T element) {
            this.element = element;
        }

        /**
         * Executes the callback if the {@link PossibleNotNull#element} is not null.
         *
         * @param callback action to be executed when the {@link PossibleNotNull#element} is not null.
         */
        public void ifNotNull(NotNullCallback<T> callback) {
            checkNotNull(callback, "The callback can not be null");

            if (isNotNull(element)) {
                callback.action(element);
            }
        }
    }
}
