package be.vdab.cinefest.services;

import be.vdab.cinefest.repositories.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FilmService {
    private FilmRepository filmRepo;

    public FilmService(FilmRepository filmRepo) {
        this.filmRepo = filmRepo;
    }

    public Long findTotaalVrijePlaaten(){
        return filmRepo.findTotaalVrijePlaatsen();
    }
}
