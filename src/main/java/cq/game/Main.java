package cq.game;

import cq.game.api.GameStateReader;
import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;
import cq.game.bots.Bot;
import cq.game.bots.CompositeBot;
import cq.game.bots.MovingBot;
import java.io.IOException;

//@Slf4j
public class Main {

	private static final Bot[] BOTS = {
		new MovingBot()
	};

    private static GameStateReader gameStateReader = new GameStateReader();
    private static GameStateWriter gameStateWriter = new GameStateWriter();

    public static void main(String[] args) throws IOException {
        Bot bot =new CompositeBot(BOTS);
        while(true) {
            InputGameState gameState = gameStateReader.nextState();
            gameStateWriter = bot.generateOrders(gameState, gameStateWriter);
            gameStateWriter.write();
        }
	}
}
