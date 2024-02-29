import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
    String n = sc.nextLine();
    if (n.charAt(n.length() - 1)==(n.charAt(0))) {
      System.out.print(true);
    } else {
      System.out.print(false);
    }
    }
}
