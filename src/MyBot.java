import lia.*;
import lia.api.*;
import logic.pathfinding.*;

/**
 * Place to write the logic for your bots.
 * */
public class MyBot implements Callable {

    /** Called only once when the game is initialized. */
    @Override
    public synchronized void process(MapData mapData) {
        // Write some code to handle mapData.
    }

    /** Repeatedly called 10 times per second from game engine
     *  with game state updates. */
    @Override
    public synchronized void process(StateUpdate stateUpdate, Api api) {
        // Write some code to handle stateUpdate every frame.
        // Use api to send responses back to the game engine.
    }

    public static void main(String[] args) throws Exception {
        NetworkingClient.connectNew(args, new MyBot());
    }
}
