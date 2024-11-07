package policy;

import java.util.Random;

class PolicyImpl implements Policy {

    final private double upPercentage;
    private int upCount = 1;
    private int downCount = 1;

    PolicyImpl(double upPercentage){
        if(upPercentage < 0 || upPercentage > 100){
            throw new IllegalArgumentException("upPercentage must be between 0 and 100");
        }
        this.upPercentage = upPercentage;
    }

    public PolicyDecision decision() {
        Random random = new Random();

        double total = upCount + downCount;

        if(upCount/total == upPercentage){
            if(random.nextBoolean()){
                upCount++;
                return PolicyDecision.UP;
            }
            else {
                downCount++;
                return PolicyDecision.DOWN;
            }
        }
        else if(upCount/total < upPercentage) {
            upCount++;
            return PolicyDecision.UP;
        }
        else {
            downCount++;
            return PolicyDecision.DOWN;
        }
    }
}