package Controllers;

import Pojos.Film;
import Services.FilmInformationDownloaderService;
import Services.FilmService;
import com.example.backend.Initializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class FilmGalleryController {

    @Autowired
    private FilmService filmService;

    @RequestMapping("/api/hello")
    public String hello() {
        return "Hello, the time at the server is now " + new Date() + "\n";
    }

    public FilmGalleryController(FilmService filmService) {
        this.filmService = filmService;
        new Initializer(this.filmService);
    }

    @PostMapping(value = "/save")
    private ResponseEntity<?> SaveFilm(@RequestBody Film filmToSave) {
        var response = filmService.SaveFilm(filmToSave);
        return new ResponseEntity<>(
                response.getValue(),
                response.getKey() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/replace")
    private ResponseEntity<?> ReplaceFilm(
            @RequestBody Film updatedFilm,
            String titleBeforeUpdate){
        var response = filmService.UpdateFilm(updatedFilm, titleBeforeUpdate);
        return new ResponseEntity<>(
                response.getValue(),
                response.getKey() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getbyname")
    private ResponseEntity<?> getFilmByName(String filmName){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    private ResponseEntity<?> UpdateFilm(String title, String newValue) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping(value = "/delete")
    private ResponseEntity<?> deleteFilm(@RequestBody Film filmToDelete){
        var response = filmService.DeleteFilm(filmToDelete);
        return new ResponseEntity<>(
                response.getValue(),
                response.getKey() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getall")
    public List<Film> getFilms() {
        return filmService.ListAll();
    }
}
