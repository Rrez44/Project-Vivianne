package service.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class ButtonMoveAnimation {
    private TranslateTransition transition;

    public ButtonMoveAnimation(Button btn, boolean reset) {
        transition = new TranslateTransition(Duration.millis(300), btn);
        if (reset) {
            transition.setToX(0);

        } else {
            transition.setToX(-10);

        }
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
    }

    public void play() {
        transition.play();
    }

    public void setOnFinished(Runnable action) {
        transition.setOnFinished(e -> action.run());
    }
}
