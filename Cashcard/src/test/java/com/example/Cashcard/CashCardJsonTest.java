package com.example.Cashcard;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

//marks the class as a test class using the Jackson framework: provides extensive json testing and parsing
@JsonTest
public class CashCardJsonTest {

    //the annotation directs spring to create objects of the requested type
    @Autowired
    private JacksonTester<CashCard> json;

    //annotation is part of junit library
    @Test
    void cashCardSerializationTest()
    throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);

        assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
        //assertThat is part of assertj library
        //assertThat(42).isEqualTo(42);
    }

    @Test
    void cashCardDeserializationTest()throws IOException{
        String expected = """
                {
                "id":99,
                "amount":123.45
                }
                """;
        assertThat(json.parse(expected)).isEqualTo(new CashCard(99L,123.45));
        assertThat(json.parseObject(expected).id()).isEqualTo(99);
        assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
    }
}
