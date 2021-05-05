package Log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import Parameters.Parameters;

/*
    not used for now 05.05.2021
 */
public class LogFile {

    public void createLogFile() {
        Logger logger = Logger.getLogger("LogFile");
        FileHandler fh;

        try {

            fh = new FileHandler(Parameters.getPathToLogFile());
            logger.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);


            logger.info("first log");


        }catch(SecurityException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
