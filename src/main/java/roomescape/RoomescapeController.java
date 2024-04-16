package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomescapeController {
    @GetMapping("")
    public String showAdminPage() {
        return "index";
    }
}