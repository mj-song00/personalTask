package com.sparta.personal_task.dto;

import com.sparta.personal_task.scheduleRepository.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private int id;
    private String contents;
    private String manager;
    private String createdAt;
    private String updatedAt;

    //create post Dto
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.contents = schedule.getContents();
        this.manager = schedule.getManager();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }

    // 전체조회 responseDto 사용
    public ScheduleResponseDto(int id, String contents, String manager, String createdAt, String updatedAt) {
        this.id = id;
        this.contents = contents;
        this.manager = manager;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
