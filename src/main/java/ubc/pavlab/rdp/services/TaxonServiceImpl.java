package ubc.pavlab.rdp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ubc.pavlab.rdp.model.Taxon;
import ubc.pavlab.rdp.repositories.TaxonRepository;

import java.util.Collection;

/**
 * Created by mjacobson on 17/01/18.
 */
@Service("taxonService")
public class TaxonServiceImpl implements TaxonService {

    @Autowired
    private TaxonRepository taxonRepository;

    @Override
    public Taxon findById( Integer id ) {
        return taxonRepository.findOne( id );
    }

    @Override
    public Taxon findByCommonName( String commonName ) {
        return taxonRepository.findByCommonName( commonName );
    }

    @Override
    public Collection<Taxon> findByActiveTrue() {
        return taxonRepository.findByActiveTrue();
    }

    @Override
    public Collection<Taxon> loadAll() {
        return taxonRepository.findAll();
    }

}
