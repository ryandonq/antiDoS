import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final String LOG_FILE_PATH = "logs.txt";
    private static int requestsCount = 0;
    private static final int MAX_REQUESTS = 5;
    private static final int BLOCK_TIME = 10; 
    private static Map<String, LocalDateTime> ipMap = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            handleRequest("192.168.1." + i);
        }
    }

    public static void handleRequest(String ip) {
        if (ipMap.containsKey(ip)) {
            LocalDateTime blockTime = ipMap.get(ip);
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isBefore(blockTime)) {
                System.out.println("IP " + ip + " is blocked until " + blockTime);
                return;
            }
        }

        requestsCount++;

        if (requestsCount > MAX_REQUESTS) {
            logAttackDetected(ip);
            ipMap.put(ip, LocalDateTime.now().plusSeconds(BLOCK_TIME));
        }
    }

    public static void logAttackDetected(String ip) {
        String message = "Ataque DoS detectado! Número de requisições: " + requestsCount;
        String timestamp = LocalDateTime.now().toString();

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(timestamp + ": " + message + " IP: " + ip);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo de logs: " + e.getMessage());
        }
    }
                                   
