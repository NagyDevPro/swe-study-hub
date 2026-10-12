# Secuirty


## Authentication
- In k8s users can be categorized into two types:-
  - **Human users**: Such as developers, administrators to the cluster.


  - **Service accounts**: Special accounts used by applications that integrates with the kubernetes API.


    
- kubernetes kube-apiserver manages the authentication of users and service accounts.
- Kubernetes manages the service accounts(creation, delettion and token management) but it doesn't manage the human users, it relies on external identiy providers(LDAP, CERTS, Tokenfiles, etc) to manage the human users.

  **Notes:-**

    * Static Token file: a file (often in .csv format) that contains contents about the users, groups assigned to them and the tokens used for authentication, you pass this file to kube-apiserver using the `--token-auth-file` flag.
    
    > Static token file is not the recommended way for authentication in production.


## TLS Certificates



* the private keys always have the extension `.key` or `-key.pem` and the public keys have the extension `.crt` or `.pem`.

* in kubeadm to view all certificates, you can view their location manually, via `cat /etc/kubernetes/manifests/kube-apiserver.yaml`

* to decode a certificate, you can use the following command:-
```bash
openssl x509 -in <certificate-file> -text -noout
```




## Certificates API

* all of kubernetes certificates operations are managed by the Controller Manager, and the API for managing certificates is called Certificates API.


- to generate a certificate signing request, you can use the following command:-
```bash
openssl req -new -newkey rsa:2048 -nodes -keyout <key-file> -out <csr-file> -subj "/CN=<common-name>/O=<organization>"
```

- after generating the csr file, you can create a certificate signing request resource in k8s using the following command:-
```bash
kubectl create -f <csr-file>
```

- certificate signing request resource has the below yaml files.
```yaml
apiVersion: certificates.k8s.io/v1
kind: CertificateSigningRequest
metadata:
  name: <csr-name>
spec:
  request: <base64-encoded-csr-file>
  signerName: <signer-name>
  usages:
  - <usage1>
  - <usage2>
```


- to approve a certificate signing request, you can use the following command:-
```bash
kubectl certificate approve <csr-name>
```

- to view the status of a certificate signing request, you can use the following command:-
```bash
kubectl get csr <csr-name> -o yaml
```





## KubeConfig

- kubeconfig is a file that contains the information about the cluster, users and contexts, it is used by kubectl to connect to the cluster.

* the kubeconfig file is usually located at `~/.kube/config` and it doesn't require you to create it as you create normal pods/deployments/etc, you just edit this file and it's read by the kubectl utility.


- kubeconfig file has the below yaml structure:-
```yaml
apiVersion: v1
kind: Config
current-context: <context-name> # the context that kubectl will use by default
clusters: # here is the clusters definition with the certificates
- cluster:
    certificate-authority-data: <base64-encoded-ca-cert>
    server: <api-server-endpoint>
  name: <cluster-name>

contexts: # compination of users and clusters to define which cluster to which user
    - name: <context-name>
      context:
        cluster: <cluster-name>
        user: <user-name>
        namespace: <namespace-name> # optional, if not specified, the default namespace will be used

users:
  - name: <user-name>
    user:
      client-certificate-data: <base64-encoded-client-cert>
      client-key-data: <base64-encoded-client-key>
```

- to view the kubeconfig file, you can use the following command:-
```bash
cat ~/.kube/config

# Or

kubectl config view
```

- you can specify a different kubeconfig file using the `--kubeconfig` flag, for example:-
```bash
kubectl get pods --kubeconfig /path/to/your/kubeconfig
```

- you can change the current context using the following command:-
```bash
kubectl config use-context <context-name>
```

- if you have multiple namespaces in your cluster, you can set the default namespace for a context using the following command:-
```bash
kubectl config set-context <context-name> --namespace=<namespace-name>
```


* certificates in kubeconfig file better to be the full path file, if you do not specify the file, you can use the property `client-certificate-data` **Making sure that the data is base64 encoded**


## Kube Api Groups
- kubernetes apis are specified to the below
  * health: to view the health of the cluster
  * metrics: to view the metrics of the cluster
  * authentication: to view the authentication of the cluster
  * api: the core api that contain all of the core resources such as pods, service, volumes,...etc
  * apis: more categorized than the core api, it contains all of the resources and advanced features like app(deployments, replicasets,...etc), batch(jobs, cronjobs,...etc), networking(ingress, network policies,...etc), storage(pv, pvc,...etc) and more.


- to view all of the api groups in the cluster, you can use the following command:-
```bash
kubectl api-resources
```

