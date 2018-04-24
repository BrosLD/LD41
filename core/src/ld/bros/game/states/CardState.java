package ld.bros.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.LudumDare41;
import ld.bros.game.card.Card;
import ld.bros.game.card.CardFactory;
import ld.bros.game.card.DescriptionField;
import ld.bros.game.main.State;
import ld.bros.game.platform.player.PlayerConfig;
import ld.bros.game.service.Locator;
import ld.bros.game.utils.HelpText;
import ld.bros.game.utils.ScoreCalculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardState extends State<MainState> {

    private static final int INITIAL_NUMBER_OF_CARDS = 5;

    private OrthographicCamera camera;

    private List<Card> cards;
    private int markedCardIndex;

    private DescriptionField descriptionField;

    private HelpText help;
    private String helpText = "[<-]/[->]: Choose\n[X]: Select / Unselect\n[R]: Discard Hand\n[Enter]: Confirm selection";

    public CardState(MainState manager) {
        super(manager);

        camera = new OrthographicCamera(LudumDare41.WIDTH, LudumDare41.HEIGHT);
        camera.setToOrtho(false);

        descriptionField = new DescriptionField();

        help = new HelpText();
        help.setHelp(helpText);
    }

    @Override
    public void enter() {
        super.enter();

        // pause mainstate timer
        Locator.event().call("pause_timer");

        // get number of (remaining) cards
        cards = manager.getRemainingCards();

        if(cards == null)
            cards = new ArrayList<Card>(INITIAL_NUMBER_OF_CARDS);

        if(cards.isEmpty()) {
            freshCards();
        }

        // align position of cards
        alignCards();

        markedCardIndex = 0;
        cards.get(markedCardIndex).setMarked(true);
    }

    private void alignCards() {
        float y_offset = (LudumDare41.HEIGHT - 140f) / 2f;
        float x_offset = (LudumDare41.WIDTH - 2*60f - (cards.size()*80f + (cards.size()-1)*30f))/2f;
        for(int i = 0; i < cards.size(); i++) {
            cards.get(i).setPos(
                    new Vector2(
                            60f + x_offset + i*(80f + 30f),
                            y_offset
                    )
            );
        }
    }

    private void freshCards() {
        cards.clear();

        for (int i = 0; i < INITIAL_NUMBER_OF_CARDS; i++) {
            cards.add(CardFactory.createCard());
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        camera.position.x = LudumDare41.WIDTH/2f;
        camera.position.y = LudumDare41.HEIGHT/2f;
        camera.update();

        if(Locator.controls().leftTapped()) {
            markedCardIndex--;
            if(markedCardIndex < 0) markedCardIndex = cards.size() - 1;
        } else if(Locator.controls().rightTapped()) {
            markedCardIndex++;
            markedCardIndex %= cards.size();
        }

        if(Locator.controls().jumpTapped()) {
            cards.get(markedCardIndex).toggleSelected();
        }

        for(int i = cards.size() - 1; i >= 0; i--) {
            cards.get(i).update(delta);
            cards.get(i).setMarked(false);
        }
        cards.get(markedCardIndex).setMarked(true);

        descriptionField.setText(cards.get(markedCardIndex).getDescription());
        descriptionField.setTitle(cards.get(markedCardIndex).getTitle());

        if(Locator.controls().startTapped()) {
            onAccept();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            freshCards();
            alignCards();
            // reduce score
            manager.reduceScore(ScoreCalculator.clearHand());
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        batch.end();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for(int i = cards.size() - 1; i >= 0; i--) {
            if(i == markedCardIndex) continue;

            cards.get(i).render(batch);
        }

        // draw marked card on top of all other
        cards.get(markedCardIndex).render(batch);

        descriptionField.render(batch);

        // draw help text
        help.render(batch);

        batch.end();
        batch.begin();
    }

    private void onAccept() {
        // collect all selected cards
        Set<Card> selected = new HashSet<Card>();

        for(int i = 0; i < cards.size(); i++) {
            if(cards.get(i).isSelected()) {
                selected.add(cards.get(i));
            }
        }

        // aggregate all individual configs
        PlayerConfig config = new PlayerConfig();
        for(Card c : selected) {
            PlayerConfig other = c.getConfig();

            config.jump |= other.jump;
            config.dash |= other.dash;
            config.teleport |= other.teleport;
            config.wallClimb |= other.wallClimb;

            config.speedMod += other.speedMod;
        }

        // set config in MainState
        manager.setPlayerConfig(config);

        // set remaining cards
        cards.removeAll(selected);
        manager.setRemainingCards(cards);

        // remove this state from stack
        manager.removeFirst();

        // reduce score depending on the number of cards (non linear!)
        manager.reduceScore(ScoreCalculator.cardsUsed(selected.size()));
    }

    @Override
    public void finish() {
        super.finish();

        Locator.event().call("un_pause_timer");
    }
}
