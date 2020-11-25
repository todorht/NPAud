package prvKolovium;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde

class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String category){
        super(String.format("Category %s was not found", category));
    }
}

class Category{
    String category;

    public Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}

abstract class NewsItem{
    String title;
    Date date;
    Category category;

    public NewsItem(String title, Date date, Category category) {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    abstract public String getTeaser();
}

class TextNewsItem extends NewsItem{
    String text;

    public TextNewsItem(String title,Date date, Category category, String text) {
        super(title,date, category);
        this.title = title;
        this.text = text;
    }

    public String getTeaser(){
        StringBuilder sb = new StringBuilder();
        if(text.length()>80){
            text = text.substring(0,80);
        }
        Calendar cal = Calendar.getInstance();
        Date date1 = cal.getTime();
        sb.append(title).append("\n").append((date1.getTime()- date.getTime())/60/1000).append("\n").append(text).append("\n");
        return sb.toString();
    }
}

class MediaNewsItem extends NewsItem{
    String url;
    int views;

    public MediaNewsItem(String title, Date date, Category category, String url, int views) {
        super(title,date, category);
        this.url = url;
        this.views = views;
    }

    public String getTeaser(){
        StringBuilder sb = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        Date date1 = cal.getTime();
        sb.append(title).append("\n").append((date1.getTime()- date.getTime())/60/1000).append("\n").append(url).append("\n").append(views).append("\n");
        return sb.toString();
    }

}

class FrontPage{
    List<NewsItem> newsItems;
    Category[] categories;

    public FrontPage(Category[] categories) {
        this.categories = categories;
        this.newsItems = new ArrayList<>();
    }

    public void addNewsItem(NewsItem newsItem){
        newsItems.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category){
        return newsItems.stream().filter(n->n.category.equals(category)).collect(Collectors.toList());
    }

    public List<NewsItem> listByCategoryName(String category){
        List<NewsItem> returnList = newsItems.stream().filter(n->n.category.getCategory().equals(category)).collect(Collectors.toList());
        if(Arrays.stream(categories).noneMatch(c->c.getCategory().equals(category)))
            throw new CategoryNotFoundException(category);
        return returnList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (NewsItem item: newsItems) sb.append(item.getTeaser());
        return sb.toString();
    }
}