> **to access the kubernetes api, you should specify the --cert, credentials.**

> **An alternative way for this is to launch **Kubectl proxy** which is a serivce that start locally on 8081 and use your credientials from kubeconfig file to access api, example:-**
```bash
kubectl proxy --port=<specify the port if there's other>
curl http://localhost:8081/api/v1/nodes -k
# and it works
```


## Authorization

- kubernetes uses many mechanizms for authorization, such as:

* **Node Authorizer**: is a layer of authorization that is used to authorize nodes accessing the kubeapi-server and defines each node should get what and it is enabled by default in kubeadm.

> **as we know that each node in the cluster requests from api server to get list of pods that should be running on it, and it's reporting its status to the api server.**

* **ABAC**: Attribute Based Access Control, is a mechanism that is used to grant user or some of users some permissions
   
   - Abac is done by creating a policy file that contains user or group and the permissions that they have, to create a policy file, you can use the following command:-
    ```yaml
    apiVersion: abac.authorization.kubernetes.io/v1
    kind: Policy
    spec:
      user: <user-name> # or group: <group-name>
      namespace: <namespace-name> # optional, if not specified, the default namespace will be
      resource: <resource-name>
      apiGroup: <api-group-name>
      verb: <verb-name> # such as get, list, create, update, delete
  ```

* **RBAC**: Instead of making permissions for each user or group, you create role that contains permissions and assign users to it.

* **Webhook**: delegate the authorization to an external service or third party, and the api server will send a reqeust to this service to check the authorization of the user.

* **AlwaysAllow**: by default, it allows all requests without checking about it.

* **AlwaysDeny**: not the default, it denies all requests without checking about it.

> **if you specify more that authorization mode, k8s will take them by order and if there's any denial, then the request will be forwarded to the next authorization mode, by default if you didn't specify any authorization mode under the flag `--authorization-mode=` in kubeapi-server, it will use the AlwaysAllow mode**



## RBAC

- RBAC is a mechanism that is used to grant user or some of users some permissions by creating roles and role bindings.
- to create a role, you can use the following command:-
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: <role-name>
  namespace: <namespace-name> # optional, if not specified, the default namespace will be used
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["pods"] # specify the resource that you want to grant permissions for
  verbs: ["get", "list", "watch"] # specify the permissions that you want to grant
```


- to create a role binding, you can use the following command:-
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: <role-binding-name>
  namespace: <namespace-name> # optional, if not specified, the default namespace will be used
subjects:
- kind: User # or Group
  name: <user-name> # or group-name
  apiGroup: rbac.authorization.k8s.io # required if kind is User or Group
roleRef:
  kind: Role # or ClusterRole
  name: <role-name> # or cluster-role-name
  apiGroup: rbac.authorization.k8s.io # required if kind is Role or ClusterRole
```


- if you are a user and you want to checkout your permissions, you can use the following command:-
```bash
kubectl auth can-i <verb> <resource> --namespace=<namespace-name>
```

if you are admin you can check the permissions of a user by using the following command:-
```bash
kubectl auth can-i <verb> <resource> --as=<user-name> --namespace=<namespace-name>
```

- to get all roles by the following command:-
```bash
kubectl get roles --all-namespaces
```

- to get all role bindings by the following command:-
```bash
kubectl get rolebindings --all-namespaces
```

- to see all namespaced resources run the following command:-
```bash
kubectl api-resources --namespaced=true
```
- to see all cluster resources run the following command:-
```bash
kubectl api-resources --namespaced=false
```


>*** be back to lab  cluster role


## Service Account

- service account is a special type of account that is used by applications to access the kubernetes API.

- when you first setup the cluster a service account named default is automatically attached to each namespace, and when you create new pod the service account is automatically attached to it, and mounts it's token under `/var/run/secrets/kubernetes.io/serviceaccount` and this token is used by the application to access the kubernetes API.

> the previous process is done because each of pods need to access or ask kube-api server about some data so it better to have service account and token inside for authorization with kubernetes, **Example: if a pod need to know specific service url and send data to it**

- to create a new service account use below command:-
```bash
kubectl create serviceaccount <service-account-name> --namespace=<namespace-name>
```

- to view the service accounts in the cluster, you can use the following command:-
```bash
kubectl get serviceaccounts --all-namespaces
```

- to view the details of a service account, you can use the following command:-
```bash
kubectl describe serviceaccount <service-account-name> --namespace=<namespace-name>
```

