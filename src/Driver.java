/** The Driver class for CompactPrefixTree */
public class Driver {
    public static void main(String[] args) {
//            Dictionary dict = new CompactPrefixTree();
//            dict.add("cat");
//            dict.add("cart");
//            dict.add("carts");
//            dict.add("case");
//            dict.add("doge");
//            dict.add("doghouse");
//            dict.add("wrist");
//            dict.add("wrath");
//            dict.add("wristle");
//            dict.print();
        CompactPrefixTree cpt = new CompactPrefixTree();
        cpt.add("apple");
        cpt.add("app");
        cpt.add("apear");
        cpt.add("april");
        cpt.add("add");
        cpt.add("amen");
        cpt.add("boy");
        cpt.add("boil");
        cpt.add("bitch");
//        cpt.found();

//        cpt.print();
        cpt.printTree();
//        System.out.println(cpt.checkPrefix("appr"));

//        String a = "applapp";
//        String b = "applement";
//        String c = cpt.findLongestCommonPrefix(a, b);
//        System.out.println(c);
//        System.out.println(a.substring(0,i));
//        System.out.println(a.substring(i));
//        System.out.println(b.substring(i));
            // Add other "tests"
    }
}
