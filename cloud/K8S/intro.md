# Kubernates

## Architecture
* Kubernates consists of below
    - Cluster
        - Control plane
        - Worker Nodes

* Cluster can be on multi servers, each component of the cluster can be also on multi servers

### Cluster

#### Control Plane
- **ETCD** : NoSql Database for saving data related to kubernates cluster
- **Scheduler**: Controls (Schedules) the time/priority of creation/deletion of pods.

* Schecduler Selects the nodes suitable for pods creation based on:
    - **Filtering** : filters nodes that have a higher/equal resources for this pod
    - **Scoring** : scores the higher nodes (after filtering) in avialable resources and ranks them

- **Api Server**: the client that enables users to interact with control plane.

* When a new user requests any action on cluster Api Server do the following: -
1. **Authentication** of user that he is inside the cluster.
2. **Autherize** the user that he has the permission.
3. **Admission Controls**:
    1. **Mutation** of the container configs if you predefined some specific configs.
    2. **Validation** of container specs if it valid to the predefined specs that you specified.
4. all the data of the container will be stored in `etcd`
5. scheduler listens on `api server`, if there's any pending container to be created scheduler asks from the api server to provide with `container data` and `worker nodes data` from `etcd` then it selects the suitable node for pod creation.

* when we want to have multi `Api Server` Component on our kubernates cluster all of them must talks to one and only one `etcd` database 

- **Controller Manager**: list of controllers that mantains and monitors the cluster components Ex: Node Controller.

* When a `worker node` disappeared 'unable to send requests to api server', Node controller set it's state in `etcd` to `not ready` waits for `5 minutes(or a perioud of time)` after that controller requests `api sever` to delete all containers related to this node from `etcd`, then another controller requests `api server` to create these containers in another `ready ` worker node,

* the unreaced worker node's `kubelet` in a worker node after the same period of time deletes all of it's `pods` in the same worker node




#### Worker Nodes
- **Kubelet** : Control plane's agent existing on worker node ,which acts as api for contorl plane to communicate with the worker node, responsible for `Join Node to cluster(to be a node)` and `creation of pods` in the worker node 

- **Kube Proxy** : is a component that is responsible for editing the ip table in order to enable `cross-node communication: communication of pods accross multi nodes (or within the same node)`
   

- **Pods**: is the smallest deployable unit in kubernates that can contain one or more containers

* All containers inside a single pod are created under the same `namespace`
* A `pause container` is created by default before the pod cration and it's responsibility is to manage networking and containerize each container inside the pod

* As each pod containers are under the same `namespace` you can call them internally via `lcoalhost`


- **Replicaset** : Ensures that specified number of pods is up and running.

* replica sets can't be created via `imperative way` must be created as `declarative way`

* Replicaset must have `selectors` as it become as tags for the pods created to help replicaset to manage the pods generated under it and it must have `template` to descripe the pods you want to create.


- **Deployment**: the same as the `Replicasets` but have extra functionality for managing pods when `Rolling Updates(Rollouts)` or `Rolling Back`

* Deployment manages `Replicasets` and by the way when you create an object from `Deployment` Kind by default the deployment creates `Replicaset` and the replicaset creates the `pod`, so the `pod` name will be `deployment-<replicasetID>-<podID>`

* Deployment has two strategies in case of `rollupdate` and `rollback`:
    - **Recreate** : Deletes the old replicaset and create a new one.
    - **RollingUpdate**: Creates new replicaset first then it creates pods in the `new` replicaset and deletes pods in the `old` replicaset one by one

* after `updates(Rollouts)` k8s remains old replicasets (which knows as `revision`) (up to a specific number) in case of `rollbacks`.

* this specific number`(of revisions)` can be changed by specifying a number for history or in `.spec.revisionHistoryLimit`.

* you can `view the history of the rollouts via command`

```bash
kubectl rollout history deployment <deployment-name> --revision <version>
```

* to `rollback` a deployment via command.

```bash
kubectl rollout undo deployment <deployment-name> --to-revision <version>
```


## How to create k8s cluster
- **Minikube**: one single node that acts as `control plane` and `worker nodes` at the same time 
- **Kubeadm**
- **Kops**
- **EKS**
- **GKE**
- **Play with k8s(online kubernates cluster)**



## How to create pods
- **Imprative way**: via `kubectl` commands
- **declarative way**: via `yaml` file with the specs of the pod you want to create and applying this yaml.


### Declarative Way (yaml file contnet)
- **Api Version**: refers to the `api` which is begin used to create kubernates object being defined.

* to list all of `api versions` via `Kubectl api-resources | grep <object>` 

- **Kind**: type of the `object` to be created.
- **metadata**: details about this pod.
- **Spec**: all of specifications about this pod(containers, env variables,...etc)

* you can create multi objects in the same file by seprating between each object by `---`

* it's better to edit on the `manifest file` instead of editing of the running the same `object`

* `kubectl get rs <rs-name> -owide` the `-owide` is to get more details about the listed resource.



## Questions to search for

- if kubelet manages all of the worker nodes, and send it's status to the `api server` then why we do need controller manager (or why we need controllers for worker nodes to be specific)

- dig deep into kube proxy

- dig deep into container networking and types of networks (bridge, host, none) + see more about pod namespace

- if i had replicaset that has 3 replicas and the label of this replica set is app: nginx_version however i have another 1 pods with the same meta data aka (app: nginx_version) what will be the actual number of replicas

