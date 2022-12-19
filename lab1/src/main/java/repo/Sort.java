package repo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Sort<T> implements Comparator<T> {
    private SortType sortType=SortType.ASC;
    private String[] sortField;

    public Iterable <T>  sort(Iterable<T> list){
        return StreamSupport
                .stream(list.spliterator(), false).sorted(this).collect(Collectors.toList());

    }

    @Override
    public int compare(T o1, T o2) {
        return this.comp(o1,o2,0);
    }

    public int comp(T o1,T o2, int x){
        try {
            String methodName= buildGetMethodName(this.sortField[x]);
            Method method1 = o1.getClass().getMethod(methodName);
            Comparable comp1 = (Comparable) method1.invoke(o1);
            Method method2 = o1.getClass().getMethod(methodName);
            Comparable comp2 = (Comparable) method2.invoke(o2);
            if (comp1.equals(comp2) && x+1<this.sortField.length) return this.comp(o1,o2,x+1);
            else return  comp1.compareTo(comp2) * sortType.getIndex();

        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Error");
        }
    }
    public enum SortType {
        ASC(1), DESC(-1);
        private int index;
        SortType(int index) { this.index = index; }
        public int getIndex() {
            return this.index;
        }
    }

    public static String buildGetMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() +
                fieldName.substring(1);
    }

    public Sort(SortType sortType,String... sortField){
        this.sortField=sortField;
        this.sortType=sortType;
    }
    public Sort(String... sortField){
        this.sortField=sortField;
    }

}
