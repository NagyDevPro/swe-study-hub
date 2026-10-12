- when a service is created a DNS Entry is automatically created for it in the cluster with the format `<service-name>.<namespace>.svc.cluster.local`. this allows services to communicate each other within the fully cluster not just within the same namespace.


- when a pod is created it also gets a DNS entry with the format `<pod-name>.<namespace>.pod.cluster.local`. this allows pods to communicate with each other within the fully cluster not just within the same namespace.


- when a namespace is created it also gets a DNS entry with the format `<namespace>.svc.cluster.local`. this allows namespaces to communicate with each other within the fully cluster not just within the same namespace.


so why this convention of `<name>.<namespace>.svc.cluster.local` and `<name>.<namespace>.pod.cluster.local`?
This convention allows for a clear and consistent way to identify and access services and pods within the cluster. By including the namespace in the DNS entry, it ensures that there is no ambiguity when multiple services or pods have the same name across different namespaces. It also allows for better organization and management of resources within the cluster, as each namespace can have its own set of services and pods without conflicts. Additionally, this convention follows the standard DNS naming conventions, making it easier for developers and administrators to understand and work with the cluster's networking.




- `.cluster.local` is the default kubernetes domain name for services.
- .svc is the subdomain for the word "service".
- --namespace option
- by default each pod is created in the default namespace, unless you specified the namespace within --namespace or within the manifest file.
- `kubectl config set-context --current --namespace=finance` to set the default namespace for the current context. this way you don't have to specify the namespace every time you run a command.

- `kubectl get pods --all-namespaces` to list all pods across all namespaces.

- ResourceQuota is a Kubernetes resource that allows admins to limit the amount of resources for namespace.

