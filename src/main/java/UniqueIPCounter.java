import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

public class UniqueIPCounter {
    public static void main(String[] args) {
        String filePath = "src/ip_addresses.txt";
        try {
            long uniqueCount = countUniqueIPs(filePath);
            System.out.println("Number of unique IP addresses: " + uniqueCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long countUniqueIPs(String filePath) throws IOException {
        Set<Long> uniqueIPs = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                long ipAsLong = ipToLong(line);
                uniqueIPs.add(ipAsLong);
            }
        }
        return uniqueIPs.size();
    }

    private static long ipToLong(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            byte[] addressBytes = inetAddress.getAddress();
            long result = 0;
            for (byte b : addressBytes) {
                result = (result << 8) | (b & 0xFF);
            }
            return result;
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Invalid IP address: " + ipAddress, e);
        }
    }
}
