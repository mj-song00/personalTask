package com.sparta.personal_task.repository;

import com.sparta.personal_task.dto.ScheduleRequestDto;
import com.sparta.personal_task.dto.ScheduleResponseDto;
import com.sparta.personal_task.scheduleEntity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


public class SchedulRepository {
    private final JdbcTemplate jdbcTemplate;

    public SchedulRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Schedule save(Schedule schedule) {
        //DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체
        String sql = "INSERT INTO post (contents, manager, password, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getContents());
                    preparedStatement.setString(2, schedule.getManager());
                    preparedStatement.setString(3, schedule.getPassword());
                    preparedStatement.setString(4, schedule.getCreatedAt());
                    preparedStatement.setString(5, schedule.getUpdatedAt());

                    return preparedStatement;
                },
                keyHolder);
        return schedule;
    }



    public List<ScheduleResponseDto> getSchedule() {
        //DB 조회
        String sql = "SELECT * FROM post order by updatedAt desc";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String contents = rs.getString("contents");
                String manager = rs.getString("manager");
                String createdAt = rs.getString("createdAt");
                String updatedAt = rs.getString("updatedAt");
                return new ScheduleResponseDto(id, contents, manager, createdAt, updatedAt);
            }
        });
    }


    public Schedule update (int id, ScheduleRequestDto request, String updatedAt){
        ;

        String sql = "UPDATE post SET contents = ?, manager = ?, updatedAt = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                request.getContents(),
                request.getManager(),
                updatedAt,
                id);

        return null;
    }

    public String delete(int id) {
        String sql = "DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return null;
    }


    public Schedule findById(int id) {
        String sql = "SELECT * FROM post WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setId(resultSet.getInt("id"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setContents(resultSet.getString("contents"));
                schedule.setManager(resultSet.getString("manager"));
                schedule.setCreatedAt(resultSet.getString("createdAt"));
                schedule.setUpdatedAt(resultSet.getString("updatedAt"));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }



}
