package policy;

sealed class PolicyType permits PolicyBalancedType, PolicyPrioritizedType { }