package connection.controllers;

import connection.service.ConnectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConnectionController.class)
@AutoConfigureMockMvc
public class ConnectionControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @Mock
    ConnectionService connectionService;

    @Test
    public void connectedSmokeTest_Pass() throws Exception {
        when(connectionService.isConnectedRoad("New York", "Toronto")).thenReturn(true);
        this.mockMvc.perform(
                get("/connected?origin=New York&destination=Toronto"))
                .andExpect(status().isOk())
                .andExpect(content().string(("yes")));
    }

    @Test
    public void connectedSmokeTest_Pass2() throws Exception {
        when(connectionService.isConnectedRoad("New York", "Buffalo")).thenReturn(true);
        this.mockMvc.perform(
                get("/connected?origin=New York&destination=Buffalo"))
                .andExpect(status().isOk())
                .andExpect(content().string(("yes")));
    }

    @Test
    public void connectedSmokeTest_Fail() throws Exception {
        when(connectionService.isConnectedRoad("New York", "Miami")).thenReturn(false);
        this.mockMvc.perform(
                get("/connected?origin=New York&destination=Miami"))
                .andExpect(status().isOk())
                .andExpect(content().string(("no")));
    }


    @Test
    public void connectedSmokeTest_Fail1() throws Exception {
        this.mockMvc.perform(
                get("/connectksaljdklajedtestias&&&&!!232=test"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(("")));
    }

    @Test
    public void connectedSmokeTest_Fail2() throws Exception {
        this.mockMvc.perform(
                get("/connected?jkajlsd=jalksdjl"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(("")));
    }

    @Test
    public void connectedSmokeTest_Fail3() throws Exception {
        this.mockMvc.perform(
                get("/connected?origin=&destination="))
                .andExpect(status().isOk())
                .andExpect(content().string(("no")));
    }
}