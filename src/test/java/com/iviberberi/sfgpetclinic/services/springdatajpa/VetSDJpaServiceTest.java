package com.iviberberi.sfgpetclinic.services.springdatajpa;

import com.iviberberi.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetSDJpaService service;

    @Test
    void deleteById() {
        service.deleteById(1l);
        service.deleteById(1l);

        verify(vetRepository,times(2)).deleteById(1l);
        verify(vetRepository,atLeastOnce()).deleteById(1l);
        verify(vetRepository,atMost(5)).deleteById(1l);
        verify(vetRepository,never()).deleteById(5l);
    }
}