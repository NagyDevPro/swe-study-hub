- to list all of the labels in specified namespace: `kubectl get pods -n <namespace> --show-labels`

- to list the pods with their nodes: `kubectl get pods -o wide`



- to get the resource in the cluster `kubectl get <resource> --all-namespaces`

- `ps -aux | grep kubelet` to check the kubelet process on the node, or by checking the file /var/lib/kubelet/config.yaml which is the default location for kubelet configuration file.



- one way to ensure that the static pod is running is to check the kubelet logs on the node, use below command:
```bash
journalctl -u kubelet -f
```

- one way to differentiate between static and non-static pods is to check the owner reference of the pod, use below command:
```bash
kubectl get pod <pod-name> -o jsonpath='{.metadata.ownerReferences[0].kind}'

kubectl describe pod <pod-name> | grep "Owner References"

kubectl get pod <pod-name> -o yaml | grep "ownerReferences"
```



You can compare the priority classes on both pods using the following command:

```bash
kubectl get pods -o custom-columns="NAME:.metadata.name,PRIORITY:.spec.priorityClassName"
```


- `kubectl get events -o wide` to check the events in the cluster, and check for any events related to scheduling or resource constraints.




- to check the logs of specific pod, use below command:
```bash
kubectl logs -f <pod-name> # the -f option is used for live streaming of the logs.

# to check the logs of specific container in a pod, use below command:
kubectl logs -f <pod-name> -c <container-name>
```


- to view the status of the rollout of a deployment, use below command:
```bash
kubectl rollout status deployment/<deployment-name>
```

- to view the history of the rollout of a deployment, use below command:
```bash
kubectl rollout history deployment/<deployment-name>
```
- to view the rolling update strategy of a deployment, use below command:
```bash
kubectl get replicasets -o wide
```
the above command will show you the current and the previous replica sets.

- to rollback to the previous version of a deployment, use below command:
```bash
kubectl rollout undo deployment/<deployment-name>
```

- to add many args while creating a pod, you can use the following command:
```bash
kubectl run <pod-name> --image=<image-name> --command -- <arg1> <arg2> <arg3>
```

- to create a configmap without using file, you can use the following command:
```bash
kubectl create configmap <configmap-name> --from-literal=<key>=<value>
```
* the --from-letral is an option that allows you to add key-value pairs directly in the command line.

- to create a configmap from a file, you can use the following command:
```bash
kubectl create configmap <configmap-name> --from-file=<file-path>
```
* note that the format of the file should be in key-value pairs, where the key is the name of the config and the value is the content of the config.

- to view configmaps in a specific namespace, you can use the following command:
```bash
kubectl get configmaps -n <namespace>
```


- to autoscale a deployment, you can use the following command:
```bash
kubectl autoscale deployment <deployment-name> --min=<min-replicas> --max=<max-replicas> --cpu-percent=<target-cpu-utilization>
```
- to create hpa for a deployment, you can use the following command:
```bash
kubectl create hpa <hpa-name> --target-deployment=<deployment-name> --min=<min-replicas> --max=<max-replicas> --cpu-percent=<target-cpu-utilization>
```

- the `--watch` option in the `kubectl get` command allows you to continuously monitor the specified resource and see real-time updates as they occur. For example, you can use `kubectl get pods --watch` to watch for changes in the pods in the cluster.

- to get the events of any resource, you can use the following command:
```bash
kubectl get events --field-selector involvedObject.name=<resource-name>
or 
kubectl get events resource <resource-name> | grep -i <event-name>
```


- to checkout the release of your server, use below command:
```bash
cat /etc/*release*
```

- to get all resources in the cluster, use below command:
```bash
kubectl get all --all-namespaces -o yaml > resources.yaml
```

- to check the cni configuration on the node, use below command:
```bash
cd /etc/cni/net.d
```
