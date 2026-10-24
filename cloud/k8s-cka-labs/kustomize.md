# Kustomize

## Kustomize vs Helm
- Kustomize and Helm are both tools for managing Kubernetes resources, but they have different approaches and use cases.

- Helm is a package manager for Kubernetes that allows you to define, install, and manage applications using Helm charts. Helm charts are pre-configured templates that can be customized with values provided by the user.

- Kustomize, on the other hand, is a tool that allows you to customize Kubernetes resource configurations without the need for templates. Kustomize uses a declarative approach to manage resources by applying transformations to base resources.

- Helm is more suitable for managing complex application but helm adds complication on learning, while kustomize is more readable than helm but it has limitation and less features than helm like package management,...etc.


## Installation
- kustomize is built in kubectl, but you sometimes need to install kustomize as kubectl might not have the latest version of kustomize cli.

- to install kustimize by the following
```bash
curl -s "https://raw.githubusercontent.com/kubernetes-sigs/kustomize/master/hack
/install_kustomize.sh"  | bash
```

## Kustomize Structure
- Kustomize is a tool that allows you to customize Kubernetes resource configurations. The structure of a Kustomize project is as follows:

```
my-kustomize/
├── kustomization.yaml  # The kustomization file that defines the resources and transformations
├── base/               # The base resources
│   ├── deployment.yaml
│   ├── service.yaml
│   └── ingress.yaml
└── overlays/           # The overlays that customize the base resources
    ├── dev/
    │   └── kustomization.yaml
    └── prod/
        └── kustomization.yaml
```

## Kustomization file
- Kustomization file is the main configuration file for Kustomize. It defines the resources and transformations that will be applied to the base resources. The kustomization.yaml file can include the following fields:

  - `resources`: This field specifies the list of resource files that will be included in the customization.

  - `patchesStrategicMerge`: This field specifies the list of patch files that will be applied to the base resources using strategic merge.

  - `configMapGenerator`: This field specifies the list of config maps that will be generated from the specified files or literals.

  - `secretGenerator`: This field specifies the list of secrets that will be generated from the specified files or literals.

  - `vars`: This field specifies the list of variables that can be used in the customization.


## Notes

- `kustomize build` command is used to build the customized resources from the kustomization.yaml file, but it will not apply these resources, if you want to apply it by the following command
```bash
kustomize build /kustomize-dep | kubectl apply -f .

# Or you can use
kubectl apply -k /kustomize-dep
```

- to delete those resources by the following
```bash
kustomzie build /kustomize-dep | kubectl delete -f .

# Or you can use
kubectl delete -k /kustomize-dep
```
