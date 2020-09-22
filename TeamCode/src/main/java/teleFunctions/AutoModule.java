package teleFunctions;

import java.util.ArrayList;

public class AutoModule {
    public boolean executing = false;
    public int stageNum = 0;
    public ArrayList<Stage> stages = new ArrayList();
    public ArrayList<Double> dynamics = new ArrayList();

    public void start() {
        executing = true;
    }

    public void update(ArrayList<Double> d) {
        dynamics = d;
        if (executing) {
            Stage s = stages.get(stageNum);
            if (s.run(dynamics.get(stageNum))) {
                stageNum++;
            }
            if (stageNum == stages.size()) {
                executing = false;
                stageNum = 0;
            }
        }
    }

    public void addStage(Stage s) {
        stages.add(s);
    }
}