- service account yaml file has the below structure:-
```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: <service-account-name>
  namespace: <namespace-name> # optional, if not specified, the default namespace will be used
automountServiceAccountToken: true # optional, it's enabling us to automatically mount the service account token, the default value is true

```

> **automountServiceAccountToken can be defined either in the service account level(meaning no token will be mounted for any pod associated with this service account) or in the pod level(meaning no token will be mounted for this pod specifically).**

- to attach a service account to a pod, you can use the following command:-
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: <pod-name>
  namespace: <namespace-name> # optional, if not specified, the default namespace will be used
spec:
  serviceAccountName: <service-account-name> # specify the service account name that you want to attach to the pod
  containers:
  - name: <container-name>
    image: <container-image>
```


- to create a toke for a service account to use it externally, you can use the following command:-
```bash
kubectl create token <service-account-name> --namespace=<namespace-name>
```

- by default the token duration is 1 hour, you can specify the duration of the token using the `--duration` flag, for example:-
```bash
kubectl create token <service-account-name> --namespace=<namespace-name> --duration=24h
```


## Image Security

- each docker image consists of these three parts:-
  - **Image Name**: the name of the image, example: nginx, redis,...etc.
  - **Image Tag**: the tag of the image, example: latest, 1.19,...etc.
  - **Image User** the user who published this image, example: dockerhub, gcr,...etc.
  - **Image Registry**: the registry that contains the image, example: docker.io, gcr.io,...etc.

  example:
```yaml
image: docker.io/nginx:latest
```
- if you have a private registry, in the docker level you can login to this registry using the following command:-
```bash
docker login <registry-url> -u <username> -p <password>
then you can pull the image using the following command:-
docker pull <registry-url>/<image-name>:<image-tag>
```

- in pod level, you can specify the image pull secret to pull the image from the private registry, example:-
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: <pod-name>
  namespace: <namespace-name> # optional, if not specified, the default namespace will be used
spec:
  containers:
  - name: <container-name>
    image: <registry-url>/<image-name>:<image-tag>
  imagePullSecrets:
  - name: <secret-name> # specify the name of the secret that contains the credentials for the private registry, note this secret is created by you in the same namespace of the pod

```

> *** read more about editing on deployments and pods and which sections are immutable and which sections are mutable.



## Security Context

- in docker the containers share the kernel with the host(host of the docker and daemon sets and some of systems that runs docker itself)
- if you list all of the processes inside the container itself you will find it with different process id than if you listed processes from the host itself

- Docker uses linux caps to limit the capabilities of the containers, so the root user inside the container is not the root user in the host.

- you can use `--cap-add` and `--cap-drop` flags to add or drop capabilities for the container, example:-
```bash
docker run --cap-add=NET_ADMIN --cap-drop=SYS_ADMIN <image-name>

docker run --privileged <image-name> # to give all capabilities to the container
```

- to set the security context for a pod or container, you can use the following yaml format:-
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: <pod-name>
  namespace: <namespace-name> # optional, if not specified, the default namespace will be used
spec:
  securityContext:
    runAsUser: <user-id> # specify the user id that the (pod) will run as, if not specified, the default user id is 0(root)
  containers:
  - name: <container-name>
    image: <container-image>
    securityContext:
      runAsUser: <user-id> # specify the user id that the container will run as, if not specified, the default user id is 0(root)
      capabilities:
        add: ["NET_ADMIN"] # specify the capabilities that you want to add to the container
        drop: ["SYS_ADMIN"] # specify the capabilities that you want to drop from the container
```

> **if you specify two users one inside the pod level and the other inside the container level, the container level will override the pod level.**


- to view the user id that the container is running as, you can use the following command:-
```bash
kubectl exec <pod-name> --namespace=<namespace-name> -- id -u

