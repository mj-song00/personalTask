package com.sparta.personal_task.scheduleController;

import com.sparta.personal_task.dto.ScheduleRequestDto;
import com.sparta.personal_task.dto.ScheduleResponseDto;
import com.sparta.personal_task.schedulService.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

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



}
