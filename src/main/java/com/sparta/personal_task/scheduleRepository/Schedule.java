package com.sparta.personal_task.scheduleRepository;

import com.sparta.personal_task.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
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
