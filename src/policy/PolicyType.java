package policy;

sealed abstract class PolicyType permits PolicyBalancedType, PolicyPrioritizedType {
    abstract Policy createPolicy();
}