# or
kubectl exec <pod-name> --namespace=<namespace-name> -- whoami
```




## Network Policy

- each request has two types of traffic:
  - egress: is where the request originated from the pod you are in
  - ingress: is where the reuqest originated from the requester and you are the reciever.

> **keep in your mind that traffic is typed egress or ingress regarding to the origination of the request, doesn't matter the response is back.**


- network policy: is a k8s resource that defines policy of the allowed ingresses or egress on a pod.

example of network policy file:-
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: <network-policy-name>
  namespace: <namespace-name> # optional, if not specified, the default namespace will be used
spec:
  podSelector: # specify the pods that this network policy will be applied to, if not specified, it will be applied to all pods in the namespace
    matchLabels:
      app: <app-name> # specify the label of the pods that this network policy will be applied to
  policyTypes:
  - Ingress # specify the type of the policy, it can be either Ingress or Egress or both
  - Egress
  ingress: # specify the ingress rules, if you specified Ingress in policyTypes
  - from:
    - ipBlock:
        cidr: <cidr-block> # specify the cidr block that is allowed to access the selected pods
        except:
        - <except-cidr-block> # specify the cidr block that is not allowed to access the selected pods, if you specified except without cidr, it will not allow any traffic from any source

    - podSelector:
        matchLabels:
          app: <app-name> # specify the label of the pods that are allowed to access the selected pods
    ports:
    - protocol: TCP
      port: <port-number> # specify the port number that is allowed to access the selected pods
      
      namespaceSelector:
        matchLabels:
          name: <namespace-name> # specify the label of the namespace that is allowed to access the selected pods, if you specified namespaceSelector without podSelector, it will allow all pods in the specified namespace to access the selected pods


  egress: # specify the egress rules, if you specified Egress in policyTypes
  - to:
    - podSelector:
        matchLabels:
          app: <app-name> # specify the label of the pods that are allowed to be accessed by the selected pods
    ports:
    - protocol: TCP
      port: <port-number> # specify the port number that is allowed to be accessed by the selected pods
```

> **network policies are supported in network solutions such as: kube-router,calico,romana and not supported on flannel.**

> **if you used network policy and your network solution doesn't support it, it will not block any traffic, meaning it will not be forced.**


> **if we have two network policies like the above example (podSelector, IpBlock) then it works like OR, meaning each of the rules will be applied, otherwise if you have two roles inside (PodSelector,namespaceSelector) then it works like AND, meaning both of the rules should be applied.**

> **Network policies are additive, so if you defined multiple network policies, they will be combined together.**

> **Network polices are applied for protocols such as TCP, UDP. any other protocol is unsupported.**

> **when you create a new network policy, it first handled by network plugin, if any pod that is affected by the policy was created before the plugin finishes, the policy will not be applied to that pod until the network plugin finishes handling**

> **Cluster ingress and egress mechanisms often require rewriting the source or destination IP of packets. In cases where this happens, it is not defined whether this happens before or after NetworkPolicy processing, and the behavior may be different for different combinations of network plugin, cloud provider, Service implementation, etc.**

> 




## Custom Resouce
- each resource of kubernetes has its own api group, for example: pods, services,...etc are in the core api group.

- each resource has its own controller that is responsible for managing the lifecycle of this resource, managing life cycle is about two sections (making necessary changes to the resource and monitor the custer to make sure about the status of the object created of this resource)
  - example: if you have made `kubectl create deployment` the deployment controller will be responsible for creating the deployment and monitor it to make sure that the desired state is achieved.

- to create your own custom resouce you at first must define k8s about the specification of this resource, hence the custom resource definition (CRD) is the resource that defines the specification of the custom resource, view below yaml.

```yaml
apiVersion: apiextensions.k8s.io/v1
kind: CustomResourceDefinition
metadata:
  name: <custom-resource-name> # the name of the custom resource, it should be in the format of <plural>.<group>
spec:
  group: <group-name> # the api group of the custom resource, it should be the same as the group in the name
  versions:
  - name: <version-name> # the version of the custom resource, example: v1, v1beta1,...etc
    served: true # specify if this version is served by the api server
    storage: true # specify if this version is used for storing the custom resource in etcd
  scope: Namespaced # specify the scope of the custom resource, it can be either Namespaced or Cluster
  names:
    plural: <plural-name> # the plural name of the custom resource, used when you make `kubectl api-resources`
    singular: <singular-name> # the singular name of the custom resource, used when you make `kubectl get <singular-name>`

    kind: <kind-name> # the kind name of the custom resource, it should be in CamelCase format and start with a capital letter, used in the definition yaml file
  
  schema: # defines the spec of the resource (typically is what you write in your yaml file)
    openAPIV3Schema: # the schema of the custom resource, it should be in the format of OpenAPI v3.
      
      type: object
      properties:
        spec:
          type: object
          properties:
            field1:
              type: string
            field2:
              type: integer

```

- to view all custom resource definitions in the cluster, you can use the following command:-
```bash
kubectl get crd
```

- to describe specific custom resource definition, you can use the following command:-
```bash
kubectl descirbe crd <custom-resource-name>
```


## refrences to be read later:-

1. kubectx and kubens: two tools that are used to switch between contexts and namespaces easily, source:-
https://github.com/ahmetb/kubectx

2. opereator hub source:-
