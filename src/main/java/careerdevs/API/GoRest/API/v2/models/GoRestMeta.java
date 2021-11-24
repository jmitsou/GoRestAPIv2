package careerdevs.API.GoRest.API.v2.models;

public class GoRestMeta {

    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public static class Pagination {
        private int pages;
        private Links links;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public Links getLinks() {
            return links;
        }

        public void setLinks(Links links) {
            this.links = links;
        }

        private static class Links {

            private String next;

            public String getNext() {
                return next;
            }

            public void setNext(String next) {
                this.next = next;
            }
        }
    }


}
