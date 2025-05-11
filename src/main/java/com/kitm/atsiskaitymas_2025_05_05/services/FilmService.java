package com.kitm.atsiskaitymas_2025_05_05.services;

import com.kitm.atsiskaitymas_2025_05_05.dao.FilmRepository;
import com.kitm.atsiskaitymas_2025_05_05.entity.Category;
import com.kitm.atsiskaitymas_2025_05_05.entity.Film;
import jakarta.transaction.Transactional;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    private FilmRepository filmRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository)
    {
        this.filmRepository = filmRepository;
    }

    public List<Film> findAll()
    {
        return filmRepository.findAll();
    }

    public Film findById(Long id)
    {
        Optional<Film> result = filmRepository.findById(id);

        Film film = null;

        if (result.isPresent())
        {
            film = result.get();
        }
        else
        {
            throw new RuntimeException("Did not find film by id -" + id);
        }

        return film;
    }

    @Transactional
    public Film save(Film film)
    {
        return filmRepository.save(film);
    }

    @Transactional
    public void deleteById(Long id)
    {
        filmRepository.deleteById(id);
    }

    public Film findByName(String name)
    {
        Optional<Film> result = filmRepository.findByName(name);

        Film film = null;

        if (result.isPresent())
        {
            film = result.get();
        }
        else
        {
            throw new RuntimeException("Did not find film by name -" + name);
        }

        return film;
    }

    public List<Film> findByCategory(Category category)
    {
        return filmRepository.findByCategory(category);
    }

}
