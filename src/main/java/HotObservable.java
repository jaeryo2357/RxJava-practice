import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;

import java.util.concurrent.TimeUnit;

public class HotObservable {

    /** 기존 Observable, Single, Flowable 들은 Cold Observable이라고 불린다.
     * Cold Ovservable이란 데이터 소스에서 발행되는 Data들은 구독자가 구독을 하고 나서부터 발행을 시작한다. 구독자가 없을 경우, 발행되지 않는다.
     *
     * Hot Observable은 구독자의 유무 상관없이 Data를 발행한다. 그래서 구독자들은 Observable에 대해 모든 Data를 받는 것을 보장받지 못한다.
     *
     */
    public static void main(String[] args) {
        coldObservable();
       //asyncSubject();

       //behaviorSubject();

       //publishSubject();

       // connectableObserable();
    }

    public static void coldObservable() {
        String[] strings = new String[]{"딸기", "소스", "치킨"};


        Flowable<String> source  = Flowable.fromArray(strings);

        source.subscribe(next -> {
                    System.out.println(Thread.currentThread().getName() + " Subscriber # 1: " + next);
                });

        source.subscribe(next -> {
            Thread.sleep(1000L);
            System.out.println(Thread.currentThread().getName() + " Subscriber # 2: " + next);
        });
    }

    //Hot Observable을 만드는 방법 중 하나인 Subject는 데이터를 발행하는 것과 구독하는 것을 모두 할 수 있다.
    //AsyncSubject는 완료(onComplete)전 마지막 Data만 관심을 가진다.
    public static void asyncSubject() {
        AsyncSubject<String> subject = AsyncSubject.create();

        subject.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 1 = > " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 2 = > " + data));
        subject.onNext("5");
        subject.onComplete();
        subject.onNext("8");
    }

    //BehaviorSubject는 가장 최근의 값 혹은 기본 값부터 받아온다.
    public static void behaviorSubject() {
        BehaviorSubject<String> subject = BehaviorSubject.createDefault("6");
        //구독 이전 데이터 발행이 없으므로 기본값 6을 받음
        subject.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 1 = > " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 2 = > " + data));
        subject.onNext("5");
        subject.onComplete();
    }

    //publishSubject는 구독한 시점부터 발행된 데이터를 구독한다.
    public static void publishSubject() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 1 = > " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 2 = > " + data));
        subject.onNext("5");
        subject.onComplete();
    }

    public static void replaySubject() {
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 1 = > " + data));
        subject.onNext("1");
        subject.onNext("3");
        subject.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 2 = > " + data));
        subject.onNext("5");
        subject.onComplete();
    }

    public static void connectableObserable() {
        String[] dt = {"1", "2", "3"};
        Observable<String> balls = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(i -> dt[i])
                .take(dt.length);

        ConnectableObservable<String> source = balls.publish();
        source.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 1 = > " + data));
        source.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 2 = > " + data));

        source.connect();//데이터 발행 시작

        try {
            Thread.sleep(250L);
            source.subscribe(data -> System.out.println(Thread.currentThread().getName() + " Subscriber # 3 = > " + data));
            Thread.sleep(100L);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
