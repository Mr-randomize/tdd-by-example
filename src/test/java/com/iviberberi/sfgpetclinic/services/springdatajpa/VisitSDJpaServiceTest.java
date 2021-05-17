package com.iviberberi.sfgpetclinic.services.springdatajpa;

import com.iviberberi.sfgpetclinic.model.Visit;
import com.iviberberi.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @DisplayName("Test Find All")
    @Test
    void findAll() {
        //given
        Visit visit = new Visit();
        Set<Visit> visits = new HashSet<>();
        visits.add(visit);
        given(visitRepository.findAll()).willReturn(visits);
        //when(visitRepository.findAll()).thenReturn(visits);

        //when
        Set<Visit> foundVisits = service.findAll();

        //then
        then(visitRepository).should().findAll();
        //verify(visitRepository).findAll();

        assertThat(foundVisits).hasSize(1);
    }

    @Test
    void findById() {
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
        //when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        Visit foundVisit = service.findById(1L);

        then(visitRepository).should().findById(anyLong());
        //verify(visitRepository).findById(anyLong());

        assertThat(foundVisit).isNotNull();
    }

    @Test
    void save() {
        Visit visit = new Visit();

        given(visitRepository.save(any(Visit.class))).willReturn(visit);
        //when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        Visit savedVisit = service.save(new Visit());

        then(visitRepository).should().save(any(Visit.class));
        //verify(visitRepository).save(any(Visit.class));

        assertThat(savedVisit).isNotNull();
    }

    @Test
    void delete() {
        Visit visit = new Visit();

        service.delete(visit);

        then(visitRepository).should().delete(any(Visit.class));
        //verify(visitRepository).delete(any(Visit.class));

    }

    @Test
    void deleteById() {

        service.deleteById(1L);

        then(visitRepository).should().deleteById(anyLong());
        //verify(visitRepository).deleteById(anyLong());
    }
}