package connection.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ConnectionService
{
    public void readFile(String filename) throws IOException;

    public boolean isConnectedRoad(String origin, String destination);

    public int lookUpHash(String origin);

    public void createConnections() throws IOException;

    public String payStore(String price) throws IOException;
}
