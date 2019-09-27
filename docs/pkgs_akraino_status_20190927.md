## Akraino packages porting summary

* Total repos: 51
* Ported repos: 43
  * Notes: Some repo includes multiple packages, the actual ported recipes (spec -> bb): 52
* Not proper for porting: 8 

## Akraino packages porting details

| Repo num|Repo name|Recipe num|Recipe name|Comments|
|---------|---------|----------|-----------|--------|
|1  |[access-management][1]|1|access-management||
|1  |[access-management][1]|2|config-encoder-macros||
|2  |[ansible-role-ntp][2]|3|ansible-role-ntp||
|3  |[build-tools][3]||Not-Ported|No spec file, this includes python and shell scripts, used to generate CentOS base REC image|
|4  |[caas-cpupooler][4]|4|caas-cpupooler|docker container: alpine and centos|
|5  |[caas-danm][5]|5|caas-danm|docker container: alpine and centos|
|5  |[caas-danm][5]|6|caas-flannel|docker container: alpine and centos|
|5  |[caas-danm][5]|7|caas-hyperdanm|docker container: alpine and centos|
|5  |[caas-danm][5]|8|caas-sriovdp|docker container: alpine and centos|
|6  |[caas-etcd][6]|9|caas-etcd|docker container: alpine|
|7  |[caas-helm][7]|10|caas-chartrep|docker container: alpine and centos|
|7  |[caas-helm][7]|11|caas-helm|docker container: alpine and centos|
|8  |[caas-install][8]|12|caas-install||
|9  |[caas-kubedns][9]|13|caas-kubedns|docker container: alpine|
|10 |[caas-kubernetes][10]|14|caas-kubernetes|docker container: centos|
|11 |[caas-lcm][11]|15|caas-lcm||
|12 |[caas-logging][12]|16|caas-elasticsearch|docker container: alpine and centos|
|12 |[caas-logging][12]|17|caas-fluentd|docker container: alpine and centos|
|13 |[caas-metrics][13]|18|caas-custom-metrics|docker container: alpine|
|13 |[caas-metrics][13]|19|caas-metrics-server|docker container: alpine|
|13 |[caas-metrics][13]|20|caas-prometheus|docker container: alpine|
|14 |[caas-registry][14]|21|caas-registry|docker container: alpine and centos|
|14 |[caas-registry][14]|22|caas-swift|docker container: alpine and centos|
|15 |[caas-security][15]|23|caas-security||
|16 |[cloudtaf][16]||Not-Ported|No spec file, this is the test framework for akraino which builds out a very big fedora based docker container (1.3G) and run tests in the container.|
|17 |[cm-plugins][17]|24|cm-plugins|||
|18 |[config-manager][18]|25|config-manager||
|19 |[distributed-state-server][19]|26|distributed-state-server||
|20 |[hostcli][20]|27|hostcli||
|21 |[hw-detector][21]|28|hw-detector||
|22 |[image-provision][22]|29|image-provision||
|23 |[infra-ansible][23]|30|infra-ansible||
|24 |[ipa-deployer][24]||Not-Ported|This will download the CentOS-7 qcow2 image and generate an ISO image based on it, then add the ISO image into the rpm package|
|25 |[ironic-virtmedia-driver][25]|31|python-ironic-virtmedia-driver||
|26 |[ironicclient][26]|32|python-ironicclient||
|27 |[ironic][27]|33|python-ironic||
|28 |[lockcli][28]|34|lockcli||
|29 |[manifest][29]||Not-Ported|config and record build version id in /etc/product-release, which may not be needed since Yocto use /etc/os-release for that info.|
|30 |[monitoring][30]|35|monitoring||
|31 |[openstack-ansible-galera_client][31]|36|openstack-ansible-galera-client||
|32 |[openstack-ansible-galera_server][32]|37|openstack-ansible-galera-server||
|33 |[openstack-ansible-haproxy_server][33]|38|openstack-ansible-haproxy-server||
|34 |[openstack-ansible-memcached_server][34]|39|openstack-ansible-memcached-server||
|35 |[openstack-ansible-openstack_openrc][35]|40|openstack-ansible-openstack-openrc||
|36 |[openstack-ansible-os_ironic][36]|41|openstack-ansible-os-ironic||
|37 |[openstack-ansible-os_keystone][37]|42|openstack-ansible-os-keystone||
|38 |[openstack-ansible-plugins][38]|43|openstack-ansible-plugins||
|39 |[openstack-ansible-rabbitmq_server][39]|44|openstack-ansible-rabbitmq-server||
|40 |[openstack-ansible-rsyslog_client][40]|45|openstack-ansible-rsyslog-client||
|41 |[openstack-ansible][41]|46|openstack-ansible||
|42 |[os-net-config][42]|47|os-net-config||
|43 |[python-ilorest-library][43]|48|python-ilorest-library||
|44 |[python-peewee][44]|49|python-peewee||
|45 |[rec][45]||Not-Ported|No spec file, this includes workflow scripts and instruction docs for REC blueprint.|
|46 |[remote-installer][46]||Not-Ported|Used to remotely install CentOS based system|
|47 |[rpmbuilder][47]||Not-Ported|Used to build CentOS based rpm pakcages|
|48 |[start-menu][48]|50|start-menu||
|49 |[storage][49]|51|storage||
|50 |[validation][50]||Not-Ported|No spec file, this includes many scripts and docker containers (golang, maven, tomcat, alpine, mariadb, etc) for akraino validation.|
|51 |[yarf][51]|52|python-yarf||

