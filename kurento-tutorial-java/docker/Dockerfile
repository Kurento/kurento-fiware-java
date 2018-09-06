FROM kurento/kurento-media-server:6.7.2-xenial

MAINTAINER Guiomar Tuñón <guiomar.tunon@gmail.com>

RUN apt-get update \
 && apt-get install -y \
        kms-platedetector \
 && apt-get clean && rm -rf /var/lib/apt/lists/*


