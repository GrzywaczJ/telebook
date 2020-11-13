package pl.grzywacz.jakub.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pl.grzywacz.jakub.model.Abonent;
import pl.grzywacz.jakub.repository.AbonentRepository;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private AbonentRepository abonentRepository;
    public List<Abonent> search(String querryParam) {
        List<Abonent> abonentList = abonentRepository.findAll();
        return abonentList.stream().filter(predicateFirstName(querryParam)
                .or(predicateLastName(querryParam))
                .or(predicgetAge(querryParam))
                .or(predicgetSex(querryParam))
                .or(predicateCity(querryParam))
                .or(predicgetStreet(querryParam))
                .or(predicgetHomeNumber(querryParam))
                .or(predicgetZipCode(querryParam))).collect(Collectors.toList());
    }
    private Predicate<Abonent> predicateFirstName(String querryParam){
        return abonent -> null != (abonent.getFirstName()) ? abonent.getFirstName().contains(querryParam) : false;
    }
    private Predicate<Abonent> predicateLastName(String querryParam){
        return abonent -> null != (abonent.getLastName()) ? abonent.getLastName().contains(querryParam) : false;
    }
    private Predicate<Abonent> predicgetAge(String querryParam){
        return abonent -> null != (abonent.getAge()) ? abonent.getAge().toString().contains(querryParam) : false;
    }
    private Predicate<Abonent> predicgetSex(String querryParam){
        return abonent -> null != (abonent.getSex()) ? abonent.getSex().contains(querryParam) : false;
    }
    private Predicate<Abonent> predicateCity(String querryParam){
        return abonent -> null != (abonent.getAddress().getCity()) ? abonent.getAddress().getCity().contains(querryParam) : false;
    }
    private Predicate<Abonent> predicgetStreet(String querryParam){
        return abonent -> null != (abonent.getAddress().getStreet()) ? abonent.getAddress().getStreet().contains(querryParam) : false;
    }
    private Predicate<Abonent> predicgetHomeNumber(String querryParam){
        return abonent -> null != (abonent.getAddress().getHomeNumber()) ? abonent.getAddress().getHomeNumber().contains(querryParam) : false;
    }
    private Predicate<Abonent> predicgetZipCode(String querryParam){
        return abonent -> null != (abonent.getAddress().getZipCode()) ? abonent.getAddress().getZipCode().contains(querryParam) : false;
    }



}
