package Server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class LoginFailed {
    private int count;
    private boolean block;
    private Date timeBlock;
}
