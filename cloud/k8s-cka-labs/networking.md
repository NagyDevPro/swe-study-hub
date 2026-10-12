# Networking


## Services Networking

- Services are virtual IPs that are used to expose a set of pods as a network service.

- **Service Types:-**
  - ClusterIP: Exposes the service on a cluster-internal IP. This makes the service only reachable from within the cluster.

  - NodePort: Exposes the service on each Node's IP at a static port. This makes the service reachable from outside the cluster using the Node's IP and the assigned port.

  - LoadBalancer: Exposes the service via a cloud provider's load balancer. This makes the service reachable from outside the cluster using a public IP address.
  - ExternalName: Maps the service to an external DNS name. This allows you to reference an external service by its DNS name within your cluster.


- When services are created by `kubeapi-server`, the `kube-proxy` component running on each node watches the API server for changes, and updates it's rules to reflect the new services Ip within each pod.

- Kube-proxy rules can be different acrodding to it's mode
- Kube proxy modes:-
  - userspace: kube-proxy watches the API server for new services and endpoints, and opens a port on the node for each service. When a request is made to the service, kube-proxy forwards the request to one of the endpoints using round-robin load balancing.

    - iptables: kube-proxy watches the API server for new services and endpoints, and updates the iptables rules on the node to reflect the new services. When a request is made to the service, iptables forwards the request to one of the endpoints using round-robin load balancing.
    
    - IPVS: kube-proxy watches the API server for new services and endpoints, and updates the IPVS rules on the node to reflect the new services. When a request is made to the service, IPVS forwards the request to one of the endpoints using round-robin load balancing.


- when new service is created, it gets it's ip from `--service-cluster-ip-range` flag in the `kube-apiserver` component by the below command:
```bash
kube-apiserver --service-cluster-ip-range IpNet (CIDR) # the default value is 10.0.0.0/24
```

you can view this range by running the below command:
```bash
ps aux | grep kube-apiserver
# you will see it under range=<cidr>

# if you get an error here you can use below
cat /etc/kubernetes/manifests/kube-apiserver.yaml | grep service-cluster-ip-range
```

> **CIDR IP of the kube-apiserver must not conflict with the kubernetes pods ip range meaning a pod and services mustn't have the same IP**

- in iptables mode, to view the rule when you create a service via below command
```bash
iptables -L -t nat | grep <service-name>

# You can also view it by the below command
cat /var/log/kube-proxy.log

# Note that the location of this file can vary depending on the way of your installation
```


## DNS Resolution in Kubernetes

- when you setup a cluster kubernets by default set up a dns 
 
- DNS resolution in kubernetes is handled by the `kube-dns` or `coredns` service, which is a cluster add-on that provides DNS resolution for services and pods in the cluster.

- for services when a service is created you can call it with it's name.

- if you want to call it from the same namespace you can use the fully qualified domain name (FQDN) of the service, which is in the format of `<service-name>.<namespace>.svc.cluster.local`.

- the kube-dns server is groups each service with it's name followed by grouping them by adding namespace so you will find .<namespace-name> then it groups them by the object so you will find the same under service(utilizing .<svc>) finally it groups all domains by cluster name `.cluster.local`


- for pods, the kube server replace it's ip octets with dashes and add the pod name and namespace name to the front of it, so you can call it with it's FQDN in the format of `<pod-ip-dashes>.<namespace>.pod.cluster.local`


