package roomescape.controller.console;

import org.springframework.stereotype.Controller;
import roomescape.annotation.ConsoleProperty;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationsResponse;
import roomescape.service.ReservationService;
import roomescape.service.request.ReservationCreateRequestInService;
import roomescape.view.ConsoleInputView;
import roomescape.view.ConsoleOutputView;

@ConsoleProperty
@Controller
public class ReservationConsoleController {
    private final ReservationService reservationService;
    private final ConsoleInputView consoleInputView;
    private final ConsoleOutputView consoleOutputView;

    public ReservationConsoleController(final ReservationService reservationService, final ConsoleInputView consoleInputView, final ConsoleOutputView consoleOutputView) {
        System.out.println("Create");
        this.reservationService = reservationService;
        this.consoleInputView = consoleInputView;
        this.consoleOutputView = consoleOutputView;
    }

    public void printReservations() {
        final ReservationsResponse response = this.reservationService.getReservations();
        consoleOutputView.printReservations(response);
    }

    public void createReservation() {
        final String name = consoleInputView.inputName();
        final String date = consoleInputView.inputDate();
        final long timeId = consoleInputView.inputId();

        final ReservationCreateResponse reservationCreateResponse =
                reservationService.createReservation(new ReservationCreateRequestInService(name, date, timeId));

        consoleOutputView.printReservationCreate(reservationCreateResponse);
    }

    public void cancelReservation() {
        final long reservationId = consoleInputView.inputId();
        reservationService.deleteReservation(reservationId);
    }
}