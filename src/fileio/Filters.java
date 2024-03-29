package fileio;

public final class Filters {
    public Filters() {

    }

    private Contains contains;
    private Sort sort;

    public Filters(final Filters filters) {
        this.contains = new Contains(filters.getContains());
        this.sort = new Sort(filters.getSort());
    }

    public Sort getSort() {
        return sort;
    }
    public void setSort(final Sort sort) {
        this.sort = sort;
    }
    public Contains getContains() {
        return contains;
    }
    public void setContains(final Contains contains) {
        this.contains = contains;
    }
}
