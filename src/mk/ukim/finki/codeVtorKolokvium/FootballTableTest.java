package mk.ukim.finki.codeVtorKolokvium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

class FootballTeam {
    String name;
    int matches;
    int wins;
    int drows;
    int loses;
    int GD;
    int points;

    public FootballTeam(String name) {
        this.name = name;
        this.matches = 0;
        this.wins = 0;
        this.drows = 0;
        this.loses = 0;
        this.GD = 0;
        this.points = 0;
    }

    public void sumPoints(){
        this.points = this.wins*3 + this.drows;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d\n", name, matches, wins, drows, loses, points);
    }

    public String getName() {
        return name;
    }

    public int getGD() {
        return GD;
    }

    public void setGD(int GD) {
        this.GD = GD;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDrows() {
        return drows;
    }

    public void setDrows(int drows) {
        this.drows = drows;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

//    @Override
//    public int compareTo(FootballTeam o) {
//        int res = Integer.compare(this.points, o.points);
//        return res == 0 ? Integer.compare(this.GD, o.GD) : res ;
//    }
}

class FootballTable{

    HashMap<String, FootballTeam> teams;

    public FootballTable() {
        this.teams = new HashMap<>();
    }


    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals){
        if(!teams.containsKey(homeTeam)) teams.put(homeTeam,new FootballTeam(homeTeam));
        if(!teams.containsKey(awayTeam)) teams.put(awayTeam,new FootballTeam(awayTeam));
        FootballTeam home = teams.get(homeTeam);
        FootballTeam away = teams.get(awayTeam);
        if(homeGoals>awayGoals){
            home.setWins(teams.get(homeTeam).getWins()+1);
            home.setMatches(teams.get(homeTeam).getMatches()+1);
            home.sumPoints();

            away.setLoses(teams.get(awayTeam).getLoses()+1);
            away.setMatches(teams.get(awayTeam).getMatches()+1);
            away.sumPoints();
        }else if(homeGoals<awayGoals){
            away.setWins(teams.get(awayTeam).getWins()+1);
            away.setMatches(teams.get(awayTeam).getMatches()+1);
            away.sumPoints();

            home.setLoses(teams.get(homeTeam).getLoses()+1);
            home.setMatches(teams.get(homeTeam).getMatches()+1);
            home.sumPoints();
        }else {
            away.setDrows(teams.get(awayTeam).getDrows()+1);
            away.setMatches(teams.get(awayTeam).getMatches()+1);
            away.sumPoints();

            home.setDrows(teams.get(homeTeam).getDrows()+1);
            home.setMatches(teams.get(homeTeam).getMatches()+1);
            home.sumPoints();
        }

        home.setGD(home.GD+(homeGoals-awayGoals));
        away.setGD(away.GD+(awayGoals-homeGoals));

    }

    public void printTable(){
        List<FootballTeam> teamList = teams.values().stream()
                .sorted(Comparator.comparing(FootballTeam::getPoints)
                        .thenComparing(FootballTeam::getGD).reversed()
                        .thenComparing(FootballTeam::getName))
                .collect(Collectors.toList());

        IntStream.range(0,teamList.size())
                .forEach(i -> System.out.printf("%2d. %s",i+1, teamList.get(i)));
    }


}
