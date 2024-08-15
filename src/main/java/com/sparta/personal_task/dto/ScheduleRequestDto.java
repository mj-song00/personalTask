package com.sparta.personal_task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRequestDto {
    private String contents;
    private String manager;
    private String password;

    public ScheduleRequestDto(String password){
        this.password = password;
    }
}
