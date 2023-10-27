package dev.deh.rys.customer;

import dev.deh.rys.entity.Address;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.groups.Default;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerIntegrationTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    SystemAuthenticator systemAuthenticator;

    @Autowired
    Validator validator;

    private Customer customer;
    @BeforeEach
    void setUp() {
         customer = dataManager.create(Customer.class);
    }

    @Test
    void given_validCustomer_when_saveCustomer_then_customerIsSaved() {



        // given
        customer.setFirstName("Foo");
        customer.setLastName("Bar");
        customer.setEmail("foo@bar.com");

        Address address = dataManager.create(Address.class);
        address.setStreet("Foo Street 1");
        address.setCity("Bar");
        address.setPostCode("25001");

        customer.setAddress(address);

        // when
        Customer savedCustomer = systemAuthenticator.withSystem(() ->  {
           return dataManager.save(customer);
        });
        // then
        assertThat(savedCustomer.getId())
                .isNotNull();

    }

    @Test
    void given_customerWithInvalidEmail_when_validateCustomer_then_customerIsInvalid() {
       // given
        customer.setEmail("invalidEmailAddress");

        // when
        Set<ConstraintViolation<Customer>> violations  =   validator.validate(customer, Default.class);

        // then
        assertThat(violations)
                .hasSize(2);// Expect 2 violations (lastName and email)

        // and
                assertThat(firstViolation(violations).getPropertyPath().toString())
                .isEqualTo("email");
        assertThat(firstViolation(violations).getMessageTemplate())
                .isEqualTo("{jakarta.validation.constraints.Email.message}");




    }

    private static ConstraintViolation<Customer> firstViolation(Set<ConstraintViolation<Customer>> violations) {
        return violations.stream().skip(1).findFirst().orElseThrow();
    } // We skip(1) to specifically target the email violation, excluding the lastName violation.

}