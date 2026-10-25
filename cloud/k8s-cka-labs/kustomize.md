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


## Tranformations

### Common Transformers
- Kustomize provides a set of common transformers that can be used to modify the base resources. These transformers include:

  - `namePrefix`: This transformer adds a prefix to the names of the resources.

  - `nameSuffix`: This transformer adds a suffix to the names of the resources.

  - `namespace`: This transformer sets the namespace for the resources.

  - `labels`: This transformer adds labels to the resources.

  - `annotations`: This transformer adds annotations to the resources.

example to add in `Kustomize.yaml`:-
```yaml
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization

namePrefix: my-prefix-
nameSuffix: -my-suffix
namespace: my-namespace
commonLabels:
  app: my-app
commonAnnotations:
    description: my-description
```

- image transformer: This transformer allows you to modify the image names and tags for the resources. You can specify the new image name and tag in the kustomization.yaml file.

example to add in `Kustomize.yaml`:-
```yaml
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization
images:
  - name: my-image # this is the value of the image not the name of the container or the name of the image, it's the value of image!
    newName: my-new-image 
    newTag: my-new-tag
```



## Patching Resources
- Patching is a way to customize some fileds on specific resource unlike the common tranformers that applies on all customize feilds, patches are specific to a specific resource

- Patches have two types.

1. Json 6902 patch, in this patching you are choose the **Target** and specify the **Patch** file that contains the changes you want to make to the target resource. The patch file is written in JSON format and follows the JSON Patch standard (RFC 6902).

see below `Kustomization.yaml`
```yaml
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization
patches:
    - target:
        kind: Deployment
        name: my-deployment
      patch: |-
        - op: replace
          path: /spec/replicas
          value: 3
```


2. strategic merge patch, in this type you do not need to write the targe and specific about the operation, you just write the metadata and kind of the resource you want to edit and write the customization, and kustomize will merge that edits

see below `Kustomization.yaml`
```yaml
apiVersion: kustomize.config.k8s.io/v1beta1
kind: Kustomization
patches:
# you must provide the kind and the resource name in patch section in order to let kustomize know which resource you want to edit
  - patch: |-
      apiVersion: apps/v1
      kind: Deployment 
      metadata:
        name: my-deployment
      spec:
        replicas: 3
```


- patches can be in a single file or in multiple files, and you can specify the path to the patch files in the kustomization.yaml file.



### Notes on patching resources

- in strategic merge patch, you can use `null` value to remove a field from the resource, for example below
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-deployment
spec:
  replicas: null # this will remove the replicas field from the deployment resource
```
- in json 6902 patch, to replace a field that is list, you need to specify the index of the item in the list that you want to replace, for example below
```yaml
- op: replace
  path: /spec/replicas/0
    value: 3 # this will replace the first item in the replicas list with the value 3
```

- in json 6902 patch, to add a new item to a list, you can also use `-` to add the new item to the end of the list, for example below
```yaml
- op: add
  path: /spec/template/spec/-
  value:
    name: nginx
    image: nginx
```

- in strategic merge patch, to remove an item from a list by the following example.
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-deployment
spec:
  template:
    spec:
      containers:
      # to remove the busybox container from the list of containers, you can use the following patch
      containers:
        - name: busybox
          $patch: delete

```









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

