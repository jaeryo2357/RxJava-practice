import common.CommonUtils;
import common.Log;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.GroupedObservable;

import java.util.concurrent.TimeUnit;

public class mapOperator {
    public static void main(String[] args) {
        //basicConCatMap();
        //basicSwitchMap();
        //basicGroupBy();
        basicScan();
    }

    //concatMap은 flatMap과 유사하며 단지 데이터가 순서대로 처리될수 있음을 보장합니다.
    //balls 1은 concatMap에 의해 2개의 데이터 흐름으로 변경되며 200L 지연되며 배포된다. 따라서 100L로 생성되는 balls 데이터들은 concat보다 빠르게 생성이 되기 때문에
    //생성과 출력 순서가 달라질 수 있다. concat은 그것을 방지하여 입력 순서대로 출력을 할 수 있게 보장된다.
    public static void basicConCatMap() {
        CommonUtils.exampleStart();

        String[] balls = {"1", "2", "3"};
        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .concatMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS).map(notUsed -> ball + "ㅁ").take(2));

        source.subscribe(Log::it);
        CommonUtils.sleep(2000L);
    }

    //동일한 시간 내에 마지막 요청만을 실행한다.
    //입력이 들어오면 기존 실행 중이었던 작업을 취소하고 현재 작업을 실행한다.
    //Rx 없이 쓰레드로 구현하면 어렵다고 한다. 센서를 탐지하는 곳에 주로 사용한다고 함
    public static void basicSwitchMap() {
        String[] balls = {"1", "2", "3"};
        Observable<String> source = Observable.interval(100L, TimeUnit.MILLISECONDS)
                .map(Long::intValue)
                .map(idx -> balls[idx])
                .take(balls.length)
                .switchMap(ball -> Observable.interval(200L, TimeUnit.MILLISECONDS).map(notUsed -> ball + "ㅁ").take(2));

        source.subscribe(Log::it);
        CommonUtils.sleep(2000L);
    }

    //Observable에서 특정 조건에 의해 그룹화 해준다.
    //getKey() 메서드로 구분된 그룹을 알 수 있다.
    public static void basicGroupBy() {
        String[] balls = {"6", "4", "2-T", "2", "6-T", "4-T"};

        Observable<GroupedObservable<String, String>> source =
                Observable.fromArray(balls).groupBy(CommonUtils::getShape);

        source.subscribe(obj -> {
            obj.subscribe(
                    val -> System.out.println("GROUP:" + obj.getKey() + "\t value:" + val)
            );
        });
    }


    //reduce와 유사한 함수로
    //마지막 값만 출력하는 reduce와 달리 입력값이 샐길떄마다 중간값을 출력한다.
    public static void basicScan() {
        String[] balls = {"1", "2", "3"};

        Observable<String> source = Observable.fromArray(balls)
                .scan((ball1, ball2) -> ball2 + "(" + ball1 + ")");

        source.subscribe(Log::it);
    }

}
