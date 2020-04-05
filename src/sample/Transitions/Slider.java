package sample.Transitions;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Slider {
    private TranslateTransition translateTransition;

    public Slider(Node e,Node second){
        translateTransition=new TranslateTransition(Duration.millis(700),e);
        translateTransition.setToX(0);
        translateTransition.setFromX(e.getScene().getWidth());
        translateTransition.play();
        translateTransition.setOnFinished(ee->{
           second.setVisible(false);
        });
    }
}
