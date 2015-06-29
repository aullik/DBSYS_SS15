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

import de.dbsys.model.Adresse;
import de.dbsys.model.Ausstattung;
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

   private final PreparedStatement testname = createPreparedStatement(
         "Select * from Kunde where mailadresse = ? and passwort = ?");

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
      try {
         testname.setString(1, email);
         testname.setString(2, pw);
         ResultSet res = testname.executeQuery();
         Kunde kd = createKunde(res);
         return Optional.of(kd);
      } catch (SQLException e) {
         System.err.println("Exception while executing login-statement");
         handleSQLException(e);
         throw new RuntimeException(e);
      }

      // return Optional.of(new Kunde());

   }

   public Optional<Kunde> createNewUser(final Kunde newKunde) {
      try {
         insertAdresse(newKunde.getAdresse());
         getConnection();
         Statement stm = createStatement();
         StringBuilder sb = new StringBuilder();
         sb.append("INSERT INTO kunde VALUES (");
         sb.append(Integer.toString(newKunde.getKundenId()) + ", ");
         sb.append("'" + newKunde.getBIC() + "', ");
         sb.append("'" + newKunde.getIBAN() + "', ");
         sb.append("'" + newKunde.getEmail() + "', ");
         sb.append("'" + newKunde.getVorname() + "', ");
         sb.append("'" + newKunde.getNachname() + "', ");
         sb.append("'" + newKunde.getPassword() + "', ");
         sb.append(Integer.toString(newKunde.getAdresse().getAdressId()) + ")");

         String myInsertQuery = sb.toString();

         stm.executeQuery(myInsertQuery);
         stm.close();
         con.commit();
         con.close();

      } catch (SQLException e) {
         System.err.println("Exception while creating a new User");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
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

   // Methode zum Erstellen von Kundenobjekten aus einem ResultSet
   public Kunde createKunde(final ResultSet set) {
      try {
         // TODO: JOIN mit adresse einfügen + Adresse auslesen

         Kunde kd = new Kunde(set.getString("vorname"), set.getString("nachname"),
               set.getString("mailadresse"), set.getString("passwort"), set.getString("IBAN"),
               set.getString("bic"), null);
         return kd;
      } catch (SQLException e) {
         System.err.println("Exception while creating 'kunde' from ResultSet");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
   }

   // Methode für den Insert einer Adresse
   public void insertAdresse(final Adresse adr) {
      try {
         getConnection();
         Statement stm = createStatement();
         StringBuilder sb = new StringBuilder();
         sb.append("INSERT INTO Adresse VALUES (");
         sb.append(Integer.toString(adr.getAdressId()) + ", ");
         sb.append(Integer.toString(adr.getLand().getLandesId()) + ", ");
         sb.append("'" + adr.getPLZ() + "', ");
         sb.append("'" + adr.getStrasze() + ", ");
         sb.append("'" + adr.getHausnummer() + ", ");

         String myInsertQuery = sb.toString();
         stm.executeQuery(myInsertQuery);
         stm.close();
         con.commit();
         con.close();
      } catch (SQLException e) {
         System.err.println("Exception while inserting new adress");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
   }
}
