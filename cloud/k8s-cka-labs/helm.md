# Helm Charts


## Introduction

- helm charts are kubenetes objects package manager, it was used to deploy group of k8s resources within one command without the need to dig deeper to details of `deployments`, `services`,`routes`,..etc.

- Helm Chart 2 released in **2016** while the verison 3 was released on **2019** with huge features:-
  
  - in helm 2, `tiller` was used to manage the release of helm charts, `tiller` had a great issue that it was run as a `root` and that caused a security issues, helm 3 removed `tiller` and now helm 3 is a client side only tool, it uses the `k8s api` to manage the release of helm charts.

  - Helm 3 utilized **3-Way Strategic Merge** that was used to easily manage the release of helm by comparing the **current live k8s objects**, the **new helm** version and the **past helm** version, this is powerful than the old `Helm 2` as `helm 2` was comparing the **current helm** version with **the past** helm version only, which can cause issues **if the live k8s objects were changed manually**  



- when we deploy helm chart by this command
```bash
helm install <release_name> <chart_name>
```

- releases are used to manage multiple instances of the same chart but with different configuration for each chart, think of it as namespaces that we are deploying pods on.


- halm charts are maintained by helm chart provider, providers such as `Bitnami`,...etc. all those helm repositories are collected in `Artifact Hub` which you can find easily the specific chart you need.


- helm chart v3 identifies in the chart.yaml file the apiVersion, this apiVersion is to differenciate the helm v2 charts and the helm v3 charts, as helm v3 had made updates to the yaml of the chart such as adding dependancies section,...etc.



## Helm Chart Structure

- Helm chart is a directory that contains all the files needed to create a k8s application, the structure of the helm chart is as follows:

```
my-chart/
├── Chart.yaml     # The chart metadata
├── values.yaml    # The default values for the chart
├── templates/     # The template files
│   ├── deployment.yaml
│   ├── service.yaml
│   └── ingress.yaml
└── README.md      # The README file
```

## Helm Chart Components

- Helm chart is composed of the following components:

  - `Chart.yaml`: This file contains the metadata of the chart such as name, version, description, and dependencies.

  - `values.yaml`: This file contains the default values for the chart. These values can be overridden by the user when installing the chart.

  - `templates/`: This directory contains the template files that define the Kubernetes resources to be created. These templates can use Go templating syntax to dynamically generate resource definitions based on the values provided.

  - `README.md`: This file provides documentation for the chart, including usage instructions and configuration options.

## Helm Chart Commands

```bash
helm --help # to show a guide on usage of helm

helm repo --help # to show a guide on usage of helm sub command repository

helm repo update --help # to show a guide on usage of helm sub command deeper repository update

helm search <chart-name> # to search for charts in the repositories, search command requires another subcommand is that (where do you search)

helm search hub <chart-name> # to search for charts in the Artifact Hub.

helm repo add <repository-url> # to add this repository to our local helm, so it might be easier for us to install the chart

helm install <release-name> <chart-name> # to install the chart, this command will create a release of the chart

helm list # to list all the releases that we have installed in our cluster

helm uninstall <release-name> # to uninstall the release of the chart, this command will delete all the resources that were created by the chart

helm repo # is useful to add, list, remove helm commands

helm repo list # to list all the repositories that we have added to our local helm

helm repo update # to update the repositories that we have added to our local helm, this command will fetch the latest version of the charts from the repositories

helm repo remove <repository-name> # to remove the repository from our local helm

helm history <release-name> # to show the history of the release, this command will show all the revisions of the release

helm rollback <release-name> <revision-number> # to rollback the release to a specific revision, this command will delete all the resources that were created by the release and create the resources from the specified revision
```


## Helm Chart Customization
- when we install a helm chart, the command `install` insatlls the chart with deploying it with it's custom values, to deploy the helm chart with your customized values you can make the below command.

```bash
helm install <release-name> <chart-name> --set <key>=<value> # to install the chart with custom values, this command will override the default values in the values.yaml file
```
- but if we had multiple values, we can add `values.yaml` file with our custom values and use install command as below.
```bash
helm insatll --values=<name-values-file>.yaml <release-name> <chart-name> # to install the chart with custom values from a values.yaml file, this command will override the default values in the values.yaml file
```

- if we want to install the chart and customizing it's default `values.yaml` file by the below
```bash
helm pull <chart-name> --untar # to pull the chart and untar it, this command will create a directory with the chart name and untar the chart files into it

ls # to list the chart after pulling it

# after cusomizing your helm values file
helm install <release-name> <chart-name> </path-to-the-helm-directory>
```

- the above example is not recommended for production use as it's customizing the default values of the chart itself.


> chart Hooks are special files that can be used to execute commands before or after the chart is installed, upgraded, or deleted. Hooks can be used to perform tasks such as database migrations, sending notifications, or cleaning up resources. Hooks are defined in the `templates/` directory of the chart and can be triggered by specific events such as `pre-install`, `post-install`, `pre-upgrade`, `post-upgrade`, `pre-delete`, and `post-delete`.
>
> 

