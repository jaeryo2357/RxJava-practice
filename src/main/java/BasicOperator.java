import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;

import java.util.Scanner;

public class BasicOperator {
    public static void main(String[] args) {
        //basicMapOperator();

        //juniorMapOperator();

        //basicFlatMapOperator();

        //gugudan();

        //basicFilter();

        //juniorFilter();

        basicReduce();

        //test();
    }

    //map은 스케줄러를 지원하지 않아 현재 스레드에서 실행
    public static void basicMapOperator() {
        String[] balls = {"1", "2", "3", "5"};

        Observable<String> source = Observable.fromArray(balls)
                .map(ball -> ball + "ㅁ");
        source.subscribe(data -> System.out.println(data));
    }

    public static void juniorMapOperator() {
        //Function<A, B>  A를 입력으로 B를 반환, 타입 변환 가능
        Function<String, Integer> getSquare = ball -> Integer.parseInt(ball);

        String[] balls = {"1", "2", "3", "5"};
        Observable<Integer> source = Observable.fromArray(balls)
                .map(getSquare);
        source.subscribe(data -> System.out.println(data));
    }

    //flatMap은 map가 유사하지만 Observable를 반환한다는 것이 다른 점이다.
    //따라서 반환값이 여러개로 사용자는 받아들일 수 있다.
    public static void basicFlatMapOperator() {
        Function<String, Observable<String>> getSquare =
                ball -> Observable.just(ball + "ㅁ", ball + "ㅁ");

        String[] balls = {"1", "2", "3", "5"};
        Observable<String> source = Observable.fromArray(balls)
                .flatMap(getSquare);
        source.subscribe(data -> System.out.println(data));
    }

    public static void gugudan() {
        Scanner scanner = new Scanner(System.in);
        int dan = scanner.nextInt();

        Function<Integer, Observable<String>> function =
                data -> Observable.range(1, 9).map(data2 -> data + " * " + data2 + " = " + data * data2);
        Observable.just(dan)
                .flatMap(function)
                .subscribe(data -> System.out.println(data));
    }

    public static void basicFilter() {
        String[] objs = {"1 CIRCLE", "2 DIAMOND", "3 TRIANGLE"};

        Observable<String> source = Observable.fromArray(objs)
                .filter(obj -> obj.endsWith("CIRCLE"));
        source.subscribe(System.out::println);
    }

    public static void juniorFilter() {
        Integer[] numbers = {100, 200, 300, 400, 500};
        Single<Integer> single;
        Observable<Integer> source;

        // 1. first  처음
        single = Observable.fromArray(numbers).first(-1);
        single.subscribe(data -> System.out.println("first() value = " + data));

        // 2. last onComplete 마지막
        single = Observable.fromArray(numbers).last(999);
        single.subscribe(data -> System.out.println("last() value = " + data));

        //3. take(int size) size만큼
        source = Observable.fromArray(numbers).take(3);
        source.subscribe(data -> System.out.println("take() value = " + data));

        //3. takeLast(int size) 마지막에서 size만큼
        source = Observable.fromArray(numbers).takeLast(3);
        source.subscribe(data -> System.out.println("takeLast() value = " + data));
    }

   //최종 결과물을 합성
    public static void basicReduce() {
        String[] balls = {"1", "3", "5"};

        Maybe<String> source = Observable.fromArray(balls)
                .reduce((ball1, ball2) -> ball2 + "(" + ball1 + ")");
        source.subscribe(System.out::println);
    }

    public static void test() {
        String[] logs = {"TV 2500", "Camera 300", "TV 1600", "Phone 800"};

        Observable.fromArray(logs)
                .filter(log -> log.startsWith("TV"))
                .reduce((log1, log2) -> "" + (Integer.parseInt(log1.split(" ")[1]) + Integer.parseInt(log2.split(" ")[1])))
                .subscribe(System.out::println);
    }

}
