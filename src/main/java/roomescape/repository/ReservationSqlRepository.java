package roomescape.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationSqlRepository implements ReservationRepository {
    private static final RowMapper<Reservation> mapper = (resultset, rowNum) ->
            new Reservation(
                    resultset.getLong("id"),
                    resultset.getString("name"),
                    resultset.getString("date"),
                    new ReservationTime(
                            resultset.getLong("time_id"),
                            LocalTime.parse(resultset.getString("time_value"))
                    ));
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationSqlRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("""
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value
                FROM reservation as r
                inner join reservation_time as t
                on r.time_id = t.id
                """, mapper);
    }

    public long create(final Reservation reservation, final long reservationTimeId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", null);
            params.put("name", reservation.getName());
            params.put("date", reservation.getDate());
            params.put("time_id", reservationTimeId);

            return jdbcInsert.executeAndReturnKey(params)
                             .longValue();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    public boolean deleteById(long reservationId) {
        try {
            jdbcTemplate.update("delete from reservation where id=?",
                    reservationId
            );
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
