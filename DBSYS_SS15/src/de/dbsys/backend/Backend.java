package de.dbsys.backend;

import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import de.dbsys.model.Adresse;
import de.dbsys.model.Attraktion;
import de.dbsys.model.Ausstattung;
import de.dbsys.model.Buchung;
import de.dbsys.model.Kunde;
import de.dbsys.model.Land;
import de.dbsys.model.Wohnung;
import javafx.collections.ObservableList;


public final class Backend {

   private Backend() {
      this.con = getConnection();
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
   // FIXME REMOVE

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

      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }

         System.err.println("Exception while creating a new User");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
      return Optional.ofNullable(newKunde);
   }

   public List<Land> getAllLands() {
      List<Land> list = new LinkedList<>();
      try {
         Statement stm = createStatement();
         String mySearchQuery = "SELECT * FROM land";
         ResultSet rset = stm.executeQuery(mySearchQuery);
         while (rset.next()) {
            Land tmp = new Land(rset.getInt("landesid"), rset.getString("landesname"));
            list.add(tmp);
         }
         stm.close();
         con.commit();

      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while selecting * from land");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
      return list;
   }

   public List<Ausstattung> getallAusstattungen() {
      LinkedList<Ausstattung> list = new LinkedList<>();
      try {
         Statement stm = createStatement();
         String mySearchQuery = "SELECT * FROM ausstattung";
         ResultSet rset = stm.executeQuery(mySearchQuery);
         while (rset.next()) {
            Ausstattung tmp = new Ausstattung(rset.getString("bezeichnung"));
            list.add(tmp);
         }
         stm.close();
         con.commit();
      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while selecting * from ausstattung");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
      return list;
   }

   public List<Wohnung> searchApartments(final LocalDate anreise, final LocalDate abreise,
         final Land land, final int zimmer, final int preisMin, final int preisMax,
         final ObservableList<Ausstattung> ausstattungen) {
      List<Wohnung> list = new LinkedList<>();
      try {
         Statement stm = createStatement();
         StringBuilder sb = new StringBuilder();
         sb.append(
               "SELECT * --f1.wohnungsnummer FROM dbsys20.Ferienwohnung f1 JOIN dbsys20.adresse a1 ON f1.adressid = a1.adressid "
                     + "JOIN dbsys20.land l1 ON l1.landesid = a1.landesid JOIN dbsys20.ausgestattetmit am1 ON f1.wohnungsnummer = am1.wohnungsnummer "
                     + "LEFT JOIN dbsys20.liegtinnaehe lin ON f1.wohnungsnummer = lin.wohnungsnummer JOIN dbsys20.attraktion at ON lin.attraktionsname = at.attraktionsname "
                     + "LEFT JOIN dbsys20.Buchung b1 ON f1.wohnungsnummer  = b1.wohnungsnummer");
         sb.append("WHERE l1.landesname = '").append(land.getLandesname()).append("'");
         for (Ausstattung aus : ausstattungen)
            sb.append("AND am1.bezeichnung = '").append(aus.getBezeichung()).append("'");
         sb.append("AND b1.abreisedatum > to_date('").append(abreise.toString()).append("')");
         sb.append("AND b1.anreisedatum < to_date('").append(anreise.toString()).append("')");

         String mySearchQuery = sb.toString();
         ResultSet rset = stm.executeQuery(mySearchQuery);

         HashMap<Integer, List<Ausstattung>> ausMap = new HashMap<>();
         HashMap<Integer, List<Attraktion>> atMap = new HashMap<>();

         while (rset.next()) {
            Wohnung tmp = getWohnung(rset);
            if (!ausMap.containsKey(tmp.getWohnungsnummer())) {
               list.add(tmp);
               ausMap.put(tmp.getWohnungsnummer(), tmp.getAusgestattet_mit());
               atMap.put(tmp.getWohnungsnummer(), tmp.getAttraktionen());
            } else {
               ausMap.get(tmp.getWohnungsnummer()).add(tmp.getAusgestattet_mit().get(0));
               atMap.get(tmp.getWohnungsnummer()).add(tmp.getAttraktionen().get(0));
            }
         }

         for (Wohnung w : list) {
            w.setAttraktionen(atMap.get(w.getWohnungsnummer()));
            w.setAusgestattet_mit(ausMap.get(w.getWohnungsnummer()));
         }

         stm.close();
         con.commit();
      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while searching for apartements");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
      return new LinkedList<>();
   }

   public Optional<Buchung> getLastCompletedBooking(final Kunde kunde) {
      // FIXME: nicht verändern, nicht gebraucht für aufgabe
      return Optional.empty();
   }

   public void evaluateBooking(final Buchung buchung, final int bewertung) {
      // FIXME: nicht verändern, nicht gebraucht für aufgabe
      // TODO Auto-generated method stub
      // bewertung zwischen 10 und 60
   }

   public Optional<Buchung> bookWohnung(final Buchung buchung) {
      // TODO Auto-generated method stub
      return Optional.empty();
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
      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while inserting new adress");
         handleSQLException(e);
         throw new RuntimeException(e);
      }
   }

   // Methode um Daten aus einem ResultSet in eine Wohnung umzuwandeln
   public Wohnung getWohnung(final ResultSet set) {

      Wohnung apt = new Wohnung();
      try {
         Attraktion at = new Attraktion();
         at.setBeschreibung(set.getString("beschreibung"));
         at.setEntfernung(set.getInt("distanz"));
         at.setName(set.getString("attraktionsname"));

         Land la = new Land(set.getInt("landesid"), set.getString("landesname"));
         Adresse adr = new Adresse(set.getString("strasze"), set.getString("plz"), la);
         Ausstattung aus = new Ausstattung(set.getString("bezeichnung"));

         apt.setAnzahlZimmer(set.getInt("anzahlzimmer"));

         apt.setAusgestattet_mit(new LinkedList<>());
         apt.setAttraktionen(new LinkedList<>());
         apt.getAttraktionen().add(at);
         apt.getAusgestattet_mit().add(aus);

         apt.setGroezse(set.getInt("groesze"));
         apt.setName(set.getString("name"));
         apt.setPreisProTag(set.getInt("preisprotag"));
         apt.setWohnungsnummer(set.getInt("wohnungsnummer"));
         apt.setAdresse(adr);

      } catch (SQLException e) {
         try {
            con.rollback();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         System.err.println("Exception while reading from ResultSet in 'getWohnung'");
         handleSQLException(e);
         throw new RuntimeException(e);
      }

      return apt;
   }
}
