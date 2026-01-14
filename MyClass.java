import java.util.*;

public class MyClass {

    static int sumDigits(String studentId) {
        int sum = 0;
        for (char c : studentId.toCharArray()) {
            if (Character.isDigit(c)) sum += c - '0';
        }
        return sum;
    }

    static String makeSignatureV2(String studentId, int score, int completed) {
        int checksum = 7;
        for (int i = 0; i < studentId.length(); i++) {
            checksum = (checksum * 37 + studentId.charAt(i)) % 100000;
        }
        checksum = (checksum + score * 89 + completed * 101) % 100000;
        return "SIG2-" + String.format("%05d", checksum);
    }

    static int askInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine());
                if (v >= min && v <= max) return v;
            } catch (Exception e) {}
            System.out.println("ค่าที่กรอกไม่ถูกต้อง (" + min + "-" + max + ")");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Java II: Quest Manager (OOP) ===");
        System.out.print("กรอกรหัสนักศึกษา: ");
        String studentId = sc.nextLine();

        int key = sumDigits(studentId) % 97;
        int energy = (key * 7 + 13) % 100;
        int logic  = (key * 11 + 5) % 100;
        int luck   = (key * 17 + 19) % 100;

        QuestService qs = new QuestService();
        qs.seedQuests(key);

        int totalScore = 0;

        while (true) {
            System.out.println("\n[Menu] 1) List 2) Do quest 3) Stats 4) Exit");
            int m = askInt(sc, "เลือกเมนู: ", 1, 4);

            if (m == 1) {
                qs.listQuests();
            } 
            else if (m == 2) {
                System.out.print("พิมพ์ quest id: ");
                String id = sc.nextLine();
                Quest q = qs.findById(id);

                if (q == null) {
                    System.out.println("ไม่พบ quest นี้");
                    continue;
                }

                if (q.canComplete(energy, logic, luck)) {
                    int r = q.rewardPoints(key);
                    totalScore += r;
                    qs.stats.put("completed", qs.stats.get("completed") + 1);
                    System.out.println("สำเร็จ! +" + r + " points");
                } else {
                    qs.stats.put("failed", qs.stats.get("failed") + 1);
                    System.out.println("ยังทำไม่ได้ (stat ไม่ถึง)");
                }
            } 
            else if (m == 3) {
                System.out.println("completed=" + qs.stats.get("completed"));
                System.out.println("failed=" + qs.stats.get("failed"));
                System.out.println("score=" + totalScore);
            } 
            else {
                break;
            }
        }

        System.out.println("\n=== Result ===");
        System.out.println("score=" + totalScore);
        System.out.println("signature=" +
            makeSignatureV2(studentId, totalScore, qs.stats.get("completed")));

        System.out.println("OOP ช่วยแยกหน้าที่ของแต่ละคลาส ทำให้แก้ไขและขยายระบบเควสต์ได้ง่ายขึ้น");

        sc.close();
    }
}
