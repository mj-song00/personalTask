package com.sparta.personal_task.scheduleController;

import com.sparta.personal_task.dto.ScheduleRequestDto;
import com.sparta.personal_task.dto.ScheduleResponseDto;
import com.sparta.personal_task.schedulService.ScheduleService;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/addPost")
    public ScheduleResponseDto createPost (@RequestBody ScheduleRequestDto requestDto ) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.createPost(requestDto);
    }

    //전체조회
    @GetMapping("/post")
    public List<ScheduleResponseDto> getSchedule(){
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getSchedule();
    }

    // 개별조회
    @GetMapping("/post/{id}")
    public ScheduleResponseDto findPost(@PathVariable int id){
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.getPost(id);
    }

    // 일정 수정
    @PutMapping("/post/{id}")
    public ScheduleResponseDto updatePost(@PathVariable int id, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.updatePost(id, requestDto);

    }

    //일정 삭제
    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable int id, @RequestBody ScheduleRequestDto requestDto){
        ScheduleService scheduleService = new ScheduleService(jdbcTemplate);
        return scheduleService.deletePost(id, requestDto);
    }
}
