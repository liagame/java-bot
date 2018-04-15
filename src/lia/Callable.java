package lia;

import lia.AiApiMessages.MapData;
import lia.AiApiMessages.Message;
import lia.AiApiMessages.StateUpdate;

public interface Callable {
    void process(MapData mapData);
    void process(StateUpdate stateUpdate);
}
