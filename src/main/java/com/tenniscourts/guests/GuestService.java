package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.repository.GuestRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private static final String NOT_FOUND_STATUS = "Not found.";

    private final GuestRepository repository;

    private final GuestMapper guestMapper;

    public GuestDTO add(GuestDTO guestDTO){
        return guestMapper.map(repository
                .saveAndFlush(guestMapper.map(guestDTO)));
    }

    public GuestDTO update(GuestDTO guestDTO){
        var theGuestDTO = repository.findById(guestDTO.getId())
                .map(guestMapper::map)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(NOT_FOUND_STATUS);
                });
        theGuestDTO.setName(guestDTO.getName());
        return guestMapper.map(
                repository.save(guestMapper.map(theGuestDTO)
                ));
    }

    public GuestDTO findById(Long id) {
        return guestMapper.map(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_STATUS)));
    }

    public List<GuestDTO> list(){
        List<GuestDTO> guestDTOList = new ArrayList<>();
        repository.findAll().stream()
                .forEach(guest -> guestDTOList.add(
                        GuestDTO.builder()
                                .id(guest.getId())
                                .name(guest.getName())
                                .build()
                ));
        return guestDTOList;
    }

    public void delete(Long guestId) {
        repository.delete(
                repository.findById(guestId)
                .orElseThrow(
                        () -> { throw new EntityNotFoundException(NOT_FOUND_STATUS);
                }));
    }

    public Guest findByName(String name){
        try {
            return repository.findByName(name);
        } catch (EntityNotFoundException exception){
            throw new EntityNotFoundException(NOT_FOUND_STATUS);
        }
    }

}