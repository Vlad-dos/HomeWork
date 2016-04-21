import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {
    // pre: a != null
    // post: size == size' + 1 &&
    //       Any 0 <= i < size - 1 data[i] == data'[i]
    //       data[size - 1] == a
    void enqueue(Object a);

    // pre: predicate != null
    // post: size = size'
    //       data == data';
    //       any 0 <= i < result.size predicate.test(result[i]) == true
    //       any data[i] not in result : predicate.test(data[i]) == false
    //       forall i in result exist unique j such as ((data[j] == result[i]) and (forall i1 in result forall i2 in result if i1 < j2 then j1 < j2))
    Queue filter(Predicate<Object> predicate);


    // pre: function != null
    // post: size = size'
    //       data == data';
    //       any 0 <= i < size result[i] == function.apply(data[i])
    Queue map(Function<Object, Object> function);

    // pre: size > 0
    // post: result == data[0]
    //       Any 0 <= i < size data[i] == data'[i]
    Object element();

    // pre: size > 0
    // post: result == data[0]
    //       Any 0 <= i < size data[i + 1] == data'[i]
    Object dequeue();

    // post: result == size
    //       data == data';
    //       size == size';
    int size();

    // post: result == size > 0
    //       data == data';
    boolean isEmpty();

    // post: size == 0
    //       data == data';
    //       size == size';
    void clear();
}
