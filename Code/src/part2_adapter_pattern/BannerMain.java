package part2_adapter_pattern;

import part2_adapter_pattern.ex2_madate_version.Print;
import part2_adapter_pattern.ex2_madate_version.PrintBanner;

public class BannerMain {
    public static void main(String[] args) {
        Print p = new PrintBanner("Hello");
        p.printStrong();
        p.printWeak();
    }
}
