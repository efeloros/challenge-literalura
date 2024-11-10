package csibyte.com.challenge_literalura.modelo;

import java.util.List;

public class Result {
    private String title;
    private List<NewAutor> authors;
    private  List<String> languages;
    private Long download_count;

    public Result(String title, List<NewAutor> authors, List<String> languages, Long download_count) {
        this.title = title;
        this.authors = authors;
        this.languages = languages;
        this.download_count = download_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NewAutor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<NewAutor> authors) {
        this.authors = authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Long getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Long download_count) {
        this.download_count = download_count;
    }
}
