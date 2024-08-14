package com.sparta.personal_task.dto;

import com.sparta.personal_task.scheduleEntity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private int id;
    private String contents;
    private String manager;
    private String createdAt;
    private String updatedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.contents = schedule.getContents();
        this.manager = schedule.getManager();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }

    // get요청시 사용
    public ScheduleResponseDto(int id, String contents, String manager, String createdAt, String updatedAt) {
        this.id = id;
        this.contents = contents;
        this.manager = manager;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
