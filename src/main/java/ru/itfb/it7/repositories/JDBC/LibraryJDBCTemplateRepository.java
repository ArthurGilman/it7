package ru.itfb.it7.repositories.JDBC;

import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itfb.it7.model.BookDisposal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class LibraryJDBCTemplateRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;



    /**
     * создание записи BookDisposal
     * @return
     */

    public Long createBookDisposal(Long copyId, LocalDate disposalDate, String reason) {
        String sql = "insert into book_disposal (copy_id, disposal_date, reason) values (:copyId, :disposalDate, :reason) returning id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("copyId", copyId);
        params.addValue("disposalDate", disposalDate);
        params.addValue("reason", reason);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }


    public Long findBookCopyById(Long copyId) {
        String lockSql = "SELECT * FROM book_copy WHERE id = :copyId FOR UPDATE";
        MapSqlParameterSource lockParams = new MapSqlParameterSource();
        lockParams.addValue("copyId", copyId);
        return jdbcTemplate.queryForObject(lockSql, lockParams, (rs, rowNum) -> rs.getLong("id"));
    }

    public List<BookDisposal> findAllBookDisposalByCopyId(Long copyId) {
        String sql = "select * from book_disposal where id = :copyId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("copyId", copyId);
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            BookDisposal bookDisposal = new BookDisposal();
            bookDisposal.setId(rs.getLong("id"));
            bookDisposal.setCopyId(rs.getLong("copy_id"));
            bookDisposal.setDisposalDate(rs.getDate("disposal_date").toLocalDate());
            bookDisposal.setReason(rs.getString("reason"));
            return bookDisposal;
        });
    }
}
