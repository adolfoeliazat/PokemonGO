package rx.internal.operators;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public final class OnSubscribeToObservableFuture {

    static class ToObservableFuture<T> implements OnSubscribe<T> {
        final Future<? extends T> that;
        private final long time;
        private final TimeUnit unit;

        /* renamed from: rx.internal.operators.OnSubscribeToObservableFuture.ToObservableFuture.1 */
        class C14141 implements Action0 {
            C14141() {
            }

            public void call() {
                ToObservableFuture.this.that.cancel(true);
            }
        }

        public ToObservableFuture(Future<? extends T> that) {
            this.that = that;
            this.time = 0;
            this.unit = null;
        }

        public ToObservableFuture(Future<? extends T> that, long time, TimeUnit unit) {
            this.that = that;
            this.time = time;
            this.unit = unit;
        }

        public void call(Subscriber<? super T> subscriber) {
            subscriber.add(Subscriptions.create(new C14141()));
            try {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(this.unit == null ? this.that.get() : this.that.get(this.time, this.unit));
                    subscriber.onCompleted();
                }
            } catch (Throwable e) {
                if (!subscriber.isUnsubscribed()) {
                    Exceptions.throwOrReport(e, (Observer) subscriber);
                }
            }
        }
    }

    private OnSubscribeToObservableFuture() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> OnSubscribe<T> toObservableFuture(Future<? extends T> that) {
        return new ToObservableFuture(that);
    }

    public static <T> OnSubscribe<T> toObservableFuture(Future<? extends T> that, long time, TimeUnit unit) {
        return new ToObservableFuture(that, time, unit);
    }
}