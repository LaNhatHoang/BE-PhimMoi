package Server.data;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhimLe {
    private String name;
    private List<Part> parts;
}
