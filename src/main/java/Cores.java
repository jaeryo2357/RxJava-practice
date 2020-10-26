import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;


public class Cores {



    public static void main(String[] args) {
       //observable2();
       //observable();
        //flowable();
    //    basicFlowable();
       // single();
       // completed();
        maybe();
    }

    private static void observable() {
        Observable.range(1, 1000_000_000)
                .map(x -> {
                    try {
                        Thread.sleep(10L);
                        System.out.println(Thread.currentThread().getName() + " sender Data:" + x);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return x;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.single())
                .subscribe(next -> {
                    try {
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread().getName() + " onNext: " + next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                }, () -> {
                    System.out.println(Thread.currentThread().getName() + " onComplete");
                });

        //observeOn에서 Worker 쓰레드로 선언안해줘서 sender가 1개만 됐음
        //3십몇만 까지 계속 sender함

        try {
            Thread.sleep(80000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void observable2() {
        String[] strings = new String[]{"딸기", "소스", "치킨"};
        Observable.fromArray(strings)
                .subscribe(next -> {
                    System.out.println(Thread.currentThread().getName() + " onNext: " + next);
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                }, () -> {
                    System.out.println(Thread.currentThread().getName() + " onComplete");
                });
    }

    private static void flowable() {
        Flowable.range(1, 1000_000_000)
                .onBackpressureBuffer()
                .map(x -> {
                    try {
                        Thread.sleep(10L);
                        System.out.println(Thread.currentThread().getName() + " sender Data:" + x);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return x;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.single())
                .subscribe(next -> {
                    try {
                        Thread.sleep(1000L);
                        System.out.println(Thread.currentThread().getName() + " onNext: " + next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                }, () -> {
                    System.out.println(Thread.currentThread().getName() + " onComplete");
                });

        //MainThread 가 종료되지 않아야 실행이 된다.
        try {
            Thread.sleep(80000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void basicFlowable() {
        String[] strings = new String[]{"딸기", "소스", "치킨"};


        Flowable.fromArray(strings)
                .subscribe(next -> {
                    System.out.println(Thread.currentThread().getName() + " onNext: " + next);
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                }, () -> {
                    System.out.println(Thread.currentThread().getName() + " onComplete");
                });
    }

    private static void single() {
        //Single.just("Hello", "RxJava") Error 발생
        Single.just("Single RxJava")
                .subscribe(success -> {
                    System.out.println(Thread.currentThread().getName() + " onSuccess: " + success);
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                });
    }

    private static void completed() {
        Completable.complete()
                .delay(1, TimeUnit.MILLISECONDS)
                .subscribe(() -> {
                    System.out.println(Thread.currentThread().getName() + " onCompleted");
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                });

        try {
            Thread.sleep(5000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void maybe() {
        Maybe.fromCallable(() -> {
            String str = null;
            return str;
        }).subscribe(success -> {
                    System.out.println(Thread.currentThread().getName() + " onSuccess: " + success);
                }, error -> {
                    System.out.println(Thread.currentThread().getName() + " onError: " + error.getMessage());
                }, () -> {
                    System.out.println(Thread.currentThread().getName() + " onCompleted");
                } );
    }
}
