package com.xu;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibraryManagerSApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
public void testJwt(HttpServletRequest request) {

        String token = request.getParameter("token");
}
@Test
public  void  testJwt(HttpServletResponse response) {

}
}
