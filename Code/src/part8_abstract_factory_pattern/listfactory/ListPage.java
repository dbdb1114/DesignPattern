package part8_abstract_factory_pattern.listfactory;

import part8_abstract_factory_pattern.factory.Item;
import part8_abstract_factory_pattern.factory.Page;

public class ListPage extends Page {
    public ListPage(String title, String author) {
        super(title, author);
    }

    @Override
    public String makeHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <title>")
                .append(title)
                .append("</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "<h1>")
                .append(title)
                .append("</h1>\n"
                        + "<ul>");
        for (Item item : content) {
            sb.append(item.makeHTML());
        }
        sb.append("</ul>\n"
                + "<hr><address>Youngin.com</address>\n"
                + "</body>\n"
                + "</html>\n");
        return sb.toString();
    }
}
