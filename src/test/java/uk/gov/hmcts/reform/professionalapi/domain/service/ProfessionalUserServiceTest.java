package uk.gov.hmcts.reform.professionalapi.domain.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.xml.ws.http.HTTPException;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Example;

import uk.gov.hmcts.reform.professionalapi.domain.entities.Organisation;
import uk.gov.hmcts.reform.professionalapi.domain.entities.ProfessionalUser;
import uk.gov.hmcts.reform.professionalapi.domain.service.persistence.ProfessionalUserRepository;

public class ProfessionalUserServiceTest {

    private final ProfessionalUserRepository professionalUserRepository = mock(ProfessionalUserRepository.class);
    private final ProfessionalUser professionalUser = new ProfessionalUser("some-fname",
            "some-lname",
            "some-email",
            "PENDING",
            mock(Organisation.class));
    private final ProfessionalUserService professionalUserService = new ProfessionalUserService(
            professionalUserRepository);

    @Test
    public void retrieveUserByEmail() {
        when(professionalUserRepository.findOne(ArgumentMatchers.<Example<ProfessionalUser>>any()))
                .thenReturn(Optional.ofNullable(professionalUser));

        ProfessionalUser user = professionalUserService.findProfessionalUserByEmailAddress("some-email");
        assertEquals(professionalUser.getFirstName(), user.getFirstName());
        assertEquals(professionalUser.getLastName(), user.getLastName());
        assertEquals(professionalUser.getEmailAddress(), user.getEmailAddress());
    }
    
    @Test (expected = HTTPException.class)
    public void retrieveUserByEmailNotFound() {
        when(professionalUserRepository.findOne(ArgumentMatchers.<Example<ProfessionalUser>>any()))
                .thenReturn(Optional.ofNullable(null));

        professionalUserService.findProfessionalUserByEmailAddress("some-email");
    }

}