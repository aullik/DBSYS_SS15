package de.dbsys.view;

import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.dbsys.util.LazyInitialisingFinal;


public abstract class AbstractMVCLoader {

   private final boolean PRINT_WARNINGS = false;

   private final LazyInitialisingFinal<ResourceBundle> resources;
   private final LazyInitialisingFinal<FXMLLoader> loader;
   private final LazyInitialisingFinal<Object> fxmlParent;
   private final LazyInitialisingFinal<Object> controller;

   protected final static String POSTFIX_THIS = "Loader";
   protected final static String POSTFIX_FXML = ".fxml";
   protected final static String POSTFIX_CSS = ".css";

   private final static Locale STD_LOCALE = Locale.ENGLISH;

   private final ResourceBundle emptyResource = new ResourceBundle() {

      @Override
      protected Object handleGetObject(final String key) {
         return null;
      }

      @Override
      public Enumeration<String> getKeys() {
         return new Enumeration<String>() {

            @Override
            public String nextElement() {
               return null;
            }

            @Override
            public boolean hasMoreElements() {
               return false;
            }
         };
      }
   };

   public AbstractMVCLoader() {
      resources = new LazyInitialisingFinal<ResourceBundle>(() -> {
         return loadResourceBoundle();
      });
      loader = new LazyInitialisingFinal<FXMLLoader>(() -> {
         return initLoader();
      });
      fxmlParent = new LazyInitialisingFinal<Object>(() -> {
         return initFxmlParent();
      });
      controller = new LazyInitialisingFinal<Object>(() -> {
         return initController();
      });
   }

   private FXMLLoader initLoader() {
      final FXMLLoader loader = new FXMLLoader(getFXMLURL());
      loader.setResources(resources.get());
      setFactory(loader);
      return loader;
   }

   private Object initFxmlParent() {
      try {
         final FXMLLoader l = loader.get();
         final Object parent = l.load();
         if (parent instanceof Parent)
            addCSSIfAvailable((Parent) parent);
         return parent;
      } catch (final IOException e) {

         Throwable cause = e;
         Throwable inner = e.getCause();
         while (inner != null) {
            cause = inner;
            inner = cause.getCause();
         }

         System.err.println("Exception while loading Parent");
         cause.printStackTrace(System.err);
         System.err.println();

         throw new IOError(e);
      }
   }

   private Object initController() {
      // view must be initialized or getController returns null;
      fxmlParent.get();
      return loader.get().getController();
   }

   private void setFactory(final FXMLLoader loader) {
      final Initializable init = controllerInstance();
      if (init == null)
         throw new NullPointerException("No controller instance returned in:\n"
               + getClass().getCanonicalName());
      loader.setController(init);
   }

   protected String getFXMLSimpleName() {
      final String simpleName = getClass().getSimpleName();
      final String fmxlName = simpleName.replaceFirst(POSTFIX_THIS + "$", "");

      return fmxlName;
   }

   private URL getFXMLURL() {

      URL toReturn;
      final Class<?> clazz = getClass();

      toReturn = clazz.getResource(getFXMLSimpleName() + POSTFIX_FXML);
      if (toReturn != null)
         return toReturn;
      throw new Error(new FileNotFoundException("No FXML File found representing: "
            + clazz.getSimpleName()));
   }

   void addCSSIfAvailable(final Parent parent) {
      final URL uri = getClass().getResource(getFXMLSimpleName() + POSTFIX_CSS);
      if (uri == null)
         return;
      final String uriToCss = uri.toExternalForm();
      parent.getStylesheets().add(uriToCss);
   }

   private ResourceBundle loadResourceBoundle() {
      ResourceBundle bundle = loadResourceBoundle(Locale.getDefault());
      if (bundle == null)
         bundle = loadResourceBoundle(STD_LOCALE);
      if (bundle == null)
         bundle = emptyResource;

      return bundle;
   }

   private ResourceBundle loadResourceBoundle(final Locale locale) {

      ResourceBundle bundle = null;
      final String bundleName = getClass().getPackage().getName() + '.' + getFXMLSimpleName();
      try {
         bundle = ResourceBundle.getBundle(bundleName, locale);
      } catch (final MissingResourceException ignore) {
         if (PRINT_WARNINGS)
            System.err.println("Missing Bundle: " + bundleName);
         return null;
      }
      final Enumeration<String> keys = bundle.getKeys();
      if (!keys.hasMoreElements())
         return null; // Empty Bundle
      while (keys.hasMoreElements())
         if (bundle.getString(keys.nextElement()).isEmpty()) {
            if (PRINT_WARNINGS)
               System.err.println("Empty Key in Bundle: " + bundle.getBaseBundleName());
            return null;
         }

      return bundle;
   }

   /**
    * @return the Controller
    */
   protected abstract Initializable controllerInstance();

   // #############################################################################################
   // #############################All Above is used in Initialization ############################
   // #############################################################################################

   /**
    * Sets a new Scene containing this View using {@link Stage#setScene(Scene)} , then calls
    * {@link Stage#show()}
    *
    * @param stage
    *           the Stage where this View will be shown;
    */
   public void showIn(final Stage stage) {
      setViewinStage(stage);
      stage.show();
   }

   /**
    * Sets a new Scene containing this View using {@link Stage#setScene(Scene)} , then calls
    * {@link Stage#showAndWait()}
    *
    * @param stage
    *           the Stage where this View will be shown;
    */
   public void showInAndWait(final Stage stage) {
      setViewinStage(stage);
      stage.showAndWait();
   }

   protected void setViewinStage(final Stage stage) {

      stage.setScene(new Scene(getView()));
      stage.setMinHeight(getView().minHeight(0));
      stage.setMinWidth(getView().minWidth(0));
      stage.setMaxHeight(getView().maxHeight(0));
      stage.setMaxWidth(getView().maxWidth(0));
   }

   @SuppressWarnings("unchecked")
   public <T> T getController() {
      return (T) controller.get();
   }

   protected Object getUncastView() {
      return fxmlParent.get();

   }

   @SuppressWarnings("unchecked")
   public <T extends Node> T getView() {
      return (T) fxmlParent.get();
   }

   public ResourceBundle getResources() {
      return resources.get();
   }

}
