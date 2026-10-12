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


## Core DNS

- for each pods to have services names each pod must have `/etc/resolve.conf` file passed to it

- a main dns server kubernetes deploy in order to handle dns and routing

- before 1.12 the dns solution kubernetes implements(kubenetes deploys) is kube-dns

- now kubenetes utilizes core-dns solution for dns server

- core-dns have many plugins that responsable for resolution of ip, caching, routing,...etc

- core-dns pod runs executable `./coredns` the same when we installed it, core-dns needs config file it's location inside `/etc/coredns/Corefile`

below is an example of the config file with explaination of each line:
```bash
# CoreDNS config file
.:53 {
  error
  health
  ready


  # this plugin is responsible for resolving DNS queries for services and pods in the cluster. It uses the Kubernetes API to look up the IP addresses of services and pods based on their names.
  kubernetes cluster.local in-addr.arpa ip6.arpa {
    pods insecure # this configuration option allows coredns to resolve pod by adding dashes

    fallthrough in-addr.arpa ip6.arpa
  }

  prometheus :9153

  # proxy plugin is responsible for forwarding DNS queries to upstream DNS servers, dns server cannot resolve the record it is forwarded to the upstream servers specified here
  proxy . /etc/resolv.conf

  cache 30
}
```

- the coreFile is passed to the core-dns pod via configmap, you can view it by running the below command:
```bash
kubectl get configmap coredns -n kube-system -o yaml
```

- core-dns have a service named `kube-dns` which is a clusterIP, if any new pod was created it will get the ip of this service and it's added to pod's `/etc/resolv.conf` file as the first nameserver, so any dns query will be sent to this service and it will resolve it for the pod.


- kubelet is responsible for setting the `kube-dns` ip to each new pod is the `kubelet` component, as it adds the cluster dns inside the `namespace` field in the `/etc/resolv.conf` file.

- the `/resolve.conf` file inside each pod have `search` entry for below.
```bash
search <namespace>.svc.cluster.local svc.cluster.local cluster.local
```





