package com.sparta.personal_task.schedulService;

import com.sparta.personal_task.dto.ScheduleRequestDto;
import com.sparta.personal_task.dto.ScheduleResponseDto;
import com.sparta.personal_task.repository.SchedulRepository;
import com.sparta.personal_task.scheduleEntity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

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


        LocalDate localDate = LocalDate.now();
        String date = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        schedule.setCreatedAt(date);
        schedule.setUpdatedAt(date);

        SchedulRepository schedulRepository = new SchedulRepository(jdbcTemplate);
        Schedule saveSchedule = schedulRepository.save(schedule);


        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);

        return scheduleResponseDto;
    }



    //개별조회
    public ScheduleResponseDto getPost(int id) {
        SchedulRepository schedulRepository = new SchedulRepository(jdbcTemplate);

        Schedule schedule = schedulRepository.findById(id);
        if (schedule != null) {
            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);
            return scheduleResponseDto;
        }
        return null;
    }


    //전체조회
    public List<ScheduleResponseDto> getSchedule() {
       SchedulRepository schedulRepository = new SchedulRepository(jdbcTemplate);
       return schedulRepository.getSchedule();
    }

    //일정 수정
    public ScheduleResponseDto updatePost(int id, ScheduleRequestDto request) {
        SchedulRepository schedulRepository = new SchedulRepository(jdbcTemplate);

        Schedule schedule = schedulRepository.findById(id);
        if (schedule == null) {
            throw new RuntimeException("이 아이디를 확인할 수 없습니다.: " + id);
        }

        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호 확인해주세요");
        }

        try {
            LocalDate date = LocalDate.now();
            String updatedAt = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            schedule.setUpdatedAt(updatedAt);

            schedulRepository.update( id, request, updatedAt );


            //update된 DB값 반환
            Schedule newSchedule =schedulRepository.findById(id);

            ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(newSchedule);
            return scheduleResponseDto;

        } catch (Exception e) {
            throw new RuntimeException("error = ", e);
        }

    }

    //일정 삭제
    public String deletePost(int id, ScheduleRequestDto requestDto) {
        SchedulRepository schedulRepository = new SchedulRepository(jdbcTemplate);

        Schedule schedule = schedulRepository.findById(id);

        if (schedule == null) {
            throw new RuntimeException("다음 아이디를 확인할 수 없습니다.: " + id);
        }

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new RuntimeException("비밀번호를 확인해주세요");
        }

        try {
            schedulRepository.delete(id);
            return "삭제완료";
        } catch (Error e) {
            throw new RuntimeException("error =", e);
        }

    }
}
