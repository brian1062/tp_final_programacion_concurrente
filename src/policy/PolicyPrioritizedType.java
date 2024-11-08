package policy;

public final class PolicyPrioritizedType extends PolicyType {
    double upPercentage;

    PolicyPrioritizedType(double upPercentage) { this.upPercentage = upPercentage; }

    @Override
    Policy createPolicy() {
        return new PolicyImpl(upPercentage);
    }
}
