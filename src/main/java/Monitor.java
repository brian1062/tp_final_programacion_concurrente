import policy.Policy;

class Monitor implements MonitorInterface {
  Policy policyQueue;
  PetriNet petriNet;

  Policy policy;

  Monitor(PetriNet petriNet, Policy policyQueue, Policy policy) {
    this.policyQueue = policyQueue;
    this.policy = policy;
    this.petriNet = petriNet;
  }

  @Override
  public boolean fireTransition(String transitionIndex) {

    return false;
  }
}

interface MonitorInterface {
  boolean fireTransition(String transition);
}
