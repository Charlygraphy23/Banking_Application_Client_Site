package sample.Transitions;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ReverseSlider {

    private TranslateTransition translateTransition;

    public ReverseSlider(Node e){
        translateTransition=new TranslateTransition(Duration.millis(700),e);
        translateTransition.setToX(-e.getScene().getWidth());
        translateTransition.setFromX(0);
        translateTransition.play();
    }
}
