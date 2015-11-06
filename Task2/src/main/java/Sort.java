import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface Sort<T> {
    List<T> sort(Collection<T> arr, Comparator<T> comparator);
}
