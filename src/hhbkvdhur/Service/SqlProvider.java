package hhbkvdhur.Service;

import hhbkvdhur.Model.*;

import java.sql.*;
import java.util.*;

public class SqlProvider
{
    private Connection connection;
    private Statement stmt;

    public SqlProvider()
    {
        this.connection = null;
        this.stmt = null;
    }

    private void openConnection() throws SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            String url = "jdbc:mysql://localhost:3306/hardwarereperatur";
            this.connection = DriverManager.getConnection(url,"root","");
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void closeConnection()
    {
        if(this.connection != null)
        {
            try
            {
                connection.close();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<Hardware> getAllHardware() throws SQLException
    {
        ArrayList<Hardware> hardwareArrayList = new ArrayList<>();
        String sql;
        ResultSet resultSet;

        openConnection();

        sql = "SELECT * FROM hardware";

        stmt = connection.createStatement();

        stmt.execute(sql);

        resultSet = stmt.getResultSet();

        while (resultSet.next() == true) {
            hardwareArrayList.add(new Hardware(
                    resultSet.getInt("id"),
                    resultSet.getString("typ"),
                    resultSet.getString("seriennummer"),
                    resultSet.getString("inventarnummer"),
                    resultSet.getString("hersteller"),
                    resultSet.getString("modell"),
                    resultSet.getInt("status"),
                    resultSet.getString("art")
            ));
        }

        stmt.close();

        closeConnection();

        return hardwareArrayList;
    }


    public Hardware getAllHardwareByRoomID(int raumId) throws SQLException
    {
        for(Hardware hardware : getAllHardware())
        {
            if(hardware.getId() == raumId)
            {
                return hardware;
            }
        }
        return null;
    }

    public List<Raum> getAllRooms() throws SQLException
    {
        ArrayList<Raum> raumArrayList = new ArrayList<>();
        String sql;
        ResultSet resultSet;

        openConnection();

        sql = "SELECT id, bezeichnung, typ, anzahlArbeitsplaetze FROM hardware;";

        // Statementobjekt erzeugen
        stmt = connection.createStatement();

        // SQL-Statement abschicken
        stmt.execute(sql);

        // Ergebnismenge holen
        resultSet = stmt.getResultSet();

        while (resultSet.next() == true)
        {
            raumArrayList.add(new Raum(
                    resultSet.getInt("id"),
                    resultSet.getString("bezeichnung"),
                    resultSet.getString("typ"),
                    resultSet.getInt("anzahlArbeitsplaetze")
            ));
        }

        stmt.close();

        closeConnection();

        return raumArrayList;
    }


    public void updateRoom(Raum room) throws SQLException {
        String sql;

        openConnection();

        sql = "UPDATE room(id, bezeichnung, typ, anzahlArbeitsplaetze ) " +
                "VALUES('" + room.getRaumid() + "','"
                + room.getBezeichnung() + "','"
                + room.getTyp() + "','"
                + room.getAnzahlArbeitsplaetze() + "', 0)";

        stmt = connection.createStatement();

        stmt.execute(sql);

        stmt.close();

        closeConnection();
    }


    public void insertRoom(Raum room) throws SQLException {
        String sql;

        openConnection();

        sql = "INSERT INTO raum(bezeichnung, typ, anzahlArbeitsplaetze) " +
                "VALUES('" + room.getBezeichnung() + "','"
                + room.getTyp() + "','"
                + room.getAnzahlArbeitsplaetze() + "', 0)";

        stmt = connection.createStatement();

        stmt.execute(sql);

        stmt.close();

        closeConnection();
    }

    public void insertHardware(Hardware hardware) throws SQLException
    {
        String sql;

        openConnection();

        sql = "INSERT INTO hardware(typ, seriennummer, inventarnummer, hersteller, modell, status, art) " +
                "VALUES('" + hardware.getTyp() + "','"
                + hardware.getSeriennummer() + "','"
                + hardware.getInventarnummer() + "','"
                + hardware.getHersteller() + "','"
                + hardware.getModell() + "','"
                + hardware.getStatus() + "',"
                + "0)";

        // Statementobjekt erzeugen
        stmt = connection.createStatement();

        // SQL-Statement abschicken
        stmt.execute(sql);

        stmt.close();

        closeConnection();
    }

    public void updateHardware()
    {

    }
}
