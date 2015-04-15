package allstar.server;

/**
 * Handles all the processing and tasks that should occur to keep the program
 * running
 *
 * @author davidboschwitz
 */
public class Processing implements Runnable {

    private static boolean STOP = false;
    private static boolean isRunning = false;

    @Override
    public void run() {
        if(isRunning){
            println("Already started up...");
            return;
        }
        println("Initializing...");
        isRunning = true;
        while(true) {
            if (STOP) {
                println("Stopping...");
                break;
            }
            Server.clientHandler.process();
        } 
        Server.clientHandler.disconnectAll();
        isRunning = false;
        try{
        Server.listenerThread.join();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }

    /**
     * Is the Process running?
     *
     * @return isRunning
     */
    public static boolean isRunning() {
        return isRunning;
    }

    /**
     * Stops the program process and prepares for shutdown of the server
     */
    public static void Stop() {
        STOP = true;
    }

    private void process() {

    }

    
    private void println(String s){
        Server.out.println("[Processing]: "+s);
    }
}
