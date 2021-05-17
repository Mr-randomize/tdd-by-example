package com.iviberberi.sfgpetclinic.controllers;

import com.iviberberi.sfgpetclinic.fauxspring.BindingResult;
import com.iviberberi.sfgpetclinic.fauxspring.Model;
import com.iviberberi.sfgpetclinic.model.Owner;
import com.iviberberi.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willAnswer(invocation -> {
            List<Owner> owners = new ArrayList<>();
            String name = invocation.getArgument(0);

            if (name.equals("%Buck%")) {
                owners.add(new Owner(1l, "Joe", "Buck"));
                return owners;
            } else if (name.equals("%CantFind%")) {
                return owners;
            } else if (name.equals("%FindMe%")) {
                owners.add(new Owner(1l, "Joe", "Buck"));
                owners.add(new Owner(2l, "Joe2", "Buck2"));
                return owners;
            }
            throw new RuntimeException("Invalid Argument");
        });
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        //given
        Owner owner = new Owner(1l, "Joe", "Buck");
        //List<Owner> ownerList = new ArrayList<>();
        //given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);

        //when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        //then
        assertThat("%Buck%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("redirect:/owners/1").isEqualToIgnoringCase(viewName);
        verifyNoInteractions(model);
    }

    @Test
    void processFindFormWildcardFound() {
        //given
        Owner owner = new Owner(1l, "Joe", "FindMe");
        InOrder inOrder = inOrder(ownerService, model);

        //when
        String viewName = controller.processFindForm(owner, bindingResult, model);

        //then
        assertThat("%FindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);

        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model,times(1)).addAttribute(anyString(), anyList());
        verifyNoMoreInteractions(model);
    }

    @Test
    void processFindFormWildcardNotFound() {
        //given
        Owner owner = new Owner(1l, "Joe", "CantFind");

        //when
        String viewName = controller.processFindForm(owner, bindingResult, null);

        verifyNoMoreInteractions(ownerService);

        //then
        assertThat("%CantFind%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
        verifyNoInteractions(model);
    }

    @Test
    void processCreationFormHasErrors() {
        //given
        Owner owner = new Owner(1l, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(true);

        //when
        String viewName = controller.processCreationForm(owner, bindingResult);

        //then
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }


    @Test
    void processCreationFormNoErrors() {
        //given
        Owner owner = new Owner(5l, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);

        //when
        String viewName = controller.processCreationForm(owner, bindingResult);

        //then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }
}