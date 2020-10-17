import io.reactivex.rxjava3.core.Flowable;

public class HelloWorld {
    public static void main(String[] args) {
        Flowable.just("Hello. RxJava").subscribe(System.out::println);

        combine();

        filter();
    }

    public static void combine() {
        String[] data1 = new String[]{"1", "2", "3"};
        String[] data2 = new String[]{"A", "B", "C"};

        Flowable.combineLatest(
                Flowable.fromArray(data1), Flowable.fromArray(data2),
                (d1, d2) -> d1 + "-" + d2).subscribe(System.out::println);
    }

    public static void filter() {
        Integer[] data = new Integer[] {1, 2, 3, 4, 5, 6};

        Flowable.fromArray(data)
                .filter(d -> d % 2 == 0)
                .subscribe(System.out::println);
    }
}
