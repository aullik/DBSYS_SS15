package de.dbsys.backend;

import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public final class Backend {

   private Backend() {
      Security.addProvider(new BouncyCastleProvider());
      test();
   }

   private final static Backend INSTANCE = new Backend();

   public static Backend get() {
      return INSTANCE;
   }

   private static final String dbHost = "oracle12c.in.htwg-konstanz.de"; // Hostname
   private static final String dbPort = "1521"; // Port -- Standard: 3306
   private static final String dbName = "ora12c"; // Datenbankname
   private static final String dbUser = "dbsys15"; // Datenbankuser
   private static final String dbPass = "dbsys15"; // Datenbankpasswort

   private Connection con;

   private Connection getConnection() {
      return getConnection(true);
   }

   private Connection getConnection(final boolean rek) {
      try {
         if (con == null || con.isClosed())
            con = DriverManager.getConnection("jdbc:oracle:thin:@" + dbHost + ":" + dbPort + "/"
                  + dbName, dbUser, dbPass);
         return con;
      } catch (SQLException t) {
         if (rek) {
            con = null;
            return getConnection(false);
         } else
            handleSQLException(t);
         throw new RuntimeException(t);
      }
   }

   private Statement createStatement() {
      return createStatement(true);
   }

   private Statement createStatement(final boolean rek) {
      try {

         return getConnection().createStatement();
      } catch (SQLException e) {
         if (rek) {
            con = null;
            return createStatement(false);
         } else {
            handleSQLException(e);
            throw new RuntimeException(e);
         }
      }
   }

   private void handleSQLException(final SQLException e) {
      System.err.println("SQLException: " + e.getMessage());
      System.err.println("SQLState: " + e.getSQLState());
      System.err.println("VendorError: " + e.getErrorCode());
      e.printStackTrace();
   }

   private interface SQLConsumer<T> {

      public void accept(T t) throws SQLException;

   }

   private void executeSelect(final String statement, final SQLConsumer<ResultSet> resultConsumer) {

      executeStatement((s) -> {
         ResultSet res = s.executeQuery(statement);
         resultConsumer.accept(res);
         System.out.println("Executed Query: " + statement);
      }, statement);

      // execute Statement throws RuntimeException if container is empty

   }

   private void executeUpdateInsert(final String statement) {
      executeStatement((s) -> {
         s.execute(statement);
         System.out.println("Executed Query: " + statement);
      }, statement);
   }

   private void
   executeStatement(final SQLConsumer<Statement> executor, final String statementString)
         throws RuntimeException {
      try {
         Statement s = createStatement();
         executor.accept(s);
         s.close();
      } catch (SQLException e) {
         System.err.println("Exception while executing Query: " + statementString);
         handleSQLException(e);
         throw new RuntimeException(e);
      }
   }

   // @SuppressWarnings("unused")
   private void test() {
      String statement = "Select 'test' from dual";
      executeSelect(statement, res -> {
         res.next();
         System.out.println(res.getString(1));
      });
   }
}
