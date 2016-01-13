package com.roc.webnote;

import com.roc.webnote.lib.Util;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AppTests {
    private MockMvc mockMvc;

    //    @SuppressWarnings("SpringJavaAutowiringInspection")
    //    @Autowired
    protected WebApplicationContext wac;

    //    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void simple() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("hello"));
    }

    @Test
    public void testOS() {
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("file.encoding"));
        class Tes {
            Integer a;

            Tes(Integer i) {
                a = i;
            }

            public String toString() {
                return a + "";
            }
        }
        Tes a = new Tes(3333);
        Tes b = a;
        System.out.println(a + "" + b);
        b = new Tes(44444);
        System.out.println(a + "" + b);
    }

    private void testException() throws Exception {
        throw new NullPointerException("NullPointer--Roc");
    }

    @Test
    public void test() {
        String uri = "/article/727627a1-fda8-45c1-b1a3-86871041898e";
        System.out.println(uri.substring(9));
    }

    @Test
    public void testSendEmail() {
        Util.sendEmail(null);
    }
}
