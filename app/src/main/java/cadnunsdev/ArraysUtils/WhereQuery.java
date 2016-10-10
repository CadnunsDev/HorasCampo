package cadnunsdev.ArraysUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiago Silva on 08/10/2016.
 */
public class WhereQuery<T> {

    public ArrayList<T> Where(ArrayList<T> lista, WhereAction<T> action){
        ArrayList<T> listaFiltrada = new ArrayList<>();
        for (T obj: lista) {
            if (action.Where(obj)){
                listaFiltrada.add(obj);
            }
        }
        return listaFiltrada;
    }

//    public Tout Select(ArrayList<T> lista, SelectAction<T, Tout> action){
//
//    }
//

    public interface WhereAction<T>{
        boolean Where(T entity);
    }

    public interface SelectAction<TInt,Tout>{
        Tout S(TInt entity);
    }
}

