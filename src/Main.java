import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 70;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 270, 250, 300, 400, 250, 350, 280};
    public static int[] heroesDamage = {25, 15, 10, 0, 5, 20, 0, 30};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Witcher", "Thor"};
    public static int medicHealing = 40;
    public static int roundNumber = 0;
    public static boolean bossStunned = false;
    public static boolean witcherRevived = false;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            round();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Герои победили!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Босс выиграл!!");
            return true;
        }
        return false;
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        if (!bossStunned) {
            bossAttacks();
        } else {
            System.out.println("Босс ошеломлен и пропускает этот раунд!");
            bossStunned = false;
        }
        medicHeals();
        heroesAttack();
        showStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length - 1); // 0,1,2,3,4,5,6,7
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0 && !heroesAttackType[i].equals("Medic") && !heroesAttackType[i].equals("Witcher")) {
                int damage = heroesDamage[i];
                if (bossDefence.equals(heroesAttackType[i])) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = damage * coeff;
                    System.out.println("КРИТИЧЕСКИЙ УРОН!!!!: " + heroesAttackType[i] + " " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }


                if (heroesAttackType[i].equals("Thor")) {
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        bossStunned = true;
                        System.out.println("Thor оглушил босса!");
                    }
                }
            }
        }
    }

    public static void bossAttacks() {
        int totalDamageToDistribute = bossDamage;
        if (heroesHealth[4] > 0) {
            int golemDamageShare = bossDamage / 5;
            totalDamageToDistribute -= golemDamageShare * (heroesHealth.length - 1);
            heroesHealth[4] -= golemDamageShare * (heroesHealth.length - 1);
            System.out.println("Golem получает " + golemDamageShare * (heroesHealth.length - 1) + " damage");
            if (heroesHealth[4] < 0) {
                heroesHealth[4] = 0;
            }
        }

        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && !heroesAttackType[i].equals("Golem")) {
                int damageToHero = bossDamage;
                if (heroesAttackType[i].equals("Lucky")) {
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        damageToHero = 0;
                        System.out.println("Lucky увернулся от атаки!!");
                    }
                }

                if (heroesHealth[i] - damageToHero < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - damageToHero;
                }
            }
        }
    }

    public static void medicHeals() {
        if (heroesHealth[3] > 0) {
            int minHealthIndex = -1;
            int minHealth = Integer.MAX_VALUE;
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != 3 && heroesHealth[i] > 0 && heroesHealth[i] < minHealth && heroesHealth[i] < 100) {
                    minHealth = heroesHealth[i];
                    minHealthIndex = i;
                }
            }
            if (minHealthIndex != -1) {
                heroesHealth[minHealthIndex] += medicHealing;
                System.out.println("Medic лечит " + heroesAttackType[minHealthIndex] + " на " + medicHealing + " жизни");
            }
        }
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "None" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}