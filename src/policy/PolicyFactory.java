package policy;

public class PolicyFactory {

    final private PolicyType type;

    public PolicyFactory(PolicyType type){
        this.type = type;
    }

    Policy create(){
        switch(type){
            case PolicyBalancedType _ -> { return new PolicyImpl(50.0); }
            case PolicyPrioritizedType prioritized -> { return new PolicyImpl(prioritized.upPercentage); }
            default -> { throw new RuntimeException("Policy type not supported"); }
        }
    }
}
