package Controllers;

import Pojos.Film;
import Services.FilmInformationDownloaderService;
import Services.FilmService;
import com.example.backend.Initializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/film")
public class FilmGalleryController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmInformationDownloaderService filmInformationDownloaderService;

    public FilmGalleryController(FilmService filmService) {
        this.filmService = filmService;
        new Initializer(this.filmService);
    }

    @PostMapping(value = "/save")
    private ResponseEntity<?> saveFilm(@RequestBody Film filmToSave) {
        var isFilmAlready = filmService.GetFilmsStartingWith(
                filmToSave.getTitles().get(0).getName());

        if(filmToSave.getRatings().size() <= 1 && isFilmAlready.size() == 0){
            this.filmInformationDownloaderService = new FilmInformationDownloaderService();
            this.filmInformationDownloaderService.Initialize(
                    filmToSave.getTitles().get(0).getName(),
                    filmToSave.getProductionYear(),
                    filmToSave.getType());
            filmToSave.setRatings(this.filmInformationDownloaderService.FindFilm());
        }

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
        var response = filmService.GetFilmsStartingWith(filmName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<?> UpdateFilm(String title, String newValue) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping(value = "/delete")
    private ResponseEntity<?> deleteFilm(String filmToDelete){
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
