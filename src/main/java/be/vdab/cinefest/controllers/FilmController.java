package be.vdab.cinefest.controllers;

import be.vdab.cinefest.domain.Film;
import be.vdab.cinefest.dto.NieuweFilm;
import be.vdab.cinefest.exceptions.FilmNotFoundException;
import be.vdab.cinefest.services.FilmService;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
public class FilmController {
    private final FilmService filmService;

    private record IdTitelJaarVrijePlaatsen(long id, String titel, int jaar, long vrijePlaatsen){
        IdTitelJaarVrijePlaatsen(Film film){
            this(film.getId(), film.getTitel(), film.getJaar(), film.getVrijePlaatsen());
        }

    }
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("films/totaalvrijeplaatsen")
    public long findTotaalVrijePlaatsen(){
        return filmService.findTotaalVrijePlaaten();
    }

    @GetMapping("films/{id}")
    IdTitelJaarVrijePlaatsen findById(@PathVariable long id){
        return filmService.findById(id)
                .map(film -> new IdTitelJaarVrijePlaatsen(film))
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    @GetMapping("films")
    Stream<IdTitelJaarVrijePlaatsen> findAll(){
        return filmService.findAll()
                .stream()
                .map(film -> new IdTitelJaarVrijePlaatsen(film));
    }

    @GetMapping(value = "films", params = "jaar")
    Stream<IdTitelJaarVrijePlaatsen> findByJaar(int jaar){
        return filmService.findByJaar(jaar)
                .stream()
                .map(film -> new IdTitelJaarVrijePlaatsen(film));
    }

    @DeleteMapping("films/{id}")
    void delete(@PathVariable long id){
        filmService.delete(id);
    }

    @PostMapping("films")
    long create(@RequestBody NieuweFilm nieuweFilm){
        var id = filmService.create(nieuweFilm);
        return id;
    }
}
