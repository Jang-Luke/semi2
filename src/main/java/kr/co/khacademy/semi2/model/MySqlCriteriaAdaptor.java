package kr.co.khacademy.semi2.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class MySqlCriteriaAdaptor {

    Criteria criteria;

    public long getLimit() {
        return criteria.getLimit();
    }

    public long getOffset() {
        return (criteria.getPage() - 1L) * criteria.getLimit();
    }

    public String getSearchField() {
        return criteria.getSearchField();
    }

    public String getKeyword() {
        return criteria.getKeyword();
    }
}
