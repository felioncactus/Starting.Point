package com.example.startingpoint;

public class WikiSummary {
    private String title;
    private String extract;
    private Thumbnail thumbnail;
    private ContentUrls content_urls;

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getExtract() {
        return extract;
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
