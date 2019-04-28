package com.hibicode.beerstore.service;

import com.hibicode.beerstore.beers.BeerRepository;
import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;
import com.hibicode.beerstore.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeerService {

    private BeerRepository beers;

    @Autowired
    public BeerService(BeerRepository beers) {
        this.beers = beers;
    }

    public Beer save(final Beer beer) {
        verifyIfBeerAlreadyExists(beer);
        return beers.save(beer);
    }

    public Beer update(final Beer beer) {
        verifyIfBeerExists(beer.getId());
        return save(beer);
    }

    public List<Beer> listAll() {
        return beers.findAll();
    }

    public void delete(final Long id) {
        beers.delete(verifyIfBeerExists(id));
    }

    private Beer verifyIfBeerExists(final Long id) {
        Optional<Beer> beerOptional = beers.findById(id);
        if (!beerOptional.isPresent()) {
            throw new EntityNotFoundException(id, Beer.class.getSimpleName());
        }

        return beerOptional.get();
    }

    private void verifyIfBeerAlreadyExists(final Beer beer) {
        Optional<Beer> beerByNameAndType = beers.findByNameAndType
                (beer.getName(), beer.getType());

        if (beerByNameAndType.isPresent() && (beer.isNew() ||
                isUpdatingToADifferentBeer(beer, beerByNameAndType))) {
            throw new BeerAlreadyExistException();
        }
    }

    private boolean isUpdatingToADifferentBeer(final Beer beer, final Optional<Beer> beerByNameAndType) {
        return beer.alreadyExist() && !beerByNameAndType.get()
                .equals(beer);
    }

}
