package com.web.web.dto;
import java.time.LocalDateTime;
import java.util.List;

import com.web.web.models.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubDTO {
    private Long id;
    @NotEmpty(message ="Club title should not be empty!")
    private String title;
    @NotEmpty(message ="Photo Url should not be empty!")
    private String photoUrl;
    @NotEmpty(message ="Content should not be empty!")
    private String content;
    private UserEntity createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<EventDTO> events;
}
