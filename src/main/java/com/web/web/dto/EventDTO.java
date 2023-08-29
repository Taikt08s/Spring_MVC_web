package com.web.web.dto;

import com.web.web.models.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private long id;
    private String name;
    @DateTimeFormat(pattern="yyyy-MM-dd' 'HH:mm")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd' 'HH:mm")
    private LocalDateTime endTime;
    private String type;
    private String photoUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Club club;

}
