package rx.observers;

import rx.Observer;
import rx.Subscriber;

public class SerializedSubscriber<T> extends Subscriber<T> {
    private final Observer<T> f766s;

    public SerializedSubscriber(Subscriber<? super T> s) {
        this(s, true);
    }

    public SerializedSubscriber(Subscriber<? super T> s, boolean shareSubscriptions) {
        super(s, shareSubscriptions);
        this.f766s = new SerializedObserver(s);
    }

    public void onCompleted() {
        this.f766s.onCompleted();
    }

    public void onError(Throwable e) {
        this.f766s.onError(e);
    }

    public void onNext(T t) {
        this.f766s.onNext(t);
    }
}
