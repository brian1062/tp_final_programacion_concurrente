import policy.*;

class Monitor implements MonitorInterface {
  Policy policyQueue;

  Policy policy;

  Monitor(Policy policyQueue, Policy policy) {
    this.policyQueue = policyQueue;
    this.policy = policy;
  }

  @Override
  public boolean fireTransition(int transition) {
    return false;
  }
}

interface MonitorInterface {
  boolean fireTransition(int transition);
}
