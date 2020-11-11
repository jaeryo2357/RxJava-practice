import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 생성 연산자는 Observable 등 데이터 흐름을 생성하는 함수들이다.
 * just, fromXXX(), create(), interval(), range(), timer(), intervalRange(), defer(), repeat()
 */
public class CreateOperator {
    public static void main(String[] args) {

        //basicInterval();

        //basicTimer();

        //basicRange();

       // basicIntervalRange();

        //basicDefer();
        basicRepeat();
    }


    //interval은 처음부터 period동안 계속 지연되고 데이터가 배포되는 함수 0, 1 , 2.. 등 0으로 시작하여 1씩 증가한다.
    //interval 함수의 다른 원형은 첫번째 매개변수 long형은 시작하는 시간을 지연하는 시간을 의미한다.
    //Observable.interval(0, 100L, TimeUnit.MILLISECONDS)
    public static void basicInterval() {
        CommonUtils.exampleStart();
        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(data -> (data + 1) * 100)
                .take(5)
                .subscribe(Log::it);

        CommonUtils.sleep(2000L);
    }

    //timer는 매개변수 값 만큼 지연되며, 1번만 값을 반환되고 onComplete 이벤트가 발생한다.
    public static void basicTimer() {
        CommonUtils.exampleStart();
        Observable.timer(500L, TimeUnit.MILLISECONDS)
                .map(notUsed -> new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
                .subscribe(Log::it);

        CommonUtils.sleep(2000L);
    }

    //range(n, m)은 n부터 m - 1까지의 Data를 생성한다.
    //현재 쓰레드에서 생성되기 때문에 sleep을 안해줘도 된다.
    public static void basicRange() {
        Observable.range(1, 9)
                .filter(data -> data % 2 == 0)
                .subscribe(Log::it);
    }


    //Interval 과 range가 합쳐진 경우
    //interval 함수와 map, take 등 기본 함수로 같은 기능을 만드는 것이 훨씬 더 가독성이 좋다.
    public static void basicIntervalRange() {
        CommonUtils.exampleStart();
        Observable.intervalRange(1, 5, 100L, 100L, TimeUnit.MILLISECONDS)
                .subscribe(Log::it);
        CommonUtils.sleep(1000L);
    }

    //기존 쓰레드에서 돌아가는 Defer은 데이터 흐름의 생성을 구독자가 생성될 때로 미룬다.
    //구독자마다 새로운 Observable를 생성
    public static void basicDefer() {
        Iterator<String> colors = Arrays.asList("1", "3", "5", "6").iterator();

        Observable<String> source = Observable.defer(() -> {
            if (colors.hasNext()) {
                String color = colors.next();
                return Observable.just(color + "-A", color + "-B", color + "-C");
            }
            return Observable.empty();
        });

        source.subscribe(Log::it);
        source.subscribe(Log::it);
    }

    //생성한 데이터 흐름을 단순 반복하는 함수
    // 책 필자에 말로 가장 활용성이 좋은 함수로 서버가 살아있는 지 확인하는 ping의 역할을 한다고 한다.
    public static void basicRepeat() {
        String[] balls = {"1", "2", "3"};
        Observable.fromArray(balls)
                .repeat(3)
                .subscribe(Log::it);
    }

    public static void juniorRepeat() {

//        Observable.timer(2, TimeUnit.SECONDS)
//                .map(val -> "server URL")
//                .map(OkHttpHeler::get)
//                .repeat()
//                .subscribe();
//
//        CommonUtils.sleep(10000L);
    }
}
