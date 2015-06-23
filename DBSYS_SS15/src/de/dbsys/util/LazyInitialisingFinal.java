package de.dbsys.util;

import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;


public class LazyInitialisingFinal<T> implements Supplier<T> {

   private T value = null;

   private final Initializer<T> init;

   private boolean initialized = false;

   @FunctionalInterface
   public interface Initializer<T> {

      T initialize();
   }

   public LazyInitialisingFinal(final Initializer<T> initializer) {
      this.init = initializer;
   }

   @Override
   public T get() {
      if (!initialized) {
         value = init.initialize();
         initialized = true;
         Validate.notNull(value);
      }
      return value;
   }

}
