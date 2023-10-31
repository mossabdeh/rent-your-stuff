package dev.deh.rys.customer;

import dev.deh.rys.entity.Address;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerValidationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;


    @Autowired
    ValidationVerification validationVerification;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = dataManager.create(Customer.class);
    }




    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_TwoViolationOccurs() {
       // given

        customer.setEmail("invalidEmailAddress");

        // when
        List<ValidationVerification.ValidationResult> violations  =   validationVerification.validate(customer);

        // then
        assertThat(violations)
                .hasSize(2);// Expect 1 violation (Street)






    }




    }