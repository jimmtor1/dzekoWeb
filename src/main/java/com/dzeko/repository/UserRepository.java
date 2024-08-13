package com.dzeko.repository;

import com.dzeko.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUsernameAndPassword(String username, String pass) {
        try {
            return jdbcTemplate.queryForObject("SELECT u.id,u.ime,r.role,r.idrole FROM userss u inner join role r on(r.idrole=u.role) WHERE u.email = ? AND u.sifra = ?", new UserRowMapper(), username, pass);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getInt("id"), rs.getString("ime"), rs.getString("role"), rs.getString("idrole"));
            return user;
        }

    }

}
