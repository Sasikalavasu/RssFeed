package app.sella.it.rssfeed.Util;

import java.util.Collection;

public class ValidationUtil {
    private ValidationUtil(){
    }
    public static boolean isNotEmpty(Collection coll){
        return (coll!=null && !coll.isEmpty());
    }

    public static boolean isNotNull(String str){
        return (str!=null && !str.isEmpty());
    }
}
