package part7_builder_pattern;

public class TextBuilder implements Builder{
    private StringBuilder sb = new StringBuilder();
    @Override
    public void makeTitle(String title) {
        sb.append("================================================\n")
                .append("[")
                .append(title)
                .append("]\n\n");
    }

    @Override
    public void makeString(String str) {
        sb.append("\uD83D\uDCE6")
                .append(str)
                .append("\n\n");
    }

    @Override
    public void makeItems(String[] items) {
        for (String s : items) {
            sb.append(" .")
                    .append(s)
                    .append("\n");
        }
        sb.append("\n");
    }

    @Override
    public void close() {
        sb.append("==========================================\n");
    }

    public String getTextResult() {
        return sb.toString();
    }
}
