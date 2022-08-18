package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.Service;
import com.bezkoder.springjwt.repository.ServiceRepository;
import com.bezkoder.springjwt.exceptions.ServiceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/ccservices")
    Service newService(@RequestBody Service newService) {
        return serviceRepository.save(newService);
    }

    @GetMapping("/ccservices")
    List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @GetMapping("/ccservices/{id}")
    Service getServiceById(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFound(id));
    }

    @PutMapping("/ccservices/{id}")
    Service updateService(@RequestBody Service newService, @PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setName(newService.getName());
                    service.setDescription(newService.getDescription());
                    service.setImageUrl(newService.getImageUrl());
                    return serviceRepository.save(service);
                }).orElseThrow(() -> new ServiceNotFound(id));
    }

    @DeleteMapping("/ccservices/{id}")
    String deleteService(@PathVariable Long id){
        if(!serviceRepository.existsById(id)){
            throw new ServiceNotFound(id);
        }
        serviceRepository.deleteById(id);
        return  "Service with id "+id+" has been deleted success.";
    }



}
