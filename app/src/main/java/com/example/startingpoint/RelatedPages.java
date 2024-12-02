package com.example.startingpoint;

import java.util.List;

public class RelatedPages {
    private List<RelatedPage> pages;

    public List<RelatedPage> getPages() {
        return pages;
    }

    public static class RelatedPage {
        private String title;
        private String extract;
        private Thumbnail thumbnail;

        private ContentUrls content_urls;

        public String getTitle() {
            return title;
        }

        public String getExtract() {
            return extract;
        }

        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        public ContentUrls getContentUrls() {
            return content_urls;
        }

        public static class Thumbnail {
            private String source;

            public String getSource() {
                return source;
            }
        }

        public static class ContentUrls {
            private Desktop desktop;

            public Desktop getDesktop() {
                return desktop;
            }

            public static class Desktop {
                private String page;

                public String getPage() {
                    return page;
                }
            }
        }
    }
}
