package bbcspaceinvaders.common;

import java.util.ArrayList;
import java.util.List;

public class Util {

    @SuppressWarnings("unchecked")
    public static <T> List<T> getAllObjectsFromType(Class<T> classToFind, List<?> list) {
        List<T> resultList = new ArrayList<T>();
        for (Object o : list) {
            if (classToFind.equals(o.getClass()))
                resultList.add((T) o);
        }
        return resultList;
    }

}
