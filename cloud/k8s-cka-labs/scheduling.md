# Scheduling

## Manual Scheduling

- in k8s we can specify the node to run a pod by adding nodeName property in the pod spec
- this is the simple, but it requires 
the pod to be not created **You cannot edit on an created pod and select the node via nodeName**

- to check the scheduler as the pod may be pending, use below command.
```bash
kubectl get pods --namespace kubesystem
``` and check for the scheduler.

```bash
# to select pods with specific label

kubectl get pods --selector key=value

```


## Taints and Tolerations

- Taint: is a property set on a node that indicates that this node should not accept any pods do not have a matching label "toleration".

- to add a taint to a node, use below command:
```bash
kubectl taint nodes <node-name> key=value:effect



###########IMPORTANT NOTE###########

# Effect can have three values:
1.NoSchedule: pod will never be scheduled on the node unless it has a matching toleration.

2.PreferNoSchedule: the scheduler will try to avoid scheduling pods on the node, but not mandatory.

3.NoExecute: pod will never be scheduled on the node, and all the existing pods that don't have a mathcing toleration on this node will be evicted 
```

- Toleration: is a property set on a pod that indicates that this pod can be scheduled on a node with a matching taint.
- to add a toleration to a pod, use below command:
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-pod
spec:
    tolerations:
    - key: "key"
        operator: "Equal"
        value: "value"
        effect: "NoSchedule"
```

- to remove a taint from a node, use below command:
```bash
kubectl taint nodes <node-name> key=value:effect-
```
- to check the taints on a node, use below command:
```bash
kubectl describe node <node-name> | grep Taints
```

* Pod with toleration is not guranteed to be scheduled on the node with matching taint.

* Master node is tainted by default to prevent scheduling of pods on it.



## Node Selector
- Node selector is a property set on a pod that restricts this pod to the node with a matching label.

- to add a node selector to a pod, use below command:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    nodeSelector:   
        key: value
```

- to check the node selector on a pod, use below command:
```bash
kubectl describe pod <pod-name> | grep Node-Selector
```

## Affinity and Anti-Affinity

- Affinity: is a property set on a pod that is like node selector but with more advanced features like having expressions and operators.

- to add an affinity to a pod, use below command:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    affinity:
    nodeAffinity:
        requiredDuringSchedulingIgnoredDuringExecution:
        nodeSelectorTerms:
        - matchExpressions:
            - key: key
                operator: In
                values:
                - value1
                - value2
```

- Node affinity has two types: `requiredDuringSchedulingIgnoredDuringExecution` and `preferredDuringSchedulingIgnoredDuringExecution`.



## Resource Requests and Limits

- GI vs GB: GI is the binary prefix for gigabyte, which is 1024^3 bytes, while GB is the decimal prefix for gigabyte, which is 1000^3 bytes. In Kubernetes, we use GI for memory resources and GB for storage resources.

- k8s throttles the CPU usage of a pod that exceeds the limit, but it does not throttle the memory usage, hence a pod that exceeds the memory limit will crash.




## Static Pods

- Static pods are pods that are managed directly by the kubelet on a node.

- to create a static pod inside the node, you can add a file of the pod definition under `/etc/kubernates/manifests`.

- this file path can be edited throu

- to view static pods use `docker ps` command or any command that is appropriate to your runtime.

> you **Cannot** create a **pod or a container** using the `docker run` command, as the manager here of static pods is **kubelet** not docker or any container runtime.

- by the side of creating static pods, kubelet provides http api to the kube-api server for creating pods.

- when a static pod is created in a node, if the kubelet is a part of a kubernetes cluster the kubelet creates mirror object of this static pod in the kube-api server, this mirror is a read only version.

- static pods are used to create `Control Plane` components as they aren't depndent on kubernetes.


> Static Pods vs Daemonset
> Daemonsets are created and managed by the `kube-api` server, while static pods are created and managed by the `kubelet` on each node. Static pods are used for critical system components, while daemonsets are used for running a copy of a pod on each node.



## Priority Classes
- are classes used to assign priority to pods, which is used by the scheduler to decide which pod to schedule first.

- priority classes are from -2147483648 to 1000000000, where the higher the value the higher the priority.

- to create a priority class, use below command:
```yaml
apiVersion: scheduling.k8s.io/v1
kind: PriorityClass
metadata:
    name: high-priority
value: 1000000
globalDefault: true # this means that this priority is the default one for all pods that do not have a priority class assigned.
description: "This is a high priority class"

preemptionPolicy: "PreemptLowerPriority" # this means that if a pod with this priority is pending and there are no resources available, the scheduler will preempt (evict) pods with lower priority to make room for this pod.

```



## Custom Schedulers

- k8s allows you to create your own scheduler and use it instead of the default one.

- to create a custom scheduler, you need to create a deployment for the scheduler and then create a config map for the scheduler configuration.
- to create a deployment for the scheduler, use below command:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: custom-scheduler
spec:
    replicas: 1
    selector:
        matchLabels:
            app: custom-scheduler
    template:
    metadata:
        labels:
            app: custom-scheduler
    spec:
        containers:
        - name: custom-scheduler
            image: <scheduler-image>
            command:
            - /custom-scheduler
            args:
            - --kubeconfig=/etc/kubernetes/scheduler-kubeconfig # this is for the scheduler to connect to the kube-api server
            - --config=/etc/kubernetes/scheduler-config.yaml # this is the configuration file for the scheduler
            volumeMounts:
            - name: config-volume
                mountPath: /etc/kubernetes/scheduler-config.yaml
                subPath: scheduler-config.yaml
        volumes:
        - name: config-volume
            configMap:
                name: custom-scheduler-config
```


- to create a config map for the scheduler configuration, use below command:
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
    name: custom-scheduler-config
data:
    scheduler-config.yaml: |
    apiVersion: kubescheduler.config.k8s.io/v1
    kind: KubeSchedulerConfiguration
    clientConnection:
        kubeconfig: /etc/kubernetes/scheduler-kubeconfig
    leaderElection:
        leaderElect: true # this is for the scheduler to elect a leader in case there are multiple replicas of the scheduler, but in our case we have only one replica so it will be the leader.
    profiles:
    - schedulerName: custom-scheduler
        plugins:
        score:
            enabled:
            - name: NodeResourcesLeastAllocated
```


- the other way or the hardcode way is to install the binary of the scheduler in the node and run it specifying the kubeconfig and the configuration files.
- 