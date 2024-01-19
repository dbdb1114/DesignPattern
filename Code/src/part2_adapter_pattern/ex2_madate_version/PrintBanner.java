package part2_adapter_pattern.ex2_madate_version;

import part2_adapter_pattern.Banner;

public class PrintBanner extends Print{
    Banner banner;

    public PrintBanner(String string) {
        this.banner = new Banner(string);
    }

    @Override
    public void printWeak() {
        banner.showWithParen();
    }

    @Override
    public void printStrong() {
        banner.showWithAster();
    }
}
