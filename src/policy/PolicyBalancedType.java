package policy;

public final class PolicyBalancedType extends PolicyType {
    @Override
    Policy createPolicy() {
        return new PolicyImpl(50.0);
    }
}