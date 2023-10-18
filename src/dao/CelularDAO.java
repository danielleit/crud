package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import factory.ConnectionFactory;
import model.Celular;

public class CelularDAO {

    private Connection conn;
    private PreparedStatement pstm;

    public ConnectionFactory connectionFactory;

    public CelularDAO() {
        this.connectionFactory = new ConnectionFactory();
        this.conn = connectionFactory.createConnection();
    }

    public void create(Celular celular) {
        String sql = "INSERT INTO celulares(modelo, fabricante, ano) VALUES (?, ?, ?);";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, celular.getModelo());
            pstm.setString(2, celular.getFabricante());
            pstm.setInt(3, celular.getAno());

            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Celular> read() {
        String sql = "SELECT * FROM celulares;";
        ArrayList<Celular> celulares = new ArrayList<>();
        ResultSet rset = null;

        try {
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                int id = rset.getInt("id");
                String modelo = rset.getString("modelo");
                String fabricante = rset.getString("fabricante");
                int ano = rset.getInt("ano");

                Celular celular = new Celular(id, modelo, fabricante, ano);
                celulares.add(celular);
            }

            pstm.close();
            rset.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return celulares;
    }

    public void update(Celular celular) {
        String sql = "UPDATE celulares SET modelo = ?, fabricante = ?, ano = ? WHERE id = ?;";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, celular.getModelo());
            pstm.setString(2, celular.getFabricante());
            pstm.setInt(3, celular.getAno());
            pstm.setInt(4, celular.getId());

            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM celulares WHERE id = ?";

        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, id);

            pstm.execute();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
