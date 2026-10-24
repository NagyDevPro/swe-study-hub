## Kubernetes Cluster Choose

- cloud or on prem
  - `kube-adm` : is very useful for on-prem
  - `GKE`: makes it easy to create a cluster on google cloud.
  - `KOPS`: is a tool that helps you create, destroy, upgrade and maintain production-grade, highly available, Kubernetes clusters from the command line.

  - `AKS`: is a managed kubernetes service provided by Microsoft Azure.

- Choose Storage
  - for high performance choose `ssd-backend storage`
  - for multiple concurrent connections choose `network based storage`


- Notes:
  - Master nodes can host workloads but it is not recommended for production clusters.
  - Nodes can be physical or virtual machines, and they can run on different operating systems, such as Linux or Windows.


## Kubernetes Cluster Installation

- on **linux**, you can install cluster with it's binaries, manually or using `kubeadm` tool.
- on **windows**, you cannot install the cluster binaries, you should use vm or kubeadm tool

- deploying k8s for development or testing.
  -  MiniKube, is a tool to install a single-node kubernetes cluster on your local machine, it also installs the required vm
  -  on the other hand, `kubeadm` can install single or multi nodes but it requires you to install the required vm manually.

- deploying k8s for production.
  - Self-managed solution category: you will deploy by yourself the cluster, vm, monitor and manage the vm and you are required to update the cluster by yourself, ex: openshift, cloud foundry container runtiem ,vgrant for kubernetes,..etc

  - Hosted soltuion: a cloud provider will deploy and manage vms and cluster to you, this is called `Kubernetes-as-a-Service`, ex: GKE, EKS, AKS, DigitalOcean Kubernetes,...etc



## High Availability Cluster

- HA is a cluster that has multiple master nodes, to avoid single point of failure.
- `api Servers` are deployed behind a load balancer, and are deployed in an active-active mode, so both are active and if one was down the other can handle.

- **Controllers** and **Schedulers** are deployed in an active-passive mode, so only one is active to avoid dublications of creation of resources.

- **Etcd** is deployed in multiple topologies:
  - **Stacked Topology**: where the etcd is deployed on the same nodes as the master nodes, this can be risky that if the etcd was down the other components cannot know the state of the cluster, so it is not recommended for production clusters.
  - **External Topology**: where the etcd is deployed on separate nodes, this is complex but it maintains the state of the cluster even one etcd node is down, but it's higher in cost.


- Etcd is a distributed key-value store:-
  - Reads from each node but write to only one node (Leader node).
  - The leader node is elected by `Raft Consensus Algorithm`, and if the leader node was down, a new leader will be elected.

  > Raft Algorithm: https://www.youtube.com/watch?v=IujMVjKvWP4 

  - When we write to the leader node, it will replicate the data to the other nodes (followers) and wait for the majority of nodes(Quorm) to acknowledge the write before it considerd successful
    
    > Quorm: is the minimum number of nodes that must acknowledge a write, or the minimum number of nodes for a cluster to funcation well, it's calculated by (nodesNum /2)+1, flooring the result

  - Etcd better to be odd not even number of nodes, to avoid lost quorm, when we split the cluster into two equal parts, and one part was down, the other part cannot reach quorm and the cluster will be down.

  - one other important thing in odd or even is that to avoid lost brain, when we split the cluster into two equal parts, and one part was down, the other part cannot reach quorm and the cluster will be down.

