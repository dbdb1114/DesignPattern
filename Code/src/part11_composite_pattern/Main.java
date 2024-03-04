package part11_composite_pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Making root entries...");
        Directory rootdir = new Directory("root");
        Directory bindir = new Directory("bin");
        Directory tmpdir = new Directory("tmp");
        Directory usrdir = new Directory("usr");
        rootdir.add(bindir);
        rootdir.add(tmpdir);
        rootdir.add(usrdir);
        bindir.add(new File("vi", 10000));
        bindir.add(new File("latex", 20000));
        rootdir.printList();
        System.out.println();

        System.out.println("Making user entries");
        Directory youngjin = new Directory("youngjin");
        Directory gildong = new Directory("gildong");
        Directory dojun = new Directory("dojun");
        usrdir.add(youngjin);
        usrdir.add(gildong);
        usrdir.add(dojun);

        File diary = new File("diary.html", 100);
        File Composite = new File("Composite.java", 200);
        File memo = new File("memo.txt",300);
        File game = new File("game.doc",400);
        File junk = new File("junk.mail",500);
        youngjin.add(diary);
        youngjin.add(Composite);
        gildong.add(memo);
        dojun.add(game);
        dojun.add(junk);
        rootdir.printList();

        System.out.println(diary.getPath());
        System.out.println(Composite.getPath());
        System.out.println(memo.getPath());
        System.out.println(game.getPath());
        System.out.println(junk.getPath());

        System.out.println("dojun.getPath() : " + dojun.getPath());

    }
}
