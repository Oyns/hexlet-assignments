package exercise.dto;

import lombok.Data;

import java.util.List;

// BEGIN
@Data
public class PostDTO {
    private Long id;
    private String title;
    private String body;
    private List<CommentDTO> comments;
}
// END
