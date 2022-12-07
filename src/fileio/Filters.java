package fileio;

public class Filters {
    public Filters() {

    }

    private Contains contains;
    private Sort sort;

    public Filters(Filters filters) {
        this.contains = new Contains(filters.getContains());
        this.sort = new Sort(filters.getSort());
    }

    public Filters(Contains contains, Sort sort) {
        this.contains = new Contains(contains);
        this.sort = new Sort(sort);
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Contains getContains() {
        return contains;
    }

    public void setContains(Contains contains) {
        this.contains = contains;
    }
}
