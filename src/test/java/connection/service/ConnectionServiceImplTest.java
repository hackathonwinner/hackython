package connection.service;

import connection.util.WeightedQuickUnionUF;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ConnectionServiceImplTest
{

    @Mock
    ConnectionService connectionService;

    @Mock
    WeightedQuickUnionUF uf;

    String filename = "city.txt";
    Set<String> hashSet = new HashSet<String>();
    Map<String, Integer> hashMap = new HashMap<String, Integer>();
    int count = 0;

    @Before
    public void setUp()
    {
        //Mocking reading the file
        List<String> data = new ArrayList<String>();
        data.add("New York, Buffalo");
        data.add("Newark, Philadelphia");
        data.add("Toronto, Albany");
        data.add("New Hampshire, Washington D.C");

        data.forEach(city -> {
            String parse[] = city.split(",");
            hashSet.add(parse[0].trim());
            hashSet.add(parse[1].trim());
        });

        hashSet.forEach(string -> {
            hashMap.put(string, count);
            ++count;
        });
    }

    @Test
    public void readFile() throws IOException
    {
        Resource resource = new ClassPathResource(filename);
        assertNotNull(resource);
    }

    @Test
    public void lookupHash()
    {
        String fail = "Blarginstein";
        String pass = "New York";

        int pass1 = hashMap.get(pass);
        when(connectionService.lookUpHash(fail)).thenReturn(-1);
        when(connectionService.lookUpHash(pass)).thenReturn(pass1);

        assertTrue(hashSet.contains(pass));
        assertFalse(hashSet.contains(fail));

        assertEquals(-1, connectionService.lookUpHash(fail));
        assertEquals(0, connectionService.lookUpHash(pass));
    }

    @Test
    public void isConnectedRoad()
    {
        String origin = "New York";
        String destination = "Toronto";
        String notConnected = "Flargistan";
        when(connectionService.lookUpHash(origin)).thenReturn(1);
        when(connectionService.lookUpHash(destination)).thenReturn(2);
        when(connectionService.lookUpHash(notConnected)).thenReturn(-1);
        when(connectionService.isConnectedRoad(origin, destination)).thenReturn(true);
        when(connectionService.isConnectedRoad(origin, notConnected)).thenReturn(false);

        assertTrue(connectionService.isConnectedRoad(origin, destination));
        assertFalse(connectionService.isConnectedRoad(origin, notConnected));
    }
}