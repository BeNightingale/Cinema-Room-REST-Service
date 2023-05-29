package cinema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class Controller {

    private final CinemaService service = new CinemaService();

    @GetMapping(value = "/seats")
    public CinemaDto getCinemaState() {
        return service.toCinemaDto(
                new Cinema(9, 9,
                        service.createFullCinemaSeatsList(9, 9)));
    }
}
