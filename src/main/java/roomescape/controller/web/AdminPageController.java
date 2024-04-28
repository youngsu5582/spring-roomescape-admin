package roomescape.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.annotation.WebProperty;

@WebProperty
@Controller
@RequestMapping("admin")
public class AdminPageController {

    @GetMapping("")
    public String showAdminPage() {
        return "index";
    }

    @GetMapping("/reservation")
    public String showReservationPage() {
        return "admin/reservation";
    }

    @GetMapping("/time")
    public String showTimePage() {
        return "admin/time";
    }
}