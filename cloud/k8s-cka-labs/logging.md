# Logging and Metrices

## Metrics

- by default kubernetes does not have a built-in metrics server.

- instead you can rely on a third-party metrics server like `metrics-server` which is a cluster-wide aggregator.

> **Metrics-server** vs **Heapster**
> `Metrics-server` is a lightweight and efficient metrics server designed for production use for kubernetes,
> while `Heapster` is an older and more feature-rich metrics server that is no longer actively maintained.


> to deploy metrics-server you can use the following command:
```bash
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

# or
git clone https://github.com/kubernetes-sigs/metrics-server.git
cd metrics-server
kubectl apply -f ./deploy/standalone/components.yaml
```

- in order to install metrics server you clone it from repository then you use kubectl deploy.

after deployment you can view metrics via below two commands
```bash
kubectl top node # to view the nodes usage

kubectl top pod # to view pods usage
```

