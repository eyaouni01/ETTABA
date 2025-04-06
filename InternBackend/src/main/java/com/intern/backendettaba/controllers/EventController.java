package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.Event;
import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.services.EventService;
import com.intern.backendettaba.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    private final ImageService imageService;


    @PostMapping(value = "/event",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Event> add(@RequestPart("event") Event event,
                                     @RequestPart("imageFile") MultipartFile[] file){

        try {
            Set<Image> images=imageService.uploadImages(file);
            event.setEventImages(images);
            //to be modified in other cases when a ticket is bought by user
            event.setNumberAvailableTickets(event.getNumberTickets());
            return eventService.saveEvent(event);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    @GetMapping("/event/{id}")
    public ResponseEntity<Event> get(@PathVariable(name = "id") Long id){
        return eventService.getEventByID(id);
    }

    @GetMapping("/event")
    public ResponseEntity<List<Event>> list(){
        return eventService.getAllEvents();
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<Event> update(@PathVariable(name = "id") Long id,@RequestBody Event event){
        return eventService.updateEvent(event,id);
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<Event> delete(@PathVariable(name = "id") Long id){
        return eventService.deleteEvent(id);
    }

    @GetMapping("/farm/{id}/event")
    public ResponseEntity<List<Event>> listByFarm(@PathVariable(name = "id") Long id){
        return eventService.getAllEventsByFarmId(id);
    }
    @PostMapping(value = "/farm/{id}/event",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Event> addToFarm(@PathVariable(name = "id") Long id,
                                           @RequestPart("event") Event event,
                                           @RequestPart("imageFile") MultipartFile[] file){
        try {
            Set<Image> images=imageService.uploadImages(file);
            event.setEventImages(images);
            return eventService.addEventToFarmById(id,event);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @DeleteMapping("/farm/{id}/event")
    public ResponseEntity<List<Event>> deleteAllFromFarm(@PathVariable(name = "id") Long id){
        return eventService.deleteAllEventsFromFarmById(id);
    }
}
