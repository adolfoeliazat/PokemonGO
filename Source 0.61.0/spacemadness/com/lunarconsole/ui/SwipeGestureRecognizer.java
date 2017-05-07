package spacemadness.com.lunarconsole.ui;

import android.view.MotionEvent;
import com.google.android.exoplayer.util.NalUnitUtil;
import com.upsight.mediation.mraid.properties.MRAIDResizeProperties;
import spacemadness.com.lunarconsole.C1628R;

public class SwipeGestureRecognizer extends GestureRecognizer<SwipeGestureRecognizer> {
    private final SwipeDirection direction;
    private float endX;
    private float endY;
    private float startX;
    private float startY;
    private boolean swiping;
    private final float threshold;

    public enum SwipeDirection {
        Up,
        Down,
        Left,
        Right
    }

    public SwipeGestureRecognizer(SwipeDirection direction, float threshold) {
        this.direction = direction;
        this.threshold = threshold;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & NalUnitUtil.EXTENDED_SAR) {
            case C1628R.styleable.RecyclerView_spanCount /*2*/:
                if (this.swiping) {
                    this.endX = event.getX(0);
                    this.endY = event.getY(0);
                    break;
                }
                break;
            case MRAIDResizeProperties.CUSTOM_CLOSE_POSITION_BOTTOM_CENTER /*5*/:
                this.swiping = true;
                this.startX = event.getX(0);
                this.startY = event.getY(0);
                break;
            case MRAIDResizeProperties.CUSTOM_CLOSE_POSITION_BOTTOM_RIGHT /*6*/:
                if (this.swiping) {
                    handleSwipe(this.direction, this.endX - this.startX, this.endY - this.startY);
                    this.swiping = false;
                    break;
                }
                break;
        }
        return true;
    }

    private void handleSwipe(SwipeDirection direction, float distX, float distY) {
        if ((direction == SwipeDirection.Down && distY >= this.threshold) || ((direction == SwipeDirection.Up && (-distY) >= this.threshold) || ((direction == SwipeDirection.Right && distX >= this.threshold) || (direction == SwipeDirection.Left && (-distX) >= this.threshold)))) {
            notifyGestureRecognizer();
        }
    }
}
