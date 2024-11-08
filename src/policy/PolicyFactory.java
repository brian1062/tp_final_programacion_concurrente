package policy;

public class PolicyFactory {
    private final PolicyType type;

    public PolicyFactory(PolicyType type) { this.type = type; }

    Policy create() {
        return type.createPolicy();
    }
}
