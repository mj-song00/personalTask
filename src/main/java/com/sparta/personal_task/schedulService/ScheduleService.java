package com.sparta.personal_task.schedulService;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sparta.personal_task.dto.ScheduleRequestDto;
import com.sparta.personal_task.dto.ScheduleResponseDto;
import com.sparta.personal_task.scheduleRepository.Schedule;
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

public class ScheduleService {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ScheduleResponseDto createPost(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);

        //DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        LocalDate date = LocalDate.now();
        String createAt = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        schedule.setCreatedAt(createAt);

        String sql = "INSERT INTO post (contents, manager, password, createdAt) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getContents());
                    preparedStatement.setString(2, schedule.getManager());
                    preparedStatement.setString(3, schedule.getPassword());
                    preparedStatement.setString(4, schedule.getCreatedAt());

                    return preparedStatement;
                },
                keyHolder);


        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    private Schedule findById(int id) {
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

    //개별조회
    public ScheduleResponseDto getPost(int id) {
        Schedule schedule = findById(id);

        if (schedule != null) {
            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
            return scheduleResponseDto;
        }
        return null;
    }


    //전체조회
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

    //일정 수정
    public ScheduleResponseDto updatePost(int id, ScheduleRequestDto request) {
        Schedule schedule = findById(id);

        if(schedule == null) {
            throw new RuntimeException("이 아이디를 확인할 수 없습니다.: " + id);
        }

        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호 확인해주세요");
        }

        try {
            LocalDate date = LocalDate.now();
            String updatedAt = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            schedule.setUpdatedAt(updatedAt);

            String sql = "UPDATE post SET contents = ?, manager = ?, updatedAt = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    request.getContents(),
                    request.getManager(),
                    updatedAt,
                    id);


            //update된 DB값 반환
            Schedule newSchedule = findById(id);

            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(newSchedule);
            return scheduleResponseDto;

        } catch (Exception e) {
            throw new RuntimeException("여긴 어떤 에러지;; 잘 모르고 썼습니다;;", e);
        }

    }

    public String deletePost(int id,ScheduleRequestDto requestDto){
        Schedule schedule = findById(id);

        if(schedule == null) {
            throw new RuntimeException("다음 아이디를 확인할 수 없습니다.: " + id);
        }

        if (!schedule.getPassword().equals(requestDto.getPassword())){
            throw new RuntimeException("비밀번호 확인해주세요");
        }

        try{
            String sql = "DELETE FROM post WHERE id = ?";
            jdbcTemplate.update(sql, id);
            return "삭제완료";
        }catch(Error e){
            throw new RuntimeException("여긴 어떤 에러지;; 잘 모르고 썼습니다;;", e);
        }
    }
}
