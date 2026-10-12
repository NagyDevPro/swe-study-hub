# Cluster Maintenance

## Node Eviction
- Node eviction is the process where you gracefully remove the node from the cluster for maintenance.

- by default if a node craches or removed from k8s cluster the cluster waits for 5 minutes before marking this node as unreachable, and scheduing it's pods to other nodes.

- if the applications on this node can tolerant for those 5 minutes, then it's ok, otherwise you must use drain and uncordon commands to evict the pods.

- drain command: used to evict all existing pods from the node and mark this node as unschedulable.
```bash
kubectl drain <node-name> --ignore-daemonsets

# the --ignore-daemonsets flag is used to ignore the daemonset pods, because they will be automatically rescheduled on other nodes.

# if you do not set it, the drain command will fail because it cannot evict the daemonset pods.
```

- uncordon command: used to mark the node as schedulable again after maintenance is done.
```bash
kubectl uncordon <node-name>
```

- cordon command: marks only the node as unschedulable, it doesn't evict the existing pods from the node.
```bash
kubectl cordon <node-name>
```


## kubernetes releases

- by default, kubernetes releases follows the semantic versioning, which is in the format of `v<major>.<minor>.<patch>`.

> **Important**: some components in the cluster may have different versions like coreDNS or KubeProxy, it's because they are not part of the kubernetes core components and have their own releases.

> some componnets in the cluster in core k8s components may have lower versions than the kube-api server, that's because they are not updated yet, but **they mustn't have higher versions than the kube-api server, to make it easy to upgrade the cluster**, otherwise it may cause **compatibility issues**.

> Kubectl can be higher than the kube-api server, because it's just a client.


## Cluster Upgrade

- every new version of kubernetes, kubernetes supports the previous two minor versions, for example if the latest version is 1.27, then kubernetes supports up to 1.25.

- when you upgrade the cluster, it's better to upgrade one minor version at a time, for example if you are on 1.25 update to 1.26 first, then update to 1.27.

> when you upgrade the control plane, this is means that the applications running on worker nodes will not be affected, but you cannot manage or access the cluster until the control plane is upgraded.


* **Nodes Update Stratgies**
    - Recreate all: in this strategy, you delete all worker nodes and update them.
    - Rolling Update: in this strategy, you update the nodes one by one.
    - replace: in this strategy you create new nodes with new version and delete the old nodes.


- Velero by HiptIO is a tool that can help you to get a backup of your cluster.

- to get backup of your etcd data you can use etcdctl tool command `snapshot save <backup-file-name>` to get a backup of your etcd data, and `snapshot restore <backup-file-name>` to restore the backup.

```bash
etcdctl snapshot save snapshot.db
```
- to view the metadata of the backup(size,timestamp,...etc) you will use below command:

```bash
etcdctl snapshot status snapshot.db
```

- when you restoring the etcd backup, it creates a new etcd data directory with new cluster configuration and new cluster members, this is to avoid any conflicts with the existing cluster members, and to prevent any non-cluster member from joining the cluster with the same cluster configuration.


