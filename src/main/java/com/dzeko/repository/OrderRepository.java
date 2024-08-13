package com.dzeko.repository;

import com.dzeko.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    String sql_insert = "INSERT INTO dzeko2.order_web (customer, creator_user, text, telephone, address) VALUES (?, ?, ?, ?, ?)";
    String sql_select_finished = "SELECT o.idorder, o.customer, o.userid, o.text, o.date_create, o.telephone, o.address, u.email as creator_user_name, o.approve, o.dimensions FROM dzeko2.order_web o left join dzeko2.userss u on(o.creator_user=u.id) WHERE finished = ? limit 200";
    String sql_select_approved = "SELECT o.idorder, o.customer, o.userid, o.text, o.date_create, o.telephone, o.address, u.email as creator_user_name, o.approve, o.dimensions FROM dzeko2.order_web o left join dzeko2.userss u on(o.creator_user=u.id) WHERE approve = ? and finished=false limit 200";
    String sql_finish_order = "UPDATE dzeko2.order_web set finished=? where idorder=?";
    String sql_approve_order = "UPDATE dzeko2.order_web set approve=true where idorder=?";
    String sql_select_idorder = "SELECT o.idorder, o.customer, o.userid, o.text, o.date_create, o.telephone, o.address, u.email as creator_user_name, o.approve, o.dimensions FROM dzeko2.order_web o left join dzeko2.userss u on(o.creator_user=u.id) WHERE idorder=?";
    String sql_update = "UPDATE dzeko2.order_web set customer=?, creator_user=?, text=?, telephone=?, address=? WHERE idorder=?";
    String sql_dimesions_added = "UPDATE dzeko2.order_web set dimensions=true, userid=? where idorder=?";
    String sql_edit_dimension_user = "UPDATE dzeko2.order_web set userid=? where idorder=?";
    String sql_by_clientekeyword = "SELECT o.idorder, o.customer, o.userid, o.text, o.date_create, o.telephone, o.address, u.email as creator_user_name, o.approve, o.dimensions FROM dzeko2.order_web o left join dzeko2.userss u on(o.creator_user=u.id) customer LIKE ?";

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer save(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql_insert, new String[]{"idorder"});
                ps.setString(1, order.getCustomer());
                ps.setInt(2, order.getCreator_user_id());
                ps.setString(3, order.getText());
                ps.setString(4, order.getTelephone());
                ps.setString(5, order.getAddress());
                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public int edit(Order order) {
        return jdbcTemplate.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql_update);
            ps.setString(1, order.getCustomer());
            ps.setInt(2, order.getCreator_user_id());
            ps.setString(3, order.getText());
            ps.setString(4, order.getTelephone());
            ps.setString(5, order.getAddress());
            ps.setInt(6, order.getIdorder());
            return ps;
        });
    }

    public int finishOrder(Integer orderid, boolean state) {
        return jdbcTemplate.update(sql_finish_order, state,orderid);
    }
    
    public int dimensionsOrder(Integer orderid, int iduser) {
        return jdbcTemplate.update(sql_dimesions_added, iduser,orderid);
    }
    
    public int updateUser(int iduser, int orderid) {
        return jdbcTemplate.update(sql_edit_dimension_user, iduser,orderid);
    }

    public List<Order> getOrderByFinished(boolean finished) {
        return jdbcTemplate.query(sql_select_finished, new OrderRowMapper(), new Object[]{finished});
    }

    public List<Order> getOrderByApprove(boolean approved) {
        return jdbcTemplate.query(sql_select_approved, new OrderRowMapper(), new Object[]{approved});
    }
      
    public int approbeOrder(int orderid) {
        return jdbcTemplate.update(sql_approve_order, orderid);
    }

    public Order getOrderByIdorder(int idorder) {
        return jdbcTemplate.queryForObject(sql_select_idorder, new OrderRowMapper(), idorder);
    }

    public List<Order> getByClientKeyword(String keyword) {
        String likePattern = "%" + keyword + "%";
        return jdbcTemplate.query(sql_by_clientekeyword, new Object[]{likePattern}, new BeanPropertyRowMapper<>(Order.class));
        
    }

    private static class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setIdorder(rs.getInt("idorder"));
            order.setCustomer(rs.getString("customer"));
            order.setUserid(rs.getInt("userid"));
            order.setDate_create(rs.getDate("date_create").toLocalDate());
            order.setText(rs.getString("text"));
            order.setTelephone(rs.getString("telephone"));
            order.setAddress(rs.getString("address"));
            order.setApprove(rs.getBoolean("approve"));
            order.setDimensions(rs.getBoolean("dimensions"));
            order.setCreator_user(rs.getString("creator_user_name"));
            return order;
        }
    }

}
