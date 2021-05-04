package io.glide.rest;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UsApiTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsApiTest {

}
