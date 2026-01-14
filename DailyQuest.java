public class DailyQuest extends Quest {

    public DailyQuest(String id, String title, int difficulty) {
        super(id, title, difficulty);
    }

    @Override
    public int rewardPoints(int key) {
        return (difficulty * 3 + key) % 25 + 5;
    }

    @Override
    public boolean canComplete(int energy, int logic, int luck) {
        return true;
    }
}
