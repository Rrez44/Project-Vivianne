package service.animations;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.time.Duration;


public class BusAnimation {

    private final ImageView busIcon;
    private final double maxBusIconPosition;
    private final Timeline timeline;

    public BusAnimation(ImageView busIcon, double maxBusIconPosition) {
        this.busIcon = busIcon;
        this.maxBusIconPosition = maxBusIconPosition;
        this.timeline = new Timeline();
    }

    public void startAnimation(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime now) {
        Duration totalDuration = Duration.between(startTime, endTime);
        Duration remainingDuration = Duration.between(now, endTime);

        double remainingSeconds = remainingDuration.getSeconds();
        double totalSeconds = totalDuration.getSeconds();

        if (remainingSeconds <= 0) {
            busIcon.setTranslateX(maxBusIconPosition);
            return;
        }

        double currentPosition = busIcon.getTranslateX();
        double remainingDistance = maxBusIconPosition - currentPosition;

        timeline.getKeyFrames().clear();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(
                javafx.util.Duration.seconds(remainingSeconds),
                new KeyValue(busIcon.translateXProperty(), maxBusIconPosition)
        );

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void stopAnimation() {
        timeline.stop();
    }
}
