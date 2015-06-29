package de.dbsys.backend;

import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import de.dbsys.model.Ausstattung;
import de.dbsys.model.Buchung;
import de.dbsys.model.Kunde;
import de.dbsys.model.Land;
import de.dbsys.model.Wohnung;
import javafx.collections.ObservableList;


public final class Backend {

   private Backend() {
      Security.addProvider(new BouncyCastleProvider());
      // Treiber laden
      try {
         DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
      // test();
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
      try {
         if (con == null || con.isClosed())
            con = DriverManager.getConnection(
                  "jdbc:oracle:thin:@" + dbHost + ":" + dbPort + "/" + dbName, dbUser, dbPass);
         return con;
      } catch (SQLException t) {
         throw new RuntimeException(t);
      }
   }

   // private final PreparedStatement testname = createPreparedStatement(
   // "Select * from Kunde where email = ? and passwort = ?");

   private PreparedStatement createPreparedStatement(final String sql) {
      try {
         return con.prepareStatement(sql);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   private Statement createStatement() {
      try {

         return getConnection().createStatement();
      } catch (SQLException e) {
         throw new RuntimeException(e);
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
      } , statement);

      // execute Statement throws RuntimeException if container is empty

   }

   private void executeUpdateInsert(final String statement) {
      executeStatement((s) -> {
         s.execute(statement);
         System.out.println("Executed Query: " + statement);
      } , statement);
   }

   private void executeStatement(final SQLConsumer<Statement> executor,
         final String statementString) throws RuntimeException {
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

   public Optional<Kunde> login(final String email, final String pw) {
      // TODO Auto-generated method stub

      return Optional.of(new Kunde());

   }

   public Optional<Kunde> createNewUser(final Kunde newKunde) {
      // TODO Auto-generated method stub
      return Optional.ofNullable(newKunde);
   }

   public List<Land> getAllLands() {
      // TODO Auto-generated method stub
      List<Land> list = new LinkedList<>();
      list.add(new Land(0, "Deutschland"));
      return list;
   }

   public List<Ausstattung> getallAusstattungen() {
      // TODO Auto-generated method stub
      LinkedList<Ausstattung> list = new LinkedList<>();
      list.add(new Ausstattung("Sauna"));
      return list;
   }

   public List<Wohnung> searchApartments(final LocalDate anreise, final LocalDate abreise,
         final Land land, final int zimmer, final int preisMin, final int preisMax,
         final ObservableList<Ausstattung> ausstattungen) {
      // TODO Auto-generated method stub
      return new LinkedList<>();
   }

   public Optional<Buchung> getLastCompletedBooking(final Kunde kunde) {
      // TODO Auto-generated method stub
      return Optional.empty();
   }

   public void evaluateBooking(final Buchung buchung, final int bewertung) {
      // TODO Auto-generated method stub
      // bewertung zwischen 10 und 60
   }

   public Optional<Buchung> bookWohnung(final Wohnung wohnung) {
      // TODO Auto-generated method stub
      return null;
   }
}
