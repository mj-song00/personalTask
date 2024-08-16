package com.sparta.personal_task.scheduleEntity;

import com.sparta.personal_task.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private int id;
    private String contents;
    private String manager;
    private String password;
    private String createdAt;
    private String updatedAt;

    public Schedule(ScheduleRequestDto requestDto){
        this.contents = requestDto.getContents();
        this.manager = requestDto.getManager();
        this.password = requestDto.getPassword();
    }


}
