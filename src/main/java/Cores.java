import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class Cores {

    //왜 BackPress 가 안생길까?
    static class CustomConsumer<T> extends DisposableObserver<T>  {
        @Override
        public void onNext(T t) {
            try {
                Thread.sleep(10000000L);
                System.out.println("onNext: " + t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable t) {
            System.out.println("onError: " + t.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete");
        }
    }

    public static void main(String[] args) {
        Observable.range(1, 1000_000_000)
                .map(x -> {
                    System.out.println("Sender Data:" + x);
                    return x;
                })
                .observeOn(Schedulers.computation())
                .subscribe(t -> {
                    Thread.sleep(10000L);
                    System.out.println("onAccept: " + t);
                });
    }

}
