package be.vdab.cinefest.services;

import be.vdab.cinefest.domain.Film;
import be.vdab.cinefest.dto.NieuweFilm;
import be.vdab.cinefest.repositories.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FilmService {
    private FilmRepository filmRepo;

    public FilmService(FilmRepository filmRepo) {
        this.filmRepo = filmRepo;
    }

    public long findTotaalVrijePlaaten(){
        return filmRepo.findTotaalVrijePlaatsen();
    }

    public Optional<Film> findById(long id){
        return filmRepo.findByid(id);
    }

    public List<Film> findAll() {
        return filmRepo.findAll();
    }

    public List<Film> findByJaar(int jaar) {
        return filmRepo.findByJaar(jaar);
    }

    @Transactional
    public void delete(long id){
        filmRepo.delete(id);
    }

    public long create(NieuweFilm nieuweFilm) {
        var vrijePlaatsen =0;
        var aankoopPrijs = BigDecimal.ZERO;
        var film = new Film(nieuweFilm.naam(), nieuweFilm.jaar(), vrijePlaatsen, aankoopPrijs);
        return filmRepo.create(film);
    }
}
