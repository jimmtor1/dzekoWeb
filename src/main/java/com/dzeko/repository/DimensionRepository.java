package com.dzeko.repository;

import com.dzeko.model.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DimensionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String sql_insert = "INSERT INTO dzeko2.dimension_web (orderid, width, height, opening, depth, quantity,wing_shorter, wing_longer, note, big_note) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String sql_select_by_order = "SELECT * FROM dzeko2.dimension_web WHERE orderid = ?";
    private final String sql_select_by_iddimension = "SELECT * FROM dzeko2.dimension_web WHERE iddimension = ?";
    private final String sql_update = "UPDATE dzeko2.dimension_web SET orderid=?, width=?, height=?, opening=?, depth=?, quantity=?, wing_shorter=?, wing_longer=?, note=?, big_note=? WHERE iddimension = ?";
    private final String sql_delete = "DELETE FROM dzeko2.dimension_web WHERE iddimension = ?";

    public DimensionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int[] saveAll(List<Dimension> dimesions) {

        List<Object[]> batchArgs = new ArrayList<>();
        for (Dimension dimension : dimesions) {
            batchArgs.add(new Object[]{
                dimension.getOrderid(), dimension.getWidth(), dimension.getHeight(), dimension.getOpening(), dimension.getDepth(), dimension.getQuantity(),
                dimension.getWingShorter(), dimension.getWingLonger(), dimension.getNote(), dimension.getBigNote()
            });
        }

        return jdbcTemplate.batchUpdate(sql_insert, batchArgs);
    }

    public List<Dimension> getDimensionsByIdorder(int idorder) {
        return jdbcTemplate.query(sql_select_by_order, new OrderRowMapper(), idorder);
    }

    public Dimension getDimensionsByIddimension(int iddimension) {
        return jdbcTemplate.queryForObject(sql_select_by_iddimension, new OrderRowMapper(), iddimension);

    }

    public int editOne(Dimension dimension) {
        return jdbcTemplate.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql_update);
            ps.setInt(1, dimension.getOrderid());
            ps.setFloat(2, dimension.getWidth());
            ps.setFloat(3, dimension.getHeight());
            ps.setString(4, dimension.getOpening());
            ps.setFloat(5, dimension.getDepth());
            ps.setInt(6, dimension.getQuantity());
            ps.setString(7, dimension.getWingShorter());
            ps.setString(8, dimension.getWingLonger());
            ps.setString(9, dimension.getNote());
            ps.setString(10, dimension.getBigNote());
            ps.setInt(11, dimension.getIddimension());
            return ps;
        });
    }

    public int deleteOne(Integer iddimension) {
        return jdbcTemplate.update(sql_delete, iddimension);
    }

    public int addOne(Dimension dimension) {
        return jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql_insert);
                ps.setInt(1, dimension.getOrderid());
                ps.setFloat(2, dimension.getWidth());
                ps.setFloat(3, dimension.getHeight());
                ps.setString(4, dimension.getOpening());
                ps.setFloat(5, dimension.getDepth());
                ps.setInt(6, dimension.getQuantity());
                ps.setString(7, dimension.getWingShorter());
                ps.setString(8, dimension.getWingLonger());
                ps.setString(9, dimension.getNote());
                ps.setString(10, dimension.getBigNote());                
                return ps;
            }
        });
    }

    private static class OrderRowMapper implements RowMapper<Dimension> {

        @Override
        public Dimension mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dimension dimension = new Dimension();
            dimension.setIddimension(rs.getInt("iddimension"));
            dimension.setOrderid(rs.getInt("orderid"));
            dimension.setWidth(rs.getFloat("width"));
            dimension.setHeight(rs.getInt("height"));
            dimension.setOpening(rs.getString("opening"));
            dimension.setDepth(rs.getFloat("depth"));
            dimension.setQuantity(rs.getInt("quantity"));
            dimension.setWingShorter(rs.getString("wing_shorter"));
            dimension.setWingLonger(rs.getString("wing_longer"));
            dimension.setNote(rs.getString("note"));
            dimension.setBigNote(rs.getString("big_note"));
            return dimension;
        }
    }

}
