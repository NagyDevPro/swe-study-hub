# Kubernetes Application Life Cycle

* the command `command: ` replaces the `ENTRYPOINT` instruction in the Dockerfile, and the `args: ` replaces the `CMD` instruction in the Dockerfile.

- if you mounted the secret as a file, each key in the secret will be a file in the container, and the content of the file will be the value of the key in the secret, kyes are stored under the path /opt/secret/ in the container.

- Storing data at-rest

* **at-rest** means data that is stored on disk.
* in encrypion at-rest config yaml file, the order of the providers is important because the first provider to encrypt the data.

- resource about storing data at-rest:
  - [Encryption at Rest](https://kubernetes.io/docs/tasks/administer-cluster/encrypt-data/)


the way Kubernetes handles secrets. Such as:

A secret is only sent to a node if a pod on that node requires it.

Kubelet stores the secret into a tmpfs so that the secret is not written to disk storage.

Once the Pod that depends on the secret is deleted, kubelet will delete its local copy of the secret data as well




## Multiple Container in a Pod
- When a Pod has multiple containers, they share the same network namespace and can communicate with each other.

- Multiple container design patterns:-
  - Colocate container: is the two containers are tightly coupled and need to share the same resources.
  
  - Sidecar container: is the two containers are loosely coupled and one container provides a service to the other container, such as logging, monitoring,...etc, **the sidecar container always runs before the main container and runs alongside the main container**.
  
  > the main difference between the colocate container and the sidecar container is that the sidecar container always runs before the main container, and the colocate container can run independently

  > the other difference is that the sidecar container is used to provide a service to the main container, while the colocate container is used to share resources with the main container.

> **Important**: Kubernetes supports self-healing applications through ReplicaSets and Replication Controllers. The replication controller helps ensure that a POD is re-created automatically when the application within the POD crashes. It helps in ensuring enough replicas of the application are running at all times.

> Kubernetes provides additional support to check the health of applications running within PODs and take necessary actions through Liveness and Readiness Probes. However, these are not required for the CKA exam and as such, they are not covered here. These are topics for the Certified Kubernetes Application Developers (CKAD) exam and are covered in the CKAD course


## Auto Scaling
- We have two types of auto-scaling in Kubernetes:
  - Horizontal Pod Scaling: it's about increasing the number of pods in a deployment, replica set, or statefulset.
  
  - Vertical Pod Scaling: it adjusting the resource limits and requests for containers in a pod.

- in kubernetes, we can have cluster scaling by the following.
  - Horizontal: by adding more nodes and using the `kubeadm join` command to add the new nodes to the cluster.

  - Vertical: by increasing the resource of the existing nodes.

- to automate the cycle we can use the following

  - Pod Level: by using Horizontal pod autoscaler (HPA) which automatically scales the number of pods in a deployment based on selected metrics, and Vertical Pod Autoscaler (VPA).

  > in pods you can use KEDA, if you want to scale based on custom events, such as messages in a queue or custom metrics.

  - Cluster Level: by using Cluster Autoscaler which automatically adjusts the size of the cluster based on the resource requests of the pods.

> When you scale a deployment to a higher number of replicas than the cluster can support due to resource constraints, Kubernetes will create as many replicas as possible within the available resources. The remaining replicas will be in a pending state until sufficient resources are freed up or added to the cluster


> **Important**: If the status of the Horizontal Pod Autoscaler (HPA) target is <UNKNOWN>/80, it typically means that the HPA is unable to retrieve the current metrics for the specified target. Run kubectl describe hpa nginx-deployment to find more details.


## in-place pod resizing
- a feature that allows you to update the resource requests and limits of running pod without restarting it.

> by default it's not enabled in k8s v1.32 till now.

- you can enable it by the following command:
```bash
kubectl alpha feature-gates InPlacePodVerticalScaling=true
```

- once you enabled it, you can update the resource requests and limits of a running pod in yaml by the following policy added to your manifest:-
```yaml
spec:
  containers:
  name: <container-name>
  resizePolicy:
    - resourceName: cpu
      restartPolicy: NotRequired
    - resourceName: memory
      restartPolicy: RestartContainer
```

- limitations of in-place pod resizing:
  - only cpu and memory resources can be resized.
  
  - Immutable QoS Class: A Pod's Quality of Service (QoS) class (Guaranteed, Burstable, or BestEffort) is determined at creation and cannot be changed by resizing.Example: A "Burstable" Pod cannot become "Guaranteed" even if you resize its requests to match its limits

  - No Resource Removal: You can change the values of requests and limits, but you cannot completely remove them once they are set
  
  - Pods managed by static CPU or Memory Manager policies generally cannot be resized in-place


## Vertical Pod Autoscaler (VPA)
- A vertical pod autoscaler automatically 


- vpa doesn't come built in such like HPA, you must apply the vpa manifest to your cluster before you can use it, via below command:
```bash
kubectl apply -f https://github.com/kubernetes/autoscaler/releases/latest/download/vertical-pod-autoscaler.yaml
```
after installing the vpa you will have three new custom resource definitions (CRDs) in your cluster:-
- VPA recommender: responsible for monitoring resouce via metrics server and providing recommendations for requests and limits.

- VPA updater: responsible for **evicting** the pods with outdated limits and requests(needs to be updated) based on the recommendations provided by the `recommender`.


- VPA admission controller: responsible for creation of pods with the recommended requests and limits provided by the `recommender`.

to create a VPA object, you can use the following manifest:-
```yaml
apiVersion: autoscaling.k8s.io/v1
kind: VerticalPodAutoscaler
metadata:
  name: my-vpa
spec:
  targetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: my-deployment
  updatePolicy:
    updateMode: "Auto"
  resourcePolicy:
    containerPolicies:
    - containerName: "*"
      minAllowed:
        cpu: 100m
        memory: 200Mi
      maxAllowed:
        cpu: 1
        memory: 1Gi
      controlledResources: ["cpu", "memory"]
```


we have 4 update modes for VPA:-
- Off: VPA just will recommend without taking any actions.

- initial: only change the requests and limits for the newly created pods.

- recreate: recreate the pods with new requests and limits.

- auto: at the current time, it's the same as recreate, but in the future it will be able to update the requests and limits inPlace without recreation.


> **Important**: when you have one replica in your deployment, the vpa updater tries to evict it and k8s prevents it from eviction because it will cause downtime, so the vpa will not be able to update the requests and limits causing a problem in the vpa updater.

