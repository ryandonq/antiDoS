import org.apache.log4j.Logger;

public class DoSLogger {
    private static final Logger logger = Logger.getLogger(DoSLogger.class);

    public static void main(String[] args) {
        logger.info("ataque DoS detectado.");
        logger.debug("endereço IP: 192.168.0.1");
        logger.debug("contagem de solicitações: 1000");
    }
}
