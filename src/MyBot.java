import lia.Api;
import lia.Callable;
import lia.NetworkingClient;
import lia.api.*;

/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {


    /** Called only once when the game is initialized. */
    @Override
    public synchronized void process(MapData mapData) {
        // TODO write some code to handle mapData
    }

    /** Repeatedly called from game engine with game state updates.  */
    @Override
    public synchronized void process(StateUpdate stateUpdate, Api api) {
        // TODO write some code to handle stateUpdate every frame.
        // Use api to send responses back to the game engine.
    }

    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
    }
}
