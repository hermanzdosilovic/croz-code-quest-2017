package cq.game.bots;

import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;

public interface Bot {
    GameStateWriter generateOrders(InputGameState state, GameStateWriter gsw);
}
