import io.reactivex.rxjava3.core.Flowable;

public class HelloWorld {
    public static void main(String[] args) {
        Flowable.just("Hello. RxJava").subscribe(System.out::println);
    }
}
