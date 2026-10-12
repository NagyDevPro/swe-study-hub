# Storage in kubernetes


## Storage in Docker(Storage Drivers and file systems)

- when you install docker it creates the following file structure under `/var/lib/docker`:

```
/aufs
/containers
/image
/volumes
```

- Docker layred architecture: 
  - Docker makes each step in the Dockerfile, each command, as a layer, each layers stacked to create a final image.
  
  - this layred architecture is the reason why docker images are so small, as layers can be resused across images, which saves space and bandwidth when pulling images from a registry.

 > **docker image has below internals: -**
 
 > **- imageID: a unique identifier for the image**
 
 > **- diffID: a unique identifier for the layer, used by hashing the contents of the layer, and it's used to check if the layer already exists on the host.**

 > **- chainID: a combination of the diffIDs of all the layers in the image, used to check if the image final state after changes.**
 
 > **- cacheID: a unique identifier for the layer, used by docker to check if the layer can be reused from the cache instead of rebuilt it again at the runtime.**




  -  when you create a container from an image, docker creates a new layer on top of the image layers, this layer is called container layer, and it's **read/write layer** (stores changes made to the container,logs,files...etc), while the image layers are **read-only** layers.


  - if you delete a container, the container layer is deleted, but the image layers are not deleted.

  - if you made any change to the container, such as delete a file or edit on a file, docker will copy this file from the image layer to the container layer, and then make the change on the container layer, this process is called **copy-on-write**.

- the componenet that is responsible for layred architecture and copy-on-write is called **storage driver**.

- Docker uses different storage drivers to manage the storage of images and containers, such as **overlay2**, **aufs**, **devicemapper**, **btrfs**...etc, **each storage driver has its own advantages and disadvantages**, and the choice of storage driver depends on the use case and the underlying file system of the host.

- by default storage drivers are based on the os system but you can manually chose the storage driver


- to create a volume on container, you can use below command
```bash
docker volume create <volume_name>


# to mount the volume on container, you can use below command

# **NOTE**:if you didn't create a volume before, docker will create a volume with the name for you
docker run -v <volume_name>:/path/in/container <image_name>

```
- there's another way of create a volume on container, which is called **bind mount**, in this case you mount to a seprate directory or file using the below command

```bash
docker run -v /path/on/host:/path/in/container <image_name>

# or you can use the newely way
docker run --mount type=bind,source=/path/on/host,target=/path/in/container <image_name>

```


## Volumes in Docker
- Volumes are handled by volume driver plugins, the default plugin is `local` which stores the data on the host filesystem `/var/lib/docker/volumes`.

- when you create a volume, you can specify the driver and the options for the volume shown in below:-
```bash
docker volume create --driver <driver_name> --opt <option_name>=<option_value> <volume_name>

```

- **container storage interface (CSI)**: is a standard for any thrid party storage provider to integrate with kubernetes, it allows kubernetes to manage the lifecycle of volumes and the storage resources in a consistent way across different storage providers.

- CSI is not only for kubernetes, but also for other container orchestration platforms, it provides a standard interface for storage providers to integrate with.

- CSI defines set of RPCs that storage providers must implement, such as `CreateVolume`, `DeleteVolume`, `AttachVolume`, `DetachVolume`...etc, and kubernetes uses these RPCs and the storage provider should implement these RPCs.



## Presistent Volumes in Kubernetes
- **Persistent Volumes(PV)** are a pool of storage resource in the cluster that can be used by multiple pods, without the need to create a new volume for each pod.


- **Persistent Volume Claim (PVC)**, is what the user creates to bound or request for a persistent volume.
  * if kubernetes didn't found a persistent volume that matches the claim, the claim will be in the pending state until a matching pv is avialable.
  * if kubernetes found 2 or more matched pv, you can choose which pv you want to bound the claim to using the `lables, and selectors`.


- to create a persistent volume claim below yaml:-
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: my-pvc
spec:
    accessModes:
        - ReadWriteOnce
    resources:
        requests:
        storage: 1Gi
```

> **Persistent Volume Claim Reclaim Policy**: is the policy that defines what happens to the persistent volume when the claim is deleted, there are 3 reclaim policies:-
> - Retain: the persistent volume is not deleted, and it's not available for other claims.
> - Delete: the persistent volume is deleted.
> - Recycle: the persistent volume data is deleted and the pv available for other claims.







































## Refrences
1. [earthly docker storage on host](https://earthly.dev/blog/docker-image-storage-on-host)