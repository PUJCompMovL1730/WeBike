package webike.webike.utils;

/**
 * Created by Juan on 11/17/2017.
 */

public interface ListFilteredActions<DataType,FilterType> extends ListActions<DataType>{
    boolean searchCriteria( DataType data , FilterType filter);
}
