package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeCreateResponse;
import roomescape.dto.response.ReservationTimesResponse;
import roomescape.service.ReservationTimeService;

@RestController()
@RequestMapping("times")
public class ReservationTimeApiController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeApiController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ReservationTimesResponse reservationTimes() {
        return this.reservationTimeService.getReservationTimes();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ReservationTimeCreateResponse createTime(@RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        return this.reservationTimeService.createReservation(reservationTimeCreateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable final Long id) {
        reservationTimeService.deleteReservation(id);
    }
}