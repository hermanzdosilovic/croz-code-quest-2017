package cq.game;

import cq.game.api.GameStateReader;
import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;
import cq.game.bots.*;

import java.io.IOException;

public class Main {
    private static GameStateReader gameStateReader = new GameStateReader();
    private static GameStateWriter gameStateWriter = new GameStateWriter();

    public static void main(String[] args) throws IOException {
        Bot bot = new Bot() {
            @Override
            public GameStateWriter generateOrders(InputGameState state, GameStateWriter gsw) {
                gsw.setMessage("Josko");
                return gsw;
            }
        }; // implement Bot interface
        while(true) {
            InputGameState gameState = gameStateReader.nextState();
            gameStateWriter = bot.generateOrders(gameState, gameStateWriter);
            gameStateWriter.write();
        }

    }
}