[1]: https://gerrit.akraino.org/r/ta/access-management
[2]: https://gerrit.akraino.org/r/ta/ansible-role-ntp
[3]: https://gerrit.akraino.org/r/ta/build-tools
[4]: https://gerrit.akraino.org/r/ta/caas-cpupooler
[5]: https://gerrit.akraino.org/r/ta/caas-danm
[6]: https://gerrit.akraino.org/r/ta/caas-etcd
[7]: https://gerrit.akraino.org/r/ta/caas-helm
[8]: https://gerrit.akraino.org/r/ta/caas-install
[9]: https://gerrit.akraino.org/r/ta/caas-kubedns
[10]: https://gerrit.akraino.org/r/ta/caas-kubernetes
[11]: https://gerrit.akraino.org/r/ta/caas-lcm
[12]: https://gerrit.akraino.org/r/ta/caas-logging
[13]: https://gerrit.akraino.org/r/ta/caas-metrics
[14]: https://gerrit.akraino.org/r/ta/caas-registry
[15]: https://gerrit.akraino.org/r/ta/caas-security
[16]: https://gerrit.akraino.org/r/ta/cloudtaf
[17]: https://gerrit.akraino.org/r/ta/cm-plugins
[18]: https://gerrit.akraino.org/r/ta/config-manager
[19]: https://gerrit.akraino.org/r/ta/distributed-state-server
[20]: https://gerrit.akraino.org/r/ta/hostcli
[21]: https://gerrit.akraino.org/r/ta/hw-detector
[22]: https://gerrit.akraino.org/r/ta/image-provision
[23]: https://gerrit.akraino.org/r/ta/infra-ansible
[24]: https://gerrit.akraino.org/r/ta/ipa-deployer
[25]: https://gerrit.akraino.org/r/ta/ironic
[26]: https://gerrit.akraino.org/r/ta/ironic-virtmedia-driver
[27]: https://gerrit.akraino.org/r/ta/ironicclient
[28]: https://gerrit.akraino.org/r/ta/lockcli
[29]: https://gerrit.akraino.org/r/ta/manifest
[30]: https://gerrit.akraino.org/r/ta/monitoring
[31]: https://gerrit.akraino.org/r/ta/openstack-ansible
[32]: https://gerrit.akraino.org/r/ta/openstack-ansible-galera_client
[33]: https://gerrit.akraino.org/r/ta/openstack-ansible-galera_server
[34]: https://gerrit.akraino.org/r/ta/openstack-ansible-haproxy_server
[35]: https://gerrit.akraino.org/r/ta/openstack-ansible-memcached_server
[36]: https://gerrit.akraino.org/r/ta/openstack-ansible-openstack_openrc
[37]: https://gerrit.akraino.org/r/ta/openstack-ansible-os_ironic
[38]: https://gerrit.akraino.org/r/ta/openstack-ansible-os_keystone
[39]: https://gerrit.akraino.org/r/ta/openstack-ansible-plugins
[40]: https://gerrit.akraino.org/r/ta/openstack-ansible-rabbitmq_server
[41]: https://gerrit.akraino.org/r/ta/openstack-ansible-rsyslog_client
[42]: https://gerrit.akraino.org/r/ta/os-net-config
[43]: https://gerrit.akraino.org/r/ta/python-ilorest-library
[44]: https://gerrit.akraino.org/r/ta/python-peewee
[45]: https://gerrit.akraino.org/r/rec
[46]: https://gerrit.akraino.org/r/ta/remote-installer
[47]: https://gerrit.akraino.org/r/ta/rpmbuilder
[48]: https://gerrit.akraino.org/r/ta/start-menu
[49]: https://gerrit.akraino.org/r/ta/storage
[50]: https://gerrit.akraino.org/r/validation
[51]: https://gerrit.akraino.org/r/ta/yarf
