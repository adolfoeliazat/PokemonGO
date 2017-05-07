package rx.internal.operators;

import com.google.android.exoplayer.extractor.ts.PtsTimestampAdjuster;
import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.observers.SerializedSubscriber;

public final class OperatorTakeUntil<T, E> implements Operator<T, T> {
    private final Observable<? extends E> other;

    /* renamed from: rx.internal.operators.OperatorTakeUntil.1 */
    class C15101 extends Subscriber<T> {
        final /* synthetic */ Subscriber val$serial;

        C15101(Subscriber x0, boolean x1, Subscriber subscriber) {
            this.val$serial = subscriber;
            super(x0, x1);
        }

        public void onNext(T t) {
            this.val$serial.onNext(t);
        }

        public void onError(Throwable e) {
            try {
                this.val$serial.onError(e);
            } finally {
                this.val$serial.unsubscribe();
            }
        }

        public void onCompleted() {
            try {
                this.val$serial.onCompleted();
            } finally {
                this.val$serial.unsubscribe();
            }
        }
    }

    /* renamed from: rx.internal.operators.OperatorTakeUntil.2 */
    class C15112 extends Subscriber<E> {
        final /* synthetic */ Subscriber val$main;

        C15112(Subscriber subscriber) {
            this.val$main = subscriber;
        }

        public void onStart() {
            request(PtsTimestampAdjuster.DO_NOT_OFFSET);
        }

        public void onCompleted() {
            this.val$main.onCompleted();
        }

        public void onError(Throwable e) {
            this.val$main.onError(e);
        }

        public void onNext(E e) {
            onCompleted();
        }
    }

    public OperatorTakeUntil(Observable<? extends E> other) {
        this.other = other;
    }

    public Subscriber<? super T> call(Subscriber<? super T> child) {
        Subscriber<T> serial = new SerializedSubscriber(child, false);
        Subscriber<T> main = new C15101(serial, false, serial);
        Subscriber<E> so = new C15112(main);
        serial.add(main);
        serial.add(so);
        child.add(serial);
        this.other.unsafeSubscribe(so);
        return main;
    }
}
