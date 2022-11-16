package io.test_gear.samples;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import io.test_gear.models.LinkType;
import io.test_gear.services.Adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleSteps {

    private int a;
    private int b;
    private int c;

    @Given("a is $number")
    public void a_is(int arg1) {
        this.a = arg1;
    }

    @Given("b is $number")
    public void b_is(int arg1) {
        this.b = arg1;
    }

    @When("I add a to b")
    public void i_add_a_to_b() {
        this.c = this.a + this.b;
    }

    @Then("result is $number")
    public void result_is(int arg1) {
        Adapter.addLinks("https://test-gear.io/", "Test 1", "Desc 1", LinkType.ISSUE);
        assertEquals(this.c, arg1);
    }

}
