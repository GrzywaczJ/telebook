package pl.grzywacz.jakub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.grzywacz.jakub.model.Abonent;

@Repository
@RepositoryRestResource(path = "abonent",collectionResourceRel = "abonent")
public interface AbonentRepository extends JpaRepository <Abonent,Long> {

}
