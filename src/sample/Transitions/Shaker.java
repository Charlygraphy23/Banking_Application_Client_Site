package sample.Transitions;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;


public class Shaker {
    private TranslateTransition translateTransition;

    public Shaker(Node e){
        translateTransition=new TranslateTransition(Duration.millis(70),e);
        translateTransition.setFromX(0);
        translateTransition.setByX(8);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(2);
        translateTransition.play();
    }
}
